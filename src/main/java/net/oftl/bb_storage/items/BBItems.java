package net.oftl.bb_storage.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oftl.bb_storage.BBStorageMod;
import net.oftl.bb_storage.items.custom.BatteryItem;

public class BBItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BBStorageMod.MOD_ID);

    public static final RegistryObject<Item> POWERED_AMETHYST_SHARD = ITEMS.register("powered_amethyst_shard", () ->
        new Item(new Item.Properties().tab(BBStorageMod.BB_RESOURCES_TAB)));
    public static final RegistryObject<Item> POWERED_EMERALD = ITEMS.register("powered_emerald", () ->
        new Item(new Item.Properties().tab(BBStorageMod.BB_RESOURCES_TAB)));

    public static final RegistryObject<Item> SMALL_BATTERY = ITEMS.register("small_battery", () ->
            new BatteryItem(new Item.Properties().tab(BBStorageMod.BB_ENERGY_TAB),
                    500, 5, 15, 500));

//    public static final RegistryObject<Item> FLOPPY_DISK_3M =
//            ITEMS.register("floppy_disk_3M", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
