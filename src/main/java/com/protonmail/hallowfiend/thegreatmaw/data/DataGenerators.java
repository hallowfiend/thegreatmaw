package com.protonmail.hallowfiend.thegreatmaw.data;

import com.protonmail.hallowfiend.thegreatmaw.TheGreatMaw;
import com.protonmail.hallowfiend.thegreatmaw.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = TheGreatMaw.MODID)
public class DataGenerators {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    ExistingFileHelper helper = event.getExistingFileHelper();

    RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();
    DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), registrySetBuilder, Set.of(TheGreatMaw.MODID));
    CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();
    generator.addProvider(event.includeServer(), new Recipes(output, lookupProvider));
    MawBlockTags blockTags = new MawBlockTags(output, lookupProvider, helper);
    generator.addProvider(event.includeServer(), blockTags);
    generator.addProvider(event.includeServer(), new MawItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));

    BlockStateDatagen blockStates = new BlockStateDatagen(output, helper);
    generator.addProvider(event.includeClient(), blockStates);
    generator.addProvider(event.includeClient(), new BlockModelDatagen(output, helper));
    generator.addProvider(event.includeClient(), new ItemModelDatagen(output, blockStates.models().existingFileHelper));
  }

}
