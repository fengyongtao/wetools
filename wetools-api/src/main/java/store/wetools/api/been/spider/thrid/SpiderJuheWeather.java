package store.wetools.api.been.spider.thrid;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpiderJuheWeather implements Serializable {

    // 源数据
    private String sourceData;

    // 日期
    private String date;

    // 温度
    private String temperature;

    // 天气情况
    private String weather;

    // 风向
    private  String direct;


}
