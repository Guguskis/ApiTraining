package lt.liutikas.person.service;

import lt.liutikas.person.exception.PersonNotFoundException;
import lt.liutikas.person.model.Person;
import lt.liutikas.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DefaultPersonService.class)
@ExtendWith(SpringExtension.class)
public class PersonServiceMockTest {
    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Test
    void find_FindingExistingPerson_ReturnsPerson() throws PersonNotFoundException {
        Person expected = new Person(5, "Matas");
        when(personRepository.findByOfficialId(5L)).thenReturn(expected);

        Person result = personService.find(5);

        assertEquals(expected, result);
    }

    @Test
    void find_FindingNotExistingPerson_ThrowsException() {
        assertThrows(PersonNotFoundException.class, () -> {
            personService.find(5);
        });
    }
}
