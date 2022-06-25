package com.estockmarket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.estockmarket.app.filters.ErrorFilter;
import com.estockmarket.app.filters.PostFilter;
import com.estockmarket.app.filters.PreFilter;
import com.estockmarket.app.filters.RouteFilter;

@SpringBootApplication
@EnableZuulProxy
public class EStockMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(EStockMarketApplication.class, args);
	}
	
	@Bean
	public PreFilter preFilter() {
	  return new PreFilter();
	}
    @Bean
    public PostFilter postFilter() {
      return new PostFilter();
    }
	@Bean
	public ErrorFilter errorFilter() {
	  return new ErrorFilter();
	}
	@Bean
	public RouteFilter routeFilter() {
	  return new RouteFilter();
	}

}
