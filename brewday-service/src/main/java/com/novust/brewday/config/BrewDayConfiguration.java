package com.novust.brewday.config;

import com.novust.brewday.HelloThing;
import com.novust.brewday.handlers.HelloHandler;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class BrewDayConfiguration implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(BrewDayConfiguration.class);

    ApplicationContext applicationContext;

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

        server.setConnectors(new Connector[] { serverConnector });
        return server;
    }

    @Bean
    public ServletContextHandler getServletContextHandler() throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        String webapp = new ClassPathResource("webapp").getURI().toString();

        ServletHolder servletHolder = new ServletHolder("static", DefaultServlet.class);
        servletHolder.setInitParameter("resourceBase", webapp);
        servletHolder.setInitParameter("dirAllowed", "true");

        contextHandler.addServlet(servletHolder,"/");

        return contextHandler;
    }

    @Bean
    public ContextHandlerCollection getContextHandlerCollection(ServletContextHandler servletContextHandlers, ContextHandler[] contextHandlers) {
        ContextHandlerCollection handlerCollection = new ContextHandlerCollection();

        handlerCollection.addHandler(servletContextHandlers);
        for(ContextHandler contextHandler : contextHandlers) {
            handlerCollection.addHandler(contextHandler);
        }
        return handlerCollection;
    }

    @Bean(name="helloContext")
    public ContextHandler helloHandler() {
        ContextHandler context = new ContextHandler("/hello");
        context.setHandler(new HelloHandler("HELLO WORLD!", "This is the body"));
        return context;
    }

    @Bean(name="otherContext")
    public ContextHandler otherHelloHandler() {
        ContextHandler context = new ContextHandler("/other");
        context.setHandler(new HelloHandler("Goodbye world", "Goodbye body"));
        return context;
    }

}
