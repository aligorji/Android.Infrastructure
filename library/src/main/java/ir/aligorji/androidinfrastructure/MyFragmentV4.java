package ir.aligorji.androidinfrastructure;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ir.aligorji.androidinfrastructure.adapters.DefaultAdapter;
import ir.aligorji.androidinfrastructure.widget.BindingRecyclerOnClickListener;
import ir.aligorji.androidinfrastructure.widget.BindingRecyclerTouchListener;
import ir.aligorji.androidinfrastructure.widget.RecyclerOnClickListener;
import ir.aligorji.androidinfrastructure.widget.RecyclerTouchListener;


public abstract class MyFragmentV4 extends Fragment
{

    protected View mRootView;
    private boolean mIsFinished = false;


    @Override
    public final void onAttach(Activity activity)
    {
        super.onAttach(activity);
        onAttachFinal(activity);
    }

    @Override
    public final void onAttach(Context context)
    {
        super.onAttach(context);
        onAttachFinal(context);
    }

    protected void onAttachFinal(Context context)
    {
        //can override
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(getContentView(), container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (!isValidInit()) return;

        if (getArguments() != null)
        {
            onTakeArguments(getArguments());

            //-----------------------------------------------
            if (mIsFinished) return;
            //-----------------------------------------------
        }

        onInitActionBar();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onInitView();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onInitAdapter();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onLoadData();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onStartupNetworkRequest();

    }

    protected final MyActivity getSamimActivity()
    {
        Activity activity = getActivity();

        if (activity instanceof MyActivity)
        {
            return (MyActivity) activity;
        }
        return null;
    }

    protected final View getRootView()
    {
        return mRootView;
    }


    // █████  ██████  ███████ ████████ ██████   █████   ██████ ████████
    //██   ██ ██   ██ ██         ██    ██   ██ ██   ██ ██         ██
    //███████ ██████  ███████    ██    ██████  ███████ ██         ██
    //██   ██ ██   ██      ██    ██    ██   ██ ██   ██ ██         ██
    //██   ██ ██████  ███████    ██    ██   ██ ██   ██  ██████    ██

    protected abstract
    @LayoutRes
    int getContentView();

    protected boolean isValidInit()
    {
        return true;
    }

    protected abstract void onTakeArguments(Bundle arguments);

    protected abstract void onInitActionBar();

    protected abstract void onInitView();

    protected abstract void onInitAdapter();

    protected abstract void onLoadData();

    protected abstract void onStartupNetworkRequest();


    //██ ███    ███ ██████  ██      ███████ ███    ███ ███████ ███    ██ ████████ ███████
    //██ ████  ████ ██   ██ ██      ██      ████  ████ ██      ████   ██    ██    ██
    //██ ██ ████ ██ ██████  ██      █████   ██ ████ ██ █████   ██ ██  ██    ██    ███████
    //██ ██  ██  ██ ██      ██      ██      ██  ██  ██ ██      ██  ██ ██    ██         ██
    //██ ██      ██ ██      ███████ ███████ ██      ██ ███████ ██   ████    ██    ███████


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }


    //██   ██ ███████ ██      ██████  ███████ ██████
    //██   ██ ██      ██      ██  ██  ██      ██   ██
    //███████ █████   ██      ██████  █████   ██████
    //██   ██ ██      ██      ██      ██      ██   ██
    //██   ██ ███████ ███████ ██      ███████ ██   ██

    protected void stop()
    {
        mIsFinished = true;
    }

    ////
    //UI
    ////

    protected final View findViewById(@IdRes int id)
    {
        return mRootView.findViewById(id);
    }

    protected final TextView findTextView(@IdRes int id)
    {
        View v = findViewById(id);
        if (v != null)
        {
            return (TextView) v;
        }
        return null;
    }

    protected final ImageView findImageView(@IdRes int id)
    {
        View v = findViewById(id);
        if (v != null)
        {
            return (ImageView) v;
        }
        return null;
    }

    protected final <T> T findView(@IdRes int id, Class<? extends View> t)
    {
        View v = findViewById(id);
        if (v != null && v.getClass().equals(t))
        {
            return (T) v;
        }
        return null;
    }

    public boolean isReadyUi()
    {
        Activity activity = getActivity();

        if (activity == null)
        {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            return !activity.isFinishing() && !activity.isDestroyed();
        }
        else
        {
            return !activity.isFinishing();
        }
    }

    //////
    //List
    //////

    protected ListView findListView()
    {
        return (ListView) findViewById(android.R.id.list);
    }

    //////////////
    //SwipeRefreshLayout
    //////////////

    protected SwipeRefreshLayout findSwipeRefreshLayout()
    {
        return (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    }

    protected SwipeRefreshLayout initSwipeRefreshLayout(@ColorInt int... colors)
    {
        final SwipeRefreshLayout swipeRefreshLayout = findSwipeRefreshLayout();

        if (swipeRefreshLayout != null)
        {
            swipeRefreshLayout.setColorSchemeColors(colors);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    onRefreshSwipeRefreshLayout(swipeRefreshLayout);
                }
            });
        }


        return swipeRefreshLayout;

    }

    protected void onRefreshSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout)
    {

    }

    //////////////
    //RecyclerView
    //////////////

    protected RecyclerView findRecyclerView()
    {
        return (RecyclerView) findViewById(R.id.recycler);
    }

    protected void setRecyclerOnItemClickListener(BindingRecyclerOnClickListener listener, DefaultAdapter adapter)
    {
        RecyclerView recyclerView = findRecyclerView();
        if (recyclerView != null)
        {
            recyclerView.addOnItemTouchListener(new BindingRecyclerTouchListener(recyclerView, adapter, listener));
        }
    }

    protected void setRecyclerOnItemClickListener(RecyclerOnClickListener listener)
    {
        RecyclerView recyclerView = findRecyclerView();
        if (recyclerView != null)
        {
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(recyclerView, listener));
        }
    }


}
