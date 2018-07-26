package com;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 1.entity存放的包必须是**data.entity.**
 * 2.repository定义放在**data.repository.**
 * 3.Mybatis mapper存放在**data.mybatis.mapper.**使用@MapperScan("**.data.mybatis.mapper.**")来注解，让mybatis知道
 * 还需要在配置文件中配置mybatis.mapper-locations=classpath*:**\mybatis\mapper\*.xml，*mapper.xml sql编写
 * @author jerry
 * @Date Jul 21, 2018
 */
@SpringBootApplication
@ComponentScan
public class IctApplication extends SpringBootServletInitializer{
	
	
	@Override
	protected SpringApplicationBuilder createSpringApplicationBuilder() {
		return super.createSpringApplicationBuilder();
	}

	@Bean
	public Queue queue() {
		return new ActiveMQQueue("sample.queue");
	}

	public static void main(String[] args) {
		SpringApplication.run(IctApplication.class, args);
	}
}
