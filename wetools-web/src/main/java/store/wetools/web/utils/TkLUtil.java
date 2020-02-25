package store.wetools.web.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 淘口令
 */
public class TkLUtil {
    public static final String pattern = "([\\p{Sc}])\\w{8,12}([\\p{Sc}])";

    private static final String URL = "https://api.taokouling.com/tkl/tkljm";
    private static final String apikey = "ipAoPdAOZV";

    /**
     * 从字符串中获取获取淘口令
     * @param content
     * @return
     */
    public static String getTKL(String content) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        if (m.find()) {
            return m.group();
        } else {
            return "";
        }
    }

    /**
     *
     * @param TKL
     * @return
     * @throws IOException
     */
    public static String TKLDecrypt(String TKL) throws Exception {
        /* 返回json样例
        {
          "ret": "调用成功",
          "url": "https://www.taobao.com/",
          "content": "百度搜淘口令网",
          "picUrl": "https://gw.alicdn.com/tfs/TB1c.wHdh6I8KJjy0FgXXXXzVXa-580-327.png",
          "taopwdOwnerId": "0",
          "validDate": "364天23小时56分38秒",
          "pjk": "taokouling.com",
          "code": 1,
          "msg": "调用成功"
        }
         */
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", apikey);
        params.put("tkl", TKL);
        return HttpUtil.httpPost(URL ,params);
    }

    public static void main(String[] args) throws Exception {
        String res = TKLDecrypt(getTKL("【露蒂芳2019冬季新款加厚圆领羊毛衫高领套头毛衣女修身针织打底衫】，椱ァ製整句话₳osi312BkCW4₳移步到氵匋Bao"));
        JSONObject resJson = JSONObject.parseObject(res);
        resJson.get("url");
    }


}
