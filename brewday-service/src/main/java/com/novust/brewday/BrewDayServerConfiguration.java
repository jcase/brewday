package com.novust.brewday;

import com.novust.shared.BrewdaySharedConfiguration;
import com.novust.shared.data.HopData;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Configuration
@ComponentScan(basePackageClasses = {BrewDayServerConfiguration.class})
@Import({BrewdaySharedConfiguration.class})
public class BrewDayServerConfiguration implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(BrewDayServerConfiguration.class);

    ApplicationContext applicationContext;

    @Autowired
    BrewdaySharedConfiguration sharedConfiguration;

    @Autowired
    HopData hopData;

    @Value("${http.bindPort:8080}")
    private int httpBindPort;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Value("${brewday.helloworld}")
    String helloWorld;

    @Value("${http.port:8080}")
    int port;

    @Bean
    public HelloThing getThing() {
        return new HelloThing(helloWorld);
    }

    @Bean
    public Server getServer(ContextHandlerCollection handlerCollection) {
        Server server = new Server(port);
        setConnectorsOnServer(server);

        server.setHandler(handlerCollection);
        return server;
    }

    public Server setConnectorsOnServer(Server server) {
        HttpConfiguration configuration = new HttpConfiguration();
        ServerConnector serverConnector = new ServerConnector(server, new HttpConnectionFactory(configuration));
        serverConnector.setPort(httpBindPort);
        // TODO: SSL

        server.setConnectors(new Connector[]{serverConnector});
        return server;
    }

    @Bean
    public ContextHandlerCollection getContextHandlerCollection(ServletContextHandler[] servletContextHandlers) {
        ContextHandlerCollection handlerCollection = new ContextHandlerCollection();

        for (ServletContextHandler servletContextHandler : servletContextHandlers) {
            handlerCollection.addHandler(servletContextHandler);
        }
        return handlerCollection;
    }

    // Static content
    @Bean
    public ServletContextHandler getServletContextHandler() throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        String webapp = new ClassPathResource("webapp").getURI().toString();

        ServletHolder servletHolder = new ServletHolder("static", DefaultServlet.class);
        servletHolder.setInitParameter("resourceBase", webapp);
        servletHolder.setInitParameter("dirAllowed", "true");

        contextHandler.addServlet(servletHolder, "/");

        return contextHandler;
    }

    @Bean
    public ResourceConfig getResourceConfig() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(com.novust.brewday.rest.HopService.class);
        return resourceConfig;
    }

    // REST handlers
    @Bean(name="restContext")
    public ServletContextHandler getRestServletContextHandler(WebApplicationContext webApplicationContext,  ResourceConfig resourceConfig) throws IOException {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addEventListener(new ContextLoaderListener(webApplicationContext));
        servletContextHandler.setContextPath("/api");
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        servletContextHandler.addServlet(servletHolder, "/*");
        return servletContextHandler;
    }

}
