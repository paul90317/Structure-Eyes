package com.paul90317.structureeyes.common;

import com.mojang.datafixers.util.Pair;
import com.paul90317.structureeyes.MainMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
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
    final private TagKey<ConfiguredStructureFeature<?,?>> structureTagKey;
    final private ResourceKey<ConfiguredStructureFeature<?,?>> structureResourceKey;
    final boolean isHolder;
    public StructureEye(TagKey<ConfiguredStructureFeature<?,?>> structure) {
        super(new Properties().stacksTo(64).tab(MainMod.MOD_TAB));
        isHolder=false;
        structureTagKey=structure;
        structureResourceKey=null;
    }
    public StructureEye(ResourceKey<ConfiguredStructureFeature<?,?>> structure) {
        super(new Properties().stacksTo(64).tab(MainMod.MOD_TAB));
        isHolder=true;
        structureTagKey=null;
        structureResourceKey=structure;
    }
    private BlockPos locateStructure(ServerLevel serverLevel, BlockPos blockPos) {
        Registry<ConfiguredStructureFeature<?,?>> registry = serverLevel.registryAccess().registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY);
        Holder<ConfiguredStructureFeature<?,?>> structureHolder=registry.getHolder(structureResourceKey).get();
        HolderSet<ConfiguredStructureFeature<?,?>> structureHolderSet=HolderSet.direct(structureHolder);
        Pair<BlockPos, Holder<ConfiguredStructureFeature<?,?>>> pair = serverLevel.getChunkSource().getGenerator().findNearestMapFeature(serverLevel, structureHolderSet, blockPos, 100, false);
        if (pair == null) {
            return null;
        } else {
            return pair.getFirst();
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (world instanceof ServerLevel serverlevel){
            BlockPos blockpos = isHolder? locateStructure(serverlevel,new BlockPos(player.getPosition(0.5f)))
                    :serverlevel
                    .findNearestMapFeature(structureTagKey, player.blockPosition(), 100, false);
            if (blockpos != null) {
                EyeOfEnder eyeofender = new EyeOfEnder(world, player.getX(), player.getY(0.5D), player.getZ());
                eyeofender.setItem(itemstack);
                eyeofender.signalTo(blockpos);
                world.addFreshEntity(eyeofender);
                world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                world.levelEvent((Player)null, 1003, player.blockPosition(), 0);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                player.swing(hand, true);
                return InteractionResultHolder.success(itemstack);
            }
        }
        return InteractionResultHolder.consume(itemstack);
    }
}