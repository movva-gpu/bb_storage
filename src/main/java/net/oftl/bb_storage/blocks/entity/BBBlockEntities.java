package net.oftl.bb_storage.blocks.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oftl.bb_storage.BBStorageMod;
import net.oftl.bb_storage.blocks.BBBlocks;
import net.oftl.bb_storage.blocks.entity.custom.GemPoweringStationBlockEntity;

public class BBBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BBStorageMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<GemPoweringStationBlockEntity>> GEM_POWERING_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("gem_cutting_station_block_entity", () ->
                    BlockEntityType.Builder.of(GemPoweringStationBlockEntity::new,
                            BBBlocks.GEM_POWERING_STATION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
