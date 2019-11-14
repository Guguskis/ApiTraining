package lt.liutikas.payment.service;

import lt.liutikas.payment.repository.PaymentRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = DefaultPaymentService.class)
@ExtendWith(SpringExtension.class)
class DefaultPaymentServiceTest {

    @MockBean
    private PaymentRepository paymentServiceRepository;

    @Autowired
    private PaymentService paymentServiceService;

}