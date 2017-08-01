package com.test.VaadinProject;

import com.test.VaadinProject.entity.TestEntity;
import com.test.VaadinProject.repository.TestRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/*
* Класс отвечает за отображение и функционирование главной страницы приложения
*/
@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

    /*
    * Передача экземпляра класса TestRepository */
    @Autowired
    private TestRepository repository;

    /*
    * Передача экземпляра класса TestEntityForm */
    @Autowired
    private TestEntityForm form;

    private VerticalLayout layout;
    private Grid<TestEntity> grid = new Grid<>(TestEntity.class);
    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private TestEntity selectedEntity = new TestEntity();

    /*
    * Инициализация главной страницы приложения*/
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
        addForm();
        addActionButtons();
        form.addCloseListener(closeEvent -> {
            updateList();
            UI.getCurrent().removeWindow(form);
        });
    }

    /*
    * Добавление на форму функциональных кнопок*/
    private void addActionButtons() {
        Button createButton = new Button("Add entity",clickEvent -> {
            form.addEntity();
            UI.getCurrent().addWindow(form);
        });
        createButton.setSizeFull();

        Button deleteButton = new Button("Delete entity",clickEvent -> {
            deleteEntity(selectedEntity);
        });

        Button editButton = new Button("Edit entity",clickEvent -> {
            form.editEntity(selectedEntity);
            UI.getCurrent().addWindow(form);
        });

        buttonsLayout.addComponentsAndExpand(editButton,deleteButton);
        layout.addComponents(createButton, buttonsLayout);
        buttonsLayout.setEnabled(false);
    }

    /*
    * Добавление основного слоя на форме*/
    private void setupLayout() {
        layout = new VerticalLayout();

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    /*
    * Добавление заголовка*/
    private void addHeader() {
        Label header = new Label("Test Project Vaadin");

        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);
    }

    /*
    * Добавление основной части формы с элементом grid*/
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
                buttonsLayout.setEnabled(false);
            } else {
                buttonsLayout.setEnabled(true);
                selectedEntity=event.getValue();
            }
        });
    }

    /*
    * Обновление списка у элемента grid*/
    private void updateList() {
        grid.setItems(repository.findAll());
    }

    /*
    * Удаление сущности*/
    private void deleteEntity(TestEntity entity){
        repository.delete(entity);
        updateList();
    }
}
