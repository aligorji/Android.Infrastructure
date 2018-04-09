package ir.aligorji.androidinfrastructure.toolbar;


import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public abstract class BaseFragToolBarV4<T extends Fragment>
{

    private final Toolbar mToolbar;
    private final T mFragment;
    private final int mLayoutRes;
    private TextView uiTxvTitle = null;
    private TextView uiTxvSubtitle = null;

    public BaseFragToolBarV4(T fragment, @LayoutRes int layoutRes)
    {
        this(fragment, layoutRes, ir.aligorji.androidinfrastructure.R.id.toolbar);
    }

    public BaseFragToolBarV4(T fragment, @LayoutRes int layoutRes, @IdRes int toolbarResource)
    {

        mFragment = fragment;
        mToolbar = fragment.getView().findViewById(toolbarResource);
        mLayoutRes = layoutRes;

        if (mToolbar == null)
        {
            throw new RuntimeException("##### Not found toolbar");
        }

        initActionbar();

    }

    private void initActionbar()
    {

        View rootView = initView();

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

        uiTxvTitle = rootView.findViewById(ir.aligorji.androidinfrastructure.R.id.title);
        uiTxvSubtitle = rootView.findViewById(ir.aligorji.androidinfrastructure.R.id.subtitle);

        mToolbar.removeAllViews();

        mToolbar.addView(rootView);

        initView(rootView);
    }

    protected View initView()
    {
        LayoutInflater mInflater = LayoutInflater.from(mFragment.getActivity());

        return mInflater.inflate(mLayoutRes, null);
    }

    protected int getLayoutRes()
    {
        return mLayoutRes;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            if (mFragment instanceof OnToolBarItemClick)
            {
                boolean result = ((OnToolBarItemClick) mFragment).onToolBarItemsClick(v);
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

    public T getFragment()
    {
        return mFragment;
    }

    protected abstract int[] getItems();

    protected abstract void initView(View rootView);

    protected abstract void onItemsClick(View v);


}
