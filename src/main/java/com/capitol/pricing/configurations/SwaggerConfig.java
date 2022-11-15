package com.capitol.pricing.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfo("Pricing API",
                "APIs for challenge project.",
                "1.0",
                "Terms of service",
                new Contact("Pablo Aguerre", "www.linkedin.com/in/pablo-aguerre-b7a3aa8", "pablo.aguerre@gmail.com"),
                "Apache-2.0 license",
                "https://github.com/paguerre3/tpricing/blob/main/LICENSE",
                Collections.emptyList());
    }

    @Bean
    public Docket apiPricingDocket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .paths(getChallengePath()).build();
    }

    /**
     * Display core API that browses for price to apply only.
     *
     * @return String
     */
    private Predicate<String> getChallengePath() {
        return regex("/api/v1/prices.*");
    }
}
