package store.wetools.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import store.wetools.api.been.User;
import store.wetools.api.service.UserService;

@RestController
@RequestMapping("/user")
public class GreetingController {
    @Reference
    private UserService userService;

    @ResponseBody
    @RequestMapping("/getUser")
    public User getUser(@RequestParam String id) {
        return userService.getUser(id);
    }
}
