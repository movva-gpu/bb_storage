package net.oftl.bb_storage.entity.villagers;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oftl.bb_storage.BBStorageMod;
import net.oftl.bb_storage.blocks.BBBlocks;

import java.lang.reflect.InvocationTargetException;

public class BBVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, BBStorageMod.MOD_ID);

    public static final RegistryObject<PoiType> GEM_POWERING_STATION_POI = POI_TYPES.register("gem_powering_station",
            () -> new PoiType(ImmutableSet.copyOf(BBBlocks.GEM_POWERING_STATION.get().getStateDefinition().getPossibleStates()),
                    1,1));

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, BBStorageMod.MOD_ID);

    public static final RegistryObject<VillagerProfession> GEM_POWERER = VILLAGER_PROFESSIONS.register("gem_powerer",
        () -> new VillagerProfession("gem_powerer", x -> x.get() == GEM_POWERING_STATION_POI.get(),
                x -> x.get() == GEM_POWERING_STATION_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BEACON_ACTIVATE));

    public static void registerPOIs() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke(null, GEM_POWERING_STATION_POI.get());
        } catch (InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
