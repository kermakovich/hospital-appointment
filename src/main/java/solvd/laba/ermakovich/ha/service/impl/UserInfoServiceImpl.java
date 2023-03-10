package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.exception.ResourceAlreadyExistsException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.repository.UserRepository;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    @Transactional
    public UserInfo create(UserInfo userInfo) {
        if (userRepository.isExistByEmail(userInfo.getEmail())) {
            throw new ResourceAlreadyExistsException(" User with this email: " + userInfo.getEmail() + " already exist");
        }
        userInfo.setPassword(hashPassword(userInfo.getPassword()));
        userInfo.setExternalId(UUID.randomUUID());
        userRepository.save(userInfo);
        return userInfo;
    }


    private String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public UserInfo findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceDoesNotExistException("user with email: " + email + "doesn`t exist"));
    }

    @Override
    public Boolean isExistByExternalId(UUID externalId) {
        return userRepository.isExistByExternalId(externalId.toString());
    }

}
