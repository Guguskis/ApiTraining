package lt.liutikas.person.service;

import lt.liutikas.dto.CreatePersonDto;
import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.Person;
import lt.liutikas.person.WebConfig;
import lt.liutikas.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = WebConfig.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonServiceTest {
    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonService service;

    @Test
    void find_ExistingPerson_ReturnsPerson() throws PersonNotFoundException {
        long officialId = 5;
        Person expected = new Person(officialId, "John");

        repository.save(expected);
        Person actual = service.find(officialId);

        assertTrue(actual.equals(expected));
    }

    @Test
    void find_NonExistingPerson_ThrowsException() {
        assertThrows(PersonNotFoundException.class, () -> {
            service.find(5);
        });
    }

    @Test
    void create_OfficialIdTaken_ThrowsException() throws PersonAlreadyExistsException {
        CreatePersonDto officialIdHolder = new CreatePersonDto(5, "John");
        CreatePersonDto usesTakenOfficialId = new CreatePersonDto(5, "Susie");

        service.create(officialIdHolder);

        assertThrows(PersonAlreadyExistsException.class, () -> {
            service.create(usesTakenOfficialId);
        });
    }

    @Test
    void create_ValidPerson_DoesNotThrowException() {
        Person validPerson = new Person(5, "John");

        assertDoesNotThrow(() -> {
            service.create(validPerson);
        });
    }

}
