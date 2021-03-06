package ir.aligorji.androidinfrastructure.example.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.aligorji.androidinfrastructure.MyBindingFragment;
import ir.aligorji.androidinfrastructure.adapters.EndlessAdapter;
import ir.aligorji.androidinfrastructure.adapters.OnLoadMoreDataAdapter;
import ir.aligorji.androidinfrastructure.example.R;
import ir.aligorji.androidinfrastructure.example.adapters.ExamPagingAdapter;
import ir.aligorji.androidinfrastructure.example.repositories.models.Exam;
import ir.aligorji.androidinfrastructure.example.repositories.viewmodel.ExamViewModel;
import ir.aligorji.androidinfrastructure.widget.BindingRecyclerOnClickListener;
import ir.aligorji.androidinfrastructure.widget.OnActionItemClickListener;
import ir.aligorji.androidinfrastructure.widget.OnItemClickListener;
import ir.aligorji.androidinfrastructure.widget.decorations.MyDividerItemDecoration;

public class ExamPagingFragment extends MyBindingFragment
        implements
        BindingRecyclerOnClickListener<ExamViewModel>, OnLoadMoreDataAdapter
{


    private ExamPagingAdapter mMyAdapter;

    public static ExamPagingFragment newInstance()
    {
        ExamPagingFragment fragment = new ExamPagingFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getContentView()
    {
        return R.layout.fragment_exam;
    }

    @Override
    protected void onTakeArguments(Bundle arguments)
    {

    }

    @Override
    protected void onInitActionBar()
    {

    }

    @Override
    protected void onInitView()
    {

    }

    @Override
    protected void onInitAdapter()
    {

        mMyAdapter = new ExamPagingAdapter(new ArrayList<ExamViewModel>(), this);

        mMyAdapter.setScrollToLastItemAfterLazyLoad(true);

        RecyclerView recyclerView = findRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //recyclerView.setItemAnimator(anim);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), 1, 16, 16));

        //setRecyclerOnItemClickListener(this, mMyAdapter);
        mMyAdapter.setActionItemListener(R.id.delete, new OnActionItemClickListener<ExamViewModel>()
        {
            @Override
            public void onActionItemClickListener(View view, ExamViewModel model, int position)
            {
                Toast.makeText(getActivity(), position + "'" + model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        mMyAdapter.setOnItemClickListener(new OnItemClickListener<ExamViewModel>()
        {
            @Override
            public void onItemClick(View view, ExamViewModel model, int position)
            {
                Exam exam = new Exam();
                exam.id = 9999;
                exam.title = "aliXXX";
                mMyAdapter.add(0, ExamViewModel.create(exam));

                Toast.makeText(getActivity(), "ITEM CLICK: " +position + "'" + model.getTitle(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public boolean onItemLongClick(View view, ExamViewModel model, int position)
            {
                return true;
            }
        });

        recyclerView.setAdapter(mMyAdapter);

        /*for (int i =1; i <= 10; i++)
        {

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    //ExamModel results = getRealm().where(ExamModel.class).equalTo("id", 1).findFirst();
                    //ExamViewModel viewModel = ExamViewModel.create(results);
                    //mMyAdapter.add(viewModel);

                    RealmResults<ExamModel> results = getRealm().where(ExamModel.class).between("id", 1, 30).findAll();
                    List<ExamViewModel> viewModel = ExamViewModel.create(results);
                    mMyAdapter.addAll(viewModel);

                }
            }, 3000 * i);
        }*/


    }
    @Override
    protected void onLoadData()
    {

    }
    @Override
    protected void onStartupNetworkRequest()
    {

    }

    int totalItems = 10;
    int pageCount = 50;

    @Override
    public boolean onLoadMoreItems(final EndlessAdapter adapter, final int page)
    {

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                //if (page == 1)
                //{
                //    mMyAdapter.onErrorLoading("aaaaaaaa");
                //    return;
                //}

                //comment this line for endless
                adapter.setTotalItems(10);


                List<ExamViewModel> viewModels = new ArrayList<>();


                for (int i = 0; i < pageCount && totalItems > 0; i++)
                {
                    totalItems--;
                    Exam exam = new Exam();
                    exam.id = i;
                    exam.title = "ali" + i;
                    viewModels.add(new ExamViewModel(exam));
                }


                //if (viewModels.isEmpty())
                //{
                //    adapter.setEndOfLoadItems();
                //    return;
                //}
                mMyAdapter.addAll(viewModels);


            }
        }, 2000);

        return true;
    }
    @Override
    public void onRecyclerItemClickListener(View view, ExamViewModel model, final int position)
    {

        Toast.makeText(getActivity(), position + "'", Toast.LENGTH_SHORT).show();
        //mMyAdapter.getItem(position).setTitle("XXX***");
        //mMyAdapter.getItem(position + -1).setTitle("XXXZZZ");
        //mMyAdapter.getItem(position + 1).setTitle("XXXAAA");

    }
    @Override
    public void onRecyclerItemLongClickListener(View view, ExamViewModel model, int position)
    {

    }


}