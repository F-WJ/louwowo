package cn.wolfcode.louwowo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        return format.format(date);
    }

    /**
     * 两个时间差
     * @param d1
     * @param d2
     * @return
     */
    public static long getDateBetween(Date d1, Date d2){
        return Math.abs((d1.getTime() - d2.getTime())/1000);
    }


    /**
     * 当天最后的时间
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        return c.getTime();
    }
}