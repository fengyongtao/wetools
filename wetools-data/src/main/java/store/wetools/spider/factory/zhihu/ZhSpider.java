package store.wetools.spider.factory.zhihu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZhSpider implements IZhSpider {

    public static void main(String[] args) throws IOException {
        // 获取页面所有元素
        Document doc = Jsoup.connect("https://www.zhihu.com").get();
        List<String> hrefList = parseDoc(doc);
        for (int i = 0; i < hrefList.size(); i++) {
            System.out.println(hrefList.get(i));
        }
    }

    public static List<String> parseDoc(Document doc) {
        // 实例化list
        List<String> list = new ArrayList<String>();
        // 根据指定class获取对象（这里我们查看页面源码，选取了一个class为 new_top 的新闻 列表）
        Elements elements = doc.getElementsByClass("news_top");
        // 根据指定的标签获取连接内容（这里选取的是a标签中的内容）
        Elements links = elements.get(0).getElementsByTag("a");
        for (int i = 0; i < links.size(); i++) {
            // 讲遍历出来的每一个a标签对象中的href属性循环存入list中
            list.add(links.get(i).attr("href"));
        }
        // 返回list
        return list;
    }


        @Override
    public Object getData() throws IOException {
        //获取编辑推荐页
        Document document = Jsoup.connect("https://www.zhihu.com/explore/recommendations")
                // 模拟火狐浏览器
                .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                .userAgent("Mozilla").get();
        Element main=document.getElementById("zh-recommend-list-full");


        return null;
    }

    @Override
    public Object dealData() {
        return null;
    }
}
