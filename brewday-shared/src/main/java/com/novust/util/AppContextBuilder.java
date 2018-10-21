package com.novust.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ResourceUtils;

import java.net.MalformedURLException;
import java.util.List;
import java.util.function.Supplier;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Handles loading of a config properties file, either a filesystem file or a classpath resource,
 * and creates an initial context for it.
 */
public class AppContextBuilder<T extends AbstractApplicationContext & AnnotationConfigRegistry> {
    T appCtx;
    private List<String> appPropertyFiles = newArrayList();
    private List<Class> configurationClasses = newArrayList();

    public AppContextBuilder(Supplier<T> supplier) {
        this.appCtx = supplier.get();
    }

    public AppContextBuilder withPropertyFile(String propertyFile) {
        appPropertyFiles.add(propertyFile);
        return this;
    }

    public AppContextBuilder withConfigurationClass(Class configurationClass) {
        configurationClasses.add(configurationClass);
        return this;
    }

    public ApplicationContext build() {
        appPropertyFiles.forEach(fileName -> {
            PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = getPropertyConfigurer(fileName);
            appCtx.addBeanFactoryPostProcessor(propertyPlaceholderConfigurer);
        });
        configurationClasses.forEach(aClass -> appCtx.register(aClass));
        appCtx.refresh();
        return appCtx;
    }

    private PropertySourcesPlaceholderConfigurer getPropertyConfigurer(String propFile)  {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources;
        try {
            resources = new Resource[] { pathToUrl(propFile) };
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ppc.setLocations(resources);
        return ppc;
    }

    private UrlResource pathToUrl(String filePath) throws MalformedURLException {
        return ResourceUtils.isUrl(filePath) ? new UrlResource(filePath) : new UrlResource("file", filePath);
    }

}
