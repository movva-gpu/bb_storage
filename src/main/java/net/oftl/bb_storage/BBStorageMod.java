package net.oftl.bb_storage;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.oftl.bb_storage.api.item.groups.BBCreativeModeTab;
import net.oftl.bb_storage.blocks.BBBlocks;
import net.oftl.bb_storage.blocks.entity.BBBlockEntities;
import net.oftl.bb_storage.entity.villagers.BBVillagers;
import net.oftl.bb_storage.items.BBItems;
import net.oftl.bb_storage.entity.paintings.BBPaintings;
import net.oftl.bb_storage.recipes.BBRecipes;
import net.oftl.bb_storage.screen.BBMenuTypes;
import net.oftl.bb_storage.screen.GemPoweringStationScreen;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BBStorageMod.MOD_ID)
public class BBStorageMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "bb_storage";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab BB_RESOURCES_TAB = new BBCreativeModeTab("bb_resources_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BBItems.POWERED_AMETHYST_SHARD.get());
        }
    };
    public static final CreativeModeTab BB_DECORATION_TAB = new BBCreativeModeTab("bb_decorations_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BBBlocks.POWERED_AMETHYST_LAMP.get());
        }
    };
    public static final CreativeModeTab BB_STATIONS_TAB = new BBCreativeModeTab("bb_stations_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BBBlocks.GEM_POWERING_STATION.get());
        }
    };
    public static final CreativeModeTab BB_ENERGY_TAB = new BBCreativeModeTab("bb_energy_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BBItems.SMALL_BATTERY.get());
        }
    };

    public BBStorageMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        BBItems.register(modEventBus);
        BBBlocks.register(modEventBus);

        BBVillagers.register(modEventBus);
        BBPaintings.register(modEventBus);
        BBBlockEntities.register(modEventBus);

        BBMenuTypes.register(modEventBus);
//        BBRecipes.register(modEventBus);

        BBRecipes.registerRecipes();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
           BBVillagers.registerPOIs();
        });
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(BBBlocks.GEM_POWERING_STATION.get(), RenderType.cutout());

            MenuScreens.register(BBMenuTypes.GEM_POWERING_STATION_MENU.get(), GemPoweringStationScreen::new);
        }
    }
}
