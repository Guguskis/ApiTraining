package lt.liutikas.paymentsapi.service;

import lt.liutikas.paymentsapi.repository.IPaymentsRepository;
import lt.liutikas.paymentsapi.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PaymentsService implements IPaymentsService {

    @Autowired
    private IPaymentsRepository repository;


    @Override
    public List<Payment> findAll() {
        var payments = (List<Payment>) repository.findAll();
        return payments;
    }

    @Override
    public Payment find(long id){
        var payment = repository.findById(id).get();
        return payment;
    }

    @Override
    public Payment save(Payment payment) {
        var created = repository.save(payment);
        return created;
    }

    @Override
    public void delete(long id) {
        var payment = repository.findById(id).get();

        if (payment != null) {
            repository.delete(payment);
        }
    }

}
