package lt.liutikas.paymentsapi.repository;

import lt.liutikas.paymentsapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentsRepository extends JpaRepository<Payment, Long> {
}
