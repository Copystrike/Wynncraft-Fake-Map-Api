package dev.nick.whynncraftfakemapapi.events;

import dev.nick.whynncraftfakemapapi.player.IWynnPlayer;
import dev.nick.whynncraftfakemapapi.server.IFakeServer;
import dev.nick.whynncraftfakemapapi.world.ServerLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by Nick on 12/05/2020 16:05.
 */
public class PlayerMoveEvent {

    private final IFakeServer fakeServer;
    private final IWynnPlayer wynnPlayer;

    public PlayerMoveEvent(IFakeServer fakeServer, IWynnPlayer wynnPlayer) {
        this.fakeServer = fakeServer;
        this.wynnPlayer = wynnPlayer;
    }

    private final Field actionBar = ObfuscationReflectionHelper.findField(IngameGui.class, "overlayMessage");

    @SubscribeEvent
    public void livingUpdate(TickEvent.ClientTickEvent event) {
        if (wynnPlayer == null || event.phase != TickEvent.Phase.START || Minecraft.getInstance().player == null) return;

        PlayerEntity playerEntity = (PlayerEntity) Minecraft.getInstance().player.getEntity();
        BlockPos playerCurrentPos = playerEntity.getPosition();
        ServerLocation playerLocation = wynnPlayer.getServerLocation();

        if (playerLocation == null || !playerLocation.toBlockPos().equals(playerCurrentPos)) {
            wynnPlayer.setServerLocation(new ServerLocation(playerCurrentPos));
            fakeServer.updateServer(wynnPlayer);
        }

        try {
            String title = (String) actionBar.get(Minecraft.getInstance().ingameGUI);
            if(!title.isEmpty()){
                StringTextComponent textComponent = new StringTextComponent(title);
                String[] chatBarData = textComponent.getText().split(" {4}");
                String[] healthData = chatBarData[0].replaceAll("[^0-9/]+", "").split("/");
                int playerHealth = Integer.parseInt(healthData[0]);
                int playerMaxHealth = Integer.parseInt(healthData[1].substring(0, healthData[1].length() - 1));

                if(wynnPlayer.getHealth() != playerHealth || wynnPlayer.getMaxHealth() != playerMaxHealth) {
                    wynnPlayer.setHealth(playerHealth);
                    wynnPlayer.setMaxHealth(playerMaxHealth);
                    fakeServer.updateServer(wynnPlayer);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}