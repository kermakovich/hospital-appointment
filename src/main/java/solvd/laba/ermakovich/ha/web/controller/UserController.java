package solvd.laba.ermakovich.ha.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import solvd.laba.ermakovich.ha.domain.jwt.Authentication;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.domain.jwt.Refresh;
import solvd.laba.ermakovich.ha.service.AuthService;
import solvd.laba.ermakovich.ha.service.UserInfoService;
import solvd.laba.ermakovich.ha.web.dto.jwt.AuthenticationDto;
import solvd.laba.ermakovich.ha.web.dto.jwt.JwtResponseDto;
import solvd.laba.ermakovich.ha.web.dto.jwt.RefreshDto;
import solvd.laba.ermakovich.ha.web.mapper.jwt.AuthenticationMapper;
import solvd.laba.ermakovich.ha.web.mapper.jwt.JwtResponseMapper;
import solvd.laba.ermakovich.ha.web.mapper.jwt.RefreshMapper;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "user")
public class UserController {

    private final AuthService authService;
    private final JwtResponseMapper mapper;
    private final RefreshMapper refreshMapper;
    private final AuthenticationMapper authenticationMapper;
    private final UserInfoService userInfoService;

    @PostMapping("/login")
    @Operation(summary = "authorizes user by email and password")
    public JwtResponseDto login(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationMapper.dtoToEntity(authenticationDto);
        JwtResponse response = authService.login(authentication);
        return mapper.entityToDto(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "refreshes jwt tokens for user")
    public JwtResponseDto refresh(@RequestBody RefreshDto refreshDto) {
        Refresh refresh = refreshMapper.dtoToEntity(refreshDto);
        JwtResponse response = authService.refresh(refresh);
        return mapper.entityToDto(response);
    }

    @GetMapping
    public Boolean isExistByExternalId(@RequestParam UUID externalId) {
        return userInfoService.isExistByExternalId(externalId);
    }

}
