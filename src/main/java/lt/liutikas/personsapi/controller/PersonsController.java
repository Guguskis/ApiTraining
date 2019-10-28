package lt.liutikas.personsapi.controller;

import lt.liutikas.personsapi.exception.PersonNotFoundException;
import lt.liutikas.personsapi.model.Person;
import lt.liutikas.personsapi.service.DefaultPersonsService;
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
public class PersonsController {

    private DefaultPersonsService service;

    public PersonsController(DefaultPersonsService service) {
        this.service = service;
        service.save(new Person(5, "Matas"));
        service.save(new Person(1, "Jokubas"));
        service.save(new Person(2, "Markas"));
        service.save(new Person(3, "Lukas"));
    }

    @GetMapping
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Person find(@PathVariable("id") long id) {
        try {
            return service.find(id);
        } catch (PersonNotFoundException e) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND), "Provide correct Person ID", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        return service.save(person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

}
