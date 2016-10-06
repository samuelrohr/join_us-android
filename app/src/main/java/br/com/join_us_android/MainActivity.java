package br.com.join_us_android;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.bluelinelabs.logansquare.LoganSquare;

import java.util.List;

import adapters.GameListAdapter;
import httpUtil.HttpGetRequestHandler;
import modules.GameInfo;

public class MainActivity extends AppCompatActivity implements HttpGetRequestHandler.HttpGetCompleted {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<GameInfo> gamesList;
    private static final String TOP_GAMES_URL = "https://api.twitch.tv/kraken/games/top?limit=50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadComponents();
    }

    private void loadComponents() {
        listView = (ListView) findViewById(R.id.listView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        HttpGetRequestHandler httpGetRequestHandler = new HttpGetRequestHandler(TOP_GAMES_URL, this);
        httpGetRequestHandler.execute();
    }

    private void refreshListView() {
        listView.setAdapter(new GameListAdapter(this.getApplicationContext(), gamesList));
    }

    @Override
    public void onHttpGetCompleted(String s) {
        //TODO passar para o parser de JSON e montar os objetos a serem mostrados na lista
        swipeRefreshLayout.setRefreshing(false);

        try {
            //Removemos o header inutil do JSON
            String header = "\"top\":";
            int jsonStart = s.indexOf(header, 0) + header.length();
            String json = s.substring(jsonStart);
            json = json.substring(0, json.length() - 2);

            gamesList = LoganSquare.parseList(json, GameInfo.class);
            refreshListView();
        } catch(Exception e) {
            String sa = e.getMessage();
            Log.e("LoganError", sa);
        }
    }
}
