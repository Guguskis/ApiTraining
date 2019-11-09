package lt.liutikas.person.controller;

import lt.liutikas.exception.PersonAlreadyExistsException;
import lt.liutikas.exception.PersonNotFoundException;
import lt.liutikas.model.Person;
import lt.liutikas.person.service.DefaultPersonService;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private DefaultPersonService service;

    @Autowired
    public PersonController(DefaultPersonService service) {
        this.service = service;
        create(new Person(5, "Matas"));
        create(new Person(1, "Jokubas"));
        create(new Person(2, "Markas"));
        create(new Person(3, "Lukas"));
    }

    @GetMapping
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping("/{officialId}")
    public Person find(@PathVariable("officialId") long officialId) {
        try {
            return service.find(officialId);
        } catch (PersonNotFoundException e) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND), "Provide correct Person official ID", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Person person) {
        try {
            service.create(person);
        } catch (PersonAlreadyExistsException e) {
            throw new ResponseStatusException((HttpStatus.BAD_REQUEST), "Provide Person with unused official ID", e);
        }
    }

    @DeleteMapping("/{officialId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("officialId") long officialId) {
        service.delete(officialId);
    }

}
