package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.domain.exception.ResourceAlreadyExistsException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserInfoServiceImpl userInfoService;


    @Test
    void verifyCreateSuccessfulTest() {
        final long userInfoId = 1L;
        UserInfo expectedUserInfo = getUserInfoWithHashedPassword();
        expectedUserInfo.setId(userInfoId);
        given(userRepository.isExistByEmail(Mockito.anyString())).willReturn(false);
        given(encoder.encode(anyString())).willReturn(expectedUserInfo.getPassword());
        doAnswer((invocation)-> {
            UserInfo userInfo = invocation.getArgument(0);
            userInfo.setId(userInfoId);
            return null;
        }).when(userRepository).save(any(UserInfo.class));

        UserInfo actualUser = userInfoService.create(getUserInfo());

        assertEquals(expectedUserInfo, actualUser, "users are not equal");
        verify(userRepository, times(1)).isExistByEmail(anyString());
        verify(userRepository, times(1)).save(any(UserInfo.class));
    }

    @Test
    void verifyCreateThrowsResourceAlreadyExistsExceptionTest() {
        given(userRepository.isExistByEmail(Mockito.anyString())).willReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userInfoService.create(getUserInfo()),
                "Exception wasn`t thrown");
    }

    @Test
    void verifyFindByEmailSuccessfulTest() {
        UserInfo expectedUserInfo = getUserInfoWithHashedPassword();
        given(userRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(expectedUserInfo));

        UserInfo foundedUser = userInfoService.findByEmail(getUserInfo().getEmail());

        assertEquals(expectedUserInfo, foundedUser, "users are not equal");
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void verifyFindByEmailThrowsResourceDoesNotExistExceptionTest() {
        given(userRepository.findByEmail(Mockito.anyString())).willReturn(Optional.empty());

        assertThrows(ResourceDoesNotExistException.class,() -> userInfoService.findByEmail("email"),
                "Exception wasn`t thrown");
    }

    private UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("name");
        userInfo.setSurname("surname");
        userInfo.setFatherhood( "fatherhood");
        userInfo.setBirthday(LocalDate.of(2001, 1, 1));
        userInfo.setEmail("email");
        userInfo.setPassword("1234");
        userInfo.setRole(UserRole.PATIENT);
        return userInfo;
    }

    private UserInfo getUserInfoWithHashedPassword() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("name");
        userInfo.setSurname("surname");
        userInfo.setFatherhood( "fatherhood");
        userInfo.setBirthday(LocalDate.of(2001, 1, 1));
        userInfo.setEmail("email");
        userInfo.setPassword("$2a$10$UmzxqbNCl9aBTtmkdAKajeXFrir6AHD6I3kE/MiLWU.W1RWLMvnYq");
        userInfo.setRole(UserRole.PATIENT);
        return userInfo;
    }

}
