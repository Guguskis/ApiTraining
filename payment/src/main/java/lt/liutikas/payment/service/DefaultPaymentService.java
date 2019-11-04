package lt.liutikas.payment.service;

import lt.liutikas.payment.model.Payment;
import lt.liutikas.payment.repository.PaymentRepository;
import lt.liutikas.person.exception.PersonNotFoundException;
import lt.liutikas.person.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPaymentService implements PaymentService {

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
    public void save(Payment payment) throws PersonNotFoundException {
        try {
            URI getPersonUri = new URI("http://localhost:8082/api/persons/" + payment.getPersonId());
            ResponseEntity<Person> response = restTemplate.getForEntity(getPersonUri, Person.class);

            repository.save(payment);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PersonNotFoundException();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void delete(long id) {
        Payment payment = repository.findById(id).get();

        if (payment != null) {
            repository.delete(payment);
        }
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
