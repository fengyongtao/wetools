package store.wetools.spider.factory.zhihu;

import store.wetools.spider.factory.ISpider;

import java.io.IOException;

public interface IZhSpider extends ISpider {
    public Object getData() throws IOException;

    public Object dealData();
}
