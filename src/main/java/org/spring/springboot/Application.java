package org.spring.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


// Spring Boot 应用的标识


/**
 * @author  Carlyle
 * @date 	Jul 24, 2017 4:30:19 PM
 * @description
 * 
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
// mapper 接口类扫描包配置
@MapperScan("org.spring.springboot.dao")
public class Application {

    public static void main(String[] args) {
    	
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class,args);
    }
}
