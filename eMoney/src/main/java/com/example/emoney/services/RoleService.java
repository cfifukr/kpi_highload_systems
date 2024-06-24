package com.example.emoney.services;

import com.example.emoney.models.Role;
import com.example.emoney.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RoleService {

    @Autowired
    private  RoleRepository roleRepository;

    public Role addRoles(Role role){
        if(findByName(role.getName()) == null){
            return roleRepository.save(role);
        }
        return null;
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role findByName(String name){
        Optional<Role> role = roleRepository.findByName(name);

        Role res = (role.isPresent()) ?  role.get() : null;

        return res;
    }


    public List<Role> getRolesFromString(String roles){
        String[] roleArray = roles.toUpperCase().strip().split(", ");
        List<Role> res = Arrays.stream(roleArray).map(i -> findByName(i)).toList();
        return res;
    }


}
