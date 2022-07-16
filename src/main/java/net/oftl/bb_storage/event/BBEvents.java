package net.oftl.bb_storage.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.oftl.bb_storage.BBStorageMod;
import net.oftl.bb_storage.entity.villagers.BBVillagers;
import net.oftl.bb_storage.items.BBItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = BBStorageMod.MOD_ID)
public class BBEvents {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent tradesEvent) {
        if(tradesEvent.getType() == BBVillagers.GEM_POWERER.get()) {
          Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = tradesEvent.getTrades();
          ItemStack powered_amethyst_stack = new ItemStack(BBItems.POWERED_AMETHYST_SHARD.get(), 4);
          ItemStack powered_emerald_stack = new ItemStack(BBItems.POWERED_EMERALD.get(), 1);

          trades.get(1).add((trader, rand) -> new MerchantOffer(
                  new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.AMETHYST_SHARD, 6),
                  powered_amethyst_stack, 8, 12,4,0.2F, 1));

          trades.get(2).add((trader, rand) -> new MerchantOffer(
                  new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.EMERALD, 8),
                  powered_emerald_stack, 2, 8,8,0.75F,2));
        }
    }
}
