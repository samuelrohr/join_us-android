package br.com.join_us_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters.GameListAdapter;
import modules.GameInfo;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new GameListAdapter(this.getApplicationContext(), mockData()));
    }

    private List<GameInfo> mockData() {
        List<GameInfo> list = new ArrayList<>();
        list.add(new GameInfo("teste", "nada", 10, 20));
        list.add(new GameInfo("teste2", "nada1", 120, 2320));
        list.add(new GameInfo("teste3", "nada2", 130, 220));
        return list;
    }
}
