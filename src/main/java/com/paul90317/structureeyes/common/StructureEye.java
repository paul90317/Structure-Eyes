package com.paul90317.structureeyes.common;

import com.paul90317.structureeyes.MainMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
public class StructureEye extends Item {
    final private TagKey<ConfiguredStructureFeature<?,?>> structure;
    public StructureEye(TagKey<ConfiguredStructureFeature<?,?>> _structure) {
        super(new Properties().stacksTo(64).tab(MainMod.MOD_TAB));
        structure=_structure;
    }

    /*public InteractionResult useOn(UseOnContext p_41182_) {
        Level level = p_41182_.getLevel();
        BlockPos blockpos = p_41182_.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(Blocks.END_PORTAL_FRAME) && !blockstate.getValue(EndPortalFrameBlock.HAS_EYE)) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                BlockState blockstate1 = blockstate.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(true));
                Block.pushEntitiesUp(blockstate, blockstate1, level, blockpos);
                level.setBlock(blockpos, blockstate1, 2);
                level.updateNeighbourForOutputSignal(blockpos, Blocks.END_PORTAL_FRAME);
                p_41182_.getItemInHand().shrink(1);
                level.levelEvent(1503, blockpos, 0);
                BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = EndPortalFrameBlock.getOrCreatePortalShape().find(level, blockpos);
                if (blockpattern$blockpatternmatch != null) {
                    BlockPos blockpos1 = blockpattern$blockpatternmatch.getFrontTopLeft().offset(-3, 0, -3);

                    for(int i = 0; i < 3; ++i) {
                        for(int j = 0; j < 3; ++j) {
                            level.setBlock(blockpos1.offset(i, 0, j), Blocks.END_PORTAL.defaultBlockState(), 2);
                        }
                    }

                    level.globalLevelEvent(1038, blockpos1.offset(1, 0, 1), 0);
                }

                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.PASS;
        }
    }*/

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handItem) {
        ItemStack itemstack = player.getItemInHand(handItem);
        /*HitResult hitresult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
        if (hitresult.getType() == HitResult.Type.BLOCK && world.getBlockState(((BlockHitResult)hitresult).getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
            return InteractionResultHolder.pass(itemstack);
        } else {*/
            player.startUsingItem(handItem);
            if (world instanceof ServerLevel serverlevel) {
                BlockPos blockpos = serverlevel.findNearestMapFeature(structure, player.blockPosition(), 100, false);
                if (blockpos != null) {
                    EyeOfEnder eyeofender = new EyeOfEnder(world, player.getX(), player.getY(0.5D), player.getZ());
                    eyeofender.setItem(itemstack);
                    eyeofender.signalTo(blockpos);
                    world.addFreshEntity(eyeofender);
                    /*if (player instanceof ServerPlayer) {
                        CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)player, blockpos);
                    }*/

                    world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                    world.levelEvent((Player)null, 1003, player.blockPosition(), 0);
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    player.swing(handItem, true);
                    return InteractionResultHolder.success(itemstack);
                }
            }

            return InteractionResultHolder.consume(itemstack);
        //}
    }
}