/**
 *
 */
package jp.co.nexus.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	 * 指定された状態区分!=0(論理削除以外)の面談報告書を抽出するSQLを実行する
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

	/**
	 * 指定された状態区分=1(編集可能状態)の面談報告書を抽出するSQLを実行する
	 * @param eventId 抽出対象の報告書IDのInteger
	 * @return reportMap 抽出結果のMap
	 */
	public Map<String, Object> searchEditReport(Integer eventId) throws EmptyResultDataAccessException {

		//SQL文作成
		String sql = "SELECT * FROM event, client, employee WHERE "
				   + "event.event_id = ? AND "
				   + "client.client_id = event_client_id AND "
				   + "employee.employee_id = event_entry_employee_id "
				   + "AND event_status = 1";

		//?の箇所を置換するデータの配列を定義
		Object[] param = { eventId };

		//クエリを実行
		Map<String, Object> reportMap = jdbcTemplate.queryForMap(sql, param);

		//取得したデータを返す
		return reportMap;
	}

	/**
	 * 指定された顧客を抽出するSQLを実行
	 * @param clientId 抽出対象の顧客IDのInteger
	 * @return clientMap 抽出結果のMap
	 * @throws EmptyResultDataAccessException
	 */
	public Map<String, Object> searchClient (String clientId) throws EmptyResultDataAccessException {

		//SQL文作成
		String sql = "SELECT * FROM client WHERE client_id = ? AND deleteflg != 0";

		//?の箇所を置換するデータの配列を定義
		Object[] param = { clientId };

		//クエリを実行
		Map<String, Object> clientMap = jdbcTemplate.queryForMap(sql, param);

		//取得したデータを返す
		return clientMap;
	}

	/**
	 * 指定された社員を抽出するSQLを実行
	 * @param EemployeeId 抽出対象の社員IDのInteger
	 * @return employeetMap 抽出結果のMap
	 * @throws EmptyResultDataAccessException
	 */
	public Map<String, Object> searchEmployee(String employeeId) throws EmptyResultDataAccessException {

		//SQL文作成
		String sql = "SELECT * FROM employee WHERE employee_id = ? AND deleteflg != 0";

		//?の箇所を置換するデータの配列を定義
		Object[] param = { employeeId };

		//クエリを実行
		Map<String, Object> employeeMap = jdbcTemplate.queryForMap(sql, param);

		//取得したデータを返す
		return employeeMap;
	}

	/**
	 * 報告書新規登録をする
	 * @param reort reportオブジェクト
	 * @return result 実行件数を返す
	 */
	public int registReport(Report report) {
		//SQL文作成
		String sql = "INSERT INTO event (event_client_id, 		  event_contact,  event_date,    event_start_time,"
									  + "event_end_time, 		  event_location, event_member,  event_sales_employee_id,"
									  + "event_entry_employee_id, event_project,  event_session, event_report,"
									  + "event_feedback_byccg,    createdate,     event_status)"
									  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

		//?の箇所を置換するデータの配列を定義
		Object[] param = {report.getClientId(),
						  report.getContactName(),
						  report.getEventDate(),
						  report.getEventStartTime(),
						  report.getEventEndTime(),
						  report.getEventLocation(),
						  report.getEventMember(),
						  report.getCcgId(),
						  report.getCreateEmployeeId(),
						  report.getEventProject(),
						  report.getEventSession(),
						  report.getEventReport(),
						  report.getEventFeedbackByCCG(),
						  report.getCreateDate()};

		//クエリを実行
		int result = jdbcTemplate.update(sql, param);

		//実行結果を返す
		return result;
	}

	/**
	 * 報告書編集をする
	 * @param report reportオブジェクト
	 * @return result 実行件数を返す
	 */
	public int updateReport(Report report) {
		//SQL文作成
		String sql = "UPDATE event SET event_contact = ?,     	 event_date = ?,     event_start_time = ?,"
									+ "event_end_time = ?, 	  	 event_location = ?, event_member = ?,"
									+ "event_project = ?,  	  	 event_session = ?,  event_report = ?,"
									+ "event_feedback_byccg = ?, updatedate = ?"
									+ "WHERE event_id = ?";

		//?の箇所を置換するデータの配列を定義
		Object[] param = {report.getContactName(),
						  report.getEventDate(),
						  report.getEventStartTime(),
						  report.getEventEndTime(),
						  report.getEventLocation(),
						  report.getEventMember(),
						  report.getEventProject(),
						  report.getEventSession(),
						  report.getEventReport(),
						  report.getEventFeedbackByCCG(),
						  report.getCreateDate(),
						  report.getEventId()};

		//クエリを実行
		int result = jdbcTemplate.update(sql, param);

		//実行結果を返す
		return result;
	}

	/**
	 * 指定された報告書を論理削除するSQLを実行する
	 * @param r_id 削除対象の報告書IDのString配列
	 * @return result 削除件数
	 */
	public int reportDelete(String[] r_id) {

		//削除した件数
		int result = 0;

		for(String id : r_id) {
			//SQL文作成
			String sql = "UPDATE event SET event_status = '0' WHERE event_id = ?";

			//?の箇所を置換するデータの配列を定義
			Object[] param = { id };

			//クエリを実行
			result += jdbcTemplate.update(sql, param);
		}
		//実行件数を返す
		return result;
	}

	/**
	 * 最後に登録された報告書を取得する
	 * @param なし
	 * @return 取得した報告書のMap
	 */
	public Map<String, Object> searchLastReport() {

		//SQL文作成
		String sql = "SELECT * FROM event WHERE event_id = (SELECT LAST_INSERT_ID())";

		//クエリを実行
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);

		//取得したデータを返す
		return map;
	}

}
