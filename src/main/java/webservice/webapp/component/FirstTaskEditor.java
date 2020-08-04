package webservice.webapp.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
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
import webservice.webapp.domain.Task1;
import webservice.webapp.domain.Task2;
import webservice.webapp.domain.TaskManager;
import webservice.webapp.repos.TaskOneRepos;
import webservice.webapp.view.TaskList;

import java.util.Optional;
import java.util.Random;

@Route("Task1")
public class FirstTaskEditor extends AppLayout implements HasUrlParameter<Integer> {
    private Integer id;
    @Autowired
    private TaskOneRepos taskOneRepos;

    private TaskManager taskManager =  new TaskManager();
    private Task1 task1 = new Task1();
    private TextField a1;
    private TextField a2;
    private FormLayout form;

    private Button execute =  new Button("Execute");



    public FirstTaskEditor() {
        form = new FormLayout();
        a1 = new TextField("","arp,live");
        a2 = new TextField("","sharp,alive.lively");
        execute = new Button("Execute");
        form.add(a1,a2,execute);
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
            Optional<Task1> tasks = taskOneRepos.findById(id);
            tasks.ifPresent(x -> {
                a1.setValue(x.getA1());
                a2.setValue(x.getA2());
            });
        }

        execute.addClickListener(clickEvent -> {
            //Создадим объект задачи получив значения с формы
            Task1 task1 =  new Task1();
            if (!id.equals(0)) {
                task1.setId(id);
            }
            task1.setA1(a1.getValue());
            task1.setA2(a2.getValue());
            task1.setR(taskManager.firstTask(task1));
            taskOneRepos.save(task1);
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
