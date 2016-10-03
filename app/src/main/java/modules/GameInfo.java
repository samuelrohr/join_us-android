package modules;

import android.graphics.Bitmap;

/**
 * Created by Rohr on 10/3/2016.
 */
public class GameInfo {
    private String gameName;
    private Bitmap gameImg;
    private int viewersCount;
    private int channelsCount;

    public GameInfo(String name, String img, int viewersCount, int channelsCount) {
        gameName = name;
        this.viewersCount = viewersCount;
        this.channelsCount = channelsCount;
    }

    public String getGameName() {
        return gameName;
    }

    public Bitmap getGameImg() {
        return gameImg;
    }

    public int getViewersCount() {
        return viewersCount;
    }

    public int getChannelsCount() {
        return channelsCount;
    }

}
