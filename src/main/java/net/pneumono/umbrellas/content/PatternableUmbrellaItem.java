package net.pneumono.umbrellas.content;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.pneumono.umbrellas.patterns.PatternRegistry;
import net.pneumono.umbrellas.patterns.UmbrellaPattern;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PatternableUmbrellaItem extends UmbrellaItem {
    private final DyeColor baseColor;

    public PatternableUmbrellaItem(Settings settings, DyeColor baseColor) {
        super(settings);
        this.baseColor = baseColor;
    }

    public DyeColor getBaseColor() {
        return baseColor;
    }

    public static List<Pair<RegistryEntry<UmbrellaPattern>, DyeColor>> getPatternsFromNbt(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        DyeColor baseColor = stack.getItem() instanceof PatternableUmbrellaItem umbrella ? umbrella.getBaseColor() : DyeColor.WHITE;

        NbtList patternListNbt = null;
        if (nbt != null && nbt.contains("Patterns", NbtElement.LIST_TYPE)) {
            patternListNbt = nbt.getList("Patterns", NbtElement.COMPOUND_TYPE).copy();
        }



        ArrayList<Pair<RegistryEntry<UmbrellaPattern>, DyeColor>> list = Lists.newArrayList();

        list.add(Pair.of(PatternRegistry.UMBRELLA_PATTERN.entryOf(PatternRegistry.keyOf("base")), baseColor));

        if (patternListNbt != null) {
            for (int i = 0; i < patternListNbt.size(); ++i) {
                NbtCompound nbtCompound = patternListNbt.getCompound(i);
                RegistryEntry<UmbrellaPattern> registryEntry = UmbrellaPattern.byId(nbtCompound.getString("Pattern"));
                if (registryEntry == null) continue;

                list.add(Pair.of(registryEntry, DyeColor.byId(nbtCompound.getInt("Color"))));
            }
        }
        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null || !nbtCompound.contains("Patterns")) {
            return;
        }
        NbtList nbtList = nbtCompound.getList("Patterns", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size() && i < 6; ++i) {
            NbtCompound nbtCompound2 = nbtList.getCompound(i);
            DyeColor dyeColor = DyeColor.byId(nbtCompound2.getInt("Color"));
            RegistryEntry<UmbrellaPattern> registryEntry = UmbrellaPattern.byId(nbtCompound2.getString("Pattern"));
            if (registryEntry == null) continue;
            registryEntry.getKey().map(key -> key.getValue().toShortTranslationKey()).ifPresent(translationKey -> {
                if (registryEntry.value().colored()) {
                    tooltip.add(Text.translatable("tooltip.umbrellas.colors." + dyeColor.getName()).append(" ").append(Text.translatable("tooltip." + translationKey)).formatted(Formatting.GRAY));
                } else {
                    tooltip.add(Text.translatable("tooltip." + translationKey).formatted(Formatting.GRAY));
                }
            });
        }
    }
}
