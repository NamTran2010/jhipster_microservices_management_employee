package com.leap.gateway.web.rest;

public @interface Order {
    int value() default Ordered.LOWEST_PRECEDENCE;
}