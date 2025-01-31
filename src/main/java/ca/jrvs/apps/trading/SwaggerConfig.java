package ca.jrvs.apps.trading;

import static springfox.documentation.builders.PathSelectors.any;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfiguration.Constants;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(Predicates.not(
            RequestHandlerSelectors.basePackage("org.springframework.boot")))
        .paths(any())
        .build()
        .pathMapping("/")
        .apiInfo(new ApiInfo(
            "Jarvis Trading Rest App", "A Spring Rest API",
            "v1.0", null,
            new Contact("David Faig", "jrvs.ca", "david.faig.jrvs.ca@jrvs.ca"),
            null, null,
            new ArrayList<>()));
  }

  @Bean
  UiConfiguration uiConfiguration() {
    return UiConfigurationBuilder.builder()
        .deepLinking(true)
        .displayOperationId(false)
        .defaultModelsExpandDepth(1)
        .defaultModelExpandDepth(1)
        .defaultModelRendering(ModelRendering.MODEL)
        .displayRequestDuration(true)
        .docExpansion(DocExpansion.NONE)
        .filter(false)
        .maxDisplayedTags(null)
        .operationsSorter(OperationsSorter.METHOD)
        .showExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
        .supportedSubmitMethods(Constants.DEFAULT_SUBMIT_METHODS)
        .validatorUrl(null)
        .build();
  }
}
