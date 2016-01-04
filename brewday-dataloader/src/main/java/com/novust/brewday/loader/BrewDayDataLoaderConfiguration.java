package com.novust.brewday.loader;

import com.novust.shared.BrewdaySharedConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackageClasses = {BrewDayDataLoaderConfiguration.class})
@ImportResource("classpath:/spring-loader.xml")
@Import({BrewdaySharedConfiguration.class})
public class BrewDayDataLoaderConfiguration {
}
