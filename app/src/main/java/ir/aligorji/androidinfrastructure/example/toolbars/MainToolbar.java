package ir.aligorji.androidinfrastructure.example.toolbars;


import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.aligorji.androidinfrastructure.example.R;
import ir.aligorji.androidinfrastructure.toolbar.BaseToolBar;

public final class MainToolbar extends BaseToolBar
{


    private View uiBtnRefrsh;


    public MainToolbar(AppCompatActivity activity)
    {
        super(activity, R.layout.toolbar_main);
    }


    @Override
    protected int[] getItems()
    {
        return new int[]{
                R.id.toolbar_refresh
        };
    }

    @Override
    protected void initView(View rootView)
    {
        uiBtnRefrsh = rootView.findViewById(R.id.toolbar_refresh);
    }

    @Override
    protected void onItemsClick(View v)
    {
        int id = v.getId();

    }
    public void setRefreshable(boolean value)
    {
        uiBtnRefrsh.setVisibility((value) ? View.VISIBLE : View.GONE);
    }
}
