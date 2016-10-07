package br.com.join_us_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.ByteArrayOutputStream;
import java.util.List;

import InternalStorage.InternalStorageManager;
import adapters.GameListAdapter;
import asyncTaskes.GameLogoTask;
import asyncTaskes.TopGamesGetTask;
import modules.GameInfo;

public class MainActivity extends AppCompatActivity implements TopGamesGetTask.HttpGetCompleted, GameLogoTask.OnImgsGetCompleted {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<GameInfo> gamesList;
    private static final String TOP_GAMES_URL = "https://api.twitch.tv/kraken/games/top?limit=50";
    private static final String FILENAME_OFFLINE = "OFFLINE_JSON.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadComponents();

        getOnlineData();
    }

    private void loadComponents() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GameListAdapter gameListAdapter = (GameListAdapter) adapterView.getAdapter();
                GameInfo gameInfo = gameListAdapter.getItem(i);

                Intent intent = new Intent(MainActivity.this, GameDetailActivity.class);
                intent.putExtra("name", gameInfo.game.name);
                intent.putExtra("viewers", gameInfo.viewersCount);
                intent.putExtra("channels", gameInfo.channelsCount);
                if(gameInfo.getGameImg() != null) {
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    gameInfo.getGameImg().compress(Bitmap.CompressFormat.PNG, 50, bs);
                    intent.putExtra("img", bs.toByteArray());
                }
                startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOnlineData();
            }
        });
    }

    /**
     * Busca na api do twitch o json contendo o 50 top games
     */
    private void getOnlineData() {
        TopGamesGetTask httpGetRequestHandler = new TopGamesGetTask(TOP_GAMES_URL, this);
        httpGetRequestHandler.execute();
    }

    /**
     * Atualiza a listView
     */
    private void refreshListView() {
        listView.setAdapter(new GameListAdapter(this.getApplicationContext(), gamesList));
    }

    /**
     * Listener da asynctask que busca dados da API do twtich. Chamado quando obtém uma resposta
     * @param s contém o json em casa feliz ou "" caso ocorra algum erro
     */
    @Override
    public void onHttpGetCompleted(String s) {
        String json = "";
        if(!s.equals("")) {
            //Removemos o header inutil do JSON
            String header = "\"top\":";
            int jsonStart = s.indexOf(header, 0) + header.length();
            json = s.substring(jsonStart);
            json = json.substring(0, json.length() - 2);

            InternalStorageManager.writeData(this.getApplicationContext(), FILENAME_OFFLINE, json);
        }
        //Nao conseguiu pegar o json tenta pegar no modo offline
        else {
            json = InternalStorageManager.readData(FILENAME_OFFLINE, this.getApplicationContext());
        }

        //Se o json não está vazio, monta a lista
        if(!json.equals("")) {
            try {
                //Monta a lista de objetos
                gamesList = LoganSquare.parseList(json, GameInfo.class);
                refreshListView();

                //Inicia task para recuperar imagens
                GameLogoTask gameLogoTask = new GameLogoTask(gamesList, this);
                gameLogoTask.execute();

            } catch (Exception e) {
                String sa = e.getMessage();
                Log.e("LoganError", sa);
            }
        }
    }

    /**
     * Listener que informa sobre o update no processo de obter as imagens dos jogos
     */
    @Override
    public void onGetImgsUpdate() {
        refreshListView();
    }

    @Override
    public void onGetImgsDone() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
