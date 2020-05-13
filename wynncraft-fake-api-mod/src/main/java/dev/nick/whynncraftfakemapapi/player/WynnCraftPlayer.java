package dev.nick.whynncraftfakemapapi.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.nick.whynncraftfakemapapi.JsonEntity;
import dev.nick.whynncraftfakemapapi.world.ServerLocation;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Nick on 12/05/2020 14:30.
 */
public class WynnCraftPlayer implements IWynnPlayer {

    private final UUID uuid;
    private final String name;
    private int health, maxHealth;
    private transient ServerLocation serverLocation;
    //private String spellClass;


    public WynnCraftPlayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public ServerLocation getServerLocation() {
        return serverLocation;
    }

    @Override
    public void setServerLocation(ServerLocation serverLocation) {
        this.serverLocation = serverLocation;
    }

    @Override
    public JsonObject toServerFormatJson(){
        JsonObject wynnCraftPlayerJsonObject = toJsonObject();
        Set<Map.Entry<String, JsonElement>> locationEntrySet = getServerLocation().toJsonObject().entrySet();
        locationEntrySet.forEach(locationEntry -> wynnCraftPlayerJsonObject.addProperty(locationEntry.getKey(), locationEntry.getValue().getAsInt()));
        return wynnCraftPlayerJsonObject;
    }
}
