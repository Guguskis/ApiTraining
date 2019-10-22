package lt.liutikas.paymentsapi.controller;

import lt.liutikas.paymentsapi.service.IPaymentsService;
import lt.liutikas.paymentsapi.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private IPaymentsService service;

    @GetMapping
    public List<Payment> findAll() {
        var payments = service.findAll();
        return payments;
    }

    @GetMapping("/{id}")
    public Payment find(@PathVariable long id) {
        var payment = service.find(id);
        return payment;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@RequestBody Payment payment) {
        var created = service.save(payment);
        return created;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }
}
