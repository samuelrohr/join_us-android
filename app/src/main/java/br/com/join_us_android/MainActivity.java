package br.com.join_us_android;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters.GameListAdapter;
import httpUtil.HttpGetRequestHandler;
import modules.GameInfo;

public class MainActivity extends AppCompatActivity implements HttpGetRequestHandler.HttpGetCompleted {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TOP_GAMES_URL = "https://api.twitch.tv/kraken/games/top";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadComponents();
    }

    private void loadComponents() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new GameListAdapter(this.getApplicationContext(), mockData()));

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

    @Override
    public void onHttpGetCompleted(String s) {
        //TODO passar para o parser de JSON e montar os objetos a serem mostrados na lista
        swipeRefreshLayout.setRefreshing(false);
    }

    //TODO remover
    private List<GameInfo> mockData() {
        List<GameInfo> list = new ArrayList<>();
        list.add(new GameInfo("teste", "nada", 10, 20));
        list.add(new GameInfo("teste2", "nada1", 120, 2320));
        list.add(new GameInfo("teste3", "nada2", 130, 220));
        return list;
    }
}
