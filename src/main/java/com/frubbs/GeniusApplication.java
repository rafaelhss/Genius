package com.frubbs;

import com.github.scribejava.apis.GeniusApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


@SpringBootApplication
public class GeniusApplication {

	private static final String NETWORK_NAME = "Genius";
	private static final String PROTECTED_RESOURCE_URL = "https://api.genius.com/songs/378195";


	@Bean
	public ServletRegistrationBean logonServletRegistrationBean(){
		return new ServletRegistrationBean(new LogonServlet(),"/Logon/*");
	}

	@Bean
	public ServletRegistrationBean callbackServletRegistrationBean(){
		return new ServletRegistrationBean(new CallBackServlet(),"/CallBack/*");
	}




	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

		SpringApplication.run(GeniusApplication.class, args);
	}
}
