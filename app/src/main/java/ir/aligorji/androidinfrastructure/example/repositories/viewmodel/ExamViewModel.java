package ir.aligorji.androidinfrastructure.example.repositories.viewmodel;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

import ir.aligorji.androidinfrastructure.example.BR;
import ir.aligorji.androidinfrastructure.example.repositories.models.Exam;

public class ExamViewModel extends BaseObservable
{


    public final Exam examModel;


    public ExamViewModel(Exam model)
    {
        this.examModel = model;
    }

    @Bindable
    public String getTitle()
    {
        return examModel.title;
    }

    @Bindable
    public String getClassName()
    {
        return examModel.className;
    }

    @Bindable
    public int getGrade()
    {
        return examModel.grade;
    }

    public void setTitle(String title)
    {
        examModel.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setClassName(String className)
    {
        examModel.className = className;
        notifyPropertyChanged(BR.className);
    }

    public void setGrade(int grade)
    {
        examModel.grade = grade;
        notifyPropertyChanged(BR.grade);
    }

    public final Exam getModel()
    {
        return examModel;
    }

    public static ExamViewModel create(Exam model)
    {
        return new ExamViewModel(model);
    }

    public static List<ExamViewModel> create(List<Exam> models)
    {
        List<ExamViewModel> viewModels = new ArrayList<>();
        for (Exam m : models)
        {
            viewModels.add(create(m));
        }
        return viewModels;
    }

}
