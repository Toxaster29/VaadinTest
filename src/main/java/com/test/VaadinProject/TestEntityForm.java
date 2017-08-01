package com.test.VaadinProject;

import com.test.VaadinProject.entity.TestEntity;
import com.test.VaadinProject.repository.TestRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;


/*
* Класс отвечает за отображение и функционирование модального окна
* */
@UIScope
@SpringComponent
public class TestEntityForm extends Window {

    /*
    * Текстовое поле на форме для ввода имени сущности*/
    private TextField nameField = new TextField("Enter the name");

    /*
    * Переменная для определения типа выполняемой операции(false-добавление, true-изменение)*/
    private boolean operationType;

    /*
    * Объявление экземпляра класса TestEntity*/
    private TestEntity entity;

    /*
    * Передача экземпляра класса TestRepository*/
    @Autowired
    private TestRepository repository;

    /*
    * Инициализация элементов на форме*/
    @PostConstruct
    private void init(){
        center();
        setClosable(false);
        HorizontalLayout buttons = new HorizontalLayout();
        VerticalLayout form = new VerticalLayout();
        Button closeButton = new Button("Cancel",
                clickEvent -> close());
        Button saveButton = new Button("Save",
                clickEvent -> {
            if (!Objects.equals(nameField.getValue(), "")) {
                saveEntity(nameField.getValue());
            }
        });
        buttons.addComponentsAndExpand(saveButton,closeButton);
        form.addComponents(nameField,buttons);
        setContent(form);
    }

    /*
    * Метод для изменения или сохранения сущностей*/
    private void saveEntity(String name){
        if(operationType){
            entity.setName(name);
            repository.save(entity);
        }
        else {
            entity.setName(name);
            entity.setCreationDate(new Date());
            repository.save(entity);
        }
        nameField.setValue("");
        close();
    }

    /*
    * Вызывается при добавлении сущности*/
    public void addEntity() {
        operationType=false;
        entity = new TestEntity();
        nameField.focus();
    }

    /*
    * Вызывается при редактировании сущности*/
    public void editEntity(TestEntity selectedEntity) {
        operationType=true;
        entity=selectedEntity;
        nameField.setValue(entity.getName());
        nameField.focus();
    }
}
