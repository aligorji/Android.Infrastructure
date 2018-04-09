package ir.aligorji.androidinfrastructure;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ir.aligorji.androidinfrastructure.adapters.DefaultAdapter;
import ir.aligorji.androidinfrastructure.widget.BindingRecyclerOnClickListener;
import ir.aligorji.androidinfrastructure.widget.BindingRecyclerTouchListener;
import ir.aligorji.androidinfrastructure.widget.RecyclerOnClickListener;
import ir.aligorji.androidinfrastructure.widget.RecyclerTouchListener;


public abstract class MyActivity extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener
{

    protected final AppCompatActivity THIS;
    private boolean mIsFinished = false;

    public MyActivity()
    {
        super();
        THIS = this;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        onPreSetContentView();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onInitContentView();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onLoadSetting();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        Intent intent = getIntent();
        if (intent != null)
        {
            onLoadIntentDate(intent);
        }

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onLoadSavedInstanceDate(savedInstanceState);

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onLoadDatabaseDate();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onInitActionBar();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        getFragmentManager().addOnBackStackChangedListener(this);

        onInitView();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onInitAdapter();

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        if (savedInstanceState == null)
        {
            onSubmitDefaultFragment();
        }

        //-----------------------------------------------
        if (mIsFinished) return;
        //-----------------------------------------------

        onStartupNetworkRequest();
    }


    protected abstract
    @LayoutRes
    int getContentView();


    //██     ██  ██████  ██████  ██   ██ ███████ ██████      ███    ███ ███████ ████████ ██   ██  ██████  ██████
    //██     ██ ██    ██ ██   ██ ██  ██  ██      ██   ██     ████  ████ ██         ██    ██   ██ ██    ██ ██   ██
    //██  █  ██ ██    ██ ██████  █████   █████   ██████      ██ ████ ██ █████      ██    ███████ ██    ██ ██   ██
    //██ ███ ██ ██    ██ ██   ██ ██  ██  ██      ██   ██     ██  ██  ██ ██         ██    ██   ██ ██    ██ ██   ██
    // ███ ███   ██████  ██   ██ ██   ██ ███████ ██   ██     ██      ██ ███████    ██    ██   ██  ██████  ██████


    protected void onInitContentView()
    {
        setContentView(getContentView());
    }

    @Override
    public void finish()
    {
        mIsFinished = true;
        super.finish();
    }

    protected abstract void onPreSetContentView();

    protected abstract void onLoadSetting();

    protected abstract void onLoadIntentDate(@NonNull Intent intent);

    protected abstract void onLoadSavedInstanceDate(@Nullable Bundle savedInstanceState);

    protected abstract void onLoadDatabaseDate();

    protected abstract void onInitActionBar();

    protected abstract void onInitView();

    protected abstract void onInitAdapter();

    protected abstract void onSubmitDefaultFragment();

    protected abstract void onStartupNetworkRequest();


    //██ ███    ███ ██████  ██      ███████ ███    ███ ███████ ███    ██ ████████ ███████
    //██ ████  ████ ██   ██ ██      ██      ████  ████ ██      ████   ██    ██    ██
    //██ ██ ████ ██ ██████  ██      █████   ██ ████ ██ █████   ██ ██  ██    ██    ███████
    //██ ██  ██  ██ ██      ██      ██      ██  ██  ██ ██      ██  ██ ██    ██         ██
    //██ ██      ██ ██      ███████ ███████ ██      ██ ███████ ██   ████    ██    ███████


    @Override
    public void onBackStackChanged()
    {
    }

    @Override
    protected void onDestroy()
    {
        getFragmentManager().removeOnBackStackChangedListener(this);
        super.onDestroy();
    }


    //██   ██ ███████ ██      ██████  ███████ ██████
    //██   ██ ██      ██      ██   ██ ██      ██   ██
    //███████ █████   ██      ██████  █████   ██████
    //██   ██ ██      ██      ██      ██      ██   ██
    //██   ██ ███████ ███████ ██      ███████ ██   ██


    ////
    //UI
    ////

    public boolean isReadyUi()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            return !isFinishing() && !isDestroyed() && !mIsFinished;
        }
        else
        {
            return !isFinishing() && !mIsFinished;
        }
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

    //////////
    //Fragment
    //////////

    public void popBackStack()
    {
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();
    }

    public boolean popBackStackUntil(Class<? extends Fragment> t)
    {
        FragmentManager fm = getFragmentManager();
        while (fm.getBackStackEntryCount()>0)
        {
            Fragment fragment = getCurrentFragment();
            if (fragment != null && fragment.getClass().equals(t))
            {
                return true;
            }
            fm.popBackStackImmediate();
        }
        return false;
    }

    protected Fragment getCurrentFragment()
    {
        return getCurrentFragment(R.id.fragment_place);
    }

    protected Fragment getCurrentFragment(int id)
    {
        return getFragmentManager().findFragmentById(id);
    }

    protected <T extends Fragment> T getCurrentFragment(Class<? extends Fragment> t)
    {
        return getCurrentFragment(t, R.id.fragment_place);
    }

    protected <T extends Fragment> T getCurrentFragment(Class<? extends Fragment> t, int id)
    {
        Fragment fragment = getFragmentManager().findFragmentById(id);
        if (fragment != null && fragment.getClass().equals(t))
        {
            return (T) fragment;
        }
        return null;
    }

    //////
    //List
    //////

    private ListView getListView()
    {
        return (ListView) findViewById(android.R.id.list);
    }

    //////////////
    //RecyclerView
    //////////////

    private RecyclerView getRecyclerView()
    {
        return (RecyclerView) findViewById(R.id.recycler);
    }

    protected void setRecyclerOnItemClickListener(RecyclerView recyclerView, BindingRecyclerOnClickListener listener, DefaultAdapter adapter)
    {
        if (recyclerView != null)
        {
            recyclerView.addOnItemTouchListener(new BindingRecyclerTouchListener(recyclerView, adapter, listener));
        }
    }

    protected void setRecyclerOnItemClickListener(BindingRecyclerOnClickListener listener, DefaultAdapter adapter)
    {
        setRecyclerOnItemClickListener(getRecyclerView(), listener, adapter);
    }

    protected void setRecyclerOnItemClickListener(RecyclerOnClickListener listener)
    {
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null)
        {
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(recyclerView, listener));
        }
    }

}
