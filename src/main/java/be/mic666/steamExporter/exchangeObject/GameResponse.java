package be.mic666.steamExporter.exchangeObject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {

    @SerializedName("game_count")
    private Integer gameCount;

    @SerializedName("games")
    private List<Game> games = new ArrayList<>();

    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Response{" +
                "\ngameCount=" + gameCount +
                "\n, games=" + games +
                "\n}";
    }
}
