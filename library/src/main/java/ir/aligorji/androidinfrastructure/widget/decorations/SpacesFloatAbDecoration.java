package ir.aligorji.androidinfrastructure.widget.decorations;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;


public class SpacesFloatAbDecoration extends RecyclerView.ItemDecoration
{

    private final int mSpace;
    private final int actionBarHeight;

    public SpacesFloatAbDecoration(Activity activity, int space)
    {
        this.mSpace = space;

        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        else
        {
            actionBarHeight = 56;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0)
        {
            outRect.top = mSpace + actionBarHeight;
        }
    }
}