package com.appwebexamen.appwebexamen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/*
 * clase configuración para señalarle a spring donde se ubicarán las vista jsp
 * con esto, es necesario se creen las carpetas WEB-INF y subcarpetas
 * 
 */


@Configuration
public class JspConfig {
	@Bean
	InternalResourceViewResolver jspViewResolver(){
	    final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setOrder(10);
	    viewResolver.setViewClass(JstlView.class);
	    viewResolver.setViewNames("*.jsp");
	    viewResolver.setPrefix("/WEB-INF/jsp/");
	    viewResolver.setSuffix("");
	    
	    return viewResolver;
	}
}
