package brander.ua.swipe_list_recycler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.EventListener;
import java.util.List;

import brander.ua.swipe_list_recycler.R;
import brander.ua.listswipemanager.SwipeManager;

public class ListViewTwoAdapter extends BaseAdapter {

    private List<String> items;
    private Context context;
    private EventListener mEventListener;
    private SwipeManager swipeList;

    public ListViewTwoAdapter(Context context, List<String> items, SwipeManager sl) {
        this.context = context;
        this.items = items;
        swipeList = sl;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String st = items.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.list_2_item, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.l_r);
        swipeList.setOnElementClick(ll);

        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(st);
        return view;
    }
}
