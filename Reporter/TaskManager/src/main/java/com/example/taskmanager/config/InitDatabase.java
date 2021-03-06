package com.example.taskmanager.config;

import com.example.taskdriver.model.TaskState;
import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Sprint;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.EmployeeRepository;
import com.example.taskmanager.repository.SprintRepository;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitDatabase implements InitializingBean {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void afterPropertiesSet() {
        Employee firstEmployee = new Employee("employee1");
        Employee secondEmployee = new Employee("employee2");
        Employee lead = new Employee("lead");
        lead.addSubordinate(firstEmployee);
        lead.addSubordinate(secondEmployee);
        lead = employeeRepository.save(lead);
        firstEmployee.setHeadId(lead.getId());
        secondEmployee.setHeadId(lead.getId());
        employeeRepository.save(lead);
        Task firstExampleTask = Task.builder()
                        .name("Task1")
                        .description("First example task")
                        .employee(firstEmployee)
                        .assigner(lead)
                        .state(TaskState.OPEN)
                        .build();
        firstExampleTask.activate();
        taskRepository.save(firstExampleTask);
        Task secondExampleTask = Task.builder()
                        .name("Task1")
                        .description("First example task")
                        .employee(firstEmployee)
                        .assigner(lead)
                        .state(TaskState.OPEN)
                        .build();
        secondExampleTask.resolve();
        taskRepository.save(secondExampleTask);
        Sprint sprint = sprintRepository.save(new Sprint("Sprint example"));
        sprint.addTask(firstExampleTask);
        sprint.addTask(secondExampleTask);
        sprintRepository.save(sprint);
    }
}
