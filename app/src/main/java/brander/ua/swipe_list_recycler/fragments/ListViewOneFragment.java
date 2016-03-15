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
import brander.ua.swipe_list_recycler.adapters.ListViewOneAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewOneFragment extends Fragment {

    private View parentLayout;
    private Activity activity;
    private List<String> stringList;
    @Bind(R.id.list) ListView list;
    private ListViewOneAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentLayout = inflater.inflate(R.layout.list_1, container, false);
        ButterKnife.bind(this, parentLayout);
        activity = getActivity();
        formList();
        SwipeManager swipeManager = new SwipeManager(list, onSwipeListener);
        adapter = new ListViewOneAdapter(activity, stringList, swipeManager);
        list.setAdapter(adapter);
        return parentLayout;
    }

    private void formList() {
        stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("list_1 p " + i);
        }
    }

    SwipeCallback onSwipeListener = new SwipeCallback() {
        @Override
        public View getSwipeView(View viewItem, int position) {
            return viewItem.findViewById(R.id.swipe);
        }

        @Override
        public int getOffsetRight(View viewItem, int position) {
            return getResources().getDimensionPixelSize(R.dimen._right);
        }

        @Override
        public int getOffsetLeft(View viewItem, int position) {
            return getResources().getDimensionPixelSize(R.dimen._left);
        }

        @Override
        public void onEndSwipe(View viewItem, int position, int swipeDirect, int swipeMode) {
            if (swipeDirect == SwipeManager.SWIPE_DIRECT_RIGHT) {
//                some elements can be removed, for example
//                list_1.remove(position);
//                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onElementClick(View viewItem, View element, int position) {
            if (element == null) {
                Toast.makeText(activity, "Item position = " + position, Toast.LENGTH_SHORT).show();
            } else {
                switch (element.getId()) {
                    case R.id.right:
                        Toast.makeText(activity, "Image element IMG position = " + position, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.color_r:
                        Toast.makeText(activity, "Color element RRR position = " + position, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.color_g:
                        Toast.makeText(activity, "Color element GGG position = " + position, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.color_b:
                        Toast.makeText(activity, "Color element BBB position = " + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(activity, "Element position = " + position, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onChangeScroll(View viewItem, int position, int deltaX, int swipeDirect, int swipeMode, int w) {
            //just opening left side view and show colored lined with pseudo animation
            if (swipeDirect == SwipeManager.SWIPE_DIRECT_RIGHT) {
                LinearLayout ll = (LinearLayout) viewItem.findViewById(R.id.left);
                int h = ll.getHeight();
                LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(deltaX, h);
                ll.setLayoutParams(rl);
            }
        }
    };
}
