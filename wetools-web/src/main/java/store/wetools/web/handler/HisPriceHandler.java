package store.wetools.web.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutImageMessage;
import com.soecode.wxtools.bean.result.WxMediaUploadResult;
import com.soecode.wxtools.exception.WxErrorException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import store.wetools.api.service.HisPriceService;
import store.wetools.web.utils.TkLUtil;

import java.io.File;
import java.util.Map;

@Component
public class HisPriceHandler implements WxMessageHandler {

    @Reference
    private HisPriceService hisPriceService;

    @SneakyThrows
    @Override
    public WxXmlOutImageMessage handle(WxXmlMessage wxXmlMessage, Map<String, Object> context, IService iService) throws WxErrorException {
        System.out.println("用户消息：" + wxXmlMessage.getContent());
        String url = null;
        String tkl = TkLUtil.getTKL(wxXmlMessage.getContent());
        if (!"".equals(tkl)) {
            String res = TkLUtil.TKLDecrypt(tkl);
            JSONObject resJson = JSONObject.parseObject(res);
            url = (String) resJson.get("url");
        } else {
            url = wxXmlMessage.getContent();
        }
        String filePath = hisPriceService.getHisPrice(url);
        WxMediaUploadResult result = iService.uploadTempMedia(WxConsts.MEDIA_IMAGE,new File(filePath));
        return WxXmlOutImageMessage.IMAGE()
                .toUser(wxXmlMessage.getFromUserName())
                .fromUser(wxXmlMessage.getToUserName())
                .mediaId(result.getMedia_id())
                .build();
    }
}
