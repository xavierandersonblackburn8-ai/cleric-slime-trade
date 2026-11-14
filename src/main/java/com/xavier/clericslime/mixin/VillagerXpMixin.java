package com.xavier.clericslime.mixin;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Doubles vanilla trade orb XP only when the sold item is slime balls or slime blocks
 * and the resulting offer pays emeralds. Other offers are untouched.
 */
@Mixin(Villager.class)
public abstract class VillagerXpMixin {
    @ModifyArg(
        method = "rewardTradeXp",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"),
        index = 2
    )
    private int clericSlimeTrade$doubleXp(int original, MerchantOffer offer) {
        if (offer.getCostA().is(Items.SLIME_BALL) && offer.getCostA().getCount() == 5
                && offer.getResult().is(Items.EMERALD) && offer.getResult().getCount() == 1) {
            return original * 2;
        }
        if (offer.getCostA().is(Items.SLIME_BLOCK) && offer.getCostA().getCount() == 1
                && offer.getResult().is(Items.EMERALD) && offer.getResult().getCount() == 2) {
            return original * 2;
        }
        return original;
    }
}
