package brander.ua.swipe_list_recycler.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import brander.ua.swipe_list_recycler.R;
import brander.ua.listswipemanager.SwipeManager;
import butterknife.Bind;
import butterknife.ButterKnife;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<String> items;
    private SwipeManager swipeRecycler;

public RecyclerViewAdapter(Context cont, List<String> ls, SwipeManager sr) {
        context = cont;
        items = ls;
        swipeRecycler = sr;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new getHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false));
    }

    class getHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title) TextView title;
        @Bind(R.id.right) ImageView right;
        @Bind(R.id.left) LinearLayout left;
        @Bind(R.id.swipe) LinearLayout swipe;
        @Bind(R.id.color_r) LinearLayout color_r;
        @Bind(R.id.color_g) LinearLayout color_g;
        @Bind(R.id.color_b) LinearLayout color_b;

        public getHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String st = items.get(position);
        ((getHolder)holder).title.setText(st);
        if (swipeRecycler != null) {
            swipeRecycler.setOnElementClick(((getHolder)holder).right);
            swipeRecycler.setOnElementClick(((getHolder)holder).color_r);
            swipeRecycler.setOnElementClick(((getHolder)holder).color_g);
            swipeRecycler.setOnElementClick(((getHolder)holder).color_b);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
