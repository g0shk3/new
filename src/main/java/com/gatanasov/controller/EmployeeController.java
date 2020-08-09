package com.gatanasov.controller;

import com.gatanasov.exeption.ResourceNotFoundException;
import com.gatanasov.model.Employee;

import com.gatanasov.repository.EmployeeRepository;

import com.gatanasov.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://g0shke.netlify.app")
@RestController
@RequestMapping
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }


    @GetMapping("/employees")
    public List<Employee> getEmployee( ){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public void createOrUpdateEmployee(@RequestBody Employee employee){
        employeeService.createOrUpdateUser(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id){
        Employee employee  = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist id :" + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee>  updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee  = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist id :" + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity <Map<String, Boolean>>deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean>  response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
