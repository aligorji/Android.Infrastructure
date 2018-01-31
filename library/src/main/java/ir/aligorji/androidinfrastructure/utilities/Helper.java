package ir.aligorji.androidinfrastructure.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.text.Html;
import android.util.DisplayMetrics;

public class Helper
{

    public static String getModel()
    {
        return Build.MODEL + "@" + Build.BRAND;
    }

    public static String getOsVersion()
    {
        return "Android-" + Build.VERSION.SDK_INT;
    }

    public static String getVersionName(Context context)
    {
        PackageInfo pInfo;
        try
        {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        }
        catch (Throwable ignored)
        {

        }
        return "0.0.0";
    }

    public static int getVersionCode(Context context)
    {
        PackageInfo pInfo;
        try
        {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        }
        catch (Throwable ignored)
        {

        }
        return -1;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context)
    {
        try
        {
            return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }
        catch (Exception ignored)
        {
        }
        return null;
    }

    public static int dpToPixel(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float pixelToDp(float px, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static String clearHtmlTags(String value)
    {
        return Html.fromHtml(value).toString();
    }

    public static void openAppInfo(Activity activity)
    {
        try
        {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + activity.getApplicationContext().getPackageName()));
            activity.startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            try
            {
                //Open the generic Apps page:
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                activity.startActivity(intent);
            }
            catch (Throwable ignore)
            {

            }
        }
    }

    public static String rialExtend(String rial)
    {
        return rial(rial) + " ريال";
    }

    public static String rial(String rial)
    {
        String result = "";
        //1,000,000


        int j = 0;
        for (int i = rial.length() - 1; i >= 0; i--, j++)
        {
            if (j > 0 && j % 3 == 0)
            {
                result = "," + result;
            }
            result = rial.charAt(i) + result;
        }

        //or

        /*int i = rial.length() - 3;
        for (; i > 0; i -= 3)
        {
            result = rial.substring(i, i + 3) + ((result.isEmpty()) ? "" : "," + result);
        }

        result = rial.substring(0, i + 3) + ((result.isEmpty()) ? "" : "," + result);*/


        return result;
    }

}
