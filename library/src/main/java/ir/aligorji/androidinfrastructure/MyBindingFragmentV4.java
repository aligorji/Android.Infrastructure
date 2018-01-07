package ir.aligorji.androidinfrastructure;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MyBindingFragmentV4<TBinding extends ViewDataBinding> extends MyFragmentV4
{

    private TBinding mBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater, getContentView(), container, false);
        mRootView = mBinding.getRoot();
        return mRootView;
    }

    protected final TBinding getBinding()
    {
        return mBinding;
    }

}
