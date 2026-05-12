package com.protonmail.hallowfiend.thegreatmaw.common.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.protonmail.hallowfiend.thegreatmaw.common.util.CalcinationRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeSerializers;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CalcinationCrucibleRecipe implements Recipe<CalcinationRecipeWrapper> {
  private final FluidStack fluid;
  private final ItemStack resultStack;
  private final int evaporationTime;

  public CalcinationCrucibleRecipe(FluidStack fluid, ItemStack resultStack, int evaporationTime) {
    this.fluid = fluid;
    this.resultStack = resultStack;
    this.evaporationTime = evaporationTime;
  }

  @Override
  public boolean matches(CalcinationRecipeWrapper calcinationRecipeWrapper, Level level) {
    return (this.fluid.getFluid().isSame(calcinationRecipeWrapper.getFluid()));
  }

  @Override
  public ItemStack assemble(CalcinationRecipeWrapper calcinationRecipeWrapper, HolderLookup.Provider provider) {
    return this.resultStack.copy();
  }
  public ItemStack assemble() {
    return assemble(null, null);
  }

  @Override
  public boolean canCraftInDimensions(int i, int i1) {
    return true;
  }

  public FluidStack getFluidIngredient() {
    return this.fluid;
  }

  public int getEvaporationTime()
  {
    return this.evaporationTime;
  }

  @Override
  public ItemStack getResultItem(HolderLookup.Provider provider) {
    return this.resultStack;
  }
  public ItemStack getResultItem() {
    return getResultItem(null);
  }

  @Override
  public @NotNull RecipeSerializer<?> getSerializer() {
    return MawRecipeSerializers.CALCINATING.get();
  }

  @Override
  public @NotNull RecipeType<?> getType() {
    return MawRecipeTypes.CALCINATING.get();
  }

  public static class Serializer implements RecipeSerializer<CalcinationCrucibleRecipe> {
    public static final MapCodec<CalcinationCrucibleRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            FluidStack.CODEC.fieldOf("fluid").forGetter(CalcinationCrucibleRecipe::getFluidIngredient),
            ItemStack.STRICT_CODEC.fieldOf("resultStack").forGetter(CalcinationCrucibleRecipe::getResultItem),
            Codec.INT.fieldOf("evaporationtime").forGetter(CalcinationCrucibleRecipe::getEvaporationTime)
    ).apply(inst, CalcinationCrucibleRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CalcinationCrucibleRecipe> STREAM_CODEC = StreamCodec.of(CalcinationCrucibleRecipe.Serializer::toNetwork, CalcinationCrucibleRecipe.Serializer::fromNetwork);


    // Return our map codec.
    @Override
    public MapCodec<CalcinationCrucibleRecipe> codec() {
      return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CalcinationCrucibleRecipe> streamCodec() {
      return STREAM_CODEC;
    }

    public static void toNetwork(RegistryFriendlyByteBuf buf, CalcinationCrucibleRecipe recipe) {
      FluidStack.STREAM_CODEC.encode(buf, recipe.fluid);
      ItemStack.STREAM_CODEC.encode(buf, recipe.resultStack);
      buf.writeVarInt(recipe.evaporationTime);
    }

    public static CalcinationCrucibleRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
      FluidStack fluid = FluidStack.STREAM_CODEC.decode(buf);
      ItemStack resultStack = ItemStack.STREAM_CODEC.decode(buf);
      int evaporationTime = buf.readVarInt();

      return new CalcinationCrucibleRecipe(fluid, resultStack, evaporationTime);
    }
  }
}
