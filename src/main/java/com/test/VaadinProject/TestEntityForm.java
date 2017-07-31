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

@UIScope
@SpringComponent
public class TestEntityForm extends Window {

    private TextField nameField = new TextField("Enter the name");
    private boolean operationType;
    private TestEntity entity;

    @Autowired
    private TestRepository repository;

    @PostConstruct
    private void init(){
        center();
        setClosable(false);
        HorizontalLayout buttons = new HorizontalLayout();
        VerticalLayout form = new VerticalLayout();
        Button closeButton = new Button("Cancel",clickEvent -> close());
        Button saveButton = new Button("Save",clickEvent -> {
            if (!Objects.equals(nameField.getValue(), "")) {
                saveEntity(nameField.getValue());
            }
        });
        buttons.addComponentsAndExpand(saveButton,closeButton);
        form.addComponents(nameField,buttons);
        setContent(form);
    }

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

    public void addEntity() {
        operationType=false;
        entity = new TestEntity();
        nameField.focus();
    }

    public void editEntity(TestEntity selectedEntity) {
        operationType=true;
        entity=selectedEntity;
        nameField.setValue(entity.getName());
        nameField.focus();
    }
}
