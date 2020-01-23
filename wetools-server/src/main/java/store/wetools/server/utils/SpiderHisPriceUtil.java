package store.wetools.server.utils;

import com.alibaba.fastjson.JSONArray;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpiderHisPriceUtil implements PageProcessor {

    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);

    @Override
    public Site getSite() {
        return this.site;
    }

    private static Map<String, Object> priceMap = new LinkedHashMap<String, Object>();

    @Override
    public void process(Page page) {
        String script = String.valueOf(page.getHtml().xpath("body//script"));
        System.out.println(script);
        String pre = "<script type=\"text/javascript\">$(document).ready(function () { flotChart.chartNow('";
        script = script.substring(pre.length(), script.length());
        System.out.println(script);
        script = script.substring(0, script.indexOf("','http"));
        System.out.println(script);
        // [1572883200000,648.0,""],[1572969600000,648.0,""],[1573056000000,648.0,""],[1573142400000,648.0,""],[1573228800000,648.0,""],[1573315200000,648.0,""],[1573401600000,583.00,""],[1573488000000,648.00,""],[1573574400000,648.00,""],[1573660800000,648.00,""],[1573747200000,648.00,""],[1573833600000,648.00,""],[1573920000000,648.00,""],[1574006400000,648.00,""],[1574092800000,648.00,""],[1574179200000,648.00,""],[1574265600000,648.00,""],[1574352000000,648.00,""],[1574438400000,648.00,""],[1574524800000,648.00,""],[1574611200000,648.00,""],[1574697600000,648.00,""],[1574784000000,648.00,""],[1574870400000,648.00,""],[1574956800000,648.00,""],[1575043200000,648.00,""],[1575129600000,648.00,""],[1575216000000,648.00,""],[1575302400000,598.0,""],[1575388800000,598.0,""],[1575475200000,598.0,""],[1575561600000,598.0,""],[1575648000000,598.0,""],[1575734400000,598.0,""],[1575820800000,598.0,""],[1575907200000,598.0,""],[1575993600000,598.0,""],[1576080000000,518.0,"购买1件,当前价:598.0,优惠券：满400减50,满减：每满300减30"],[1576166400000,598.0,""],[1576252800000,598.0,""],[1576339200000,598.0,""],[1576425600000,598.0,""],[1576512000000,598.0,""],[1576598400000,598.0,""],[1576684800000,598.0,""],[1576771200000,598.0,""],[1576857600000,598.0,""],[1576944000000,598.0,""],[1577030400000,598.0,""],[1577116800000,598.0,""],[1577203200000,598.0,""],[1577289600000,598.0,""],[1577376000000,598.0,""],[1577462400000,598.0,""],[1577548800000,598.0,""],[1577635200000,598.0,""],[1577721600000,598.0,""],[1577808000000,598.0,""],[1577894400000,598.0,""],[1577980800000,598.0,""],[1578067200000,598.0,""],[1578153600000,598.0,""],[1578240000000,598.0,""],[1578326400000,598.0,""],[1578412800000,598.0,""],[1578499200000,598.0,""],[1578585600000,598.0,""],[1578672000000,598.0,""],[1578758400000,598.0,""],[1578844800000,598.0,""],[1578931200000,598.0,""],[1579017600000,598.0,""],[1579104000000,598.0,""],[1579190400000,598.0,""],[1579276800000,598,""]
        script = "[" + script + "]";
        JSONArray scriptJSONArray = JSONArray.parseArray(script);
        for (int i = 0; i < scriptJSONArray.size(); i++) {
            JSONArray eleJSONArray = (JSONArray) scriptJSONArray.get(i);
            String time = String.valueOf(eleJSONArray.get(0));
            double price = Double.valueOf(eleJSONArray.get(1).toString());
            priceMap.put(time, price);
        }
    }

    public static Map<String, Object> getHisPrice(String url) {
        url = "http://p.zwjhl.com/price.aspx?url=" + url;
        Spider.create(new SpiderHisPriceUtil()).addUrl(url).thread(1).run();
        return priceMap;
    }

    public static void main(String[] args) {
        Spider.create(new SpiderHisPriceUtil())
                .addUrl("http://p.zwjhl.com/price.aspx?url=https%3a%2f%2fdetail.tmall.com%2fitem.htm%3fid%3d606231126363%26ali_trackid%3d2%3amm_26632572_0_0%3a1579353672_265_763784451%26spm%3da21bo.7925890.192091.1%26pvid%3da0625b2d-998b-4781-a07b-52ba57a7844b%26scm%3d1007.12846.156652.999999999999999")
                .thread(1).run();
        System.out.println(priceMap);
    }
}
