package com.paul90317.structureeyes;

import com.paul90317.structureeyes.core.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MainMod.MOD_ID)
public class MainMod {
    public static final String MOD_ID = "structureeyes";
    public static final CreativeModeTab MOD_TAB=new CreativeModeTab(MOD_ID){
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon(){
            return new ItemStack(ModItems.VillageEye.get());
        }
    };

    public MainMod() {
        IEventBus bus=FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
