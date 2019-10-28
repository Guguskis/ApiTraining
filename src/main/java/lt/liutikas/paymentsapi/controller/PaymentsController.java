package lt.liutikas.paymentsapi.controller;

import lt.liutikas.paymentsapi.model.Payment;
import lt.liutikas.paymentsapi.service.PaymentsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private PaymentsService service;

    public PaymentsController(PaymentsService service) {
        this.service = service;
        service.save(new Payment(555, 1));
        service.save(new Payment(33, 1));
        service.save(new Payment(10, 2));
        service.save(new Payment(332, 3));
    }

    @GetMapping
    public List<Payment> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@RequestBody Payment payment) {
        return service.save(payment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @GetMapping("/{personId}")
    public List<Payment> findPersonPayments(@PathVariable("personId") long personId) {
        return service.findPersonPayments(personId);
    }
}
