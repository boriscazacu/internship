package com.unifun.orm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity(name = "student")
public class Student extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    public String firstName;
    public String lastName;
    public String sex;
    @ManyToOne(fetch = FetchType.LAZY)
    private Grupa grupId;

    public Student(String firstName, String lastName, String sex, Long grupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.grupId.grupId = grupId;
    }
    public Student(){}


    @Transactional
    public static String deleteByID(Long id){
        return  Student.deleteById(id) ? "deleted" : "not delete";
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", grupId=" + grupId +
                '}';
    }
}
