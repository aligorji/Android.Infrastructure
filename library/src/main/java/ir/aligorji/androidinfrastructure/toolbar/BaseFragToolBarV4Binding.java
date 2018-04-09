package ir.aligorji.androidinfrastructure.toolbar;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import ir.aligorji.androidinfrastructure.R;


public abstract class BaseFragToolBarV4Binding<TBinding extends ViewDataBinding, T extends Fragment> extends BaseFragToolBarV4<T>
{

    private TBinding mBinding;

    public BaseFragToolBarV4Binding(T fragment, @LayoutRes int layoutRes)
    {
        this(fragment, layoutRes, R.id.toolbar);
    }

    public BaseFragToolBarV4Binding(T fragment, @LayoutRes int layoutRes, @IdRes int toolbarResource)
    {
        super(fragment, layoutRes, toolbarResource);

        mBinding = null;

        binding();
    }

    @Override
    protected boolean isBinding()
    {
        return true;
    }

    @Override
    protected View initView()
    {
        LayoutInflater mInflater = LayoutInflater.from(getFragment().getActivity());

        mBinding = DataBindingUtil.inflate(mInflater, getLayoutRes(), null, false);
        return mBinding.getRoot();
    }

    public final TBinding getBinding()
    {
        return mBinding;
    }

}
