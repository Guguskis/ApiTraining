package lt.liutikas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long id;
    @Column(name = "OFFICIALID")
    private long officialId;
    @Column(name = "NAME")
    private String name;

    public Person() {
    }

    public Person(long officialId, String name) {
        this.officialId = officialId;
        this.name = name;
    }

    public Person(long officialId) {
        this.officialId = officialId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOfficialId() {
        return officialId;
    }

    public void setOfficialId(long officialId) {
        this.officialId = officialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object p) {
        if (p instanceof Person) {
            return ((Person) p).getOfficialId() == getOfficialId();
        }

        return false;
    }
}
