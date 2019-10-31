package lt.liutikas.personsapi.repository;

import lt.liutikas.paymentsapi.model.Payment;
import lt.liutikas.personsapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Long> {
    Person findByOfficialId(long personId);

}
