package com.example.turistguidedel3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(); // Midlertidig løsning uden database
    }
}