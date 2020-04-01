package com.organization.springStudentsToClasses.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.organization.springStudentsToClasses.exceptions.InvalidOperationException;
import com.organization.springStudentsToClasses.exceptions.NotFoundException;
import com.organization.springStudentsToClasses.models.StudentData;
import com.organization.springStudentsToClasses.services.StudentSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/student")
public class StudentController {

  private final StudentSaveService service;

  @Autowired
  public StudentController(StudentSaveService service) {
    this.service = service;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/")
  public ResponseEntity getAllStudents() {
    return new ResponseEntity(this.service.getAll(), HttpStatus.OK);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{studentId}")
  public ResponseEntity getStudentClasses(@PathVariable int studentId)
      throws NotFoundException {
    return new ResponseEntity(this.service.getById(studentId), HttpStatus.OK);
  }

  @RequestMapping(method=POST, value="/")
  public ResponseEntity createStudent(@RequestBody StudentData studentBase) {
    return new ResponseEntity(this.service.save(studentBase), HttpStatus.OK);
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
  public ResponseEntity updateStudent(@PathVariable int id, @RequestBody StudentData studentBase)
      throws NotFoundException {
    return new ResponseEntity(this.service.update(studentBase), HttpStatus.OK);
  }

  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
  public ResponseEntity deleteStudent(@PathVariable int id)
      throws NotFoundException, InvalidOperationException {
    this.service.delete(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/search")
  public ResponseEntity getAllStudentsSearch(
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    return new ResponseEntity(this.service.getAllSearch(firstName, lastName), HttpStatus.OK);
  }
}
