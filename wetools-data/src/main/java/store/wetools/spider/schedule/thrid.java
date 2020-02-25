package store.wetools.spider.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import store.wetools.spider.factory.thrid.weather.JuheWeather;

@Configuration
@EnableScheduling
public class thrid {

    @Autowired
    private JuheWeather juheWeather;

    @Scheduled(cron = "0/1 * * * * ?")
    public void juheWeatherTimer() {
        for (int i = 0; i < juheWeather.provinceList.size(); i++) {
            juheWeather.doWeather(juheWeather.provinceList.get(i));
        }
    }

}
