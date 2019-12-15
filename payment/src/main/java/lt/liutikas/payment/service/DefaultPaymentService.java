package lt.liutikas.payment.service;

import ch.qos.logback.classic.Logger;
import lt.liutikas.dto.CreatePaymentDto;
import lt.liutikas.model.Payment;
import lt.liutikas.model.Person;
import lt.liutikas.payment.repository.PaymentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPaymentService implements PaymentService {

    // MM: hardcoded URL. Should be in properties file
    private static final String PERSON_API_URL = "http://localhost:8082/api/persons/";
    private static final Logger logger = (Logger) LoggerFactory.getLogger(DefaultPaymentService.class);

    // MM: should be marked as final
    private PaymentRepository repository;
    private RestTemplate restTemplate;

    @Autowired
    public DefaultPaymentService(PaymentRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Payment> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(CreatePaymentDto paymentDto) {
        long personId = 0;
        try {
            personId = getPersonId(paymentDto.getPersonOfficialId());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                postPerson(paymentDto);
                personId = getPersonId(paymentDto.getPersonOfficialId());
            } else {
                logger.error("Unhandled response status code from Person service", e);
            }
        }
        // MM: if person creation fails then will be created payment with persinId=0, is it ok?
        createPayment(paymentDto, personId);
    }

    private long getPersonId(long officialId) {
        String getPersonUrl = PERSON_API_URL + officialId;
        return restTemplate
                .getForEntity(getPersonUrl, Person.class)
                .getBody()
                .getId();
    }

    private void postPerson(CreatePaymentDto paymentDto) {
        HttpHeaders headers = setupJSONHeaders();
        HttpEntity<Person> request = setupPersonRequest(paymentDto, headers);
        restTemplate.postForLocation(PERSON_API_URL, request);
    }

    // MM: createPersonRequest method name would be better
    private HttpEntity<Person> setupPersonRequest(CreatePaymentDto paymentDto, HttpHeaders headers) {
        Person person = new Person(paymentDto.getPersonOfficialId());
        return new HttpEntity<>(person, headers);
    }

    private HttpHeaders setupJSONHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private void createPayment(CreatePaymentDto paymentDto, long personId) {
        Payment payment = new Payment(personId, paymentDto.getAmount());
        repository.save(payment);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Payment> findPersonPayments(long personId) {
        return repository
                .findAll()
                .stream()
                .filter(payment -> payment.getPersonId() == personId)
                .collect(Collectors.toList());
    }

}
