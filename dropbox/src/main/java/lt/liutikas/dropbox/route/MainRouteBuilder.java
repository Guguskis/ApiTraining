package lt.liutikas.dropbox.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainRouteBuilder extends RouteBuilder {
    @Value("${person.api.url}")
    private String PERSONS_API_URL;

    @Value("${root.folder}")
    private String ROOT_FOLDER;

    // @formatter:off
    @Override
    public void configure() {
        from(ROOT_FOLDER + "/in?move=../success&moveFailed=../failure")
                .routeId("mainRoute")
                    .unmarshal().csv()
                    .to("direct:parse")
                    .to("direct:import")
                .end();

        from("direct:import")
                .routeId("import")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal().json()
                .to(PERSONS_API_URL + "batch");
    }
    // @formatter:on

}
