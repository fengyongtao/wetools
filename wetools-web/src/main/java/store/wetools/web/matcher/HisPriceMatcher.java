package store.wetools.web.matcher;

import com.soecode.wxtools.api.WxMessageMatcher;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.util.StringUtils;
import store.wetools.web.utils.TkLUtil;

public class HisPriceMatcher implements WxMessageMatcher {

    @Override
    public boolean match(WxXmlMessage message) {
        if(StringUtils.isNotEmpty(message.getContent())){
            // 匹配商品网址
            if(message.getContent().startsWith("http://") || message.getContent().startsWith("https://")){
                return true;
            }
            if(message.getContent().startsWith("http://")){
                return true;
            }
            // 匹配套口令
            if ("" != TkLUtil.getTKL(message.getContent())) {
                return true;
            }
        }
        return false;
    }
}
