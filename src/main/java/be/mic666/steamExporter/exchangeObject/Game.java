package be.mic666.steamExporter.exchangeObject;

import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("appid")
    private Integer appid;

    @SerializedName("name")
    private String name;

    @SerializedName("playtime_forever")
    private Integer playtimeForever;

    @SerializedName("img_icon_url")
    private String imgIconUrl;

    @SerializedName("img_logo_url")
    private String imgLogoUrl;

    @SerializedName("has_community_visible_stats")
    private Boolean hasCommunityVisibleStats;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlaytimeForever() {
        return playtimeForever;
    }

    public void setPlaytimeForever(Integer playtimeForever) {
        this.playtimeForever = playtimeForever;
    }

    public String getImgIconUrl() {
        return imgIconUrl;
    }

    public void setImgIconUrl(String imgIconUrl) {
        this.imgIconUrl = imgIconUrl;
    }

    public String getImgLogoUrl() {
        return imgLogoUrl;
    }

    public void setImgLogoUrl(String imgLogoUrl) {
        this.imgLogoUrl = imgLogoUrl;
    }

    public Boolean getHasCommunityVisibleStats() {
        return hasCommunityVisibleStats;
    }

    public void setHasCommunityVisibleStats(Boolean hasCommunityVisibleStats) {
        this.hasCommunityVisibleStats = hasCommunityVisibleStats;
    }

    @Override
    public String toString() {
        return "\n\tGame{" +
                "\n\t\tappid=" + appid +
                "\n\t\t, name='" + name + '\'' +
                "\n\t\t, playtimeForever=" + playtimeForever +
                "\n\t\t, imgIconUrl='" + imgIconUrl + '\'' +
                "\n\t\t, imgLogoUrl='" + imgLogoUrl + '\'' +
                "\n\t\t, hasCommunityVisibleStats=" + hasCommunityVisibleStats +
                "\n\t}";
    }
}
