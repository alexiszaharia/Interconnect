/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author Alexis
 */
@Configuration
@ComponentScan(basePackages = {"ro.interconnect.*"}, 
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, 
                value = EnableWebMvc.class)})
public class ProjectConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:interconnect");
        dataSource.setUsername("USER_DIZERTATIE");
        dataSource.setPassword("123456");
        return dataSource;
    }
    
    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public ConfigurareDetalii configurareDetalii() {
        ConfigurareDetalii configurareDetalii = new ConfigurareDetalii();
        configurareDetalii.setNrElemPePagina(10);
        configurareDetalii.setCaleFisiere("C:\\Users\\Alexis\\Documents\\Interconnect");
        
        return configurareDetalii;
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        
        return bCryptPasswordEncoder;
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(500000);
        multipartResolver.setMaxUploadSizePerFile(250000);
        return multipartResolver;
    }
}
