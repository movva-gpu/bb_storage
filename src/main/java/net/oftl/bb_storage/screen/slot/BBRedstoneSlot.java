package net.oftl.bb_storage.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BBRedstoneSlot extends SlotItemHandler {

    public BBRedstoneSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if(stack.getItem() == Items.REDSTONE) {
            return true;
        } else {
            return false;
        }
    }
}
