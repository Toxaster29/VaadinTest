package com.test.VaadinProject;

import com.test.VaadinProject.entity.TestEntity;
import com.test.VaadinProject.repository.TestRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

@UIScope
@SpringComponent
public class TestEntityForm extends Window {

    private TextField nameField = new TextField("Enter the name");

    @Autowired
    private TestRepository repository;

    public TestEntityForm(){ }

    public void SaveEntity()
    {
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
        nameField.focus();
        form.addComponents(nameField,buttons);
        setContent(form);
    }

    public void UpdateEntity(TestEntity entity){
        center();
        setClosable(false);
        HorizontalLayout buttons = new HorizontalLayout();
        VerticalLayout form = new VerticalLayout();
        Button closeButton = new Button("Cancel",clickEvent -> close());
        nameField.setValue(entity.getName());
        Button saveButton = new Button("Save",clickEvent -> {
            if (!Objects.equals(nameField.getValue(), "")) {
                updateEntity(entity,nameField.getValue());
            }
        });
        buttons.addComponentsAndExpand(saveButton,closeButton);
        nameField.focus();
        form.addComponents(nameField,buttons);
        setContent(form);
    }

    private void saveEntity(String name){
        TestEntity entity = new TestEntity();
        entity.setName(name);
        entity.setCreationDate(new Date());
        repository.save(entity);
        nameField.setValue("");
        close();
    }

    private void updateEntity(TestEntity entity,String name){
        entity.setName(name);
        repository.save(entity);
        nameField.setValue("");
        close();
    }
}
