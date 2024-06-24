package com.example.emoney.controllers;

import com.example.emoney.dtos.RoleDto;
import com.example.emoney.models.Role;
import com.example.emoney.services.RoleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody RoleDto roleDto){
        Role role = new Role();
        role.setName(roleDto.getName());

        return new ResponseEntity<>(roleService.addRoles(role), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAll(){

        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }
}
