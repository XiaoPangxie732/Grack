package cn.maxpixel.mods.grack.server;

import cn.maxpixel.mods.grack.GrackMod;
import cn.maxpixel.mods.grack.GrackScript;
import com.google.common.collect.ImmutableMap;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.resource.ListenerKey;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

public class ServerScriptLibrary implements PreparableReloadListener {
    public static final Identifier KEY = GrackMod.rl("server_script_library");
    public static final ListenerKey<ServerScriptLibrary> LISTENER_KEY = ListenerKey.create(KEY);
    public static final ResourceKey<Registry<GrackScript>> SCRIPT_REGISTRY_KEY = ResourceKey.createRegistryKey(GrackMod.rl("script"));
    private static final FileToIdConverter LISTER = new FileToIdConverter(Registries.elementsDirPath(SCRIPT_REGISTRY_KEY), ".groovy");
    private static final CompilerConfiguration COMPILER_CONFIGURATION = new CompilerConfiguration() {{
        setScriptBaseClass(GrackScript.class.getName());
    }};
    private static final GroovyShell SHELL = new GroovyShell(ServerScriptLibrary.class.getClassLoader(), COMPILER_CONFIGURATION);

    private volatile ImmutableMap<Identifier, GrackScript> scripts = ImmutableMap.of();

    public Optional<GrackScript> getScript(Identifier id) {
        return Optional.ofNullable(scripts.get(id));
    }

    public ImmutableMap<Identifier, GrackScript> getScripts() {
        return scripts;
    }

    @Override
    public @NonNull CompletableFuture<Void> reload(@NonNull SharedState currentReload, @NonNull Executor taskExecutor, @NonNull PreparationBarrier preparationBarrier, @NonNull Executor reloadExecutor) {
        GrackMod.LOGGER.info("Reloading Groovy scripts");
        ResourceManager manager = currentReload.resourceManager();
        CompletableFuture<Object2ObjectOpenHashMap<Identifier, CompletableFuture<GrackScript>>> scripts = CompletableFuture.supplyAsync(
                () -> LISTER.listMatchingResources(manager), taskExecutor
        ).thenCompose(scriptsToLoad -> {
            Object2ObjectOpenHashMap<Identifier, CompletableFuture<GrackScript>> result = new Object2ObjectOpenHashMap<>();
            for (Map.Entry<Identifier, Resource> entry : scriptsToLoad.entrySet()) {
                Identifier resourceId = entry.getKey();
                Identifier id = LISTER.fileToId(resourceId);
                result.put(id, CompletableFuture.supplyAsync(() -> {
                    try (var reader = entry.getValue().openAsReader()) {
                        return (GrackScript) SHELL.parse(reader, new Binding());
                    } catch (IOException e) {
                        throw new CompletionException(e);
                    }
                }, taskExecutor));
            }
            return CompletableFuture.allOf(result.values().toArray(new CompletableFuture[0]))
                    .handle((ignore, throwable) -> result);
        });
        return scripts.thenCompose(preparationBarrier::wait)
                .thenAcceptAsync(data -> {
                    ImmutableMap.Builder<Identifier, GrackScript> newScripts = ImmutableMap.builder();
                    data.forEach((id, future) -> future.handle((script, throwable) -> {
                        if (throwable != null) {
                            GrackMod.LOGGER.error("Failed to load function {}", id, throwable);
                        } else {
                            newScripts.put(id, script);
                        }
                        return null;
                    }).join());
                    this.scripts = newScripts.build();
                    GrackMod.LOGGER.info("Loaded {} Groovy scripts", this.scripts.size());
                }, reloadExecutor);
    }
}