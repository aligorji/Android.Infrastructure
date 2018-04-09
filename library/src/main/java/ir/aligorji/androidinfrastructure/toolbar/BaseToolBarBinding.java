package ir.aligorji.androidinfrastructure.toolbar;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import ir.aligorji.androidinfrastructure.R;


public abstract class BaseToolBarBinding<TBinding extends ViewDataBinding, T extends AppCompatActivity> extends BaseToolBar<T>
{

    private TBinding mBinding = null;

    public BaseToolBarBinding(T activity, @LayoutRes int layoutRes)
    {
        this(activity, layoutRes, R.id.toolbar);
    }

    public BaseToolBarBinding(T activity, @LayoutRes int layoutRes, @IdRes int toolbarResource)
    {
        super(activity, layoutRes, toolbarResource);
    }

    @Override
    protected View initView()
    {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());

        mBinding = DataBindingUtil.inflate(mInflater, getLayoutRes(), null, false);
        return mBinding.getRoot();
    }

    public final TBinding getBinding()
    {
        return mBinding;
    }

}
