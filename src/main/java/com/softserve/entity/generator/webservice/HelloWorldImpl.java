package com.softserve.entity.generator.webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "com.softserve.entity.generator.webservice.HelloWorld")
public class HelloWorldImpl implements HelloWorld{

    @Override
    public String getHelloWorldAsString() {
        return "Hello World JAX-WS";
    }
}
