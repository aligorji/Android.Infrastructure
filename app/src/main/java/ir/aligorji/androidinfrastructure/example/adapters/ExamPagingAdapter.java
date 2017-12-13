package ir.aligorji.androidinfrastructure.example.adapters;


import android.view.View;

import java.util.List;

import ir.aligorji.androidinfrastructure.adapters.BindingRecyclerPagingAdapter;
import ir.aligorji.androidinfrastructure.adapters.EndlessAdapter;
import ir.aligorji.androidinfrastructure.adapters.OnLoadMoreDataAdapter;
import ir.aligorji.androidinfrastructure.example.BR;
import ir.aligorji.androidinfrastructure.example.R;
import ir.aligorji.androidinfrastructure.example.databinding.ItemExamBinding;
import ir.aligorji.androidinfrastructure.example.repositories.viewmodel.ExamViewModel;
import ir.aligorji.androidinfrastructure.example.viewholder.LoadingViewHolder;

public class ExamPagingAdapter extends BindingRecyclerPagingAdapter<ExamViewModel, ItemExamBinding, LoadingViewHolder>
{


    public ExamPagingAdapter(List<ExamViewModel> items, OnLoadMoreDataAdapter listener)
    {
        super(items, listener);
    }


    @Override
    public int getItemViewId(int position)
    {
        return R.layout.item_exam;
    }
    @Override
    public int getLoadingViewId()
    {
        return R.layout.item_loading;
    }

    @Override
    protected LoadingViewHolder createLoadingViewHolder(View root)
    {
        return new LoadingViewHolder(root);
    }

    @Override
    protected int getBindingVariableIdInBR()
    {
        return BR.exam;
    }

    @Override
    protected void onBinding(ItemExamBinding binding, int position)
    {

    }
    @Override
    protected void onBindLoading(LoadingViewHolder holder)
    {
        holder.loading();
    }
    @Override
    public void onBindErrorLoading(Object message, LoadingViewHolder holder, EndlessAdapter adapter, int page)
    {
        holder.error((String) message, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tryLoading();
            }
        });
    }

}