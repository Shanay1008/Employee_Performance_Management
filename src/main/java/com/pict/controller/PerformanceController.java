package com.pict.controller;

import com.pict.entity.Employee;
import com.pict.entity.PerformanceReview;
import com.pict.entity.Task;
import com.pict.service.PerformanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = performanceService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployeesByDept(@RequestParam String department) {
        return ResponseEntity.ok(performanceService.getEmployeesByDepartment(department));
    }

    @PostMapping("/tasks/{employeeId}")
    public ResponseEntity<Task> assignTask(
            @PathVariable Long employeeId,
            @RequestParam String taskTitle) {
        Task assignedTask = performanceService.assignTask(employeeId, taskTitle);
        return ResponseEntity.ok(assignedTask);
    }

    @GetMapping("/summary/{employeeId}")
    public ResponseEntity<Map<String, Object>> getSummary(@PathVariable Long employeeId) {
        Map<String, Object> summary = performanceService.getPerformanceSummary(employeeId);
        return ResponseEntity.ok(summary);
    }

    @PutMapping("/tasks/{taskId}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long taskId) {
        Task updatedTask = performanceService.completeTask(taskId);
        return ResponseEntity.ok(updatedTask);
    }

    @PostMapping("/reviews")
    public ResponseEntity<PerformanceReview> addReview(@Valid @RequestBody PerformanceReview review) {
        PerformanceReview savedReview = performanceService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }
}