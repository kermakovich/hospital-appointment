package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.exception.ResourceAlreadyExistsException;
import solvd.laba.ermakovich.ha.repository.UserRepository;
import solvd.laba.ermakovich.ha.service.UserInfoService;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserInfo save(UserInfo userInfo) {
        userRepository.findByEmail(userInfo.getEmail())
                .ifPresent((user) ->
                {throw new ResourceAlreadyExistsException(" User with this email: "
                                                            + userInfo.getEmail() + " already exist");});
        userRepository.save(userInfo);
        return userInfo;
    }

}
