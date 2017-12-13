package ir.aligorji.androidinfrastructure.utilities;


import java.io.Serializable;
import java.util.Locale;

public final class FaTime implements Serializable
{

    public final static String EMPTY_TIME = "00:00:00";

    public final long totalMili;
    //
    public final short sec;
    public final short min;
    public final short hour;
    //
    public final String time;

    public FaTime(long totalMili)
    {
        this.sec = (short) ((totalMili / 1000) % 60);
        this.min = (short) (((totalMili / (1000 * 60)) % 60));
        this.hour = (short) (((totalMili / (1000 * 60 * 60)) % 24));

        this.time = String.format(Locale.US, "%02d:%02d:%02d", this.hour, this.min, this.sec);

        this.totalMili = totalMili;

    }

    public FaTime(short hour, short min, short sec)
    {
        this.sec = sec;
        this.min = min;
        this.hour = hour;

        this.time = String.format(Locale.US, "%02d:%02d:%02d", this.hour, this.min, this.sec);

        totalMili = ((hour * 3600) + (min * 60) + (sec)) * 1000;
    }

    public FaTime(String time)
    {
        if (time == null)
        {
            throw new NullPointerException("Invalid time can not be null");
        }

        try
        {
            final String[] h_m_s = time.split(":");

            if (h_m_s.length != 3)
            {
                throw new Exception();
            }

            this.hour = Short.parseShort(h_m_s[0]);
            this.min = Short.parseShort(h_m_s[1]);
            this.sec = Short.parseShort(h_m_s[2]);
            this.time = String.format(Locale.US, "%02d:%02d:%02d", this.hour, this.min, this.sec);
        }
        catch (Throwable ignored)
        {
            throw new RuntimeException("##### Invalid time format [" + time + "]");
        }

        totalMili = ((hour * 3600) + (min * 60) + (sec)) * 1000;

    }


    @Override
    public String toString()
    {
        return time;
    }

    public int getMinute()
    {
        return (int) (totalMili / 1000 / 60);
    }


    public boolean isGreaterThan(FaTime time)
    {
        return this.totalMili > time.totalMili;
    }

    public boolean isGreaterOrEqualThan(FaTime time)
    {
        return this.totalMili >= time.totalMili;
    }

    public boolean isLessThan(FaTime time)
    {
        return this.totalMili < time.totalMili;
    }

    public boolean isLessOrEqualThan(FaTime time)
    {
        return this.totalMili <= time.totalMili;
    }

    public boolean isBetween(FaTime start, FaTime end)
    {
        return start.totalMili <= this.totalMili && this.totalMili <= end.totalMili;
    }

    public FaTime subWith(FaTime time)
    {
        long m = this.totalMili - time.totalMili;
        if (m < 0)
        {
            return null;
        }
        return new FaTime(m);
    }

    public static FaTime fromMin(int min)
    {
        return new FaTime(min * 60 * 1000);
    }

}
