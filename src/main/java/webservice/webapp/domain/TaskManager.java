package webservice.webapp.domain;

import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class TaskManager implements Serializable {
    private List<Task1> tasks1 = new LinkedList<Task1>();
    private List<Task2> tasks2 = new LinkedList<Task2>();
    private Task1 task1;
    private Task2 task2;

    public TaskManager() {
    }

    public List getTasks1() {
        return tasks1;
    }

    public void setTasks1(List tasks1) {
        this.tasks1 = tasks1;
    }

    public List getTasks2() {
        return tasks2;
    }

    public void setTasks2(List tasks2) {
        this.tasks2 = tasks2;
    }

    public TaskManager clearID(TaskManager taskManager){
        tasks1.addAll(taskManager.getTasks1());
        tasks2.addAll(taskManager.getTasks2());
        for (Task1 task1 :tasks1) {
            task1.setId(null);
        }
        for (Task2 task2:tasks2){
            task2.setId(null);
        }
        taskManager.setTasks1(tasks1);
        taskManager.setTasks2(tasks2);
        return taskManager;
    }

    public String secondTask(Task2 task2) {
        String[] Numbers = Integer.toString(task2.getNumb()).split("");
        String result = "";
        for (int i = 0; i < Numbers.length - 1; i++) {
            if (Integer.valueOf(Numbers[i]) > 0) {
                for (int j = i; j < Numbers.length - 1; j++) {
                    Numbers[i] += '0';
                }
            }
        }
        result = Arrays.toString(Numbers);
        result = result.substring(1, result.length() - 1).replace(", 0", "").replace(",", " +");
        task2.setResult(result);
        return result;
    }

    public String firstTask(Task1 task1) {
        String[] a1 = task1.getA1().split(",");
        String[] a2 = task1.getA2().split(",");
        String[] r = new String[a1.length];

        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a2.length; j++) {
                if (a2[j].contains(a1[i])) r[i] = a1[i];
            }
        }
        task1.setR(unNull(r));
        return task1.getR();
    }

    private static String unNull(String[] r) {
        int lastElement = 0;
        for (int i = 0; i < r.length; i++) {
            if (r[i] != null) {
                r[lastElement] = r[i];
                lastElement++;
            }
        }
        String[] notNullArray = Arrays.copyOf(r, lastElement);
        Arrays.sort(notNullArray);
        return Arrays.toString(notNullArray);
    }

    public TaskManager load(String path) {
        TaskManager taskManager = new TaskManager();
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(path);
            taskManager = gson.fromJson(reader, TaskManager.class);
            reader.close();
        } catch (IOException e) {
        }
        return taskManager;
    }

    public void save(String path, TaskManager taskManager) {
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(gson.toJson(taskManager));
            writer.close();
        } catch (IOException e) {
        }
    }

}
