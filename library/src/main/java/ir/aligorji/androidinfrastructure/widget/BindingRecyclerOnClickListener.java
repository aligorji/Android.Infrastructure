package ir.aligorji.androidinfrastructure.widget;

import android.databinding.BaseObservable;
import android.view.View;

public interface BindingRecyclerOnClickListener<T extends BaseObservable>
{

    void onRecyclerItemClickListener(View view, T model, int position);

    void onRecyclerItemLongClickListener(View view, T model, int position);
}
