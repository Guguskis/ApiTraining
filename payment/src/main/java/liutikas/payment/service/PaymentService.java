package liutikas.payment.service;

import liutikas.payment.model.Payment;
import liutikas.person.exception.PersonNotFoundException;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    Payment save(Payment payment) throws PersonNotFoundException;

    void delete(long id);

    List<Payment> findPersonPayments(long personId);
}
