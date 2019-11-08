package lt.liutikas.payment.service;

import lt.liutikas.model.CreatePaymentDTO;
import lt.liutikas.model.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    void save(CreatePaymentDTO payment);

    void delete(long id);

    List<Payment> findPersonPayments(long personId);
}
