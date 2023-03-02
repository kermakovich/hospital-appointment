package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
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
    private final RestTemplate restTemplate;


    @Override
    @Transactional
    public UserInfo create(UserInfo userInfo) {
        if (userRepository.isExistByEmail(userInfo.getEmail())) {
            throw new ResourceAlreadyExistsException(" User with this email: " + userInfo.getEmail() + " already exist");
        }
        userInfo.setPassword(hashPassword(userInfo.getPassword()));
        userInfo.setExternalId(UUID.randomUUID());
        createAccount(userInfo.getExternalId());
        userRepository.save(userInfo);
        return userInfo;
    }

    private void createAccount(UUID externalId) {
        HttpEntity<?> request = new HttpEntity<>(externalId);
        ResponseEntity<?> resp = restTemplate.postForEntity("http://HOSPITAL-FINANCE/api/v1/accounts",
                request, Object.class);
        if (resp.getStatusCode() != HttpStatus.CREATED) {
            throw new IllegalOperationException("operation can not be executed. try later");
        }
    }

    private String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public UserInfo findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceDoesNotExistException("user with email: " + email + "doesn`t exist"));
    }

}
