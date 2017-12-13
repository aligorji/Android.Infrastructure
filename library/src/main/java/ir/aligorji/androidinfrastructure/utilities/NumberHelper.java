package ir.aligorji.androidinfrastructure.utilities;


public class NumberHelper
{

    public static String toIntIfCan(Double value)
    {
        if (value == null)
        {
            return null;
        }

        if (value % 1 == 0)
        {
            return value.intValue() + "";
        }

        return value + "";

    }
}
