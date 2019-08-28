package org.marvin.core.controllers;

import org.marvin.core.impls.repositories.AccountRepository;
import org.marvin.core.interfaces.IPlanRepositories;
import org.marvin.core.models.Account;
import org.marvin.core.models.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TodoController {

    private IPlanRepositories<Todo> planRepo;
    private AccountRepository repository;

    @GetMapping
    public ResponseEntity checkApi(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/todos")
    public Collection<Todo> listAll()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Account account = repository.findByLogin(authentication.getName());

        return account.getTodos();
    }

    @PostMapping("/todos")
    public ResponseEntity save(@RequestBody Todo todo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Account accounts = repository.findByLogin(authentication.getName());

        long genId = planRepo.create(todo,accounts.getId());

        Todo created = planRepo.findById(genId);

        return new ResponseEntity<>(created,HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity delete(@PathVariable("id") long id){

        planRepo.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/todos/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                               @RequestBody Todo updatedTodo) {

        String titlePatch = updatedTodo.getTitle();
        boolean donePatch = updatedTodo.isCompleted();

        Todo todo = planRepo.findById(id);

        if(todo != null ){

            if(titlePatch != null){
                todo.setTitle(titlePatch);
            }

            todo.setCompleted(donePatch);

        }else return new ResponseEntity(HttpStatus.NOT_FOUND);

        Todo updated = planRepo.update(todo);

        return new ResponseEntity<Todo>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/todos")
    public ResponseEntity clearCompleted(){

        planRepo.deleteComplete();

        return new ResponseEntity(HttpStatus.OK);
    }

    @Autowired
    public void setPlanRepo(IPlanRepositories planRepo) {
        this.planRepo = planRepo;
    }

    @Autowired
    public void setRepository(AccountRepository repository) {
        this.repository = repository;
    }
}
