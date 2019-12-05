package lt.liutikas.dropbox;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EntityScan("lt.liutikas.model")
@ComponentScan("lt.liutikas.dropbox")
public class WebConfig {

}