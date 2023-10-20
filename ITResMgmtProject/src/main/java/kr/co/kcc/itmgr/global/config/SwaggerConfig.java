package kr.co.kcc.itmgr.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    Info info = new Info()
        .title("자원 관리 시스템")
        .version("1.0")
        .description("자원 관리 시스템 API");

    return new OpenAPI()
        .components(new Components())
        .info(info);
  }
}