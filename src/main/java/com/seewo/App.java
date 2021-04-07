package com.seewo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cvte.psd.conf.core.spring.annotation.EnableApolloConfig;
import com.seewo.honeycomb.web.annotation.EnableWeb;
import com.seewo.tx.annotation.EnableCustomTransaction;
import org.springframework.scheduling.annotation.EnableAsync;

/** 
 * @Author: Nuwa 
 * @Description: 由IDEA插件Nuwa生成的类
 */
@EnableCustomTransaction
@EnableApolloConfig
@EnableWeb
@EnableAsync
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
