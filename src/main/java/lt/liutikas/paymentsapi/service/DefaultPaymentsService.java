package lt.liutikas.paymentsapi.service;

import lt.liutikas.paymentsapi.model.Payment;
import lt.liutikas.paymentsapi.repository.PaymentsRepository;
import lt.liutikas.personsapi.controller.PersonsController;
import lt.liutikas.personsapi.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPaymentsService implements PaymentsService {

    private PaymentsRepository repository;
    private PersonsController personsController;

    @Autowired
    public DefaultPaymentsService(PaymentsRepository repository, PersonsController personsController) {
        this.repository = repository;
        this.personsController = personsController;

    }

    @Override
    public List<Payment> findAll() {
        var payments = (List<Payment>) repository.findAll();
        return payments;
    }

    @Override
    public Payment save(Payment payment) throws PersonNotFoundException {
        personsController.find(payment.getPersonId());
        return repository.save(payment);
//        try{
//        }catch(Exception e){
//            //if not found throw new error that person does not exist
//            throw new PersonN
//        }
    }

    @Override
    public void delete(long id) {
        var payment = repository.findById(id).get();

        if (payment != null) {
            repository.delete(payment);
        }
    }

    @Override
    public List<Payment> findPersonPayments(long personId) {
        var personPayments = repository
                .findAll()
                .stream()
                .filter(payment -> payment.getPersonId() == personId)
                .collect(Collectors.toList());
        return personPayments;
    }

}
