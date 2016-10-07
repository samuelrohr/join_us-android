package asyncTaskes;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rohr on 10/4/2016.
 */
public class TopGamesGetTask extends AsyncTask<String, Void, String> {

    private String url = "";
    private HttpGetCompleted listener;
    private static final String CLIENT_ID = "m7d3djff8ihb50r5tykr8zw5g2zijr7";

    public TopGamesGetTask(String url, HttpGetCompleted listener) {
        super();

        this.url = url;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        listener.onHttpGetCompleted(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Client-ID", CLIENT_ID);
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);

            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Get Response
                InputStream is = urlConnection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            } else {
                return "";
            }

        } catch (Exception e) {
            Log.e("GetGameInfoError", e.getMessage());
        }
        return "";
    }

    public interface HttpGetCompleted {
        void onHttpGetCompleted(String s);
    }
}
