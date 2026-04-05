package com.pict.service;

import com.pict.entity.Employee;
import com.pict.entity.PerformanceReview;
import com.pict.entity.Task;
import com.pict.repository.EmployeeRepository;
import com.pict.repository.PerformanceReviewRepository;
import com.pict.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private PerformanceReviewRepository reviewRepo;

    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public List<Employee> getEmployeesByDepartment(String dept) {
        return employeeRepo.findByDepartment(dept);
    }

    public Task assignTask(Long employeeId, String taskTitle) {
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Task task = new Task();
        task.setTitle(taskTitle);
        task.setEmployee(emp);
        return taskRepo.save(task);
    }

    public Map<String, Object> getPerformanceSummary(Long employeeId) {
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Task> tasks = taskRepo.findByEmployeeId(employeeId);
        List<PerformanceReview> reviews = reviewRepo.findByEmployeeId(employeeId);

        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(t -> "COMPLETED".equals(t.getStatus())).count();
        double completionRate = totalTasks > 0 ? (double) completedTasks / totalTasks * 100 : 0;

        double avgRating = reviews.stream()
                .mapToInt(PerformanceReview::getRating)
                .average()
                .orElse(0.0);

        Map<String, Object> summary = new HashMap<>();
        summary.put("employeeName", emp.getName());
        summary.put("department", emp.getDepartment());
        summary.put("totalTasks", totalTasks);
        summary.put("completedTasks", completedTasks);
        summary.put("completionRate", String.format("%.2f%%", completionRate));
        summary.put("averageRating", String.format("%.2f", avgRating));
        summary.put("reviewCount", reviews.size());

        return summary;
    }

    public Task completeTask(Long taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus("COMPLETED");
        return taskRepo.save(task);
    }

    public PerformanceReview addReview(PerformanceReview review) {

        Employee emp = employeeRepo.findById(review.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        review.setEmployee(emp);
        return reviewRepo.save(review);
    }
}