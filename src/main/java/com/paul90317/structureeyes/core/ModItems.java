package com.paul90317.structureeyes.core;

import com.paul90317.structureeyes.MainMod;
import com.paul90317.structureeyes.common.StructureEye;
import net.minecraft.tags.ConfiguredStructureTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MainMod.MOD_ID);
    public static final RegistryObject<Item> VillageEye = ITEMS.register("village_eye",
            () -> new StructureEye(ConfiguredStructureTags.VILLAGE));
    /*public static final RegistryObject<Item> EndCityEye = ITEMS.register("end_city_eye",
            () -> new StructureEye(ConfiguredStructureTags.));
    public static final RegistryObject<Item> FortressEye = ITEMS.register("fortress_eye",
            () -> new net.hellopaul.structureeyes.item.StructureEye(Structure.FORTRESS));
    public static final RegistryObject<Item> BastionRemnantEye = ITEMS.register("bastion_remnant_eye",
            () -> new net.hellopaul.structureeyes.item.StructureEye(Structure.BASTION_REMNANT));*/
}