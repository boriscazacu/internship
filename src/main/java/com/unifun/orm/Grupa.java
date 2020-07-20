package com.unifun.orm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity(name = "grup")
public class Grupa extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
   /// @Column(name = "grupId")
    public Long grupId;
    public String grupName;
    public String grupMentor;

    public String getGrupName() {
        return grupName;
    }

    @Override
    public String toString() {
        return "Grupa{" +
                "grupId=" + grupId +
                ", grupName='" + grupName + '\'' +
                ", grupMentor='" + grupMentor + '\'' +
                '}';
    }
}
