package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.join_us_android.R;
import modules.GameInfo;

/**
 * Created by Rohr on 10/3/2016.
 */
public class GameListAdapter extends BaseAdapter {

    private Context context;
    private List<GameInfo> items;
    private LayoutInflater layoutInflater;

    public GameListAdapter(Context context, List<GameInfo> gameInfosList) {
        this.context = context;
        this.items = gameInfosList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item item;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_view_item, null);
        }

        item = new Item(view);

        item.imageView.setImageBitmap(items.get(i).getGameImg());
        item.textView.setText(items.get(i).game.name);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public GameInfo getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Item {
        ImageView imageView;
        TextView textView;

        public Item(View view) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
