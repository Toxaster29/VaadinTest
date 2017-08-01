package com.test.VaadinProject.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

/*
* Класс для определения сущности, которая используется в приложении
* */
@Entity
@Table(name="test")
public class TestEntity {

    /*
    * Идентификатор объекта*/
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment",strategy = "increment")
    private Integer id;

    /*
    * Наименование сущности*/
    @Column(name="Name",nullable = false,length = 50)
    private String name;

    /*
    * Дата создания определенной сущности*/
    @Column(name="CreationDate",nullable = false)
    private Date creationDate;

    public TestEntity(){}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
