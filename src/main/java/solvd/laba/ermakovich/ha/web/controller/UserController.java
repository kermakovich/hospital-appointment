package solvd.laba.ermakovich.ha.web.controller;

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
import solvd.laba.ermakovich.ha.web.mapper.AuthenticationMapper;
import solvd.laba.ermakovich.ha.web.mapper.JwtResponseMapper;
import solvd.laba.ermakovich.ha.web.mapper.RefreshMapper;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final JwtResponseMapper mapper;
    private final RefreshMapper refreshMapper;
    private final AuthenticationMapper authenticationMapper;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationMapper.dtoToEntity(authenticationDto);
        JwtResponse response = authService.login(authentication);
        return mapper.entityToDto(response);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@RequestBody RefreshDto refreshDto) {
        Refresh refresh = refreshMapper.dtoToEntity(refreshDto);
        JwtResponse response = authService.refresh(refresh);
        return mapper.entityToDto(response);
    }

}
