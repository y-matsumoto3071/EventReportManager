package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nexus.repository.PasswordDao;

@Service
public class PasswordService{

	@Autowired
	PasswordDao passwordDao;

	//全検索する
	public List<Map<String, Object>> searchPassword() {
		List<Map<String, Object>> list = passwordDao.searchPassword();
		return list;
	}

}