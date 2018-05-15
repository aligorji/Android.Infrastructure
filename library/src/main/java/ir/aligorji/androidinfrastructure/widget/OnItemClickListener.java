package ir.aligorji.androidinfrastructure.widget;

import android.databinding.BaseObservable;
import android.view.View;

public abstract class OnItemClickListener<T extends BaseObservable>
{

    public abstract void onItemClick(View view, T model, int position);

    public void onItemLongClick(View view, T model, int position)
    {
    }
}
