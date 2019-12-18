package lt.liutikas.payment.service;

import lt.liutikas.dto.CreatePaymentDto;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    void create(CreatePaymentDto payment) throws PersonNotFoundException;

    void delete(long id);

    List<Payment> findPersonPayments(long personId);
}
