package ir.aligorji.androidinfrastructure.utilities;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

public final class FaDate implements Serializable
{

    public final static String SPLITTER = " ";
    public final static String EMPTY_DATE = "0000/00/00";
    public final static String EMPTY_TIME = "00:00:00";
    public final static String EMPTY_DATE_TIME = EMPTY_DATE + SPLITTER + EMPTY_TIME;

    public final long totalSec;
    //
    public final short sec;
    public final short min;
    public final short hour;
    public final short day;
    public final short month;
    public final short year;
    //
    public final String date;
    public final String time;

    public FaDate(short y, short m, short d)
    {
        this.sec = 0;
        this.min = 0;
        this.hour = 0;
        this.day = d;
        this.month = m;
        this.year = y;

        this.date = String.format(Locale.US, "%04d/%02d/%02d", this.year, this.month, this.day);
        this.time = String.format(Locale.US, "%02d:%02d:%02d", this.hour, this.min, this.sec);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new FaCalendar().getGregorianDate(date, hour, min, sec));
        totalSec = calendar.getTimeInMillis();
    }

    public FaDate(String dateTime)
    {
        if (dateTime == null)
        {
            throw new NullPointerException("Invalid farsi date time can not be null");
        }

        String[] dt = dateTime.split(FaDate.SPLITTER);

        if (dt.length == 2)
        {
            try
            {
                final String[] y_m_d = dt[0].split("/");

                if (y_m_d.length != 3)
                {
                    throw new Exception();
                }

                this.year = Short.parseShort(y_m_d[0]);
                this.month = Short.parseShort(y_m_d[1]);
                this.day = Short.parseShort(y_m_d[2]);
                this.date = String.format(Locale.US, "%04d/%02d/%02d", this.year, this.month, this.day);
            }
            catch (Throwable ignored)
            {
                throw new RuntimeException("##### Invalid date format [" + dt[0] + "]");
            }

            try
            {
                final String[] h_m_s = dt[1].split(":");

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
                throw new RuntimeException("##### Invalid time format [" + dt[1] + "]");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new FaCalendar().getGregorianDate(date, hour, min, sec));
            totalSec = calendar.getTimeInMillis();
        }
        else
        {
            throw new RuntimeException("Invalid farsi date time format; " + dateTime);
        }

    }

    public FaDate(String date, String time)
    {
        this(date + SPLITTER + time);
    }

    @Override
    public String toString()
    {
        return date + SPLITTER + time;
    }

    public FaTime getTime()
    {
        return new FaTime(time);
    }

    public String getDate()
    {
        return date;
    }

    public Calendar getCalendar()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new FaCalendar().getGregorianDate(date, hour, min, sec));
        return calendar;
    }
    public FaDate addDays(int days)
    {
        Calendar calendar = getCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return new FaDate(new FaCalendar().getJalaliDateTime(calendar.getTime()));
    }

    public int getDayOfWeek()
    {
        int dayOfWeek = getCalendar().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 7)
        {
            return 1;
        }
        else
        {
            return dayOfWeek + 1;
        }
    }

    public boolean isGreaterThan(FaDate date)
    {
        return this.totalSec > date.totalSec;
    }

    public boolean isGreaterOrEqualThan(FaDate date)
    {
        return this.totalSec >= date.totalSec;
    }

    public boolean isLessThan(FaDate date)
    {
        return this.totalSec < date.totalSec;
    }

    public boolean isLessOrEqualThan(FaDate date)
    {
        return this.totalSec <= date.totalSec;
    }

    public boolean isBetween(FaDate start, FaDate end)
    {
        return start.totalSec <= this.totalSec && this.totalSec <= end.totalSec;
    }

    public FaDate getDateFirstWeek()
    {
        int dayOfWeek = getDayOfWeek();

        return addDays(-(7 - dayOfWeek));
    }

    public FaDate getDateByDayOfWeek(int day)
    {
        int dayOfWeek = getDayOfWeek();

        return addDays(-(dayOfWeek - day));
    }

    public FaDate getDateOnlyZeroTime()
    {
        return new FaDate(date, FaDate.EMPTY_TIME);
    }

    public static FaDate now()
    {
        return new FaDate(new FaCalendar().getJalaliDateTime(Calendar.getInstance().getTime()));
    }

    public static FaDate nowDate()
    {
        return fromDate(now().getDate());
    }

    public static FaDate fromDate(String date)
    {
        return new FaDate(date + SPLITTER + EMPTY_TIME);
    }


}
