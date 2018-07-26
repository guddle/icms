package com.ictframe.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author jerry
 * @date 2018-7-16
 */

@Configuration
@MapperScan("**.mybatis.mapper.**")
public class MybatisConfig {

}
