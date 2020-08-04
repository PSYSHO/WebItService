package webservice.webapp.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import webservice.webapp.component.FirstTaskEditor;
import webservice.webapp.component.SecondTaskEditor;
import webservice.webapp.domain.Task1;
import webservice.webapp.domain.Task2;
import webservice.webapp.domain.TaskManager;
import webservice.webapp.repos.TaskOneRepos;
import webservice.webapp.repos.TaskTwoRepos;

import java.util.*;

import javax.annotation.PostConstruct;
@Route("")
public class TaskList extends AppLayout {
    String path = "Tasks";
    private TaskManager taskManager = new TaskManager();
    @Autowired
    private TaskOneRepos taskOneRepos;
    @Autowired
    private TaskTwoRepos taskTwoRepos;
    private RouterLink routerLink;
    private RouterLink routerLink1;

    private Grid<Task1> taskOneGrid = new Grid<>(Task1.class);
    private Grid<Task2> taskTwoGrid = new Grid<>(Task2.class);
    VerticalLayout layout;
    private Button  button =  new Button("Task2",VaadinIcon.PLUS.create());
    private Button  button1 =  new Button("Task1",VaadinIcon.PLUS.create());
    private Button load =  new Button("Load",VaadinIcon.ARCHIVE.create());
    private Button save =  new Button("Save",VaadinIcon.ARCHIVE.create());

    public TaskList() {
        load.addClickListener(e->{
        taskManager =taskManager.load(path);
        taskManager.clearID(taskManager);
        taskOneRepos.saveAll(taskManager.getTasks1());
        taskTwoRepos.saveAll(taskManager.getTasks2());
        taskOneGrid.setItems(taskOneRepos.findAll());
        taskTwoGrid.setItems(taskTwoRepos.findAll());
    });
        save.addClickListener(e->{
            taskManager.setTasks1(taskOneRepos.findAll());
            taskManager.setTasks2(taskTwoRepos.findAll());
            taskManager.save(path,taskManager);
        });
        layout = new VerticalLayout();
        HorizontalLayout horizontalLayout =  new HorizontalLayout();
        routerLink = new RouterLink("",SecondTaskEditor.class,0);
        routerLink1 =new RouterLink("",FirstTaskEditor.class,0);
        routerLink.getElement().appendChild(button.getElement());
        routerLink1.getElement().appendChild(button1.getElement());
        horizontalLayout.add(routerLink,routerLink1,save,load);
        layout.addAndExpand( taskOneGrid,taskTwoGrid, horizontalLayout);
        addToNavbar(new H3("Списки задач"));
        setContent(layout);
    }

    @PostConstruct
    public void fillGrid() {
        List<Task2> task2s = taskTwoRepos.findAll();
        List<Task1> task1s = taskOneRepos.findAll();
        if (!task2s.isEmpty()) {
            //Добавим кнопку удаления и редактирования
            taskTwoGrid.addColumn(new NativeButtonRenderer<>("Удалить", task2 -> {
                Dialog dialog = new Dialog();
                Button confirm = new Button("Удалить");
                Button cancel = new Button("Отмена");
                dialog.add("Вы уверены что хотите удалить?");
                dialog.add(confirm);
                dialog.add(cancel);

                confirm.addClickListener(clickEvent -> {
                    taskTwoRepos.delete(task2);
                    dialog.close();
                    Notification notification = new Notification("Задача удалена", 1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();

                    taskTwoGrid.setItems(taskTwoRepos.findAll());

                });

                cancel.addClickListener(clickEvent -> {
                    dialog.close();
                });

                dialog.open();

            }));
            taskTwoGrid.setItems(taskTwoRepos.findAll());
        }
        if(!task1s.isEmpty()){
            taskOneGrid.addColumn(new NativeButtonRenderer<>("Удалить", task1 -> {
            Dialog dialog = new Dialog();
            Button confirm = new Button("Удалить");
            Button cancel = new Button("Отмена");
            dialog.add("Вы уверены что хотите удалить?");
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addClickListener(clickEvent -> {
                taskOneRepos.delete(task1);
                dialog.close();
                Notification notification = new Notification("Контакт удален", 1000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();

                taskOneGrid.setItems(taskOneRepos.findAll());

            });

            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });

            dialog.open();

        }));

            taskOneGrid.setItems(task1s);

        }

    }


}
