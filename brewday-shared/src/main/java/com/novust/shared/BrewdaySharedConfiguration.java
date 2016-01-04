package com.novust.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@ComponentScan(basePackageClasses = { BrewdaySharedConfiguration.class})
public class BrewdaySharedConfiguration {
    public BrewdaySharedConfiguration() {
        System.out.println("hi mom");
    }

    @Value("${mongodb.host:localhost}")
    String host;

    @Value("${mongodb.dbname:dbname}")
    String database;

    @Bean(name="defaultObjectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    MongoClient mongoClient() {
        return new MongoClient(host);
    }

    @Bean
    MongoDbFactory mongoDbFactory(MongoClient mongoClient) throws Exception {
        return new SimpleMongoDbFactory(mongoClient, database);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) throws Exception {
        return new MongoTemplate(mongoDbFactory);
    }
}
