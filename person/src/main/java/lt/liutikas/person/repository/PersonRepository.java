package lt.liutikas.person.repository;

import lt.liutikas.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByOfficialId(long personOfficialId);
}
