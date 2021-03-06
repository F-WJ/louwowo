package cn.wolfcode.luowowo.article;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.wolfcode.luowowo.article.mapper")
@EnableDubbo
public class ActicleServer {

    public static void main(String[] args) {
        SpringApplication.run(ActicleServer.class, args);
    }
}
