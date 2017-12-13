package ir.aligorji.androidinfrastructure.utilities;


public final class MyString
{

    public static boolean isNullOrEmpty(String text)
    {
        return text == null || text.isEmpty();
    }

    public static boolean isNullOrEmptyOrWhiteSpace(String text)
    {
        return text == null || text.trim().isEmpty();
    }

    public static String ifEmptyPutDash(String text)
    {
        return MyString.isNullOrEmptyOrWhiteSpace(text) ? "---" : text;
    }

    public static String ifEmptyPutEmpty(String text)
    {
        return MyString.isNullOrEmptyOrWhiteSpace(text) ? "" : text;
    }
}
