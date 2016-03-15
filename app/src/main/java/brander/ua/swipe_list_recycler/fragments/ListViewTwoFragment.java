package brander.ua.swipe_list_recycler.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brander.ua.listswipemanager.SwipeCallback;
import brander.ua.swipe_list_recycler.R;
import brander.ua.listswipemanager.SwipeManager;
import brander.ua.swipe_list_recycler.adapters.ListViewTwoAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewTwoFragment extends Fragment {

    private View parentLayout;
    private Activity activity;
    private List<String> stringList;
    @Bind(R.id.list)
    ListView list;
    private ListViewTwoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentLayout = inflater.inflate(R.layout.list_2, container, false);
        ButterKnife.bind(this, parentLayout);
        activity = getActivity();
        formList();
        SwipeManager swipeManager = new SwipeManager(list, onSwipeListener);
        adapter = new ListViewTwoAdapter(activity, stringList, swipeManager);
        list.setAdapter(adapter);
        return parentLayout;
    }

    private void formList() {
        stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("list_2 p " + i);
        }
    }

    SwipeCallback onSwipeListener = new SwipeCallback() {
        @Override
        public View getSwipeView(View viewItem, int position) {
            return viewItem.findViewById(R.id.swipe);
        }

        @Override
        public int getOffsetRight(View viewItem, int position) {
            return 150;
        }

        @Override
        public int getOffsetLeft(View viewItem, int position) {
            return 300;
        }

        @Override
        public void onEndSwipe(View viewItem, int position, int swipeDirect, int swipeMode) {
            if (swipeDirect == SwipeManager.SWIPE_DIRECT_RIGHT && swipeMode == SwipeManager.SWIPE_MODE_OPEN) {
                stringList.remove(position);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onElementClick(View viewItem, View element, int position) {
            if (element == null) {
                Toast.makeText(activity, "Item position = " + position, Toast.LENGTH_SHORT).show();
            } else {
                switch (element.getId()) {
                    case R.id.l_r:
                        Toast.makeText(activity, "Image position = " + position, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }

        @Override
        public void onChangeScroll(View viewItem, int position, int deltaX, int swipeDirect, int swipeMode, int w) {
            //just showing left image
            if (swipeDirect == SwipeManager.SWIPE_DIRECT_LEFT) {
                LinearLayout ll = (LinearLayout) viewItem.findViewById(R.id.l_r);
                ll.setLeft(deltaX + w);
            }
        }
    };
}
