package random.block;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author 启梦
 */
@Mod(Main.MOD_NAME)
public final class Main {
    public static final String MOD_NAME = "random_block";

    public Main() {
        ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
