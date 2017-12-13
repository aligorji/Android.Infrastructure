package ir.aligorji.androidinfrastructure.utilities;


public final class FaTool
{

    public static final String[] WEEK_NAMES = {
            "شنبه",
            "یکشنبه",
            "دوشنبه",
            "سه شنبه",
            "چهارشنبه",
            "پنجشنبه",
            "جمعه"
    };

    public static final String[] NUMBER_LETTERS = {
            "صفر",
            "یک",
            "دو",
            "سه",
            "چهار",
            "پنج",
            "شش",
            "هفت",
            "هشت",
            "نه",
            "ده",
            "یازده",
            "دوازده"
    };

    public static String getWeekName(int dayOfWeek)
    {
        return WEEK_NAMES[dayOfWeek-1];
    }

    public static String getLetters(int number)
    {
        return NUMBER_LETTERS[number];
    }



}
