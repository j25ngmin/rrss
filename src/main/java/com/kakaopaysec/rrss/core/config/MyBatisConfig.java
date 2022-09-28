package com.kakaopaysec.rrss.core.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages={"com.kakaopaysec.rrss.*.mapper", "com.kakaopaysec.rrss.**.*.mapper"}, sqlSessionFactoryRef="sqlSessionFactory", sqlSessionTemplateRef="sqlSessionTemplate")
public class MyBatisConfig {

   @Value("${mybatis.mapper.resource}")
   String mybatisMapperResource;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisMapperResource));
        return sqlSessionFactoryBean.getObject();
    }

}