package net.minecraft.entity.ai.controller;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class LookController {
   protected final MobEntity mob;
   protected float deltaLookYaw;
   protected float deltaLookPitch;
   protected boolean isLooking;
   protected double posX;
   protected double posY;
   protected double posZ;

   public LookController(MobEntity mob) {
      this.mob = mob;
   }

   public void setLookPosition(Vec3d p_220674_1_) {
      this.setLookPosition(p_220674_1_.x, p_220674_1_.y, p_220674_1_.z);
   }

   public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
      this.setLookPosition(entityIn.func_226277_ct_(), getEyePosition(entityIn), entityIn.func_226281_cx_(), deltaYaw, deltaPitch);
   }

   public void setLookPosition(double p_220679_1_, double p_220679_3_, double p_220679_5_) {
      this.setLookPosition(p_220679_1_, p_220679_3_, p_220679_5_, (float)this.mob.func_213396_dB(), (float)this.mob.getVerticalFaceSpeed());
   }

   public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.deltaLookYaw = deltaYaw;
      this.deltaLookPitch = deltaPitch;
      this.isLooking = true;
   }

   public void tick() {
      if (this.func_220680_b()) {
         this.mob.rotationPitch = 0.0F;
      }

      if (this.isLooking) {
         this.isLooking = false;
         this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.getTargetYaw(), this.deltaLookYaw);
         this.mob.rotationPitch = this.clampedRotate(this.mob.rotationPitch, this.getTargetPitch(), this.deltaLookPitch);
      } else {
         this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.mob.renderYawOffset, 10.0F);
      }

      if (!this.mob.getNavigator().noPath()) {
         this.mob.rotationYawHead = MathHelper.func_219800_b(this.mob.rotationYawHead, this.mob.renderYawOffset, (float)this.mob.getHorizontalFaceSpeed());
      }

   }

   protected boolean func_220680_b() {
      return true;
   }

   public boolean getIsLooking() {
      return this.isLooking;
   }

   public double getLookPosX() {
      return this.posX;
   }

   public double getLookPosY() {
      return this.posY;
   }

   public double getLookPosZ() {
      return this.posZ;
   }

   protected float getTargetPitch() {
      double d0 = this.posX - this.mob.func_226277_ct_();
      double d1 = this.posY - this.mob.func_226280_cw_();
      double d2 = this.posZ - this.mob.func_226281_cx_();
      double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
      return (float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
   }

   protected float getTargetYaw() {
      double d0 = this.posX - this.mob.func_226277_ct_();
      double d1 = this.posZ - this.mob.func_226281_cx_();
      return (float)(MathHelper.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
   }

   protected float clampedRotate(float from, float to, float maxDelta) {
      float f = MathHelper.wrapSubtractDegrees(from, to);
      float f1 = MathHelper.clamp(f, -maxDelta, maxDelta);
      return from + f1;
   }

   private static double getEyePosition(Entity p_220676_0_) {
      return p_220676_0_ instanceof LivingEntity ? p_220676_0_.func_226280_cw_() : (p_220676_0_.getBoundingBox().minY + p_220676_0_.getBoundingBox().maxY) / 2.0D;
   }
}