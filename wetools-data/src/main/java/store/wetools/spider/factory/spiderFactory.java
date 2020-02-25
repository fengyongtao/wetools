package store.wetools.spider.factory;

public class spiderFactory {

    public static ISpider createDoc(Class<?> clazz) {
        ISpider spider = null;
        try {
            spider = (ISpider) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return spider;
    }
}
