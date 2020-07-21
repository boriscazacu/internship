package com.unifun.orm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity(name = "grup")
public class Grupa extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   /// @Column(name = "grupId")
    public Long grupId;
    public String grupName;
    public String grupMentor;

    public Grupa() {
    }

    public Grupa( String grupName, String grupMentor) {
        this.grupName = grupName;
        this.grupMentor = grupMentor;
    }
    @Transactional
    public static String deleteByID(Long id){
        return  Grupa.deleteById(id) ? "deleted" : "not delete";
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
