package yaroslav.sharipov.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Value(value = "${application.server.username}")
    private String username;

    @Value(value = "${application.server.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
//                .setConnectTimeout(Duration.ofSeconds(2))
//                .setReadTimeout(Duration.ofSeconds(3))
                .basicAuthentication(username,password)
                .build();
    }
}