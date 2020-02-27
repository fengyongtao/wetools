package store.wetools.server.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import store.wetools.api.been.spider.thrid.SpiderJuheWeather;
import store.wetools.api.service.spider.thrid.SpiderJuheWeatherService;
import store.wetools.server.dao.spider.thrid.SpiderJuheWeatherDao;

@Service
public class SpiderJuheWeatherServiceImpl implements SpiderJuheWeatherService {

    @Autowired
    private SpiderJuheWeatherDao spiderJuheWeatherDao;

    @Override
    public boolean insertSelective(SpiderJuheWeather spiderJuheWeather) {
        return spiderJuheWeatherDao.insertSelective(spiderJuheWeather);
    }
}
