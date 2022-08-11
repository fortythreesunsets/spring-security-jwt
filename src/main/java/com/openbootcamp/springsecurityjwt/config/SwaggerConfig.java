package com.openbootcamp.springsecurityjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Configuración Swagger para la generación de documentación de la API REST
 * HTML: http://localhost:8080/swagger-ui/
 * JSON: http://localhost:8080/v2/api-docs
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKey() { return new ApiKey("JWT", AUTHORIZATION_HEADER, "header"); }

//    @Bean
//    public InternalResourceViewResolver defaultViewResolver() { return new InternalResourceViewResolver(); }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiDetails() {
        return new ApiInfoBuilder().title("Awesome API REST")
                .description("Pruebas Spring Boot API REST")
                .version("1.0")
                .termsOfServiceUrl("http://localhost/terms")
                .contact(new Contact("fortythreesunsets", "https://github.com/fortythreesunsets", "fortythreesunsets@mail.com"))
                .license("MIT")
                .licenseUrl("Prueba")
                .build();
//                Collections.emptyList();
    }
}
