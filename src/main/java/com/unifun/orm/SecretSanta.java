package com.unifun.orm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "secret_santa")
public class SecretSanta extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String password;

    @Column(name = "is_used", columnDefinition = "BIT(1) DEFAULT 0")
    public boolean isUsed;

    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    public boolean selected;

    public SecretSanta(String name){
        this.name = name;
    }
    public SecretSanta(){}

    public static SecretSanta getByName(String name) {
        return SecretSanta.find("name", name).firstResult();
    }
}
