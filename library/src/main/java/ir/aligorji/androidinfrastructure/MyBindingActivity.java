package ir.aligorji.androidinfrastructure;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

public abstract class MyBindingActivity<T extends ViewDataBinding> extends MyActivity
{

    private T mBinding = null;

    @Override
    protected final void onInitContentView()
    {
        mBinding = DataBindingUtil.setContentView(this, getContentView());
    }

    protected final T getBinding()
    {
        return mBinding;
    }


}
