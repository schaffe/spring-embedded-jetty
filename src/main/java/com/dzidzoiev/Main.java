package com.dzidzoiev;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


public class Main {
    public static void main(final String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(AppConfig.class);
        applicationContext.registerShutdownHook();
        applicationContext.refresh();
    }
}
