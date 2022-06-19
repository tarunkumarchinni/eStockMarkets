package com.estockmarket.configuration;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.estockmarket.app.bean.Company;
import com.estockmarket.app.repository.CompanyRepository;
@EnableMongoRepositories(basePackageClasses = CompanyRepository.class)
@Configuration
public class MongoConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(CompanyRepository companyRepository) {
        return strings -> {
        	companyRepository.save(new Company("","Calvin", "Ryan","123",123,"123","122"));
        };
    }
}
