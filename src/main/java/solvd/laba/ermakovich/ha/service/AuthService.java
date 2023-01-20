package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.jwt.Authentication;
import solvd.laba.ermakovich.ha.domain.jwt.JwtResponse;
import solvd.laba.ermakovich.ha.domain.jwt.Refresh;

public interface AuthService {

    JwtResponse login(Authentication authentication);

    JwtResponse refresh(Refresh refresh);
}
