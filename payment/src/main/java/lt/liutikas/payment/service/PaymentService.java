package lt.liutikas.payment.service;

import lt.liutikas.model.CreatePaymentDTO;
import lt.liutikas.model.Payment;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    void create(CreatePaymentDTO payment) throws ResourceAccessException;

    void delete(long id);

    List<Payment> findPersonPayments(long personId);
}
