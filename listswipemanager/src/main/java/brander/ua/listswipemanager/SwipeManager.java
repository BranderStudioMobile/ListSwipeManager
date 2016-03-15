package brander.ua.listswipemanager;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import java.util.Date;

public class SwipeManager implements View.OnTouchListener {

    public static int SWIPE_DIRECT_LEFT = 0;
    public static int SWIPE_DIRECT_RIGHT = 1;
    public static int SWIPE_MODE_OPEN = 0;
    public static int SWIPE_MODE_CLOSE = 1;

    private View viewSwipe = null;
    private int width = -1000;
    private int downX, downY, _downX; // Coordinates
    private int deltaX;
    private int maxDeltaLeft;
    private int halfDeltaLeft;
    private int maxDeltaRight;
    private int halfDeltaRight;
    private int HORIZONTAL_MIN_DISTANCE = 20;
    private int VERTICAL_MIN_DISTANCE = 20;
    private int HORIZONTAL_MIN_CLICK = 7;
    private int VERTICAL_MIN_CLICK = 7;
    private RecyclerView recyclerView;
    private ListView listView;
    private SwipeCallback swipeCallBack;
    private int mFirstVisibleItem;
    private int mPosition;
    private View viewItem;
    private int coordinateLeft, coordinateTop;
    private int deltaT, deltaS, sleepT, countStep, currentStep;
    private int signDeltaX, signEndX, endX;
    private boolean firstStep;
    private int swipeDirect, swipeMode;

    public SwipeManager(RecyclerView rv, SwipeCallback swipeCB) {
        recyclerView = rv;
        recyclerView.setOnTouchListener(this);
        swipeCallBack = swipeCB;
    }

    public SwipeManager(ListView lv, SwipeCallback swipeCB) {
        listView = lv;
        listView.setOnTouchListener(this);
        listView.setOnScrollListener(onScrollListener);
        swipeCallBack = swipeCB;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (width <= 0) {
                    width = setWidthAndCoordinate();
                }
                downX = (int)event.getX();
                _downX = downX;
                downY = (int)event.getY();
                deltaX = 0;
                setItemAndPosition(event);
                if (viewItem != null) {
                    maxDeltaRight = swipeCallBack.getOffsetRight(viewItem, mPosition);
                    maxDeltaLeft = swipeCallBack.getOffsetLeft(viewItem, mPosition);
                    if (maxDeltaLeft > width) maxDeltaLeft = width;
                    if (maxDeltaRight > width) maxDeltaRight = width;
                    halfDeltaLeft = maxDeltaLeft / 2;
                    halfDeltaRight = maxDeltaRight / 2;
                    viewSwipe = swipeCallBack.getSwipeView(viewItem, mPosition);
                    downX -= viewSwipe.getLeft();
                }
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                int getX = (int)event.getX();
                int getY = (int)event.getY();
                if (Math.abs(downY - getY) > VERTICAL_MIN_DISTANCE) return false;
                deltaX = getX - downX;
                if ((Math.abs(getX - _downX) > HORIZONTAL_MIN_DISTANCE && viewSwipe != null)) {
                    if (deltaX > maxDeltaLeft) deltaX = maxDeltaLeft;
                    if (deltaX < -maxDeltaRight) deltaX = - maxDeltaRight;
                    animationChangeView(deltaX);
                    return true;
                }
                return false;
            }
            case MotionEvent.ACTION_UP: {
                if (Math.abs(_downX - (int) event.getX()) < HORIZONTAL_MIN_CLICK
                        && Math.abs(downY - (int) event.getY()) < VERTICAL_MIN_CLICK) {
                    swipeCallBack.onElementClick(viewItem, null, mPosition);
                }
                if (Math.abs(_downX - (int) event.getX()) < HORIZONTAL_MIN_DISTANCE) return  false;
                if (viewItem != null) {
                    animationSwipeEnder(120);
                }
            }
        }
        return false;
    }

    private void animationSwipeEnder(int d) {
        signDeltaX = 1;
        signEndX = 1;
        if (deltaX > 0) {
            swipeDirect = SWIPE_DIRECT_RIGHT;
            if (deltaX > halfDeltaLeft) {
                swipeMode = SWIPE_MODE_OPEN;
                endX = maxDeltaLeft;
            } else {
                swipeMode = SWIPE_MODE_CLOSE;
                endX = 0;
                signDeltaX = -1;
            }
        } else {
            swipeDirect = SWIPE_DIRECT_LEFT;
            if (deltaX < -halfDeltaRight) {
                swipeMode = SWIPE_MODE_OPEN;
                endX = - maxDeltaRight;
                signDeltaX = -1;
                signEndX = -1;
            } else {
                swipeMode = SWIPE_MODE_CLOSE;
                endX = 0;
            }
        }
        deltaT = 10;
        countStep = d / deltaT;
        currentStep = 0;
        deltaS = endX - deltaX;
        firstStep = true;
        animationStep();
    }

    private void animationStep() {
        currentStep++;
        deltaX += deltaS * currentStep / countStep;
        if (signDeltaX * deltaX < signEndX * endX) {
            if (firstStep) {
                firstStep = false;
                long beginT = new Date().getTime();
                animationChangeView(deltaX);
                long endT = new Date().getTime();
                int t = (int) (endT - beginT);
                if (t < deltaT) {
                    sleepT = deltaT - t;
                } else {
                    sleepT = 0;
                }
            } else {
                animationChangeView(deltaX);
            }
            if (sleepT > 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animationStep();
                    }
                }, sleepT);
            } else {
                animationStep();
            }
        } else {
            deltaX  = endX;
            animationChangeView(deltaX);
            swipeCallBack.onEndSwipe(viewItem, mPosition, swipeDirect, swipeMode);
        }
    }

    private void animationChangeView(int delta) {
        if (viewItem == null) return;
        viewSwipe.setLeft(deltaX);
        viewSwipe.setRight(width + deltaX);
        if (deltaX > 0) {
            swipeDirect = SWIPE_DIRECT_RIGHT;
        } else {
            swipeDirect = SWIPE_DIRECT_LEFT;
        }
        swipeCallBack.onChangeScroll(viewItem, mPosition, deltaX, swipeDirect, swipeMode, width);
    }

    public void setOnElementClick(View v) {
        v.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int position;
            Rect rectView = new Rect();
            v.getGlobalVisibleRect(rectView, null);
            if (recyclerView == null) {
                position = listView.pointToPosition(rectView.left - coordinateLeft, rectView.top - coordinateTop);
                viewItem = listView.getChildAt(position - mFirstVisibleItem);
            } else {
                RecyclerView.ViewHolder holder = findChildViewHolder(recyclerView,
                        rectView.left - coordinateLeft, rectView.top - coordinateTop);
                viewItem = holder.itemView;
                position = getSynchronizedPosition(holder);
            }
            if (position != RecyclerView.NO_POSITION) {
                swipeCallBack.onElementClick(viewItem, v, position);
            }
        }
    };

    private int setWidthAndCoordinate() {
        int w;
        Rect rect = new Rect();
        if (recyclerView == null) {
            w = listView.getWidth()
                    - listView.getPaddingLeft()
                    - listView.getPaddingRight();
            listView.getGlobalVisibleRect(rect, null);
        } else {
            w = recyclerView.getWidth()
                    - recyclerView.getPaddingLeft()
                    - recyclerView.getPaddingRight();
            recyclerView.getGlobalVisibleRect(rect, null);
        }
        coordinateLeft = rect.left;
        coordinateTop = rect.top;
        return w;
    }

    private void setItemAndPosition(MotionEvent event) {
        if (recyclerView == null) {
            mPosition = listView.pointToPosition(downX, downY);
            viewItem = listView.getChildAt(mPosition - mFirstVisibleItem);
        } else {
            RecyclerView.ViewHolder holder = findChildViewHolder(recyclerView, event.getX(), event.getY());
            mPosition = getSynchronizedPosition(holder);
            viewItem = holder.itemView;
        }
    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            mFirstVisibleItem = firstVisibleItem;
        }
    };

    public static RecyclerView.ViewHolder findChildViewHolder(RecyclerView rv, float x, float y) {
        final View child = rv.findChildViewUnder(x, y);
        return (child != null) ? rv.getChildViewHolder(child) : null;
    }

    public static int getSynchronizedPosition(RecyclerView.ViewHolder holder) {
        int pos1 = holder.getLayoutPosition();
        int pos2 = holder.getAdapterPosition();
        if (pos1 == pos2) {
            return pos1;
        } else {
            return RecyclerView.NO_POSITION;
        }
    }

}
