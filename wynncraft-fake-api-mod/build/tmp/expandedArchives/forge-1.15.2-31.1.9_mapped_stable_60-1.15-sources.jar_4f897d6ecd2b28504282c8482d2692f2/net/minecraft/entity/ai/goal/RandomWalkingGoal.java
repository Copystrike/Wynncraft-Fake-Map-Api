package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class RandomWalkingGoal extends Goal {
   protected final CreatureEntity creature;
   protected double x;
   protected double y;
   protected double z;
   protected final double speed;
   protected int executionChance;
   protected boolean mustUpdate;

   public RandomWalkingGoal(CreatureEntity creatureIn, double speedIn) {
      this(creatureIn, speedIn, 120);
   }

   public RandomWalkingGoal(CreatureEntity creatureIn, double speedIn, int chance) {
      this.creature = creatureIn;
      this.speed = speedIn;
      this.executionChance = chance;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean shouldExecute() {
      if (this.creature.isBeingRidden()) {
         return false;
      } else {
         if (!this.mustUpdate) {
            if (this.creature.getIdleTime() >= 100) {
               return false;
            }

            if (this.creature.getRNG().nextInt(this.executionChance) != 0) {
               return false;
            }
         }

         Vec3d vec3d = this.getPosition();
         if (vec3d == null) {
            return false;
         } else {
            this.x = vec3d.x;
            this.y = vec3d.y;
            this.z = vec3d.z;
            this.mustUpdate = false;
            return true;
         }
      }
   }

   @Nullable
   protected Vec3d getPosition() {
      return RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);
   }

   public boolean shouldContinueExecuting() {
      return !this.creature.getNavigator().noPath() && !this.creature.isBeingRidden();
   }

   public void startExecuting() {
      this.creature.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
   }

   public void resetTask() {
      this.creature.getNavigator().clearPath();
      super.resetTask();
   }

   public void makeUpdate() {
      this.mustUpdate = true;
   }

   public void setExecutionChance(int newchance) {
      this.executionChance = newchance;
   }
}