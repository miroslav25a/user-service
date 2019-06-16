package com.iceze.control;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iceze.UserServiceApp;
import com.iceze.dao.UserDao;
import com.iceze.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UserServiceApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserRestApiControllerIntegrationTest {
    private static final String DATE_PATTERN = "yyyy-mm-dd";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserDao userDao;

    private ObjectMapper mapper;
    private DateFormat dateFormat;

    @Before
    public void setup() {
        mapper  = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        dateFormat = new SimpleDateFormat(DATE_PATTERN);
    }

    @After
    public void resetDb() {
        userDao.deleteAll();
    }

    @Test
    public void getUserReturnsStatusOk() throws Exception {
        User user = createTestUser();

        mvc.perform(get("/api/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void createUserReturnsOk() throws Exception {
        User user = getUser();

        mvc.perform(post("/api/user/").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(user)))
                .andDo(print())
                .andExpect(status().isCreated()).andExpect(header().string("Location", "http://localhost/api/user/2"));

        List<User> result = userDao.findAll();

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo(user.getName());
        assertThat(dateFormat.format(result.get(0).getDob())).isEqualTo(dateFormat.format(user.getDob()));
        assertThat(result.get(0).getId()).isEqualTo(2L);
    }

    private User getUser() {
        User user = new User();
        user.setName("John");
        user.setDob(new Date());

        return user;
    }

    private User createTestUser() {
        User user = getUser();
        user.setId(1L);

        return userDao.saveAndFlush(user);
    }
}
