package store.wetools.server.utils;

import java.io.File;

public class FileUtil {

    /**
     * 判断文件夹是否存在，如果不存在则新建
     * @param chartPath
     */
    public static void isChartPathExist(String chartPath) {
        File file = new File(chartPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
