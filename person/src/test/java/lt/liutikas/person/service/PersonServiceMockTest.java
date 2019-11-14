package lt.liutikas.person.service;

import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.Person;
import lt.liutikas.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DefaultPersonService.class)
@ExtendWith(SpringExtension.class)
public class PersonServiceMockTest {
    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Test
    void find_ExistingPerson_ReturnsPerson() throws PersonNotFoundException {
        long officialId = 5;
        Person expected = new Person(officialId, "John");
        when(personRepository.findByOfficialId(officialId)).thenReturn(expected);

        Person result = personService.find(officialId);

        assert (result.equals(expected));
    }

    @Test
    void find_NonExistingPerson_ThrowsException() {
        assertThrows(PersonNotFoundException.class, () -> {
            personService.find(5);
        });
    }

    @Test
    void create_WithOfficialIdTaken_ThrowsException() {
        Person person = new Person(5, "John");
        when(personRepository.findByOfficialId(person.getOfficialId()))
                .thenReturn(person);

        assertThrows(PersonAlreadyExistsException.class, () -> {
            personService.create(person);
        });
    }

    @Test
    void create_ValidPerson_DoesNotThrowException() {
        Person person = new Person(5, "John");
        when(personRepository.findByOfficialId(anyLong()))
                .thenReturn(null);

        assertDoesNotThrow(() -> personService.create(person));
    }
}
