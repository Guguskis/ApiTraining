package lt.liutikas.payment.service;

import ch.qos.logback.classic.Logger;
import lt.liutikas.model.CreatePaymentDTO;
import lt.liutikas.model.Payment;
import lt.liutikas.model.Person;
import lt.liutikas.payment.repository.PaymentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPaymentService implements PaymentService {

    private static Logger logger;

    static {
        logger = (Logger) LoggerFactory.getLogger(DefaultPaymentService.class.getSimpleName());
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
        logger.info("DefaultPaymentService created");
        return repository.findAll();
    }

    @Override
    public void save(CreatePaymentDTO paymentDTO) {
        try {
            String url = "http://localhost:8082/api/persons/" + paymentDTO.getPersonOfficialId();
            ResponseEntity<Person> response = restTemplate.getForEntity(url, Person.class);

            Payment payment = new Payment(response.getBody().getId(), paymentDTO.getAmount());
            repository.save(payment);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Todo create a new person if it does not exist
                logger.warn("Person not found");
            } else {
                logger.error("Unhandled response status code from PersonAPI", e);
            }
        } catch (Exception e) {
            logger.error("Unhandled exception", e);
        }
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
