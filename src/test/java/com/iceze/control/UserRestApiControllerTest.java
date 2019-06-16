package com.iceze.control;

import com.iceze.model.User;
import com.iceze.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestApiController.class)
public class UserRestApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean(name = "userService")
    private UserService userService;

    @Test
    public void findAllUsersReturnsOK() throws Exception {
        Optional<List<User>> users = createUsers();

        given(userService.findAllUsers()).willReturn(users);

        mvc.perform(get("/api/user/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(users.get().get(0).getName())));
    }

    @Test
    public void findAllUsersReturnsNotFound() throws Exception {
        Optional<List<User>> users = Optional.empty();

        given(userService.findAllUsers()).willReturn(users);

        mvc.perform(get("/api/user/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    private Optional<List<User>> createUsers() {
        User user = new User();

        user.setName("John");
        user.setDob(new Date());
        user.setId(1L);

        return Optional.of(Arrays.asList(user));
    }
}
