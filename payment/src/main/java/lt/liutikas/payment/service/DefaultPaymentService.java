package lt.liutikas.payment.service;

import ch.qos.logback.classic.Logger;
import lt.liutikas.dto.CreatePaymentDto;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.Payment;
import lt.liutikas.model.Person;
import lt.liutikas.payment.repository.PaymentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPaymentService implements PaymentService {

    private final Logger logger = (Logger) LoggerFactory.getLogger(DefaultPaymentService.class);

    @Value("${person.api.url}")
    private String PERSON_API_URL;

    private final PaymentRepository repository;
    private final RestTemplate restTemplate;

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
    public void create(CreatePaymentDto paymentDto) throws PersonNotFoundException {
        try {
            long personId = getPersonId(paymentDto.getPersonOfficialId());
            createPayment(paymentDto, personId);
        } catch (HttpStatusCodeException e) {
            throw new PersonNotFoundException("Person was not found.");
        }
    }


    private long getPersonId(long officialId) {
        String getPersonUrl = PERSON_API_URL + officialId;

        return restTemplate
                .getForEntity(getPersonUrl, Person.class)
                .getBody()
                .getId();
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
