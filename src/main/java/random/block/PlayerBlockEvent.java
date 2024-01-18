package random.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 启梦
 */
@Mod.EventBusSubscriber
public class PlayerBlockEvent {
    public static boolean isNotValid(PlayerEntity player) { //放置是否合法
        return player.getCooldowns().isOnCooldown(ItemInit.PLAYER_BREAK_NORMAL_ITEM.get());
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void destroy(BlockEvent.BreakEvent event) { //针对玩家
        PlayerEntity player = event.getPlayer();
        if (isNotValid(player)) {
            return;
        }
        BlockPos pos = event.getPos();
        World world = player.level;
        if (!player.isCreative()) {
            BlockState state = event.getState();
            Block block = state.getBlock();
            block.playerDestroy(world, player, pos, state,
                    world.getBlockEntity(pos), player.getUseItem());
            block.popExperience((ServerWorld) world, pos, event.getExpToDrop());
            player.getMainHandItem().mineBlock(world, state, pos, player);
        }
        world.setBlock(pos,
                Util.BLOCKS.get(ThreadLocalRandom.current().nextInt(Util.BLOCKS.size()))
                        .defaultBlockState(),
                Constants.BlockFlags.RERENDER_MAIN_THREAD);
        player.addEffect(new EffectInstance(
                Util.EFFECTS.get(ThreadLocalRandom.current().nextInt(Util.EFFECTS.size())),
                60 * 20));
        event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void setBlock(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof PlayerEntity) || event.getEntity().level.isClientSide) {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntity();
        if (isNotValid(player)) {
            return;
        }
        Util.ENTITY_TYPES.get(ThreadLocalRandom.current().nextInt(Util.ENTITY_TYPES.size()))
                .spawn((ServerWorld) player.level, null, player,
                        event.getPos(), SpawnReason.COMMAND, true, true);
    }
}
