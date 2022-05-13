/**
 *
 */
package jp.co.nexus.repository;

import java.util.Map;

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

	/**
	 * 指定された面談報告書を抽出するSQLを実行する
	 * @param eventId 抽出対象の報告書IDのInteger
	 * @return reportMap 抽出結果のMap
	 */
	public Map<String, Object> searchReport(Integer eventId){

		//SQL文作成
		String sql = "SELECT * FROM event, client, employee"
				+ " WHERE event.event_id = ?"
				+ " AND client.client_id = event_client_id"
				+ " AND employee.employee_id = event_entry_employee_id"
				+ " AND event_status != 0";

		//?の箇所を置換するデータの配列を定義
		Object[] param = { eventId };

		//クエリを実行
		Map<String, Object> reportMap = jdbcTemplate.queryForMap(sql, param);

		//取得したデータを返す
		return reportMap;
	}


}
