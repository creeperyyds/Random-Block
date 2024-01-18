package random.block;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * @author 启梦
 */
public class BreakNormalItem extends Item {
    public static final int HALF_MINUTE = 20 * 30;
    public BreakNormalItem() {
        super(new Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(ItemInit.PLAYER_BREAK_NORMAL_ITEM.get(),
                player.abilities.instabuild ? HALF_MINUTE : HALF_MINUTE - this.getDamage(stack));
        stack.hurtAndBreak(1, player, playerEntity -> {
            playerEntity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return ActionResult.sidedSuccess(stack,
                world.isClientSide);
    }

    @Override
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment instanceof UnbreakingEnchantment;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 400;
    }
}
