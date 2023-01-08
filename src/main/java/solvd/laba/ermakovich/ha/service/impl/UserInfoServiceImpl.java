package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.exception.EmailAlreadyExistsException;
import solvd.laba.ermakovich.ha.repository.UserRepository;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepository userRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        findByEmail(userInfo.getEmail()).ifPresent((user) -> {throw new EmailAlreadyExistsException(userInfo.getEmail());});
        userRepository.save(userInfo);
        return userInfo;
    }

    @Override
    public Optional<UserInfo> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
