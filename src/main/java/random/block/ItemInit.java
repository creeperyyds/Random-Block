package random.block;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author 启梦
 */
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_NAME);
    public static final RegistryObject<Item> PLAYER_BREAK_NORMAL_ITEM = ITEMS.register("break_normal", BreakNormalItem::new);
}
