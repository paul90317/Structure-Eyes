package com.paul90317.structureeyes.core;

import com.paul90317.structureeyes.MainMod;
import com.paul90317.structureeyes.common.StructureEye;
import net.minecraft.tags.ConfiguredStructureTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MainMod.MOD_ID);
    public static final RegistryObject<Item> VillageEye,EndCityEye,FortressEye,BastionRemnantEye;
    static {
        VillageEye = ITEMS.register("village_eye",
                () -> new StructureEye(ConfiguredStructureTags.VILLAGE));
        EndCityEye = ITEMS.register("end_city_eye",
                () -> new StructureEye(BuiltinStructures.END_CITY));
        FortressEye = ITEMS.register("fortress_eye",
                () -> new StructureEye(BuiltinStructures.FORTRESS));
        BastionRemnantEye = ITEMS.register("bastion_remnant_eye",
                () -> new StructureEye(BuiltinStructures.BASTION_REMNANT));
    }
}