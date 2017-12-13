package ir.aligorji.androidinfrastructure.widget;

import android.view.View;

public interface RecyclerOnClickListener
{

    void onRecyclerItemClickListener(View view, int position);

    void onRecyclerItemLongClickListener(View view, int position);
}
