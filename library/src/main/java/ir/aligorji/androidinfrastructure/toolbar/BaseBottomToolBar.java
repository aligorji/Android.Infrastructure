package ir.aligorji.androidinfrastructure.toolbar;


import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.aligorji.androidinfrastructure.R;


public abstract class BaseBottomToolBar
{

    private final Activity mActivity;
    private final int mLayoutRes;
    private final ViewGroup mToolbar;

    public BaseBottomToolBar(Activity activity, @LayoutRes int layoutRes)
    {
        this(activity, layoutRes, R.id.toolbar_bottom);
    }

    public BaseBottomToolBar(Activity activity, @LayoutRes int layoutRes, @IdRes int toolbarResource)
    {

        mActivity = activity;
        mToolbar = (ViewGroup) activity.findViewById(toolbarResource);
        mLayoutRes = layoutRes;

        if (mToolbar == null)
        {
            throw new RuntimeException("##### Not found bottom toolbar");
        }

        initToolbar();

    }

    private void initToolbar()
    {


        LayoutInflater mInflater = LayoutInflater.from(mActivity);

        View rootView = mInflater.inflate(mLayoutRes, null);

        int[] items = getItems();
        if (items != null)
        {
            for (int id : items)
            {
                View v = rootView.findViewById(id);
                if (v != null)
                {
                    v.setOnClickListener(mClickListener);
                }
            }
        }

        mToolbar.addView(rootView);

        initView(rootView);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            if (mActivity instanceof OnToolBarItemClick)
            {
                boolean result = ((OnToolBarItemClick) mActivity).onToolBarItemsClick(v);
                if (!result)
                {
                    return;
                }
            }

            onItemsClick(v);

        }
    };

    public ViewGroup getToolbar()
    {
        return mToolbar;
    }


    public Activity getActivity()
    {
        return mActivity;
    }

    protected abstract int[] getItems();

    protected abstract void initView(View rootView);

    protected abstract void onItemsClick(View v);


}
