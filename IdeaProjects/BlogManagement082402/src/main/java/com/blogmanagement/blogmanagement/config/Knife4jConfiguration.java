package com.blogmanagement.blogmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
//.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("# BlogManagement APIs")
                        .termsOfServiceUrl("http://gaj.cq.gov.cn/")
                        .contact("1092788902@qq.com")
                        .version("N.0")
                        .build())
//分组名称
                .groupName("恐龙")
                .select()
//这⾥指定Controller扫描包路径

                .apis(RequestHandlerSelectors.basePackage("com.blogmanagement.blogmanagement.controller")
                )
                .paths(PathSelectors.any())
                .build();
        return docket;

    }
}
