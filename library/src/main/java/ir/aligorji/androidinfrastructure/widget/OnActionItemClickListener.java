package ir.aligorji.androidinfrastructure.widget;

import android.databinding.BaseObservable;
import android.view.View;

public abstract class OnActionItemClickListener<T extends BaseObservable>
{

    public abstract void onActionItemClickListener(View view, T model, int position);

    public void onActionItemLongClickListener(View view, T model, int position)
    {
    }
}
