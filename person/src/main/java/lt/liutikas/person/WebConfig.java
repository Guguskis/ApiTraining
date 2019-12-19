package lt.liutikas.person;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EntityScan("lt.liutikas.model")
public class WebConfig {
    @Bean
    public ProducerTemplate producerTemplate() {
        return new DefaultProducerTemplate(new DefaultCamelContext());
    }

}