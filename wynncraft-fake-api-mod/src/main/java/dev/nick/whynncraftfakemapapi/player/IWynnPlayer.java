package dev.nick.whynncraftfakemapapi.player;

import com.google.gson.JsonObject;
import dev.nick.whynncraftfakemapapi.JsonEntity;
import dev.nick.whynncraftfakemapapi.world.ServerLocation;

import java.util.UUID;

/**
 * Created by Nick on 12/05/2020 18:23.
 */
public interface IWynnPlayer extends JsonEntity {

    String getName();

    UUID getUuid();

    int getHealth();

    void setHealth(int health);

    int getMaxHealth();

    void setMaxHealth(int maxHealth);

    ServerLocation getServerLocation();

    void setServerLocation(ServerLocation serverLocation);

    JsonObject toServerFormatJson();
}
