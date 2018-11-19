package net.megatree.shortenurl.config;

import net.megatree.shortenurl.security.ValidInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by mythss on 2018-11-20.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    ValidInterceptor validInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validInterceptor).addPathPatterns("/a/**");
    }
}
