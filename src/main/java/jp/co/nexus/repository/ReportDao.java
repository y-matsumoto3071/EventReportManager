/**
 *
 */
package jp.co.nexus.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.nexus.model.Report;

/**
 * @author Yuki Matsumoto
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
	}


}
