package group24.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2

public class ServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfoBuilder()
				.title("Alien Shooter APIs")
				.description("")
				.version("1.0")
				.termsOfServiceUrl("Free to use")
				.contact(new springfox.documentation.service.Contact("İdil Zeynep Alemdar", ".","idil.alemdar@metu.edu.tr"))
				.contact(new springfox.documentation.service.Contact("Sevim Seda Çokoğlu", ".","???@metu.edu.tr"))
				.license("GNU GPLv3")
				.licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html")
				.build();
	}


}
