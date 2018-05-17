package ir.aligorji.androidinfrastructure.widget.decorations;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ir.aligorji.androidinfrastructure.utilities.Helper;


public class MyDividerItemDecoration extends RecyclerView.ItemDecoration
{

    private final int mVerticalSpace;
    private final int mHorizontalSpace;
    private final int mHalfHorizontalSpace;
    private final int columns;
    private final boolean reverse;

    public MyDividerItemDecoration(Context context, int columns, int verticalSpace, int horizontalSpace)
    {
        this(context, columns, verticalSpace, horizontalSpace, false);
    }

    public MyDividerItemDecoration(Context context, int columns, int verticalSpace, int horizontalSpace, boolean reverse)
    {
        this.columns = columns;
        this.reverse = reverse;
        this.mVerticalSpace = Helper.dpToPixel(verticalSpace, context);
        this.mHorizontalSpace = Helper.dpToPixel(horizontalSpace, context);
        this.mHalfHorizontalSpace = this.mHorizontalSpace / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {

        int position = parent.getChildAdapterPosition(view);

        outRect.left = (position % columns == 0) ? mHorizontalSpace : mHalfHorizontalSpace;
        outRect.right = (position % columns == columns - 1) ? mHorizontalSpace : mHalfHorizontalSpace;

        // Add top margin only for the first item[s]
        // when more columns, first row include this condition
        if (position < columns)
        {
            if (reverse)
            {
                outRect.bottom = 0;
            }
            else
            {
                outRect.top = 0;
            }
            return;
        }

        if (reverse)
        {
            outRect.bottom = mVerticalSpace;
        }
        else
        {
            outRect.top = mVerticalSpace;
        }

    }
}