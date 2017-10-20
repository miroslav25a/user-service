package com.iceze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.iceze"})
public class UserServiceApp 
{
    public static void main( String[] args ) {
    	SpringApplication.run(UserServiceApp.class, args);
    }
}
