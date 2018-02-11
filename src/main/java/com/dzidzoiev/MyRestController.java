package com.dzidzoiev;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/greet")
@Component
public class MyRestController {

    @GET
    public String greet() {
        return "Hello, Artur";
    }
}