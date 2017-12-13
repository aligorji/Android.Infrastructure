package ir.aligorji.androidinfrastructure.widget;


import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import ir.aligorji.androidinfrastructure.adapters.DefaultAdapter;

public class BindingRecyclerTouchListener implements RecyclerView.OnItemTouchListener
{

    private final DefaultAdapter mAdapter;
    private final GestureDetector gestureDetector;
    private final BindingRecyclerOnClickListener clickListener;

    public BindingRecyclerTouchListener(Context context, final RecyclerView recyclerView, final DefaultAdapter adapter, final BindingRecyclerOnClickListener clickListener)
    {
        this.clickListener = clickListener;
        this.mAdapter = adapter;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null)
                {
                    int position = recyclerView.getChildAdapterPosition(child);

                    clickListener.onRecyclerItemLongClickListener(child, (BaseObservable) mAdapter.getItem(position), position);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
    {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
        {
            int position = rv.getChildAdapterPosition(child);

            clickListener.onRecyclerItemClickListener(child, (BaseObservable) mAdapter.getItem(position), position);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e)
    {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
    {

    }

}