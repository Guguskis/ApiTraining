package lt.liutikas.dropbox.route;

import org.apache.camel.builder.RouteBuilder;

public class MainRoute extends RouteBuilder {
    private final String ROOT_FOLDER = "file://dropbox/files";

    @Override
    public void configure() {
        from(getInFolder())
                .routeId("mainRoute")
                .unmarshal().csv()
                .to("direct:validation")
                .to("direct:handling");
    }

    private String getInFolder() {
        return ROOT_FOLDER + "/in?delete=true";
    }

}
