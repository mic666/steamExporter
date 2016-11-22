package be.mic666.steamExporter.exchangeObject;

public class GameLst {
    private GameResponse response;

    public GameResponse getResponse() {

        return response;
    }

    public void setResponse(GameResponse response) {

        this.response = response;
    }

    @Override
    public String toString() {
        return "GameLst{" +
                "\nresponse=" + response +
                "\n}";
    }
}
