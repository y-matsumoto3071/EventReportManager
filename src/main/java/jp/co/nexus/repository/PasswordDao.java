package jp.co.nexus.repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	//面談報告書を全検索する
	public List<Map<String, Object>> searchPassword() {

		//現在日付の取得
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date =df.format(d);


		// SQL文作成
		String sql = "SELECT password_id, password_body from password WHERE ? BETWEEN startdate AND enddate";

		Object[] param = {date};

		// クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,param);

		// 取得したリストを返す
		return list;
	}
}