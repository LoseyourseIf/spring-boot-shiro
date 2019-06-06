package xingyu.lu.springboot.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xingyu.lu
 * @create 2019-06-03 15:26
 **/
@SpringBootApplication
@MapperScan(basePackages = {"xingyu.lu.springboot.shiro.dao"})
public class SpringBootShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootShiroApplication.class, args);
    }
}