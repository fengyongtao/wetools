package store.wetools.spider.factory.zhihu;

import org.springframework.beans.factory.annotation.Autowired;
import store.wetools.api.service.spider.zhihu.SpiderZhihuUserService;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;

/**
 * 知乎用户小爬虫<br>
 * 输入搜索用户关键词(keyword)，并把搜出来的用户信息爬出来<br>

 * @date 2016-5-3
 * @website ghb.soecode.com
 * @csdn blog.csdn.net/antgan
 * @author antgan
 *
 */
public class ZhiHuUserPageProcessor implements PageProcessor{
    //抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000)
            .addCookie("KLBRSID","e42bab774ac0012482937540873c03cf|1579252388|1579252375");
    //用户数量
    private static int num = 0;

    @Autowired
    private SpiderZhihuUserService zhihuUserService;

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        // 设置代理
        /*HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("122.136.212.132",53281)));
*/
        long startTime ,endTime;
        System.out.println("========知乎用户信息小爬虫【启动】喽！=========");
        startTime = new Date().getTime();
        Spider.create(new ZhiHuUserPageProcessor())
                .addUrl("https://www.zhihu.com/people/excited-vczh/following").thread(1).run();
        endTime = new Date().getTime();
        System.out.println("========知乎用户信息小爬虫【结束】喽！=========");
        System.out.println("一共爬到"+num+"个用户信息！用时为："+(endTime-startTime)/1000+"s");
    }

    /**
     * process 方法是webmagic爬虫的核心<br>
     * 编写抽取【待爬取目标链接】的逻辑代码在html中。
     */
    @Override
    public void process(Page page) {

        // 判断是不是用户主页
        boolean matchFlag = page.getUrl().regex("https://www.zhihu.com/people/\\S/following").match();
        //1. 如果是用户列表页面 【入口页面】，将所有用户的详细页面的url放入target集合中。
        if(matchFlag){
            page.addTargetRequests(
                    page.getHtml().xpath(
                            "//div[@class='Profile-mainColumn']/div[@class='Card ProfileMain']/div[@class='ListShortcut']/div[@class='List']").links().all());
        } else{
            num++;//用户数++

        }
    }


}
