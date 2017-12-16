package ir.aligorji.androidinfrastructure.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.aligorji.androidinfrastructure.widget.EndlessRecyclerViewScrollListener;
import ir.aligorji.androidinfrastructure.widget.RecyclerViewHelper;


public abstract class BindingRecyclerPagingAdapter<T extends BaseObservable, TBinding extends ViewDataBinding, TLoading extends BindingRecyclerPagingAdapter.InnerLoadingViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements EndlessAdapter
{

    private static final int LAYOUT_LOADING = -1;


    private final OnLoadMoreDataAdapter mOnLoadMoreDataAdapter;
    private int mTotalItems;
    private TLoading mLoadingView = null;
    private final List<T> mItems;
    private RecyclerView mRecyclerView = null;
    private int mCurrentPage = 0;
    private boolean mIsDataLoading = false;
    private boolean mIsErrorDataLoading = false;
    private Object mDataLoadingErrorMessage = null;
    private boolean mScrollToLastItemAfterLazyLoad = true;
    private OnAdapterChangeItemsListener mChangeItemsListener = null;

    public BindingRecyclerPagingAdapter(List<T> items, OnLoadMoreDataAdapter listener)
    {
        this.mItems = items;
        this.mOnLoadMoreDataAdapter = listener;
        setUnlimitedItems();
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
        if (position >= mItems.size())
        {
            return LAYOUT_LOADING;
        }
        else
        {
            return getItemViewId(position);
        }
    }

    public abstract int getItemViewId(int position);

    public abstract int getLoadingViewId();

    @Override
    public final void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(recyclerView, mCurrentPage)
        {
            @Override
            public boolean onLoadMore(EndlessAdapter adapter, int page)
            {
                mCurrentPage = page;
                return onLoadMoreItems(adapter, page);
            }
        });
    }

    private boolean onLoadMoreItems(EndlessAdapter adapter, int page)
    {
        if (mOnLoadMoreDataAdapter != null)
        {
            return mOnLoadMoreDataAdapter.onLoadMoreItems(adapter, page);
        }
        return false;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == LAYOUT_LOADING)
        {
            View root = LayoutInflater.from(parent.getContext()).inflate(getLoadingViewId(), parent, false);
            mLoadingView = createLoadingViewHolder(root);
            return mLoadingView;
        }
        else
        {
            TBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
            return new ViewHolder(binding);
        }
    }

    protected abstract TLoading createLoadingViewHolder(View root);

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        if (viewHolder instanceof InnerLoadingViewHolder)
        {

            mIsDataLoading = true;
            if (mIsErrorDataLoading)
            {
                onBindErrorLoading(mDataLoadingErrorMessage, (TLoading) viewHolder, this, mCurrentPage);
            }
            else
            {
                onBindLoading((TLoading) viewHolder);
            }

        }
        else
        {

            mIsDataLoading = false;

            ViewHolder holder = (ViewHolder) viewHolder;

            int variableId = getBindingVariableIdInBR();
            if (variableId > 0)
            {
                holder.mBinding.setVariable(variableId, getItem(position));
                holder.mBinding.executePendingBindings();
            }
            onBinding((TBinding) holder.mBinding, position);
        }
    }

    protected abstract int getBindingVariableIdInBR();

    protected abstract void onBinding(TBinding binding, int position);

    protected abstract void onBindLoading(TLoading holder);

    public abstract void onBindErrorLoading(Object object, TLoading loadingViewHolder, EndlessAdapter adapter, int page);


    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder viewHolder)
    {
        if (viewHolder instanceof BindingRecyclerAdapter.AnimationCleanable)
        {
            ((BindingRecyclerAdapter.AnimationCleanable) viewHolder).clearAnimation();
        }
    }


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
    public final int getItemCount()
    {
        if (mTotalItems >= 0 && mItems.size() >= mTotalItems)
        {
            return mItems.size();
        }
        else
        {
            return mItems.size() + 1;
        }
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

        if (mScrollToLastItemAfterLazyLoad)
        {
            boolean inLastList = isPositionInLast() && list.size() > 0;

            int beforeAddSize = mItems.size();

            notifyItemRemoved(beforeAddSize);

            mItems.addAll(list);

            notifyItemRangeInserted(
                    beforeAddSize,
                    list.size());

            if (inLastList)
            {
                mRecyclerView.smoothScrollToPosition(beforeAddSize);
            }
        }
        else
        {
            int beforeAddSize = mItems.size();

            notifyItemRemoved(beforeAddSize);

            mItems.addAll(list);

            notifyItemRangeInserted(
                    beforeAddSize,
                    list.size());
        }

        onChangeItems();
    }

    public void clear()
    {
        mItems.clear();
        notifyDataSetChanged();

        onChangeItems();
    }

    public List<T> getItems()
    {
        return mItems;
    }

    private boolean isPositionInLast()
    {
        return RecyclerViewHelper.findLastVisibleItemPosition(mRecyclerView) >= mItems.size();
    }

    public final T getItem(int position)
    {
        return mItems.get(position);
    }

    public void notifyBindChanged(int position)
    {
        getItem(position).notifyChange();
    }

    public void setScrollToLastItemAfterLazyLoad(boolean enable)
    {
        mScrollToLastItemAfterLazyLoad = enable;
    }


    //███████ ███    ██ ██████  ██      ███████ ███████ ███████
    //██      ████   ██ ██   ██ ██      ██      ██      ██
    //█████   ██ ██  ██ ██   ██ ██      █████   ███████ ███████
    //██      ██  ██ ██ ██   ██ ██      ██           ██      ██
    //███████ ██   ████ ██████  ███████ ███████ ███████ ███████

    @Override
    public final int getLoadedItemCount()
    {
        return mItems.size();
    }

    @Override
    public int getTotalItems()
    {
        return mTotalItems;
    }
    @Override
    public void setTotalItems(int mTotalItems)
    {
        this.mTotalItems = mTotalItems;
    }
    @Override
    public void setEndOfLoadItems()
    {
        mIsDataLoading = false;

        this.mTotalItems = getLoadedItemCount();
        //notifyItemRemoved(this.mTotalItems);
        notifyItemChanged(this.mTotalItems);
    }
    @Override
    public void setUnlimitedItems()
    {
        this.mTotalItems = -1;
    }
    @Override
    public boolean isUnlimitedItems()
    {
        return this.mTotalItems < 0;
    }
    @Override
    public boolean isEndOfLoadItems()
    {
        return mItems.size() >= getTotalItems();
    }

    @Override
    public final void onErrorLoading(Object object)
    {
        if (mLoadingView != null)
        {
            mIsErrorDataLoading = true;
            mDataLoadingErrorMessage = object;
            notifyItemChanged(getLoadedItemCount());

            if (isPositionInLast())
            {
                mRecyclerView.smoothScrollToPosition(getLoadedItemCount());
            }
        }
    }

    @Override
    public final void tryLoading()
    {
        if (mIsDataLoading)
        {
            mIsErrorDataLoading = false;
            mDataLoadingErrorMessage = null;
            notifyItemChanged(getLoadedItemCount());

            onLoadMoreItems(this, mCurrentPage);
        }

    }


    //██    ██ ██ ███████ ██     ██     ██   ██  ██████  ██      ██████  ███████ ██████
    //██    ██ ██ ██      ██     ██     ██   ██ ██    ██ ██      ██   ██ ██      ██   ██
    //██    ██ ██ █████   ██  █  ██     ███████ ██    ██ ██      ██   ██ █████   ██████
    // ██  ██  ██ ██      ██ ███ ██     ██   ██ ██    ██ ██      ██   ██ ██      ██   ██
    //  ████   ██ ███████  ███ ███      ██   ██  ██████  ███████ ██████  ███████ ██   ██


    public static class ViewHolder extends RecyclerView.ViewHolder implements BindingRecyclerAdapter.AnimationCleanable
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

    public abstract static class InnerLoadingViewHolder extends RecyclerView.ViewHolder implements BindingRecyclerAdapter.AnimationCleanable
    {

        private final View root;
        public InnerLoadingViewHolder(View root)
        {
            super(root);
            this.root = root;
        }

        @Override
        public void clearAnimation()
        {
            root.clearAnimation();
        }
    }


}