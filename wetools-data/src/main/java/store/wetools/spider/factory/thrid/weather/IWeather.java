package store.wetools.spider.factory.thrid.weather;

public interface IWeather {

    public String getData(String url, String params);

    public String dealData(String data);

}
