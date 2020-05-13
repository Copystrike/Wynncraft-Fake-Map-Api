package dev.nick.whynncraftfakemapapi.server;

import dev.nick.whynncraftfakemapapi.player.IWynnPlayer;
import dev.nick.whynncraftfakemapapi.player.WynnCraftPlayer;

/**
 * Created by Nick on 12/05/2020 14:34.
 */
public interface IFakeServer {

    void updateServer(IWynnPlayer wynnPlayer);
}
