package be.mic666.steamExporter.api;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;


public class SteamApiCaller {

    public String getGamesListWithDetails() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost host = new HttpHost("api.steampowered.com", 80, "http");
        HttpGet apiGetter = new HttpGet("/IPlayerService/GetOwnedGames/v0001/?key=340FC978DB7CF4556BDA7BFA51C30265&steamid=76561198003309669&format=json&include_appinfo=1");
        try {
            HttpResponse httpResponse = httpClient.execute(host, apiGetter);
            System.out.println("Response code : " + httpResponse.getStatusLine().getStatusCode());
            return IOUtils.toString(httpResponse.getEntity().getContent(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong", e);
        }
    }
}
