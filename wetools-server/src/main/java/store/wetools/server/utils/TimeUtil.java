package store.wetools.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 将时间戳转为指定的格式
     * @param stamp
     * @param format
     * @return
     */
    public static String stampToTime(String stamp, String format) {
        if (stamp == null || format == null) {
            return null;
        }
        if (stamp.length() == 10) {
            return new SimpleDateFormat(format).format(new Date(Long.valueOf(stamp) * 1000));
        }
        if (stamp.length() == 13) {
            return new SimpleDateFormat(format).format(Double.valueOf(stamp));
        }
        return null;
    }
}
