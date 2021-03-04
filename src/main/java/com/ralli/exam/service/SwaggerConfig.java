package com.ralli.exam.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ralli.exam"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false)
                ;
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Exam")
                .version("0.0.1")
                .description("Exam")
                .contact(new Contact("Andrew Miterev", "https://www.linkedin.com/in/andrew-miterev-9490b2b0/", "andrew.miterev@gmail.com"))
                .build();
    }

}
