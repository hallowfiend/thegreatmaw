package com.protonmail.hallowfiend.thegreatmaw.registry;

import com.google.common.collect.Sets;
import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashSet;
import java.util.List;
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
  public static final Supplier<Item> PORCELAIN_BRICK_STAIRS = registerWithTab("porcelain_brick_stairs",
          () -> new BlockItem(ModBlocks.PORCELAIN_BRICK_STAIRS.get(), basicItem()));
  public static final Supplier<Item> CALCINATIONCRUCIBLE = registerWithTab("calcination_crucible",
          () -> new BlockItem(ModBlocks.CALCINATIONCRUCIBLE.get(), basicItem()));
  //small bricks
  public static final Supplier<Item> WHITE_SMALL_PORCELAIN_BRICKS = registerWithTab("white_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.WHITE_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> LIGHT_GRAY_SMALL_PORCELAIN_BRICKS = registerWithTab("light_gray_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.LIGHT_GRAY_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> GRAY_SMALL_PORCELAIN_BRICKS = registerWithTab("gray_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.GRAY_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> BLACK_SMALL_PORCELAIN_BRICKS = registerWithTab("black_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.BLACK_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> BROWN_SMALL_PORCELAIN_BRICKS = registerWithTab("brown_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.BROWN_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> RED_SMALL_PORCELAIN_BRICKS = registerWithTab("red_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.RED_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> ORANGE_SMALL_PORCELAIN_BRICKS = registerWithTab("orange_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.ORANGE_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> YELLOW_SMALL_PORCELAIN_BRICKS = registerWithTab("yellow_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.YELLOW_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> LIME_SMALL_PORCELAIN_BRICKS = registerWithTab("lime_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.LIME_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> GREEN_SMALL_PORCELAIN_BRICKS = registerWithTab("green_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.GREEN_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> CYAN_SMALL_PORCELAIN_BRICKS = registerWithTab("cyan_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.CYAN_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> LIGHT_BLUE_SMALL_PORCELAIN_BRICKS = registerWithTab("light_blue_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.LIGHT_BLUE_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> BLUE_SMALL_PORCELAIN_BRICKS = registerWithTab("blue_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.BLUE_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> PURPLE_SMALL_PORCELAIN_BRICKS = registerWithTab("purple_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.PURPLE_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> MAGENTA_SMALL_PORCELAIN_BRICKS = registerWithTab("magenta_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.MAGENTA_SMALL_PORCELAIN_BRICKS.get(), basicItem()));
  public static final Supplier<Item> PINK_SMALL_PORCELAIN_BRICKS = registerWithTab("pink_small_porcelain_bricks",
          () -> new BlockItem(ModBlocks.PINK_SMALL_PORCELAIN_BRICKS.get(), basicItem()));


}
