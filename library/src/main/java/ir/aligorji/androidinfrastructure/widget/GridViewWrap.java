package ir.aligorji.androidinfrastructure.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class GridViewWrap extends GridView
{

    public GridViewWrap(Context context)
    {
        super(context);
    }

    public GridViewWrap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GridViewWrap(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int heightSpec = heightMeasureSpec;
        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT)
        {
            heightSpec = MeasureSpec.makeMeasureSpec(1073741723, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}