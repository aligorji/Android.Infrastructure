package ir.aligorji.androidinfrastructure.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public abstract class Ui
{

    public static void setFullScreen(Activity activity, boolean value)
    {
        try
        {
            if (value)
            {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

            }
            else
            {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            }
        }
        catch (Throwable ignore)
        {

        }
    }

    public static void showKeyboard(View view)
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
        catch (Throwable ignore)
        {

        }
    }

    public static void showAsyncKeyboard(final View view)
    {
        if (view == null)
        {
            return;
        }

        view.requestFocus();
        view.post(new Runnable()
        {
            @Override
            public void run()
            {
                showKeyboard(view);
            }
        });

    }

    public static void showAsyncKeyboard(final View view, long delay)
    {
        if (view == null)
        {
            return;
        }

        view.requestFocus();
        view.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                showKeyboard(view);
            }
        }, delay);

    }

    public static void hideKeyboard(Activity activity)
    {
        try
        {
            View view = activity.getCurrentFocus();
            if (view != null)
            {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        catch (Throwable ignore)
        {

        }
    }


}
