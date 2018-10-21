package com.novust.brewday;

import com.novust.util.AppContextBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class BrewDayService {
    public static final Logger logger = LoggerFactory.getLogger(BrewDayService.class);

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

        ApplicationContext ctx  = new AppContextBuilder<>(AnnotationConfigWebApplicationContext::new)
                .withPropertyFile(configFileLocation)
                .withConfigurationClass(BrewDayServerConfiguration.class)
                .build();

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
}
