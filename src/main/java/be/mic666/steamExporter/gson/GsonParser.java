package be.mic666.steamExporter.gson;


import be.mic666.steamExporter.exchangeObject.GameLst;
import com.google.gson.Gson;

public class GsonParser {

    private static Gson gson = new Gson();

    public static GameLst fromJsonToGameLst(String json) {
        GameLst gameLst = gson.fromJson(json, GameLst.class);
        return gameLst;
    }

    public static String fromGameLstToJson(GameLst gameLst) {
        return gson.toJson(gameLst);
    }
}
