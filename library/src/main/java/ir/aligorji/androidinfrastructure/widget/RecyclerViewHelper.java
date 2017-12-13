package ir.aligorji.androidinfrastructure.widget;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class RecyclerViewHelper
{

    public static int findLastVisibleItemPosition(RecyclerView recyclerView)
    {
        if (recyclerView == null)
        {
            return -1;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            return getLastVisibleItem(lastVisibleItemPositions);
        }
        else if (layoutManager instanceof LinearLayoutManager)
        {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        else if (layoutManager instanceof GridLayoutManager)
        {
            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        return 0;
    }

    private static int getLastVisibleItem(int[] lastVisibleItemPositions)
    {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++)
        {
            if (i == 0)
            {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize)
            {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

}
