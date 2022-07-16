package net.oftl.bb_storage.blocks.entity.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.oftl.bb_storage.blocks.entity.BBBlockEntities;
import net.oftl.bb_storage.items.custom.BatteryItem;
import net.oftl.bb_storage.recipes.GemPoweringStationRecipe;
import net.oftl.bb_storage.screen.GemPoweringStationMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GemPoweringStationBlockEntity extends BlockEntity implements MenuProvider {

    // Variables
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    public GemPoweringStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BBBlockEntities.GEM_POWERING_STATION_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return GemPoweringStationBlockEntity.this.progress;
                    case 1: return GemPoweringStationBlockEntity.this.maxProgress;
                    case 2: return getItemStackInSlot(GemPoweringStationBlockEntity.this);
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: GemPoweringStationBlockEntity.this.progress = value; break;
                    case 1: GemPoweringStationBlockEntity.this.maxProgress = value; break;
                    case 2: getItemStackInSlot(GemPoweringStationBlockEntity.this); break;
                }
            }

            public int getCount() {
                return 3;
            }
        };
    }

    public int getItemStackInSlot(GemPoweringStationBlockEntity entity) {
        Item itemInSlot = entity.itemHandler.getStackInSlot(1).getItem();
        if (itemInSlot == Items.AMETHYST_SHARD) {
            return 1;
        } else if (itemInSlot == Items.EMERALD) {
            return 2;
        } else if (itemInSlot == null) {
            return 0;
        } return  0;
    }

    //BlockEntity
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.gem_powering_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new GemPoweringStationMenu(containerId, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("gem_powering_station.progress", progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(serializeNBT().getCompound("inventory"));
        progress = nbt.getInt("gem_powering_station.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    //Recipe
    public static void tick(Level level, BlockPos pos, BlockState state, GemPoweringStationBlockEntity blockEntity) {
        if(hasRecipe(blockEntity)) {
            blockEntity.progress++;
            setChanged(level, pos, state);
            if(blockEntity.progress >= blockEntity.maxProgress) {
                craftItem(blockEntity);
            }
        } else {
            blockEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private static boolean hasRecipe(GemPoweringStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<GemPoweringStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(GemPoweringStationRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())
                && hasRedstoneInRedstoneSlot(entity)
                && hasEnoughEnergyInBattery(entity);
    }

    private static boolean hasRedstoneInRedstoneSlot(GemPoweringStationBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(2).getItem() == Items.REDSTONE;
    }
    private static boolean hasEnoughEnergyInBattery(GemPoweringStationBlockEntity entity) {
        if (entity.itemHandler.getStackInSlot(0).getItem() instanceof BatteryItem) {
            return ((BatteryItem) entity.itemHandler.getStackInSlot(0).getItem()).getEnergyStored() > 250;
        } else {
            LogUtils.getLogger().info("hello world something is wrong");
            return false;
        }
    }

    private static void craftItem(GemPoweringStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<GemPoweringStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(GemPoweringStationRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.extractItem(2,1, false);

            entity.itemHandler.setStackInSlot(3, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(3).getCount() + 1));

            entity.resetProgress();
            double x = entity.getBlockPos().getX();
            double y = entity.getBlockPos().getY();
            double z = entity.getBlockPos().getZ();
            entity.level.playSound(entity.level.getNearestPlayer(TargetingConditions.DEFAULT ,x, y, z),
                    entity.getBlockPos(), SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0F, 0.75F);
            ((BatteryItem) entity.itemHandler.getStackInSlot(0).getItem()).extractEnergy(250, false);

        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
