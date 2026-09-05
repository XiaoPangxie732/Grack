package cn.maxpixel.mods.grack.attachment;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;

public class GrackPlayerData implements ValueIOSerializable {
    private final Object2ObjectOpenHashMap<String, Object> data = new Object2ObjectOpenHashMap<>();

    @Override
    public void serialize(ValueOutput output) {
        data.forEach((key, value) -> {
            if (value instanceof Long l) output.putLong(key + "§long", l);
            else if (value instanceof Integer i) output.putInt(key + "§int", i);
            else if (value instanceof Boolean b) output.putBoolean(key + "§boolean", b);
            else if (value instanceof String s) output.putString(key + "§String", s);
            else if (value instanceof BlockPos bp) output.store(key + "§BlockPos", BlockPos.CODEC, bp);
        });
    }

    @Override
    public void deserialize(ValueInput input) {
        for (String keyType : input.keySet()) {
            String[] sa = keyType.split("§");
            String key = sa[0];
            String type = sa[1];
            switch (type) {
                case "long" -> data.put(key, input.getLong(keyType).orElseThrow());
                case "int" -> data.put(key, input.getInt(keyType).orElseThrow());
                case "boolean" -> data.put(key, input.getBooleanOr(keyType, false));
                case "String" -> data.put(key, input.getString(keyType).orElseThrow());
                case "BlockPos" -> data.put(key, input.read(keyType, BlockPos.CODEC).orElseThrow());
            }
        }
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public boolean has(String key) {
        return data.containsKey(key);
    }
}
