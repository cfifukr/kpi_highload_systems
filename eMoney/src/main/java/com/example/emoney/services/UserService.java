package com.example.emoney.services;

import com.example.emoney.dtos.RegistrationDto;
import com.example.emoney.enums.Currency;
import com.example.emoney.models.User;
import com.example.emoney.models.Wallet;
import com.example.emoney.repositories.UserRepository;
import com.example.emoney.utils.Hash;
import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;



    @Transactional(readOnly = true)
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username){
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isEmpty()){
            return null;
        }
        return user.get();
    }

    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public User login(String username, String password){
        String passHash = Hash.toStringHash256(password);
        System.out.println(passHash);

        Optional<User> user = userRepository.findUserByUsername(username);

        if(user.isEmpty() || user.get().getPassword().compareTo(passHash) !=0){
            return null;
        }

        return user.get();
    };

    @Transactional
    public User registration(RegistrationDto registrationDto){

        String hashPassword =Hash.toStringHash256(registrationDto.getPassword());

        if(userRepository.existsByUsername(registrationDto.getUsername()) || hashPassword == null){
            return null;
        }



        User user = new User().builder()
                .username(registrationDto.getUsername())
                .password(hashPassword)
                .name(registrationDto.getName())
                .roles(roleService.getRolesFromString("ROLE_USER"))
                .wallets(new ArrayList<>())
                    .build();

        Wallet wallet = Wallet.createWallet("Default wallet", Currency.UAH);
        user.addWallet(wallet);



        return userRepository.save(user);
    }


}
