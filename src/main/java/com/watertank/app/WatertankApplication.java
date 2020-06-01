package com.watertank.app;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.watertank.util.WatertankHttpClientUtils;

/**
 * This is the main class witch will initialize the WatertankApplication
 * 
 * @author Hoffman
 *
 */
@ServletComponentScan
@EnableAutoConfiguration
@ComponentScan({ "com.watertank.rest", "com.watertank.dao.Imp", "com.watertank.app", "com.watertank.util" })
@SpringBootApplication
@PropertySource("classpath:application.properties")

public class WatertankApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatertankApplication.class, args);
	}

}
