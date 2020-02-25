package store.wetools.server.service.impl.spider.zhihu;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import store.wetools.api.been.spider.zhihu.SpiderZhihuUser;
import store.wetools.api.service.spider.zhihu.SpiderZhihuUserService;
import store.wetools.server.dao.spider.zhihu.SpiderZhihuUserDao;

@Service
@Component
public class SpiderZhihuUserServiceImpl implements SpiderZhihuUserService {


    @Autowired
    private SpiderZhihuUserDao zhihuUserDao;

    @Override
    public int saveUser(SpiderZhihuUser spiderZhihuUser) {
        return zhihuUserDao.saveUser(spiderZhihuUser);
    }
}
