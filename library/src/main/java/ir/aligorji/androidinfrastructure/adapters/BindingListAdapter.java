package ir.aligorji.androidinfrastructure.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ir.aligorji.androidinfrastructure.models.IdentityViewModel;

public abstract class BindingListAdapter<T extends BaseObservable, TBinding extends ViewDataBinding> extends BaseAdapter
{

    private final List<T> mItems;

    protected BindingListAdapter(List<T> items)
    {
        mItems = items;
    }

    @Override
    public final int getCount()
    {
        return mItems.size();
    }
    @Override
    public final T getItem(int position)
    {
        return mItems.get(position);
    }
    @Override
    public final long getItemId(int position)
    {
        T item = getItem(position);

        if (item instanceof IdentityViewModel && ((IdentityViewModel) item).getId() instanceof Number)
        {
            return ((Number) ((IdentityViewModel) item).getId()).longValue();
        }
        return -1;
    }
    @Override
    public final View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;

        if (rowView == null)
        {
            TBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemViewType(position), parent, false);

            rowView = binding.getRoot();
            rowView.setTag(new ViewHolder(binding));
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        int variableId = getBindingVariableIdInBR();
        if (variableId > 0)
        {
            holder.mBinding.setVariable(variableId, getItem(position));
            holder.mBinding.executePendingBindings();
        }
        onBinding((TBinding) holder.mBinding, position);

        return rowView;

    }

    public abstract int getItemViewType(int position);

    protected abstract int getBindingVariableIdInBR();

    protected abstract void onBinding(TBinding binding, int position);

    public static class ViewHolder
    {

        public final ViewDataBinding mBinding;

        public ViewHolder(ViewDataBinding binding)
        {
            mBinding = binding;
        }
    }
}
