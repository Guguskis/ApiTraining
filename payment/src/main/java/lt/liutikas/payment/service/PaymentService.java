package lt.liutikas.payment.service;

import lt.liutikas.model.Payment;
import lt.liutikas.exception.PersonNotFoundException;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    void save(Payment payment) throws PersonNotFoundException;

    void delete(long id);

    List<Payment> findPersonPayments(long personId);
}
