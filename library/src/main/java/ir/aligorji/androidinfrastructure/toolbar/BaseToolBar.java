package ir.aligorji.androidinfrastructure.toolbar;


import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ir.aligorji.androidinfrastructure.R;


public abstract class BaseToolBar<T extends AppCompatActivity>
{

    private final Toolbar mToolbar;
    private final T mActivity;
    private final int mLayoutRes;
    private TextView uiTxvTitle = null;
    private TextView uiTxvSubtitle = null;

    public BaseToolBar(T activity, @LayoutRes int layoutRes)
    {
        this(activity, layoutRes, R.id.toolbar);
    }

    public BaseToolBar(T activity, @LayoutRes int layoutRes, @IdRes int toolbarResource)
    {

        mActivity = activity;
        mToolbar = (Toolbar) activity.findViewById(toolbarResource);
        mLayoutRes = layoutRes;

        if (mToolbar == null)
        {
            throw new RuntimeException("##### Not found toolbar");
        }

        initActionbar();

    }

    private void initActionbar()
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

        uiTxvTitle = (TextView) rootView.findViewById(R.id.title);
        uiTxvSubtitle = (TextView) rootView.findViewById(R.id.subtitle);

        mToolbar.removeAllViews();

        mToolbar.addView(rootView);

        mActivity.setSupportActionBar(mToolbar);

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

    public Toolbar getToolbar()
    {
        return mToolbar;
    }

    public void setTitle(String s)
    {
        if (uiTxvTitle != null)
        {
            uiTxvTitle.setText(s);
        }
    }

    public void setTitle(int res)
    {
        if (uiTxvTitle != null)
        {
            uiTxvTitle.setText(res);
        }
    }

    public void setSubtitle(String s)
    {
        if (uiTxvSubtitle != null)
        {
            uiTxvSubtitle.setText(s);
        }
    }

    public void setSubtitle(int res)
    {
        if (uiTxvSubtitle != null)
        {
            uiTxvSubtitle.setText(res);
        }
    }

    public T getActivity()
    {
        return mActivity;
    }

    protected abstract int[] getItems();

    protected abstract void initView(View rootView);

    protected abstract void onItemsClick(View v);


}
