package brander.ua.swipe_list_recycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import brander.ua.swipe_list_recycler.fragments.ListViewOneFragment;
import brander.ua.swipe_list_recycler.fragments.ListViewTwoFragment;
import brander.ua.swipe_list_recycler.fragments.RecyclerViewFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.list_1)
    public void showListViewOne() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ListViewOneFragment(), "List_1")
                .commit();
    }

    @OnClick(R.id.list_2)
    public void showListViewTwo() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ListViewTwoFragment(), "List_2")
                .commit();
    }

    @OnClick(R.id.recycler)
    public void showRecyclerView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new RecyclerViewFragment(), "Recycler")
                .commit();
    }
}
