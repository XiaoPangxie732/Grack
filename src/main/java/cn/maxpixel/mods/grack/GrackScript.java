package cn.maxpixel.mods.grack;

import cn.maxpixel.mods.grack.attachment.GrackPlayerData;
import com.mojang.brigadier.context.CommandContext;
import groovy.lang.Script;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;

import java.util.Optional;

public abstract class GrackScript extends Script {
    public static boolean isBetween(BlockPos pos, BlockPos from, BlockPos to) {
        int minX = Math.min(from.getX(), to.getX());
        int maxX = Math.max(from.getX(), to.getX());
        int minY = Math.min(from.getY(), to.getY());
        int maxY = Math.max(from.getY(), to.getY());
        int minZ = Math.min(from.getZ(), to.getZ());
        int maxZ = Math.max(from.getZ(), to.getZ());
        return pos.getX() >= minX && pos.getX() <= maxX &&
                pos.getY() >= minY && pos.getY() <= maxY &&
                pos.getZ() >= minZ && pos.getZ() <= maxZ;
    }

    public GrackPlayerData getPlayerData() {
        var context = (CommandContext<CommandSourceStack>) getBinding().getProperty("context");
        var source = context.getSource();
        if (source.isPlayer()) {
            return source.getPlayer().getData(DataAttachmentRegistry.PLAYER_DATA);
        }
        return null;
    }

    public static BlockPos ofBlockPos(Optional<int[]> arr) {
        return arr.map(ints -> new BlockPos(ints[0], ints[1], ints[2])).orElseThrow();
    }
}