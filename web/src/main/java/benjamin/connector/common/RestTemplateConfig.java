package benjamin.connector.common;

import benjamin.connector.jenkins.JenkinsConnector;
import benjamin.connector.sonar.SonarConnectorImpl;
import benjamin.connector.sonar.model.SonarConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Configuration
public class RestTemplateConfig {

    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;

    @Autowired(required = false)
    private List<ClientHttpRequestInterceptor> interceptors;

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate result = new RestTemplate(messageConverters);
        if (interceptors != null) {
            result.setInterceptors(interceptors);
        }
        return result;
    }

    @Bean
    @Scope("prototype")
    public JenkinsConnector jenkinsConnector(String url, String username, String apiToken) {
        return   JenkinsConnector.build(restHelper, restTemplate, url, username, apiToken);
    }


    @Bean
    @Scope("prototype")
    public SonarConnector sonarConnector(String url, String username, String apiToken) {
        return SonarConnectorImpl.build(restHelper, restTemplate, url, username, apiToken);
    }

}
