package net.minecraft.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class ShulkerBoxDispenseBehavior extends OptionalDispenseBehavior {
   protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
      this.successful = false;
      Item item = stack.getItem();
      if (item instanceof BlockItem) {
         Direction direction = source.getBlockState().get(DispenserBlock.FACING);
         BlockPos blockpos = source.getBlockPos().offset(direction);
         Direction direction1 = source.getWorld().isAirBlock(blockpos.func_177977_b()) ? direction : Direction.UP;
         this.successful = ((BlockItem)item).tryPlace(new DirectionalPlaceContext(source.getWorld(), blockpos, direction, stack, direction1)) == ActionResultType.SUCCESS;
      }

      return stack;
   }
}