package ir.aligorji.androidinfrastructure.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BindingPagerAdapter<T extends BaseObservable, TBinding extends ViewDataBinding> extends PagerAdapter
{

    private final List<T> mItems;


    public BindingPagerAdapter(List<T> items)
    {
        this.mItems = items;
    }

    @NonNull
    @Override
    public final Object instantiateItem(@NonNull ViewGroup container, int position)
    {

        TBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), getItemViewId(position), container, false);

        //--

        int variableId = getBindingVariableId();
        if (variableId > 0)
        {
            binding.setVariable(variableId, getItem(position));
            binding.executePendingBindings();
        }
        onBinding(binding, position);

        //--

        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public final void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public final int getCount()
    {
        return mItems.size();
    }

    @Override
    public final boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    public final T getItem(int position)
    {
        return mItems.get(position);
    }

    protected abstract int getBindingVariableId();
    protected abstract int getItemViewId(int position);
    protected abstract void onBinding(TBinding binding, int position);
}