package cn.cotenite.leaf;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author RichardYoung
 * @Description
 * @Date 2025/3/20 11:44
 */
@Configurable
@EnableScheduling
@EnableDubbo
@SpringBootApplication
public class IdApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(IdApplication.class, args);
    }
}
