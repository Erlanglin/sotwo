package org.myoranges.sotwo.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket publicApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("公共")
                .genericModelSubstitutes(DeferredResult.class)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.myoranges"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(publicApiInfo());
    }

    private ApiInfo publicApiInfo() {
        return new ApiInfoBuilder()
                .title("sotwo-wx-api - API文档")
                .description("接口文档")
                .version("1.0.0")
                .license("sotwo")
                .build();
    }
}
