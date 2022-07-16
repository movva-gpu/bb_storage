package net.oftl.bb_storage.event;

import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.oftl.bb_storage.BBStorageMod;
import net.oftl.bb_storage.recipes.GemPoweringStationRecipe;

@Mod.EventBusSubscriber(modid = BBStorageMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BBEventBusEvents {

//  @SubscribeEvent
//  public static void registerRecipeTypes(final NewRegistryEvent event) {
//      Registry.register(Registry.RECIPE_TYPE, GemPoweringStationRecipe.Type.ID, GemPoweringStationRecipe.Type.INSTANCE);
//   }

}
