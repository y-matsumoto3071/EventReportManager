package jp.co.nexus.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * EmployeeDao.java
 * 社員管理機能で使用する社員情報の登録・検索・編集・削除に関するSQLを
 * 作成して実行するクラス
 *
 * @author 本間 洋平
 * @version 21/01/02 コメントの追加　担当者：松本雄樹
 *
 */
@Repository
public class EmployeeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

	/**
	 * 社員情報を全件取得してListで返すSQLを実行する
	 * @param なし
	 * @return list 全件取得結果のList
	 */
	public List<Map<String, Object>> searchAll() {

		// SQL文作成
		String sql = "SELECT * from employee";

		// クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		// 取得したリストを返す
		return list;
	}

	/**
	 * 社員情報の削除フラグ=0以外を抽出してListで返すSQLを実行する
	 * @param なし
	 * @return list 削除フラグ=0以外の社員情報抽出結果のList
	 */
	public List<Map<String, Object>> searchActive() {

		// SQL文作成
		String sql = "SELECT * from employee WHERE deleteflg != 0";

		// クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		// 取得したリストを返す
		return list;
	}

	/**
	 * 社員IDで指定された社員情報を抽出するSQLを実行する。
	 * @param employeeId 抽出対象の社員IDのInteger
	 * @return emp 抽出結果のMap
	 */
    public Map<String, Object> searchEmployee(Integer employeeId) {

        // SQL文作成
        String sql = "SELECT * FROM employee WHERE employee_id= ?";

        // ？の箇所を置換するデータの配列を定義
        Object[] param = {employeeId};

        // クエリを実行
        Map<String, Object> emp = jdbcTemplate.queryForMap(sql, param);

        // 取得したデータを返す
        return emp;
    }

	/**
	 * 指定された社員情報を論理削除するSQLを実行する
	 * @param e_id 削除対象の社員IDのString配列
	 * @return result 削除件数
	 */
	public int deleteEmployee(String[] e_id) {
		// 削除した件数
		int result = 0;

		for (String id : e_id) {

			// SQL文作成
			String sql = "UPDATE employee SET deleteflg = '0', "
					+ "employee_name = concat(employee_name, '【廃止】')  "
					+ "WHERE employee_id =?";

			// ？の箇所を置換するデータの配列を定義
			Object[] param = { id };

			// クエリを実行
			result += jdbcTemplate.update(sql, param);
		}

		// 実行件数を返す
		return result;
	}

	/**
	 * 社員情報をINSERTするSQLを実行する。
	 * @param e_name INSERTする社員名のString
	 * @param e_category INSERTするカテゴリのString
	 * @return INSERT件数
	 */
	public int registEmployee(String e_name, String e_category) {

		// 実行結果
		int result = 0;

		// SQL文作成
		String sql = "INSERT INTO employee (employee_name, employee_category, deleteflg) VALUES (?, ?, 1)";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { e_name,e_category };

		// クエリを実行
		jdbcTemplate.update(sql, param);

		// 実行結果を返す
		return result;
	}

	/**
	 * 登録された社員情報をUPDATEするSQLを実行する
	 * @param e_name UPDATE後の社員名のString
	 * @param e_category UPDATEするカテゴリのString
	 * @param e_id UPDATE対象の社員IDのString
	 */
	public int editEmployee(String e_name, String e_category, String e_id) {
		// 編集した件数
		int result = 0;

		// SQL文作成
		String sql = "UPDATE employee SET employee_name = ?, employee_category=? WHERE employee_id =?";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { e_name,e_category,e_id };

		// クエリを実行
		result += jdbcTemplate.update(sql, param);

		// 実行件数を返す
		return result;
	}

}

