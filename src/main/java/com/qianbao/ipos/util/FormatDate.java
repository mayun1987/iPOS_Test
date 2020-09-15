package com.qianbao.ipos.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    //返回格式化当前时间
    public static String timeFormat(long timestamp, String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);  //"MM-dd HH:mm"  "YYYY-MM-dd HH:mm:ss"  "YYYYMMddHHmmssSSS"
        Date date = new Date(timestamp);
        String formatTime = formatter.format(date);
        return formatTime;
    }
}
