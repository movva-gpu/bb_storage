package net.oftl.bb_storage.entity.paintings;

import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oftl.bb_storage.BBStorageMod;

public class BBPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, BBStorageMod.MOD_ID);

    public static final RegistryObject<PaintingVariant> EVIL_DONUT = PAINTINGS.register("evil_donut",
            () -> new PaintingVariant(16,16));
    public static final RegistryObject<PaintingVariant> ALIVE_DONUT = PAINTINGS.register("alive_donut",
            () -> new PaintingVariant(32,16));
    public static final RegistryObject<PaintingVariant> BIG_DONUT = PAINTINGS.register("big_donut",
            () -> new PaintingVariant(32,32));
    public static final RegistryObject<PaintingVariant> POTATO = PAINTINGS.register("potato",
            () -> new PaintingVariant(16,16));

    public static void register(IEventBus eventBus) {
        PAINTINGS.register(eventBus);
    }
}
