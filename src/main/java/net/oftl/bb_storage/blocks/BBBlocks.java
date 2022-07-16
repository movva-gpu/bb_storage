package net.oftl.bb_storage.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oftl.bb_storage.BBStorageMod;
import net.oftl.bb_storage.blocks.custom.GemPoweringStationBlock;
import net.oftl.bb_storage.items.BBItems;

import java.util.function.Supplier;

public class BBBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BBStorageMod.MOD_ID);

    public static final RegistryObject<Block> POWERED_EMERALD_BLOCK = registerBlock("powered_emerald_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.FIRE)
                    .strength(4.5F, 5.6F).requiresCorrectToolForDrops()), BBStorageMod.BB_RESOURCES_TAB);
    public static final RegistryObject<Block> POWERED_AMETHYST_BLOCK = registerBlock("powered_amethyst_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE)
                    .strength(3.3F, 4.4F).requiresCorrectToolForDrops()), BBStorageMod.BB_RESOURCES_TAB);
    public static final RegistryObject<Block> POWERED_AMETHYST_LAMP = registerBlock("powered_amethyst_lamp",
            () -> new Block(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.COLOR_PURPLE)
                    .strength(0.3F).sound(SoundType.GLASS)
                    .lightLevel((lightLevel) -> { return 15; })), BBStorageMod.BB_DECORATION_TAB);

    public static final RegistryObject<Block> GEM_POWERING_STATION = registerBlock("gem_powering_station",
            () -> new GemPoweringStationBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion()
                    .strength(1.0F).sound(SoundType.WOOD)), BBStorageMod.BB_STATIONS_TAB);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                                                                    CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                           CreativeModeTab tab) {
        return BBItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
