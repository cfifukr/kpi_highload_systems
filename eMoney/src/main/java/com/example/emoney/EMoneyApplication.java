package com.example.emoney;

import com.example.emoney.models.Role;
import com.example.emoney.services.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class EMoneyApplication implements ApplicationRunner {

    private final RoleService roleService;


    public static void main(String[] args) {
        SpringApplication.run(EMoneyApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role role_user = new Role(1,"ROLE_USER");
        Role role_admin = new Role(2,"ROLE_ADMIN");

        roleService.addRoles(role_user);
        roleService.addRoles(role_admin);


    }
}
