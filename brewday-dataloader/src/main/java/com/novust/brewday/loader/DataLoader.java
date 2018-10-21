package com.novust.brewday.loader;

import com.novust.util.AppContextBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        ApplicationContext ctx  = new AppContextBuilder<>(AnnotationConfigApplicationContext::new)
                .withPropertyFile(configFileLocation)
                .withConfigurationClass(BrewDayDataLoaderConfiguration.class)
                .build();

        DataLoaderWorker dataLoaderWorker = ctx.getBean(DataLoaderWorker.class);
        dataLoaderWorker.loadData(dataLoaderConfigFileLocation);
    }
}
