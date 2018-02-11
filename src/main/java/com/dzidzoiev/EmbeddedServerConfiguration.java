package com.dzidzoiev;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

@Configuration
public class EmbeddedServerConfiguration implements ApplicationContextAware {
    private ApplicationContext ctx;

    @Bean(destroyMethod = "stop")
    public Server jettyServer() throws Exception {
        Server server = new Server(8080);

        // Register and map the dispatcher servlet
        final ServletHolder servletHolder = new ServletHolder(new CXFServlet());
        final ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(servletHolder, "/rest/*");

        ServletContext servletContext = context.getServletContext();
        //set reference to actual Spring ApplicationContext
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ctx);

        server.setHandler(context);
        return server;
    }

    @Bean
    @DependsOn("jaxRsServer")
    public Void initServer(Server server) throws Exception {
        server.start();
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
