package ir.aligorji.androidinfrastructure.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

import ir.aligorji.androidinfrastructure.models.IdentityViewModel;
import ir.aligorji.androidinfrastructure.widget.OnActionItemClickListener;
import ir.aligorji.androidinfrastructure.widget.OnItemClickListener;

public abstract class BindingListAdapter<T extends BaseObservable, TBinding extends ViewDataBinding> extends BaseAdapter
{

    private final List<T> mItems;
    private HashMap<Integer, OnActionItemClickListener> mActionItems = null;
    private OnItemClickListener mOnClickItemListener = null;

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
    public final View getView(final int position, View convertView, ViewGroup parent)
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

        //On click action items
        if (mActionItems != null && mActionItems.size() > 0)
        {
            for (final int id : mActionItems.keySet())
            {
                final View view = holder.mBinding.getRoot().findViewById(id);
                if (view != null)
                {
                    final OnActionItemClickListener listener = mActionItems.get(id);

                    if (listener != null)
                    {
                        view.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                listener.onActionItemClickListener(view, getItem(position), position);
                            }
                        });
                    }
                    else
                    {
                        view.setOnClickListener(null);
                    }
                }
            }
        }

        //root click handler
        if (mOnClickItemListener != null)
        {
            holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mOnClickItemListener != null)
                    {
                        mOnClickItemListener.onItemClick(v, getItem(position), position);
                    }
                }
            });
        }

        onBinding((TBinding) holder.mBinding, position);

        return rowView;

    }

    public void setActionItemListener(@IdRes int id, @Nullable OnActionItemClickListener<T> listener)
    {
        if (mActionItems == null)
        {
            mActionItems = new HashMap<>();
        }

        mActionItems.put(id, listener);
    }

    public void removeActionItemListener(@IdRes int id)
    {
        if (mActionItems == null)
        {
            return;
        }

        mActionItems.put(id, null);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener)
    {
        mOnClickItemListener = listener;
    }

    public OnItemClickListener<T> getOnItemClickListener()
    {
        return mOnClickItemListener;
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
