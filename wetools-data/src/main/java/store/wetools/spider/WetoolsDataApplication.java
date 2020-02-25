package store.wetools.spider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class WetoolsDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(WetoolsDataApplication.class, args);
    }

}
