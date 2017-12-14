package ir.aligorji.androidinfrastructure.example.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import ir.aligorji.androidinfrastructure.widget.decorations.SpacesFloatAbDecoration;

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
    protected boolean onTakeArguments(Bundle arguments)
    {
        return true;
    }

    @Override
    protected boolean onInitActionBar()
    {
        return true;
    }

    @Override
    protected boolean onInitView()
    {

        return true;
    }

    @Override
    protected boolean onInitAdapter()
    {

        mMyAdapter = new ExamPagingAdapter(new ArrayList<ExamViewModel>(), this);

        RecyclerView recyclerView = findRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //recyclerView.setItemAnimator(anim);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpacesFloatAbDecoration(getActivity(), 16));

        setRecyclerOnItemClickListener(this, mMyAdapter);

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

        return true;
    }
    @Override
    protected boolean onLoadData()
    {
        return true;
    }
    @Override
    protected void onStartupNetworkRequest()
    {

    }


    @Override
    public boolean onLoadMoreItems(final EndlessAdapter adapter, final int page)
    {

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //comment this line for endless
                adapter.setTotalItems(50);


                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        List<ExamViewModel> viewModels = new ArrayList<>();

                        Exam exam = new Exam();
                        exam.id = 1;
                        exam.title = "ali";
                        ExamViewModel vm = new ExamViewModel(exam);
                        viewModels.add(vm);

                        if (viewModels.isEmpty())
                        {
                            adapter.setEndOfLoadItems();
                            return;
                        }
                        mMyAdapter.addAll(viewModels);
                    }
                }, 5000);


            }
        }, 1000);

        return true;
    }
    @Override
    public void onRecyclerItemClickListener(View view, ExamViewModel model, final int position)
    {

        mMyAdapter.getItem(position).setTitle("XXX***");
        mMyAdapter.getItem(position + -1).setTitle("XXXZZZ");
        mMyAdapter.getItem(position + 1).setTitle("XXXAAA");

    }
    @Override
    public void onRecyclerItemLongClickListener(View view, ExamViewModel model, int position)
    {

    }


}