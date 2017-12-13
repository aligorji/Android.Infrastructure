package ir.aligorji.androidinfrastructure.widget.decorations;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SpaceDividerDecoration extends RecyclerView.ItemDecoration
{

    private final int mSpace;

    public SpaceDividerDecoration(Activity activity, int space)
    {
        this.mSpace = activity.getResources().getDimensionPixelSize(space);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {


        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildAdapterPosition(view) == 0)
        {
            outRect.top = 0;
            return;
        }*/

        outRect.bottom = mSpace;

    }
}