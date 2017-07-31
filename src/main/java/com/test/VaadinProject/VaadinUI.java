package com.test.VaadinProject;

import com.test.VaadinProject.entity.TestEntity;
import com.test.VaadinProject.repository.TestRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

    @Autowired
    private TestRepository repository;

    private VerticalLayout layout;
    private Grid<TestEntity> grid = new Grid<>(TestEntity.class);
    private HorizontalLayout ButtonsLayout = new HorizontalLayout();
    private TestEntity selectedEntity = new TestEntity();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
        addForm();
        addActionButtons();
    }

    private void addActionButtons() {
        Button createButton = new Button("Add entity",clickEvent -> {
            TestEntityForm form = new TestEntityForm();
            UI.getCurrent().addWindow(form);
        });
        createButton.setSizeFull();

        Button deleteButton = new Button("Delete entity",clickEvent -> {
            deleteEntity(selectedEntity);
        });

        Button editButton = new Button("Edit entity",clickEvent -> {
            TestEntityForm form = new TestEntityForm(selectedEntity);
            UI.getCurrent().addWindow(form);
        });
        ButtonsLayout.addComponentsAndExpand(editButton,deleteButton);
        layout.addComponents(createButton,ButtonsLayout);
        ButtonsLayout.setEnabled(false);
    }

    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    private void addHeader() {
        Label header = new Label("Test Project Vaadin");
        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("80%");

        grid.setColumns("id","name", "creationDate");

        formLayout.addComponent(grid);
        formLayout.setSizeFull();
        grid.setSizeFull();
        formLayout.setExpandRatio(grid, 1);

        layout.addComponent(formLayout);

        updateList();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                ButtonsLayout.setEnabled(false);
            } else {
                ButtonsLayout.setEnabled(true);
                selectedEntity=event.getValue();
            }
        });
    }

    private void updateList() {
        grid.setItems(repository.findAll());
    }

    private void deleteEntity(TestEntity entity){
        repository.delete(entity);
        updateList();
    }
}
