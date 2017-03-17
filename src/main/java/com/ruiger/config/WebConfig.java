package com.ruiger.config;

import com.ruiger.interceptor.UserSessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author 睿哥
 * @version 1.0
 * @time 11:21
 * @description #
 * @since 2016/12/23
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	UserSessionInterceptor userSessionInterceptor(){
		return new UserSessionInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//拦截器
		registry.addInterceptor(userSessionInterceptor())
				.addPathPatterns("/wx/**");
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController( "/index" ).setViewName("index.html" );
		registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
		super.addViewControllers( registry );
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		super.configurePathMatch(configurer);
		configurer.setUseSuffixPatternMatch(false);
	}

}
