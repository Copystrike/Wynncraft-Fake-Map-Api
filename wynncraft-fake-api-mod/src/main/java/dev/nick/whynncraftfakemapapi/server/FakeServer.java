package dev.nick.whynncraftfakemapapi.server;

import com.google.gson.Gson;
import dev.nick.whynncraftfakemapapi.player.IWynnPlayer;
import dev.nick.whynncraftfakemapapi.player.WynnCraftPlayer;
import dev.nick.whynncraftfakemapapi.world.ServerLocation;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Nick on 12/05/2020 14:28.
 */
public class FakeServer implements IFakeServer {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final URL serverURL;
    private final OkHttpClient okHttpClient;

    public FakeServer(URL serverURL) {
        this.serverURL = serverURL;

        this.okHttpClient = new OkHttpClient();
    }

    @Override
    public void updateServer(IWynnPlayer wynnPlayer) {
        Request request = new Request.Builder()
                .url(serverURL)
                .post(RequestBody.create(wynnPlayer.toServerFormatJson().toString(), JSON))
                .build();

        new Thread(() -> {
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
            } catch (IOException ignored) {

            } finally {
                if (response != null && response.body() != null) {
                    response.body().close();
                }
            }
        }).start();
    }
}
