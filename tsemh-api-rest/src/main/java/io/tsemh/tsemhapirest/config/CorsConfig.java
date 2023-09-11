package io.tsemh.tsemhapirest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	//em produção permitir apenas meu host enviar requisições
        registry.addMapping("/**")
        		.allowCredentials(true)
                .allowedOrigins("https://tsemhweb-api-production.up.railway.app/tsemhapi/")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
