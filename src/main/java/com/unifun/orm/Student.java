package com.unifun.orm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity(name = "student")
public class Student extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstName;
    public String lastName;
    public String sex;
    @ManyToOne(fetch = FetchType.LAZY)
    public Grupa grupId;

    public Student(String firstName, String lastName, String sex, Grupa grup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.grupId = grup;
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
                ", Grup=" + grupId +
                '}';
    }
}
