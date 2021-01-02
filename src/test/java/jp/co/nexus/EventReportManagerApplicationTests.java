package jp.co.nexus;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.nexus.service.PasswordService;

@SpringBootTest
class EventReportManagerApplicationTests {

	@Autowired
	PasswordService passwordService;

	@Test
	void getPasswordTest() {
		String active_pw = passwordService.getPassword(1);
		assertThat(active_pw, is("123456"));
	}

}
