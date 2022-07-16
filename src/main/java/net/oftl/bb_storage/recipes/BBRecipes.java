package net.oftl.bb_storage.recipes;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oftl.bb_storage.BBStorageMod;
import org.checkerframework.checker.signature.qual.Identifier;

import java.sql.ResultSet;

public class BBRecipes {
//    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
//            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BBStorageMod.MOD_ID);
//
//    public static final RegistryObject<GemPoweringStationRecipe.Serializer> GEM_POWERING_SERIALIZER =
//            SERIALIZERS.register("gem_powering_station_serializer", () -> GemPoweringStationRecipe.Serializer.INSTANCE);

//    public static void register(IEventBus eventBus) {
//        SERIALIZERS.register(eventBus);
//
//    }

    public static void registerRecipes() {
        ForgeRegistries.RECIPE_SERIALIZERS.register(GemPoweringStationRecipe.Serializer.ID,
                GemPoweringStationRecipe.Serializer.INSTANCE);
        ForgeRegistries.RECIPE_TYPES.register(GemPoweringStationRecipe.Type.ID,
                GemPoweringStationRecipe.Type.INSTANCE);
    }
}
