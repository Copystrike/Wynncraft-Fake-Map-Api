package dev.nick.whynncraftfakemapapi.core;

import dev.nick.whynncraftfakemapapi.events.PlayerMoveEvent;
import dev.nick.whynncraftfakemapapi.player.WynnCraftPlayer;
import dev.nick.whynncraftfakemapapi.server.FakeServer;
import dev.nick.whynncraftfakemapapi.server.IFakeServer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("whynncraft-fake-map-api")
public class WynnCraftFakeMapApi {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    private final IFakeServer fakeServer;
    private WynnCraftPlayer wynnCraftPlayer;

    public WynnCraftFakeMapApi() throws MalformedURLException {
        fakeServer = new FakeServer(new URL("http://localhost:44889/map/getMyLocation"));

        // Register the clientSetupEvent to register the player
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetupEvent);
    }


    private void clientSetupEvent(final FMLClientSetupEvent event) {
        Session player = Minecraft.getInstance().getSession();
        UUID playerUniqueID = player.getProfile().getId();
        String playerUsername = player.getUsername();

        wynnCraftPlayer = new WynnCraftPlayer(playerUsername, playerUniqueID);

        // Register the PlayerMoveEvent to update the coords
        MinecraftForge.EVENT_BUS.register(new PlayerMoveEvent(fakeServer, wynnCraftPlayer));
    }

    public IFakeServer getFakeServer() {
        return fakeServer;
    }

    public WynnCraftPlayer getWynnCraftPlayer() {
        return wynnCraftPlayer;
    }
}
