package ir.aligorji.androidinfrastructure.widget;

import android.databinding.BaseObservable;
import android.view.View;

public interface OnItemClickListener<T extends BaseObservable>
{
    void onItemClick(View view, T model, int position);
    boolean onItemLongClick(View view, T model, int position);
}
