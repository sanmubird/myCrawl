package com.etoak.common.commonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtil {


    /**
     * 取得当前日期
     *
     * @return String yyyy-mm-dd格式
     */
    public static String getDateDayStr() {
        return format(getDate(), "yyyy-MM-dd");
    }

    /**
     * 取得当前日期
     *
     * @return String yyyy-mm-dd格式
     */
    public static String getDateTImeStr() {
        return format(getDate(), "yyyy-MM-dd HH:mm:ss");
    }


    /*
    * 得到日期
    * */
    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }


    /**
     * 格式：dateFormat 指定的格式
     *
     * @param date       待格式化的日期
     * @param dateFormat 日期格式
     * @return 日期的字符串格式，date为空时返回""
     */
    public static String format(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /*判断字符串是否符合日期格式*/
    public static boolean isDateTime(String datetime) {
        Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))((\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))|(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9]))))?$");
        return p.matcher(datetime).matches();
    }

    /*将日期字符串转化为标准的日期字符串'yyyy-MM-dd hh:mm:ss' */
    public static String formatter2StandardDatestr(String datetime) {
        StringBuilder datebuilder = new StringBuilder(32);
        datetime = datetime.trim();
        int l = datetime.length();
        if (l == 19) {
            datebuilder.append(datetime);
        } else if (l < 10) {
            datebuilder.append(formatterDateStr(datetime)).append(" 00:00:00");
        } else {
            String[] dataArr = datetime.split(" ");
            if (dataArr.length == 2) {
                datebuilder.append(formatterDateStr(dataArr[0])).append(" ").append(formatterTimeStr(dataArr[1]));
            } else {
                datebuilder.append(formatterDateStr(dataArr[0])).append(" 00:00:00");
            }
        }
        return datebuilder.toString();
    }

    /*格式化日期字符串*/
    private static String formatterDateStr(String dateStr) {
        StringBuilder datebuilder = new StringBuilder(16);
        if (dateStr.length() == 10) {
            datebuilder.append(dateStr);
        } else {
            if (dateStr.contains("/")) {
                dateStr = dateStr.replaceAll("/", "-");
            }
            String[] dataArr = dateStr.split("-");
            for (String s : dataArr) {
                if (s.length() == 1) {
                    datebuilder.append("0").append(s).append("-");
                } else {
                    datebuilder.append(s).append("-");
                }
            }
            datebuilder = datebuilder.deleteCharAt(datebuilder.lastIndexOf("-"));
            switch (datebuilder.length()) {
                case 4:
                    datebuilder.append("-01-01");
                    break;
                case 7:
                    datebuilder.append("-01");
                    break;
                default:
                    break;
            }
        }
        return datebuilder.toString();
    }

    /*格式化时间字符串*/
    private static String formatterTimeStr(String timeStr) {
        StringBuilder datebuilder = new StringBuilder(16);
        if (timeStr.length() == 8) {
            datebuilder.append(timeStr);
        } else {
            String[] dataArr = timeStr.split(":");
            for (String s : dataArr) {
                if (s.length() == 1) {
                    datebuilder.append("0").append(s).append(":");
                } else {
                    datebuilder.append(s).append(":");
                }
            }
            datebuilder = datebuilder.deleteCharAt(datebuilder.lastIndexOf(":"));
            switch (datebuilder.length()) {
                case 2:
                    datebuilder.append(":00:00");
                    break;
                case 5:
                    datebuilder.append(":00");
                    break;
                default:
                    break;
            }
        }
        return datebuilder.toString();
    }

    /**
     * 判断输入的距当前时间差是否超过指定的天数;仅考虑输入日期不超过当前日期;
     */
    public static boolean isAfterNowSomeDays( String dateStr , int days ){
        dateStr = formatter2StandardDatestr(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 日历对象
        Calendar cal = Calendar.getInstance();
        // 设置为输入日期
        cal.setTime(new Date());
        // 日期加法
        cal.add(Calendar.DATE, -days);
        // 日期结果
        Date someDay = cal.getTime();
        try {
            Date inputDay = sdf.parse(dateStr);
            return ( someDay.getTime() - inputDay.getTime()) > 0 ? false : true ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false ;
    }
}
