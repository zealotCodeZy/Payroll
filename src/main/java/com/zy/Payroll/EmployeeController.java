package com.zy.Payroll;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    EmployeeController(EmployeeRepository repository,EmployeeModelAssembler assembler){
        this.repository=repository;
        this.assembler=assembler;
    }
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all(){
        List<EntityModel<Employee>> employees=repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel()
                );
    }
    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id){
        Employee employee= repository.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }
    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee employee){
        EntityModel<Employee> entityModel=assembler.toModel(repository.save(employee));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee,@PathVariable Long id){
        Employee updatedEmployee= repository.findById(id).map(
                employee1 -> {
                    employee1.setName(newEmployee.getName());
                    employee1.setRole(newEmployee.getRole());
                    return repository.save(employee1);
                }
        ).orElseGet(()->{
           newEmployee.setId(id);
           return repository.save(newEmployee);
        });
        EntityModel<Employee> entityModel=assembler.toModel(updatedEmployee);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
