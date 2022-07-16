package net.oftl.bb_storage.api.item.groups;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.oftl.bb_storage.BBStorageMod;

import javax.annotation.Nonnull;

public abstract class BBCreativeModeTab extends CreativeModeTab {

    public BBCreativeModeTab(String label) {
        super(label);
    }

    @Override
    public abstract ItemStack makeIcon();

    @Nonnull
    @Override
    public ResourceLocation getBackgroundImage() {
        return new ResourceLocation(BBStorageMod.MOD_ID, "textures/gui/container/creative_inventory/bb_tab_background.png");

    }

    @Nonnull
    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation(BBStorageMod.MOD_ID, "textures/gui/container/creative_inventory/bb_tabs.png");
    }
}
