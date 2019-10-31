package lt.liutikas.paymentsapi.service;

import lt.liutikas.paymentsapi.model.Payment;
import lt.liutikas.personsapi.exception.PersonNotFoundException;

import java.util.List;

public interface PaymentsService {

    List<Payment> findAll();

    Payment save(Payment payment) throws PersonNotFoundException;

    void delete(long id);

    List<Payment> findPersonPayments(long personId);
}
