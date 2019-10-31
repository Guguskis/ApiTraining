package lt.liutikas.payment.service;

import lt.liutikas.payment.model.Payment;
import lt.liutikas.payment.repository.PaymentRepository;
import lt.liutikas.person.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public Payment save(Payment payment) throws PersonNotFoundException {
        //Todo add restTemplate call to person api to check if person exists
        return repository.save(payment);

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
