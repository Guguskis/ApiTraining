package lt.liutikas.dropbox.route.validation;

import lt.liutikas.dropbox.WebConfig;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CamelSpringBootTest
@ContextConfiguration(classes = WebConfig.class)
class ValidationRouteTest {

    //Todo
//    @After
//    public void tearDown() throws Exception {
//        mockEndpoint.reset();
//    }

    @EndpointInject("mock:result")
    private MockEndpoint mock;

    @Produce("direct:validation")
    private ProducerTemplate template;

    @Test
    void route_CorrectData_ReturnsTrue() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "officialId", "languageId"));
        body.add(Arrays.asList("Michael", "1", "1"));
        body.add(Arrays.asList("Toby", "2", "1"));
        body.add(Arrays.asList("Jan", "3", "3"));

        mock.expectedHeaderReceived("valid", true);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_NoData_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_OnlyProperties_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "officialId", "languageId"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_NoProperties_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("Michael", "1", "1"));
        body.add(Arrays.asList("Jan", "3", "3"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_DuplicateProperty_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "name", "officialId", "languageId"));
        body.add(Arrays.asList("Michael", "Michael", "1", "1"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_MissingRequiredProperty_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "officialId"));
        body.add(Arrays.asList("Michael", "1"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_RowHasTooManyValues_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "officialId", "languageId"));
        body.add(Arrays.asList("Michael", "1", "1", "extra value"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_RowHasMissingValues_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "officialId", "languageId"));
        body.add(Arrays.asList("Michael", "1"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_ValueIsStringWhereLongExpected_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "officialId", "languageId"));
        body.add(Arrays.asList("Michael", "1", "string value"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

    @Test
    void route_ValueContainsNumbersWhereOnlyLettersExpected_ReturnsFalse() throws InterruptedException {
        List<List<String>> body = new ArrayList<>();
        body.add(Arrays.asList("name", "officialId", "languageId"));
        body.add(Arrays.asList("Michael3123", "1", "1"));

        mock.expectedHeaderReceived("valid", false);
        template.sendBody(body);

        mock.assertIsSatisfied();
    }

//    @Test
//    void route_CorrectData_ReturnsFalse() throws InterruptedException {
//        List<List<String>> body = new ArrayList<>();
//        body.add(Arrays.asList("name", "officialId", "languageId"));
//        body.add(Arrays.asList("Michael", "1", "1"));
//        body.add(Arrays.asList("Toby", "2", "1"));
//        body.add(Arrays.asList("Jan", "3", "3"));
//
//        result.expectedHeaderReceived("valid", false);
//        producer.sendBody(body);
//
//        result.assertIsSatisfied();
//    }

}