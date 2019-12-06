package lt.liutikas.dropbox;

import lt.liutikas.dropbox.route.HandlingRoute;
import lt.liutikas.dropbox.route.ImportingRoute;
import lt.liutikas.dropbox.route.MainRoute;
import lt.liutikas.dropbox.route.ParsingRoute;
import lt.liutikas.dropbox.route.validation.ValidationRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DropboxApplication extends RouteBuilder {
    private final CamelContext context;

    public DropboxApplication(CamelContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(DropboxApplication.class, args);
    }

    @Override
    public void configure() throws Exception {
        context.addRoutes(new ValidationRoute());
        context.addRoutes(new HandlingRoute());
        context.addRoutes(new ParsingRoute());
        context.addRoutes(new ImportingRoute());
        context.addRoutes(new MainRoute());
    }


}
