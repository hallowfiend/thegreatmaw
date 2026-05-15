package com.protonmail.hallowfiend.thegreatmaw.common.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class FluidInputRecipeWrapper extends DummyRecipeWrapper{
  // wrapper that accepts fluid inputs and item outputs

  public abstract boolean matches(FluidStack inputFluid);

  public abstract Optional<SizedFluidIngredient> getInputFluid();

  public abstract ItemStack getOutputItem();

  public final boolean testFluid(FluidStack fluid) {
    // map sized ingredient to plain ingredient because we're not interested in amount here, just the fluid type
    return getInputFluid().map(i -> i.ingredient().test(fluid)).orElse(false);
  }

  public record Inputs(Optional<SizedFluidIngredient> inputFluid) {
    public static final Codec<Inputs> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("fluid")
                    .forGetter(Inputs::inputFluid)
    ).apply(builder, Inputs::new));
    public static StreamCodec<RegistryFriendlyByteBuf, Inputs> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(SizedFluidIngredient.STREAM_CODEC), Inputs::inputFluid,
            Inputs::new
    );

    public static Inputs of(@Nullable SizedFluidIngredient inputFluid) {
      return new Inputs(Optional.ofNullable(inputFluid));
    }
  }

  public record Outputs(ItemStack outputItem) {
    public static final Codec<Outputs> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ItemStack.CODEC.optionalFieldOf("item_output", ItemStack.EMPTY)
                    .forGetter(Outputs::outputItem)
    ).apply(builder, Outputs::new));
    public static StreamCodec<RegistryFriendlyByteBuf, Outputs> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC, Outputs::outputItem,
            Outputs::new
    );
  }

}
