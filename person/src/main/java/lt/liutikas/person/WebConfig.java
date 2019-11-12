package lt.liutikas.person;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
// MM: don't use wildcard
@EntityScan("lt.liutikas.*")
public class WebConfig {

}