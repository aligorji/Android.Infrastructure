package ir.aligorji.androidinfrastructure.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ir.aligorji.androidinfrastructure.models.IdentityViewModel;


public abstract class BindingRecyclerAdapter<T extends BaseObservable, TBinding extends ViewDataBinding>
        extends RecyclerView.Adapter<BindingRecyclerAdapter.ViewHolder>
        implements DefaultAdapter
{

    private List<T> mItems;
    private OnAdapterChangeItemsListener mChangeItemsListener = null;


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
    public final void onBindViewHolder(ViewHolder holder, int position)
    {
        int variableId = getBindingVariableId();
        if (variableId > 0)
        {
            holder.mBinding.setVariable(variableId, getItem(position));
            holder.mBinding.executePendingBindings();
        }
        onBinding((TBinding) holder.mBinding, position);
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