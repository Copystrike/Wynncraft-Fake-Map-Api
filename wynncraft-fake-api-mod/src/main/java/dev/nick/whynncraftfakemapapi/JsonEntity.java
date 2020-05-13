package dev.nick.whynncraftfakemapapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by Nick on 12/05/2020 15:19.
 */
public interface JsonEntity {

    default JsonObject toJsonObject(){
        return (JsonObject) new Gson().toJsonTree(this);
    }
}
