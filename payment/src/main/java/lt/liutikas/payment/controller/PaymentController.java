package lt.liutikas.payment.controller;

import ch.qos.logback.classic.Logger;
import lt.liutikas.model.CreatePaymentDTO;
import lt.liutikas.model.Payment;
import lt.liutikas.payment.service.PaymentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static Logger logger;

    static {
        logger = (Logger) LoggerFactory.getLogger(PaymentController.class);
    }

    private PaymentService service;


    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Payment> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreatePaymentDTO payment) {
        try {
            service.create(payment);
        } catch (ResourceAccessException e) {
            logger.error("Request to Person service took too long.");
        }
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
