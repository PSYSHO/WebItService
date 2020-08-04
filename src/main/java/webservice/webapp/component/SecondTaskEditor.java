package webservice.webapp.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import webservice.webapp.domain.Task2;
import webservice.webapp.domain.TaskManager;
import webservice.webapp.repos.TaskTwoRepos;
import webservice.webapp.view.TaskList;

import java.util.Optional;

@Route("Task2")
public class SecondTaskEditor extends AppLayout implements HasUrlParameter<Integer> {
    private Integer id;
    @Autowired
    private  TaskTwoRepos taskTwoRepos;

    private TaskManager taskManager = new TaskManager();

    private Task2 task2 = new Task2();
    private FormLayout form;

    private TextField number = new TextField("","Enter integer Number");
    private Button execute = new Button("Execute");

    public SecondTaskEditor(){
        form = new FormLayout();
        number = new TextField("","Enter integer Number");
        execute = new Button("Execute");
        form.add(number,execute);
        setContent(form);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer idTask) {
        id = idTask;
        if(!id.equals(0)){
            addToNavbar(new H3(""));
        }else{
            addToNavbar(new H3("Create Task"));
        }
        fillForm();
    }
    public void fillForm() {

        if (!id.equals(0)) {
            Optional<Task2> tasks = taskTwoRepos.findById(id);
            tasks.ifPresent(x -> {
                number.setValue(String.valueOf(x.getNumb()));
            });
        }

        execute.addClickListener(clickEvent -> {
            //Создадим объект задачи получив значения с формы
            Task2 task2 =  new Task2();
            if (!id.equals(0)) {
                task2.setId(id);
            }
            task2.setNumb(Integer.parseInt(number.getValue()));
            task2.setResult(taskManager.secondTask(task2));
            taskTwoRepos.save(task2);
            //Выведем уведомление пользователю и переведем его к списку задач
            Notification notification = new Notification(id.equals(0) ? "The task has been created" : "The task has changed", 1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(TaskList.class);
            });
            form.setEnabled(false);
            notification.open();
        });
    }

}
