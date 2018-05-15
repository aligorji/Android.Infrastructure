package ir.aligorji.androidinfrastructure.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import ir.aligorji.androidinfrastructure.models.IdentityViewModel;
import ir.aligorji.androidinfrastructure.widget.OnActionItemClickListener;
import ir.aligorji.androidinfrastructure.widget.OnItemClickListener;


public abstract class BindingRecyclerAdapter<T extends BaseObservable, TBinding extends ViewDataBinding>
        extends RecyclerView.Adapter<BindingRecyclerAdapter.ViewHolder>
        implements DefaultAdapter
{

    private List<T> mItems;
    private HashMap<Integer, OnActionItemClickListener> mActionItems = null;
    private OnAdapterChangeItemsListener mChangeItemsListener = null;
    private OnItemClickListener mOnClickItemListener = null;


    public BindingRecyclerAdapter(List<T> items)
    {
        this.mItems = items;
    }

    private void onChangeItems()
    {
        if (this.mChangeItemsListener != null)
        {
            this.mChangeItemsListener.onAdapterChangeItems();
        }
    }

    @Override
    public final int getItemViewType(int position)
    {
        return getItemViewId(position);
    }

    public abstract int getItemViewId(int position);

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        TBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public final void onBindViewHolder(final ViewHolder viewHolder, int position)
    {
        int variableId = getBindingVariableId();
        if (variableId > 0)
        {
            viewHolder.mBinding.setVariable(variableId, getItem(position));
            viewHolder.mBinding.executePendingBindings();
        }

        //On click action items
        if (mActionItems != null && mActionItems.size() > 0)
        {
            for (final int id : mActionItems.keySet())
            {
                final View view = viewHolder.mBinding.getRoot().findViewById(id);
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
                                int pos = viewHolder.getAdapterPosition();

                                listener.onActionItemClickListener(view, getItem(pos), pos);
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
            viewHolder.mBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mOnClickItemListener != null)
                    {
                        int pos = viewHolder.getAdapterPosition();

                        mOnClickItemListener.onItemClick(v, getItem(pos), pos);
                    }
                }
            });
        }


        //on binding
        onBinding((TBinding) viewHolder.mBinding, position);
    }

    @Override
    public void onViewDetachedFromWindow(final ViewHolder viewHolder)
    {
        viewHolder.clearAnimation();
    }

    protected abstract int getBindingVariableId();

    protected abstract void onBinding(TBinding binding, int position);

    //██████  ██    ██ ██████  ██      ██  ██████
    //██   ██ ██    ██ ██   ██ ██      ██ ██
    //██████  ██    ██ ██████  ██      ██ ██
    //██      ██    ██ ██   ██ ██      ██ ██
    //██       ██████  ██████  ███████ ██  ██████


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

    public void setOnAdapterChangeItems(OnAdapterChangeItemsListener listener)
    {
        this.mChangeItemsListener = listener;
    }

    public OnAdapterChangeItemsListener getOnAdapterChangeItems()
    {
        return this.mChangeItemsListener;
    }

    @Override
    public int getItemCount()
    {
        return mItems.size();
    }

    @Override
    public final int getLoadedItemCount()
    {
        return getItemCount();
    }

    public void add(T value)
    {
        mItems.add(value);
        notifyItemInserted(mItems.size() - 1);

        onChangeItems();
    }

    public void update(int position, T value)
    {
        mItems.set(position, value);
        notifyItemChanged(position);

        onChangeItems();
    }

    public void remove(T value)
    {
        int index = mItems.indexOf(value);
        if (index >= 0)
        {
            remove(index);
        }
    }

    public void remove(int position)
    {
        mItems.remove(position);
        notifyItemRemoved(position);

        onChangeItems();
    }

    public void addAll(List<T> list)
    {
        int beforeAddSize = mItems.size();
        mItems.addAll(list);
        notifyItemRangeInserted(beforeAddSize, list.size());

        onChangeItems();
    }

    public void changeList(List<T> list)
    {
        mItems = list;
    }

    public void clear()
    {
        int beforeAddSize = mItems.size();

        mItems.clear();

        notifyItemRangeRemoved(0, beforeAddSize);

        onChangeItems();
    }

    public T getItem(int position)
    {
        return mItems.get(position);
    }

    public List<T> getItems()
    {
        return mItems;
    }

    public Integer getItemPositionById(@NonNull Object id)
    {
        int index = 0;
        for (T item : mItems)
        {
            if (item instanceof IdentityViewModel)
            {
                if (id.equals(((IdentityViewModel) item).getId()))
                {
                    return index;
                }
            }
            else
            {
                throw new RuntimeException("##### Adapter can't get by id, please implement [IdentityViewModel] in adapter model.");
            }

            index++;
        }
        return null;
    }

    public void notifyBindChanged(int position)
    {
        getItem(position).notifyChange();
    }


    //██    ██ ██ ███████ ██     ██     ██   ██  ██████  ██      ██████  ███████ ██████
    //██    ██ ██ ██      ██     ██     ██   ██ ██    ██ ██      ██   ██ ██      ██   ██
    //██    ██ ██ █████   ██  █  ██     ███████ ██    ██ ██      ██   ██ █████   ██████
    // ██  ██  ██ ██      ██ ███ ██     ██   ██ ██    ██ ██      ██   ██ ██      ██   ██
    //  ████   ██ ███████  ███ ███      ██   ██  ██████  ███████ ██████  ███████ ██   ██


    public static class ViewHolder extends RecyclerView.ViewHolder implements AnimationCleanable
    {

        public final ViewDataBinding mBinding;

        public ViewHolder(ViewDataBinding binding)
        {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void clearAnimation()
        {
            mBinding.getRoot().clearAnimation();
        }
    }

    public interface AnimationCleanable
    {

        void clearAnimation();
    }


}