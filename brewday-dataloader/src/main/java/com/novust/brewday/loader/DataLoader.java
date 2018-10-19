package com.novust.brewday.loader;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ResourceUtils;

import java.net.MalformedURLException;

public class DataLoader {

    static String configFileLocation = null;
    static String dataLoaderConfigFileLocation = null;

    public static void main(String[] args) throws ParseException, MalformedURLException {
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
        // TODO need this to work by:
        // If the config file is a classpath resource, path in the config file are also classpath resources
        // If the config file is NOT a classpath resource, paths in the config file are not classpath resources
        // paths with a leading / are absolute, paths w/out the leading / should be relative
        dataLoaderWorker.loadData(dataLoaderConfigFileLocation);

    }

    public static PropertySourcesPlaceholderConfigurer getPropertyConfigurer(String propFile) throws MalformedURLException {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new Resource[] { pathToUrl(propFile) };
        ppc.setLocations(resources);
        return ppc;
    }

    static UrlResource pathToUrl(String filePath) throws MalformedURLException {
        return ResourceUtils.isUrl(filePath) ? new UrlResource(filePath) : new UrlResource("file", filePath);
    }

}
