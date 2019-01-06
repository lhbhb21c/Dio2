package com.community.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//데이터베이스 설정 클래스 입니다.
//xml로 비교하면 rootcontext.xml와 같습니다. 
@Configuration
@ComponentScan(basePackages= {"com.community.service"})
@ComponentScan(basePackages="com.community.aop")
@ComponentScan(basePackages="com.community.task")

@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement

@MapperScan(basePackages= {"com.community.mapper"})
public class RootConfig {

	
	
  @Bean
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
    hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE");

    hikariConfig.setUsername("beom");
    hikariConfig.setPassword("1234");

    hikariConfig.setMinimumIdle(5);

    HikariDataSource dataSource = new HikariDataSource(hikariConfig);

    return dataSource;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource());
    return (SqlSessionFactory) sqlSessionFactory.getObject();
  }
  
  @Bean
  public DataSourceTransactionManager txManager() {
      return new DataSourceTransactionManager(dataSource());
  }

}

