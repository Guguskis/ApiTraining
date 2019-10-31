package lt.liutikas.payment.service;

import lt.liutikas.payment.model.Payment;
import lt.liutikas.person.exception.PersonNotFoundException;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    Payment save(Payment payment) throws PersonNotFoundException;

    void delete(long id);

    List<Payment> findPersonPayments(long personId);
}
