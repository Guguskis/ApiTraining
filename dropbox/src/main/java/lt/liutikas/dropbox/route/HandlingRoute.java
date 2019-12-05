package lt.liutikas.dropbox.route;

import org.apache.camel.builder.RouteBuilder;

public class HandlingRoute extends RouteBuilder {
    private final String ROOT_FOLDER = "file://dropbox/files";

    @Override
    public void configure() throws Exception {
        from("direct:handling")
                .routeId("resultFolderHandling")
                .choice()
                .when(header("valid").isEqualTo(true))
                .to("direct:importing")
                .marshal().csv()
                .to(getSuccessFolder())
                .to(getSuccessFolder())
                .otherwise()
                .to(getFailureFolder());
    }

    private String getFailureFolder() {
        return ROOT_FOLDER + "/failure";
    }

    private String getSuccessFolder() {
        return ROOT_FOLDER + "/success";
    }
}
