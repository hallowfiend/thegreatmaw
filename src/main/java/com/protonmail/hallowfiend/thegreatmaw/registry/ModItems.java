package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.google.common.collect.Sets;
import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static vectorwing.farmersdelight.common.registry.ModItems.basicItem;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, TheGreatMaw.MODID);
  public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

  public static Supplier<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
    Supplier<Item> newItem = ITEMS.register(name, supplier);
    CREATIVE_TAB_ITEMS.add(newItem);
    return newItem;
  }
  // crafting materials
  public static final Supplier<Item> SALT = registerWithTab("salt", () -> new Item(basicItem()));
  // porcelain
  public static final Supplier<Item> UNFIRED_PORCELAIN = registerWithTab("unfired_porcelain", () -> new Item(basicItem()));
  public static final Supplier<Item> UNFIRED_CALCINATIONCRUCIBLE = registerWithTab("unfired_calcination_crucible", () -> new Item(basicItem()));
  public static final Supplier<Item> PORCELAIN_BRICK = registerWithTab("porcelain_brick", () -> new Item(basicItem()));
  public static final Supplier<Item> PORCELAIN_BRICKS = registerWithTab("porcelain_bricks",
          () -> new BlockItem(ModBlocks.PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> PORCELAIN_BRICK_SLAB = registerWithTab("porcelain_brick_slab",
          () -> new BlockItem(ModBlocks.PORCELAIN_BRICK_SLAB.get(), basicItem()));
  public static final Supplier<Item> CALCINATIONCRUCIBLE = registerWithTab("calcination_crucible",
          () -> new BlockItem(ModBlocks.CALCINATIONCRUCIBLE.get(), basicItem()));

}
