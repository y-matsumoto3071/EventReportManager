package jp.co.nexus;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.nexus.repository.PasswordDao;

@SpringBootTest
class EventReportManagerApplicationTests {

	@Autowired
	PasswordDao passwordDao;

	@Test
	void getPasswordTest() {
		assertThat(passwordDao.getPassword(1), is("123456"));
	}

}
