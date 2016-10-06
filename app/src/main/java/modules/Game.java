package modules;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Rohr on 10/5/2016.
 */

@JsonObject
public class Game {
    @JsonField
    public String name;

    @JsonField
    public Logo logo;

    public  Game() {
    }
}
