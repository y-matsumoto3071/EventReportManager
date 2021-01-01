package jp.co.nexus;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Map;

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
		Map<String, Object> map = passwordDao.getPassword(1);
		assertThat(map.get("password_body").toString(), is("123456"));
	}

}
