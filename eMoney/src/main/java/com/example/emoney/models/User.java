package com.example.emoney.models;


import com.example.emoney.services.RoleService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {
    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    public void addWallet(Wallet wallet) throws RuntimeException{
        if(wallets.size() < 5) {
            wallets.add(wallet);
            wallet.setUser(this);
        }else{
            throw new RuntimeException("User can have only 5 wallets");
        }
    }


    public void removeWallet(Wallet wallet) {
        wallets.remove(wallet);
        wallet.setUser(this);
    }

    public void addGoal(Goal goal){
        goal.setUser(this);
        goals.add(goal);
    }

    public void deleteGoal(Goal goal){
        goal.setUser(null);
        goals.remove(goal);
    }


    public static String rolesToString(Collection<Role> roles){
        String res = "";
        for(Role role : roles){
            res += role.getName() + ", ";
        }
        return  res;
    }

    public static Collection<Role>  stringToRoles(String str, RoleService roleService){
        List<Role> res = new ArrayList<>();
        String[] array = str.split(", ");
        for(String i : array){
            Role role = roleService.findByName(i);
            if( role != null){
                res.add(role);
            }
        }
        return res;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(i -> new SimpleGrantedAuthority(i.getName())).toList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
