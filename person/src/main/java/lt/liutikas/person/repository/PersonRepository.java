package lt.liutikas.person.repository;

import lt.liutikas.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByOfficialId(long personId);

    // MM: I suggest to read this chapter https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    // Here you will learn how to create queries in method name.
}
