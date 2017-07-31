package com.test.VaadinProject;

import com.test.VaadinProject.entity.TestEntity;
import com.test.VaadinProject.repository.TestRepository;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

public class TestEntityForm extends Window {

    private Button saveButton = new Button("Save");
    private TextField nameField = new TextField("Enter the name");

    @Autowired
    private TestRepository repository;

    public TestEntityForm(){
        initWindow();
        saveButton.addClickListener(clickEvent -> {
            if (!Objects.equals(nameField.getValue(), "")) {
                saveEntity(nameField.getValue());
            }
        });
    }
    public TestEntityForm(TestEntity entity){
        initWindow();
        nameField.setValue(entity.getName());
        saveButton.addClickListener(clickEvent -> {
            if (nameField.getValue()!="") {
                updateEntity(entity,nameField.getValue());
            }
        });
    }

    private void initWindow(){
        center();
        setClosable(false);
        HorizontalLayout buttons = new HorizontalLayout();
        VerticalLayout form = new VerticalLayout();
        Button closeButton = new Button("Cancel",clickEvent -> close());
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
        close();
    }

    private void updateEntity(TestEntity entity,String name){
        entity.setName(name);
        repository.save(entity);
        close();
    }
}
