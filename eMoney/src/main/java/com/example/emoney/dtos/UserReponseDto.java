package com.example.emoney.dtos;

import com.example.emoney.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReponseDto {
    private String username;
    private String name;
    private String role;
    private List<WalletResponseDto> wallets;

    public static UserReponseDto getDto(User user){
        UserReponseDto dto = UserReponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRoles().toString())
                    .build();

        dto.setWallets(user.getWallets().stream()
                .map(i -> WalletResponseDto.getDto(i)).toList());
        return dto;
    }
}
