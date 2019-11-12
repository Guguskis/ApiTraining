package lt.liutikas.payment.service;

import ch.qos.logback.classic.Logger;
import lt.liutikas.model.CreatePaymentDTO;
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
// MM: unused import
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPaymentService implements PaymentService {

    private static final String PERSON_API_URL = "http://localhost:8082/api/persons/";
    private static Logger logger;

    // MM: don't assign logger in separated part. Just use:
    // private static final Logger logger = (Logger) LoggerFactory.getLogger(DefaultPaymentService.class);
    // also static final
    static {
        logger = (Logger) LoggerFactory.getLogger(DefaultPaymentService.class);
    }

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
    public void save(CreatePaymentDTO paymentDTO) {
        long personId = 0;
        try {
            personId = getPersonId(paymentDTO.getPersonOfficialId());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                postPerson(paymentDTO);
                personId = getPersonId(paymentDTO.getPersonOfficialId());
            } else {
                logger.error("Unhandled response status code from Person service", e);
            }
        }
        createPayment(paymentDTO, personId);
    }

    private long getPersonId(long officialId){
        String getPersonUrl = PERSON_API_URL + officialId;
        return restTemplate
                .getForEntity(getPersonUrl, Person.class)
                .getBody()
                .getId();
    }

    private void postPerson(CreatePaymentDTO paymentDTO){
        HttpHeaders headers = setupJSONHeaders();
        HttpEntity<Person> request = setupPersonRequest(paymentDTO, headers);
        restTemplate.postForLocation(PERSON_API_URL, request);
    }

    private HttpEntity<Person> setupPersonRequest(CreatePaymentDTO paymentDTO, HttpHeaders headers) {
        Person person = new Person(paymentDTO.getPersonOfficialId());
        return new HttpEntity<>(person, headers);
    }

    private HttpHeaders setupJSONHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private void createPayment(CreatePaymentDTO paymentDTO, long personId) {
        Payment payment = new Payment(personId, paymentDTO.getAmount());
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
