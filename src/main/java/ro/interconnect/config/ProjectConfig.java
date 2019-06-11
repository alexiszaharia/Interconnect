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
        configurareDetalii.setNrElemPePagina(1);
        
        return configurareDetalii;
    }
}
