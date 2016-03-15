package brander.ua.listswipemanager;

import android.view.View;

/**
 * Created by user on 15.03.16.
 */
public interface SwipeCallback {

    /**
     * Method for picking necessary list item view.
     * Receive all list item layout and returns exactly need view for swiping.
     * @param viewItem list item(full layout with all views),
     * @param position current working position(should be derived from the List/Recycler adapter)
     */
    View getSwipeView(View viewItem, int position);

    /**
     * Method provide fixed integer value for right swipe offset.
     * Get's all list item layout and returns integer value for right offset of the swiping view.
     * @param viewItem list item(full layout with all views),
     * @param position current working position(should be derived from the List/Recycler adapter)
     * */
    int getOffsetRight(View viewItem, int position);

    /**
     * Method provide fixed integer value for left swipe offset.
     * Get's all list item layout and returns integer value for left offset of the swiping view.
     * @param viewItem list item(full layout with all views),
     * @param position current working position(should be derived from the List/Recycler adapter)
     * */
    int getOffsetLeft(View viewItem, int position);

    /**
     * Method for user handling swipe ent events.
     * Method will be called each time when swiped view disappearing from the screen.
     * @param viewItem view returned by getSwipeView()
     * @param position position in list(derived from the adapter)
     * @param swipeDirect integer value(can be SwipeManager.SWIPE_DIRECT_LEFT or SwipeManager.SWIPE_DIRECT_RIGHT)
     * @param swipeMode integer value(can be SwipeManager.SWIPE_MODE_OPEN or SwipeManager.SWIPE_MODE_CLOSE)
     * */
    void onEndSwipe(View viewItem, int position, int swipeDirect, int swipeMode);

    /**
     * Method for items clicks detecting.
     * Will be called each time when one of tne swipe view layout items was touched(clicked).
     * Note: in term of click means MotionEvent.ACTION_UP event!
     * @param viewItem root swipe view layout
     * @param element view, which was clicked
     *                @param position root view list position
     * */
    void onElementClick(View viewItem, View element, int position);

    /**
     * Method will be called each time, when swipe view is moving.
     * You should override method logic if you want to do any actions with left or right context views.
     * @param viewItem root swipe view layout
     * @param position root view list position
     * @param deltaX distance which view passed during swiping
     * @param swipeDirect integer value(can be SwipeManager.SWIPE_DIRECT_LEFT or SwipeManager.SWIPE_DIRECT_RIGHT)
     * @param swipeMode integer value(can be SwipeManager.SWIPE_MODE_OPEN or SwipeManager.SWIPE_MODE_CLOSE)
     * */
    void onChangeScroll(View viewItem, int position, int deltaX, int swipeDirect, int swipeMode, int width);
}
