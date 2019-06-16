package com.iceze.control;

import com.iceze.UserServiceApp;
import com.iceze.dao.UserDao;
import com.iceze.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UserServiceApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserRestApiControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserDao userDao;

    @After
    public void resetDb() {
        userDao.deleteAll();
    }

    @Test
    public void getUserReturnsStatus200() throws Exception {
        User user = createTestUser();

        mvc.perform(get("/api/user/" + user.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    private User createTestUser() {
        User user = new User();
        user.setName("John");
        user.setDob(new Date());
        user.setId(1L);

        return userDao.saveAndFlush(user);
    }
}
