/**
 *
 */
package jp.co.nexus.repository;

import java.util.List;
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
 * @author 中村 美南海
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
	 * 状態区分=0以外の面談報告書を全件取得してListで返すSQLを実行する
	 * @param なし
	 * @return list 取得結果のList
	 */
	public List<Map<String, Object>> searchAll() {

		//SQL文作成
		String sql = "SELECT * FROM event, client, employee WHERE "
				   + "client.client_id = event_client_id AND "
				   + "employee.employee_id = event_entry_employee_id "
				   + "AND event_status != 0";

		//クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		//取得したリストを返す
		return list;

	}

	/**
	 * 指定された面談報告書を抽出するSQLを実行する
	 * @param eventId 抽出対象の報告書IDのInteger
	 * @return reportMap 抽出結果のMap
	 */
	public Map<String, Object> searchReport(Integer eventId){

		//SQL文作成
		String sql = "SELECT * FROM event, client, employee WHERE "
				   + "event.event_id = ? AND "
				   + "client.client_id = event_client_id AND "
				   + "employee.employee_id = event_entry_employee_id "
				   + "AND event_status != 0";

		//?の箇所を置換するデータの配列を定義
		Object[] param = { eventId };

		//クエリを実行
		Map<String, Object> reportMap = jdbcTemplate.queryForMap(sql, param);

		//取得したデータを返す
		return reportMap;
	}

}
