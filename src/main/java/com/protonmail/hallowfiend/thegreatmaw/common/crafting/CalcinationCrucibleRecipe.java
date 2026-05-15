package com.protonmail.hallowfiend.thegreatmaw.common.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.protonmail.hallowfiend.thegreatmaw.common.util.FluidInputRecipeWrapper;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeSerializers;
import com.protonmail.hallowfiend.thegreatmaw.registry.MawRecipeTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CalcinationCrucibleRecipe extends FluidInputRecipeWrapper {
  private final Inputs inputs;
  private final Outputs outputs;
  private final int evaporationTime;

  public CalcinationCrucibleRecipe(
          Inputs inputs, Outputs outputs, int evaporationTime
  ) {
    this.inputs = inputs;
    this.outputs = outputs;
    this.evaporationTime = evaporationTime;
  }

  @Override
  public boolean matches(FluidStack fluidStack) {

    return inputs.inputFluid().map(ingr -> ingr.ingredient().test(fluidStack)).orElse(fluidStack.isEmpty());
  }

  @Override
  public Optional<SizedFluidIngredient> getInputFluid() {
    return inputs.inputFluid();
  }

  @Override
  public ItemStack getOutputItem() {
    return outputs.outputItem();
  }

  public Outputs outputs() {
    return outputs;
  }

  public Inputs inputs() {
    return inputs;
  }


  @Override
  public boolean canCraftInDimensions(int i, int i1) {
    return true;
  }

  public int getEvaporationTime() {
    return this.evaporationTime;
  }

  @Override
  public @NotNull RecipeSerializer<?> getSerializer() {
    return MawRecipeSerializers.CALCINATING.get();
  }

  @Override
  public RecipeType<?> getType() {
    return (RecipeType<?>) MawRecipeTypes.CALCINATING.get();
  }

  public interface IFactory <T extends CalcinationCrucibleRecipe> {
    T create(Inputs inputs, Outputs outputs, int evaporationTime);
  }

  public static class Serializer<T extends CalcinationCrucibleRecipe> implements RecipeSerializer<T> {
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;
    public Serializer(IFactory<T> factory) {
      this.codec = RecordCodecBuilder.<T>mapCodec(inst -> inst.group(
                      Inputs.CODEC.fieldOf("inputs")
                              .forGetter(CalcinationCrucibleRecipe::inputs),
                      Outputs.CODEC.fieldOf("outputs")
                              .forGetter(CalcinationCrucibleRecipe::outputs),
                      Codec.INT.optionalFieldOf("evaporationTime", 200)
                              .forGetter(CalcinationCrucibleRecipe::getEvaporationTime)
              ).apply(inst, factory::create))
              .validate(recipe -> recipe.getInputFluid().isPresent() ?
                      DataResult.success(recipe) :
                      DataResult.error(() -> "error generating recipe!", recipe)
              );

      this.streamCodec = StreamCodec.composite(
              Inputs.STREAM_CODEC, CalcinationCrucibleRecipe::inputs,
              Outputs.STREAM_CODEC, CalcinationCrucibleRecipe::outputs,
              ByteBufCodecs.INT, CalcinationCrucibleRecipe::getEvaporationTime,
              factory::create
      );
    }

    @Override
    public MapCodec<T> codec() {
      return codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
      return streamCodec;
    }
  }
}

