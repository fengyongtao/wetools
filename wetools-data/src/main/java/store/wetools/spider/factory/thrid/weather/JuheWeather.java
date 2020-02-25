package store.wetools.spider.factory.thrid.weather;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import store.wetools.api.been.spider.thrid.SpiderJuheWeather;
import store.wetools.api.service.spider.thrid.SpiderJuheWeatherService;
import store.wetools.spider.utils.HttpUtil;
import store.wetools.spider.utils.TimeUtil;

/**
 * 聚合数据-天气查询
 * 通过城市名称或城市ID查询天气预报情况
 */
@Component
public class JuheWeather extends Weather implements IWeather {

    private static final String key = "3a4ca83b281dc643c17483c4def4b0ed";

    private static final String url = "http://apis.juhe.cn/simpleWeather/query";

    @Reference
    private SpiderJuheWeatherService spiderJuheWeatherService;

    @Override
    public String doWeather(String city) {
        // 请求参数
        String params = "key=" + key + "&city=" + city;
        String str = dealData(getData(url, params));
        return null;
    }

    @Override
    public String getData(String url, String params) {
        String res = null;
        try {
            res = HttpUtil.sendGet(url, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }


    /**
     * 处理数据示例
     * {"reason":"查询成功!","result":{"city":"天津","realtime":{"temperature":"1","humidity":"62","info":"霾","wid":"53","direct":"西北风","power":"3级","aqi":"119"},"future":[{"date":"2020-02-07","temperature":"-3\/5℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西南风转西北风"},{"date":"2020-02-08","temperature":"-2\/8℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西北风转东南风"},{"date":"2020-02-09","temperature":"-1\/9℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西南风"},{"date":"2020-02-10","temperature":"0\/12℃","weather":"晴转多云","wid":{"day":"00","night":"01"},"direct":"西南风转南风"},{"date":"2020-02-11","temperature":"3\/13℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"东风转东北风"}]},"error_code":0}
     * @param data
     * @return
     */
    @Override
    public String dealData(String data) {
        JSONObject jobj = JSONObject.parseObject(data);
        if (!"0".equals(jobj.get("error_code").toString())) {
            return null;
        };
        JSONObject resInfo = (JSONObject) jobj.get("result");
        // 当前天气详情情况
//        JSONObject rtInfo = (JSONObject) resInfo.get("realtime");
//        String info = (String) rtInfo.get("info"); // 天气情况，如：晴、多云
//        String wid = (String) rtInfo.get("wid"); // 天气标识id，可参考小接口2
//        String temperature = (String) rtInfo.get("temperature"); // 温度，可能为空
//        String humidity = (String) rtInfo.get("humidity"); // 湿度，可能为空
//        String direct = (String) rtInfo.get("direct"); // 风向，可能为空
//        String power = (String) rtInfo.get("power"); // 风力，可能为空
//        String aqi = (String) rtInfo.get("aqi"); // 空气质量指数，可能为空

        // 	近5天天气情况
        JSONArray futureArray = (JSONArray) resInfo.get("future");
        for (int i = 0; i < futureArray.size(); i++) {
            JSONObject futureInfo = (JSONObject) futureArray.get(i);
            String date = (String) futureInfo.get("date");
            // 只解析当前日期的数据
            if (TimeUtil.getCurrentTime("yyyy-MM-dd").equals(date)) {
                SpiderJuheWeather jw = new SpiderJuheWeather();
                jw.setDate(date);
                jw.setDirect((String) futureInfo.get("direct"));
                jw.setSourceData(data);
                jw.setTemperature((String) futureInfo.get("temperature"));
                jw.setWeather((String) futureInfo.get("weather"));
                spiderJuheWeatherService.insertSelective(jw);
            }
        }
        return null;
    }

}
