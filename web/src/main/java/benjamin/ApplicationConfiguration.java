package benjamin;


import benjamin.connector.jenkins.JenkinsJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("benjamin")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;

    @Bean
    public MappingJackson2HttpMessageConverter messageConverters() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(messageConverters);
        super.configureMessageConverters(converters);
    }

    @Bean
    public HttpMessageConverter<Object> xmlSourceHttpMessageConverter() {
        SourceHttpMessageConverter xmlConverter =
                new SourceHttpMessageConverter();
        List<MediaType> types = new ArrayList<>();
        types.add(new MediaType("application", "xml"));
        xmlConverter.setSupportedMediaTypes(types);


        return xmlConverter;
    }

    @Bean
    public HttpMessageConverter<Object> jaxbSourceHttpMessageConverter() {

        MarshallingHttpMessageConverter xmlConverter =
                new MarshallingHttpMessageConverter();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        xmlConverter.setMarshaller(marshaller);
        xmlConverter.setUnmarshaller(marshaller);
        marshaller.setClassesToBeBound(JenkinsJobConfig.class);
        return xmlConverter;
    }

}