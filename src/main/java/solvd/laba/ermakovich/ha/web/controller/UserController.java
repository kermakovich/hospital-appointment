package solvd.laba.ermakovich.ha.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvd.laba.ermakovich.ha.domain.jwt.Authentication;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.domain.jwt.Refresh;
import solvd.laba.ermakovich.ha.service.AuthService;
import solvd.laba.ermakovich.ha.web.dto.jwt.AuthenticationDto;
import solvd.laba.ermakovich.ha.web.dto.jwt.JwtResponseDto;
import solvd.laba.ermakovich.ha.web.dto.jwt.RefreshDto;
import solvd.laba.ermakovich.ha.web.mapper.jwt.AuthenticationMapper;
import solvd.laba.ermakovich.ha.web.mapper.jwt.JwtResponseMapper;
import solvd.laba.ermakovich.ha.web.mapper.jwt.RefreshMapper;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "The User API")
public class UserController {

    private final AuthService authService;
    private final JwtResponseMapper mapper;
    private final RefreshMapper refreshMapper;
    private final AuthenticationMapper authenticationMapper;

    @PostMapping("/login")
    @Operation(summary = "user authorization by email, password")
    public JwtResponseDto login(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationMapper.dtoToEntity(authenticationDto);
        JwtResponse response = authService.login(authentication);
        return mapper.entityToDto(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "refresh jwt tokens for user")
    public JwtResponseDto refresh(@RequestBody RefreshDto refreshDto) {
        Refresh refresh = refreshMapper.dtoToEntity(refreshDto);
        JwtResponse response = authService.refresh(refresh);
        return mapper.entityToDto(response);
    }

}
