package net.oftl.bb_storage.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.oftl.bb_storage.BBStorageMod;

public class GemPoweringStationScreen extends AbstractContainerScreen<GemPoweringStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BBStorageMod.MOD_ID,"textures/gui/gem_powering_station.png");

    public GemPoweringStationScreen(GemPoweringStationMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) /2;
        int y = (height - imageHeight) /2;

        this.blit(poseStack, x, y, 0,0, imageWidth, imageHeight);

        if(menu.isCrafting()) {
            if (menu.data.get(2) == 1) {
                blit(poseStack, x + 70, y + 15, 176, 32, menu.getScaledProgress(), 24);
            }else if (menu.data.get(2) == 2){
                blit(poseStack, x + 70, y + 15, 176, 56, menu.getScaledProgress(), 24);
            } else if (menu.data.get(2) == 0) {
                blit(poseStack, x + 70, y + 15, 176, 32, menu.getScaledProgress(), 24);

            }
        }
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
