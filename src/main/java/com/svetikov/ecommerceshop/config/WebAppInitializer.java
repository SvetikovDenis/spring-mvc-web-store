package com.svetikov.ecommerceshop.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(PersistenceJPAConfig.class,WebMvcConfig.class);
        context.setServletContext(servletContext);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }
}
