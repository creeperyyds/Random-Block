package random.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 启梦
 */
public class Util {
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<EntityType<?>> ENTITY_TYPES = new ArrayList<>();
    public static final ArrayList<Effect> EFFECTS = new ArrayList<>();
    @SuppressWarnings("unchecked")
    private static <T> void add(ArrayList<T> list, Field field) {
        try {
            list.add((T) field.get(null));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        Arrays.stream(Blocks.class.getFields()).forEach(blockField -> add(BLOCKS, blockField));
        Arrays.stream(EntityType.class.getFields()).forEach(entityTypeField -> {
            if (entityTypeField.getType() == EntityType.class) {
                add(ENTITY_TYPES, entityTypeField);
            }
        });
        Arrays.stream(Effects.class.getFields()).forEach(effectField -> add(EFFECTS, effectField));
        EFFECTS.remove(Effects.DAMAGE_RESISTANCE);
    }
}
