package com.sale.hot.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@OpenAPIDefinition(
        info = @Info(title = "HotSale API 명세서",
                description = """
                            HotSale API 명세서
                            Y/N 또는 모든 유형에 대한 값은 반드시 대문자여야 합니다
                            또한 전화번호, 사업자 등록번호 등 입력시 -(하이픈) 없이 입력합니다.
                            예 ) 01012345678(o) 010-1234-5678(x)
                        """
                ,
                version = "v1"))
@Configuration
public class SwaggerConfig {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public SwaggerConfig(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    private static final String BEARER_TOKEN_PREFIX = "Bearer";
    private static final String CB_SHOP_ID = "CB-SHOP-ID";

    @Bean
    public OpenAPI openAPI() {
        String authorization = HttpHeaders.AUTHORIZATION;
       // SecurityRequirement securityRequirement = new SecurityRequirement().addList(authorization, CB_SHOP_ID);

        Components components = new Components()
                .addSecuritySchemes(authorization,
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .name(authorization)
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                                .scheme(BEARER_TOKEN_PREFIX)
                                .bearerFormat("JWT")
                                .description("인증 토큰")
                );

        OpenAPI openAPI = new OpenAPI()
                //.addSecurityItem(securityRequirement)
                .components(components);


        return openAPI;
    }


}
