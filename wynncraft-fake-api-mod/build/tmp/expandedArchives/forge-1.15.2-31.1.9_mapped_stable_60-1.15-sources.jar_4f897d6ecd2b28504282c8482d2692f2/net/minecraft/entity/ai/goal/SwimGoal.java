package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.MobEntity;

public class SwimGoal extends Goal {
   private final MobEntity entity;

   public SwimGoal(MobEntity entityIn) {
      this.entity = entityIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP));
      entityIn.getNavigator().setCanSwim(true);
   }

   public boolean shouldExecute() {
      double d0 = (double)this.entity.getEyeHeight() < 0.4D ? 0.2D : 0.4D;
      return this.entity.isInWater() && this.entity.getSubmergedHeight() > d0 || this.entity.isInLava();
   }

   public void tick() {
      if (this.entity.getRNG().nextFloat() < 0.8F) {
         this.entity.getJumpController().setJumping();
      }

   }
}