package lt.liutikas.paymentsapi.repository;

import lt.liutikas.paymentsapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, Long> {

}
