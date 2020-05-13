package dev.nick.whynncraftfakemapapi.world;

import dev.nick.whynncraftfakemapapi.JsonEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Vector;

/**
 * Created by Nick on 12/05/2020 14:32.
 */
public class ServerLocation implements JsonEntity {

    private int x, y, z;

    public ServerLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ServerLocation(BlockPos blockPos) {
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(getX(), getY(), getZ());
    }
}
