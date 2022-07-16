package net.oftl.bb_storage.screen.slot;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.oftl.bb_storage.items.custom.BatteryItem;

public class BBBatterySlot extends SlotItemHandler {

    public BBBatterySlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if(!(stack.getItem() instanceof BatteryItem)) {
            return false;
        }
        return true;
    }
}
