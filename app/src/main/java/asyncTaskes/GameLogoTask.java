package asyncTaskes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import modules.GameInfo;

/**
 * Created by Rohr on 10/6/2016.
 */
public class GameLogoTask extends AsyncTask<String, Void, Void> {

    private List<GameInfo> list;
    private OnImgsGetCompleted listerner;

    public GameLogoTask(List<GameInfo> list, OnImgsGetCompleted listener) {
        this.list = list;
        this.listerner = listener;
    }


    @Override
    protected Void doInBackground(String... strings) {
        for(GameInfo gameInfo : list) {
            String strUrl = gameInfo.game.logo.medium;

            try {
                URL url = new URL(strUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);

                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Get Response
                    InputStream is = urlConnection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(is);

                    gameInfo.setGameImg(myBitmap);
                    publishProgress();
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

        listerner.onGetImgsUpdate();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        listerner.onGetImgsDone();
    }

    public interface OnImgsGetCompleted {
        void onGetImgsUpdate();
        void onGetImgsDone();
    }
}
