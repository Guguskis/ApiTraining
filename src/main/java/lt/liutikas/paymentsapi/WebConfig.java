package lt.liutikas.paymentsapi;

import lt.liutikas.paymentsapi.service.PaymentsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {
    @Bean("lt.liutikas.paymentsapi.service.IPaymentsService")
    public PaymentsService getService(){
        return new PaymentsService();
    }
}