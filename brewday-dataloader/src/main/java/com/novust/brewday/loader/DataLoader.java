package com.novust.brewday.loader;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DataLoader {

    static String configFileLocation = null;
    static String dataLoaderConfigFileLocation = null;

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("f", true, "configFileLocation (properties file)");
        options.addOption("d", true, "dataloader configuration file (json file)");

        CommandLineParser parser = new BasicParser();
        CommandLine cmdLine = parser.parse(options, args);

        if(cmdLine.hasOption("f")) {
            configFileLocation = cmdLine.getOptionValue("f");
        }

        if(cmdLine.hasOption("d")) {
            dataLoaderConfigFileLocation = cmdLine.getOptionValue("d");
        }

        if(StringUtils.isBlank(configFileLocation)) {
            throw new IllegalArgumentException("Missing configFileLocation");
        }
        if(StringUtils.isBlank(dataLoaderConfigFileLocation)) {
            throw new IllegalArgumentException("Missing dataLoader configuration file");
        }

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = getPropertyConfigurer(configFileLocation);
        ctx.addBeanFactoryPostProcessor(propertyPlaceholderConfigurer);
        ctx.register(BrewDayDataLoaderConfiguration.class);
        ctx.refresh();

        DataLoaderWorker dataLoaderWorker = ctx.getBean(DataLoaderWorker.class);
        dataLoaderWorker.loadData(dataLoaderConfigFileLocation);

    }

    public static PropertySourcesPlaceholderConfigurer getPropertyConfigurer(String propFile) {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new Resource[] { new ClassPathResource(propFile) };
        ppc.setLocations(resources);
        return ppc;
    }

}
