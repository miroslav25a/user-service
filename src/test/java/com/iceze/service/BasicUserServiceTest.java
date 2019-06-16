package com.iceze.service;

import com.iceze.dao.UserDao;
import com.iceze.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BasicUserServiceTest {
    //@InjectMocks
    private BasicUserService userService;
    @Mock
    private UserDao userDao;

    @Before
    public void setup() {
        //MockitoAnnotations.initMocks(this); // not required if it's MockitoJUnitRunner
        userService = new BasicUserService(userDao);
    }

    @Test
    public void saveUserTest() {
        User user = createUser();
        User user2 = createUser();
        user2.setId(new Long(1));

        when(userDao.saveAndFlush(any(User.class))).thenReturn(user2);
        Optional<User> result = userService.saveUser(user);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(user2.getId());
        assertThat(result.get().getDob()).isEqualTo(user2.getDob());
        assertThat(result.get().getName()).isEqualTo(user2.getName());

        verify(userDao, times(1)).saveAndFlush(any(User.class));
    }

    private User createUser() {
        User user = new User();
        user.setName("John");
        user.setDob(new Date());

        return user;
    }
}
