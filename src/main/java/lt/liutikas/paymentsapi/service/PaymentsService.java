package lt.liutikas.paymentsapi.service;

import lt.liutikas.paymentsapi.model.Payment;

import java.util.List;

public interface PaymentsService {

    List<Payment> findAll();

    Payment find(long id);

    Payment save(Payment payment);

    void delete(long id);
}
