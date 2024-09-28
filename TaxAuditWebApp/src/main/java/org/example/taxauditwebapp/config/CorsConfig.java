package org.example.taxauditwebapp.config; // Adjust the package if you placed it elsewhere

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allows CORS for all endpoints
                .allowedOrigins("*") // Replace with your frontend URL and port
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Specify allowed methods
                .allowedHeaders("*") // Allows all headers
                .allowCredentials(false); // Allows credentials like cookies or authorization headers
    }
}
