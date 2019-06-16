package com.iceze.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iceze.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoIntegrationTest {
    private static final String NAME_1 = "John";
    private static final Date DOB_1 = new Date();
    private static final String NAME_2 = "Mark";
    private static final Date DOB_2 = new Date();

	@Autowired
	private UserDao userDao;
	
	@Test
	public void saveAndFindByIdTest() {
		User result = userDao.save(createUser(NAME_1, DOB_1));
		assertThat(result).isNotNull();
		assertThat(result.getId()).isNotNull().isGreaterThan(0);
		
		Optional<User> optionalResult = userDao.findById(result.getId());
		assertThat(optionalResult.isPresent()).isTrue();
		assertThat(optionalResult.get().getId()).isEqualTo(result.getId());
		assertThat(optionalResult.get().getName()).isEqualTo(NAME_1);
		assertThat(optionalResult.get().getDob()).isEqualTo(DOB_1);
	}

	@Test
    public void findAllTest() {
        User user1 = userDao.save(createUser(NAME_1, DOB_1));
        assertThat(user1).isNotNull();
        assertThat(user1.getId()).isNotNull().isGreaterThan(0);

        User user2 = userDao.save(createUser(NAME_2, DOB_2));
        assertThat(user2).isNotNull();
        assertThat(user2.getId()).isNotNull().isGreaterThan(0);

        List<User> result = userDao.findAll();
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(user1);
        assertThat(result).contains(user2);
    }

    @Test
    public void deleteTest() {
        User user1 = userDao.save(createUser(NAME_1, DOB_1));
        assertThat(user1).isNotNull();
        assertThat(user1.getId()).isNotNull().isGreaterThan(0);

        Optional<User> optionalUser1 = userDao.findById(user1.getId());
        assertThat(optionalUser1.isPresent()).isTrue();
        assertThat(optionalUser1.get().getId()).isEqualTo(user1.getId());
        assertThat(optionalUser1.get().getName()).isEqualTo(NAME_1);
        assertThat(optionalUser1.get().getDob()).isEqualTo(DOB_1);

        userDao.delete(user1.getId());
        Optional<User> result = userDao.findById(user1.getId());
        assertThat(result.isPresent()).isFalse();
    }

    private User createUser(final String name, final Date dob) {
        User user = new User();
        user.setName(name);
        user.setDob(dob);

        return user;
    }
}
