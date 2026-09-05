package cn.maxpixel.mods.grack;

import cn.maxpixel.mods.grack.attachment.GrackPlayerData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DataAttachmentRegistry {
    static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GrackMod.MODID);

    public static final Supplier<AttachmentType<GrackPlayerData>> PLAYER_DATA = ATTACHMENT_TYPES.register(
            "player_data", () -> AttachmentType.serializable(GrackPlayerData::new).build());
}
