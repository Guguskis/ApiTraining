package lt.liutikas.dropbox.route;

import org.apache.camel.builder.RouteBuilder;

public class MainRoute extends RouteBuilder {
    private final String ROOT_FOLDER = "file://dropbox/files";

    @Override
    public void configure() {
//        from(getInFolder())
//                .routeId("mainRoute")
//                .unmarshal().csv()
//                .to("direct:validation")
//                .to("direct:handling");

//        onException(MalformedInputException.class).log("Exception handled");

        from(getInFolder())
                .routeId("simplerMainRoute")
                .unmarshal().csv()
                .to("direct:parse")
                .to("direct:import");

    }

    private String getInFolder() {
        return ROOT_FOLDER + "/in?delete=true";
    }

}
