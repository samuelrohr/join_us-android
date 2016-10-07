package br.com.join_us_android;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rohr on 10/6/2016.
 */
public class GameDetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView viewersTextView;
    private TextView channelsTextView;
    private TextView nameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        setObjects();
        loadData();
    }

    private void setObjects() {
        imageView = (ImageView) findViewById(R.id.detailImageView);
        viewersTextView = (TextView) findViewById(R.id.viewersTextView);
        channelsTextView = (TextView) findViewById(R.id.channelsTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
    }

    private void loadData() {
        nameTextView.setText(getIntent().getStringExtra("name"));
        String viewers = "Viewers: " + getIntent().getIntExtra("viewers", -1);
        viewersTextView.setText(viewers);
        String channels = "Channels: " + getIntent().getIntExtra("channels", -1);
        channelsTextView.setText(channels);
        byte[] imgBytes = getIntent().getByteArrayExtra("img");
        if(imgBytes != null) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length));
        }
    }
}
