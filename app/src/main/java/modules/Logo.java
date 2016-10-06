package modules;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Rohr on 10/5/2016.
 */
@JsonObject
public class Logo {

    public Logo() {
    }

    @JsonField
    public String large;

    @JsonField
    public String medium;

    @JsonField
    public String small;

}
