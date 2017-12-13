package ir.aligorji.androidinfrastructure.example.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ir.aligorji.androidinfrastructure.MyActivity;
import ir.aligorji.androidinfrastructure.example.R;
import ir.aligorji.androidinfrastructure.example.fragments.ExamPagingFragment;
import ir.aligorji.androidinfrastructure.example.toolbars.MainToolbar;


public class MainActivity extends MyActivity
{

    private MainToolbar uiToolBar;

    @Override
    protected int getContentView()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void onPreSetContentView()
    {

    }
    @Override
    protected void onLoadSetting()
    {

    }
    @Override
    protected void onLoadIntentDate(@NonNull Intent intent)
    {

    }
    @Override
    protected void onLoadSavedInstanceDate(@Nullable Bundle savedInstanceState)
    {

    }
    @Override
    protected void onLoadDatabaseDate()
    {

    }

    @Override
    protected void onStartupNetworkRequest()
    {

    }


    @Override
    protected void onInitActionBar()
    {
        //uiToolBar = new MainToolbar(this);
        //uiToolBar.setTitle("علی گرجی");

    }
    @Override
    protected void onInitView()
    {

    }
    @Override
    protected void onInitAdapter()
    {

    }

    @Override
    protected void onSubmitDefaultFragment()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_place, ExamPagingFragment.newInstance());
        //ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}
