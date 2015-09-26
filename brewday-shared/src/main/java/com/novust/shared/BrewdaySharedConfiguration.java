package com.novust.shared;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { BrewdaySharedConfiguration.class})
public class BrewdaySharedConfiguration {
    public BrewdaySharedConfiguration() {
        System.out.println("hi mom");
    }
}
