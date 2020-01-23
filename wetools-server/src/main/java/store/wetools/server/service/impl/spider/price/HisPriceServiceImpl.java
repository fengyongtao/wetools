package store.wetools.server.service.impl.spider.price;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.ArrayUtils;
import org.jfree.data.category.CategoryDataset;
import org.springframework.stereotype.Component;
import store.wetools.api.service.HisPriceService;
import store.wetools.server.utils.JfreeUtil;
import store.wetools.server.utils.SpiderHisPriceUtil;
import store.wetools.server.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Component
public class HisPriceServiceImpl implements HisPriceService {

    private static final String charPath = "E:/tools/";
    private static final String charName = "price.png";

    public String getHisPrice(String url) {
        // 根据url获取该商品的历史价格数据
        Map<String, Object> priceMap = SpiderHisPriceUtil.getHisPrice(url);
        // 转换数据类型
        List<String> xlist = new ArrayList<String>();
        List<Double> ylist = new ArrayList<Double>();
        List<Double> tempList = new ArrayList<Double>();
        for(Map.Entry<String, Object> entry : priceMap.entrySet()){
            double price = (double) entry.getValue();
            // tempList中如果已经包含了该价格，那么不在向xlist，ylist中追加数据
            if (!tempList.contains(price)) {
                tempList.add(price);
                xlist.add(TimeUtil.stampToTime((String) entry.getKey(), "yyyy-MM-dd"));
                ylist.add((double) entry.getValue());
            }
        }
        String[] xdata = xlist.toArray(new String[xlist.size()]);
        double[][] ydata = {ArrayUtils.toPrimitive(ylist.toArray(new Double[ylist.size()]))};
        // 根据历史价格数据生成想用的图片并存储到服务器上
        makeLineAndShapeChart(xdata, ydata);
        // 将该图片的路径返回
        return charPath + charName;
    }

    /**
     * 生成折线图
     * @param xdata
     * @param ydata
     */
    public void makeLineAndShapeChart(String[] xdata, double[][] ydata) {
        String TITLE = "历史价格走势图";
        String X = "time";
        String Y = "price";
        String[] rowKeys = {"price"};
        CategoryDataset dataset = JfreeUtil.getBarData(ydata, rowKeys, xdata);
        JfreeUtil.createTimeXYChar(TITLE, X, Y, dataset, charPath, charName);
    }

    public static void main(String[] args) {
        HisPriceServiceImpl hisPriceServiceImpl = new HisPriceServiceImpl();
        hisPriceServiceImpl.getHisPrice("https%3a%2f%2fdetail.tmall.com%2fitem.htm%3fid%3d606231126363%26ali_trackid%3d2%3amm_26632572_0_0%3a1579353672_265_763784451%26spm%3da21bo.7925890.192091.1%26pvid%3da0625b2d-998b-4781-a07b-52ba57a7844b%26scm%3d1007.12846.156652.999999999999999");

    }


}
