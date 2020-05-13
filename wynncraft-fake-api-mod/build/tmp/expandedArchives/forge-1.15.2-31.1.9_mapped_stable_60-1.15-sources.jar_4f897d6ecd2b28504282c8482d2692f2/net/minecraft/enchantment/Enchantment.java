package net.minecraft.enchantment;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class Enchantment extends net.minecraftforge.registries.ForgeRegistryEntry<Enchantment> {
   private final EquipmentSlotType[] applicableEquipmentTypes;
   private final Enchantment.Rarity rarity;
   @Nullable
   public EnchantmentType type;
   @Nullable
   protected String name;

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public static Enchantment getEnchantmentByID(int id) {
      return Registry.ENCHANTMENT.getByValue(id);
   }

   protected Enchantment(Enchantment.Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
      this.rarity = rarityIn;
      this.type = typeIn;
      this.applicableEquipmentTypes = slots;
   }

   public Map<EquipmentSlotType, ItemStack> getEntityEquipment(LivingEntity livingEntityIn) {
      Map<EquipmentSlotType, ItemStack> map = Maps.newEnumMap(EquipmentSlotType.class);

      for(EquipmentSlotType equipmentslottype : this.applicableEquipmentTypes) {
         ItemStack itemstack = livingEntityIn.getItemStackFromSlot(equipmentslottype);
         if (!itemstack.isEmpty()) {
            map.put(equipmentslottype, itemstack);
         }
      }

      return map;
   }

   public Enchantment.Rarity getRarity() {
      return this.rarity;
   }

   public int getMinLevel() {
      return 1;
   }

   public int getMaxLevel() {
      return 1;
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 1 + enchantmentLevel * 10;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 5;
   }

   public int calcModifierDamage(int level, DamageSource source) {
      return 0;
   }

   public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
      return 0.0F;
   }

   public final boolean isCompatibleWith(Enchantment enchantmentIn) {
      return this.canApplyTogether(enchantmentIn) && enchantmentIn.canApplyTogether(this);
   }

   protected boolean canApplyTogether(Enchantment ench) {
      return this != ench;
   }

   protected String getDefaultTranslationKey() {
      if (this.name == null) {
         this.name = Util.makeTranslationKey("enchantment", Registry.ENCHANTMENT.getKey(this));
      }

      return this.name;
   }

   public String getName() {
      return this.getDefaultTranslationKey();
   }

   public ITextComponent getDisplayName(int level) {
      ITextComponent itextcomponent = new TranslationTextComponent(this.getName());
      if (this.isCurse()) {
         itextcomponent.applyTextStyle(TextFormatting.RED);
      } else {
         itextcomponent.applyTextStyle(TextFormatting.GRAY);
      }

      if (level != 1 || this.getMaxLevel() != 1) {
         itextcomponent.appendText(" ").appendSibling(new TranslationTextComponent("enchantment.level." + level));
      }

      return itextcomponent;
   }

   public boolean canApply(ItemStack stack) {
      return canApplyAtEnchantingTable(stack);
   }

   public void onEntityDamaged(LivingEntity user, Entity target, int level) {
   }

   public void onUserHurt(LivingEntity user, Entity attacker, int level) {
   }

   public boolean isTreasureEnchantment() {
      return false;
   }

   public boolean isCurse() {
      return false;
   }

   /**
    * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
    * applies for <i>all possible</i> enchantments.
    * @param stack
    * @return
    */
   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.canApplyAtEnchantingTable(this);
   }

   /**
    * Is this enchantment allowed to be enchanted on books via Enchantment Table
    * @return false to disable the vanilla feature
    */
   public boolean isAllowedOnBooks() {
      return true;
   }

   public static enum Rarity {
      COMMON(10),
      UNCOMMON(5),
      RARE(2),
      VERY_RARE(1);

      private final int weight;

      private Rarity(int rarityWeight) {
         this.weight = rarityWeight;
      }

      public int getWeight() {
         return this.weight;
      }
   }
}