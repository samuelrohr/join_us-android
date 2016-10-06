package jsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import modules.GameInfo;

/**
 * Created by Rohr on 10/3/2016.
 */
public class JsonParser {

    public void getGamesInfo(String json) {
        try {
            ArrayList<GameInfo> gamesInfo = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);
            JSONObject obj;
            for(int i = 0; i < 721; i++) {
                obj = jsonArray.getJSONObject(i);
                String name = (String) obj.get("name");
                int id = Integer.parseInt((String) obj.get("id"));
                JSONArray jsonArrayTypes = (JSONArray) obj.get("types");

                ArrayList<String> types = new ArrayList<>();
                for(int j = 0; j < jsonArrayTypes.length(); j++) {
                    String str = (String) jsonArrayTypes.get(j);
                    types.add(str);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
