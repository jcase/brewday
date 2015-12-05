package com.novust.brewday;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class BrewDayService {
    public static final Logger logger = LoggerFactory.getLogger(BrewDayService.class);


    public static final String LOG_PATH = "./logs/brewday.log";

    static String configFileLocation = null;

    public static void main(String[] args) throws ParseException {
        System.out.println("STARTING");
        Options options = new Options();
        options.addOption("f", true, "configuration file (property)");

        CommandLineParser parser = new BasicParser();
        CommandLine cmdLine = parser.parse(options, args);

        if(cmdLine.hasOption("f")) {
            configFileLocation = cmdLine.getOptionValue("f");
        }
        // TODO other options...


        if(StringUtils.isBlank(configFileLocation)) {
            logger.error("No supplied config file");
            return;
        }
        logger.info("Got config file " + configFileLocation);

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = getPropertyConfigurer(configFileLocation);
        ctx.addBeanFactoryPostProcessor(propertyPlaceholderConfigurer);
        ctx.register(BrewDayServerConfiguration.class);
        ctx.refresh();

        Server server = ctx.getBean(Server.class);
        logger.info("Starting server...");
        try {
            server.start();
            logger.info("Server started");
            server.join();
            logger.info("Server joined; exiting...");
        } catch (Exception e) {
            logger.error("Could not start server : {}", e);
        }
    }


    public static PropertySourcesPlaceholderConfigurer getPropertyConfigurer(String propFile) {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new Resource[] { new ClassPathResource(propFile) };
        ppc.setLocations(resources);
        return ppc;
    }
}
