package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.exception.EntityAlreadyExistsException;
import solvd.laba.ermakovich.ha.repository.UserRepository;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserInfo save(UserInfo userInfo) {
        findByEmail(userInfo.getEmail())
                .ifPresent((user) ->
                {throw new EntityAlreadyExistsException(" User with this email: "
                                                            + userInfo.getEmail() + " already exist");});
        userRepository.save(userInfo);
        return userInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfo> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
