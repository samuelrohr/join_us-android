package modules;

import android.graphics.Bitmap;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Rohr on 10/3/2016.
 */

@JsonObject
public class GameInfo {

    @JsonField(name = "viewers")
    public int viewersCount;

    @JsonField(name = "channels")
    public int channelsCount;

    @JsonField
    public Game game;

    private Bitmap gameImg;

    public GameInfo() {

    }

    public Bitmap getGameImg() {
        return gameImg;
    }
}
