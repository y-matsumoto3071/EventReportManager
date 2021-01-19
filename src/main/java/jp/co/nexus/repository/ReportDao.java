/**
 *
 */
package jp.co.nexus.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.nexus.model.Report;

/**
 * ReportDao.java
 * 報告書管理機能で使用する報告書情報の登録・検索・編集・削除に関するSQLを
 * 作成して実行するクラス
 *
 * @author 氏名を記載すること
 *
 */
@Repository
public class ReportDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ReportDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insertReport(Report report) {
		jdbcTemplate.update("sql", "args");
	}


}
