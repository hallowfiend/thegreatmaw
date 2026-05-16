package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Set;
import java.util.stream.Collectors;

import static vectorwing.farmersdelight.data.ItemModels.takeAll;

public class ItemModelDatagen extends ItemModelProvider {

  public static final String GENERATED = "item/generated";

  public ItemModelDatagen(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, TheGreatMaw.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    Set<Item> items = BuiltInRegistries.ITEM.stream().filter(i -> TheGreatMaw.MODID.equals(BuiltInRegistries.ITEM.getKey(i).getNamespace()))
            .collect(Collectors.toSet());

    items.remove(ModItems.UNFIRED_CALCINATIONCRUCIBLE.get());

    // Generic blocks
    takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));
    // Generic items
    items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));
  }

  public void blockBasedModel(Item item, String suffix) {
    withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
  }

  public void blockBasedModel(Item item, ResourceLocation block) {
    withExistingParent(itemName(item), block);
  }

  public void itemGeneratedModel(Item item, ResourceLocation texture) {
    withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
  }

  public ResourceLocation resourceItem(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, "item/" + path);
  }

  private String itemName(Item item) {
    return BuiltInRegistries.ITEM.getKey(item).getPath();
  }

  public ResourceLocation resourceBlock(String path) {
    return ResourceLocation.fromNamespaceAndPath(TheGreatMaw.MODID, "block/" + path);
  }

}
