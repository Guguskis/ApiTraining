package lt.liutikas.paymentsapi.service;

import lt.liutikas.paymentsapi.model.Payment;

import java.util.List;

public interface IPaymentsService {

    List<Payment> findAll();

    Payment find(long id);
    Payment save(Payment payment);

    void delete(long id);
}
