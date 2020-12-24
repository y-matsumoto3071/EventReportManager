package jp.co.nexus.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

	//全検索する
	public List<Map<String, Object>> searchAll() {

		// SQL文作成
		String sql = "SELECT * from employee";

		// クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		// 取得したリストを返す
		return list;
	}

    // 社員情報をIDで検索する
    public Map<String, Object> searchEmployee(Integer employeeId) {

        // SQL文作成
        String sql = "SELECT * FROM employee WHERE employee_id= ?";

        // ？の箇所を置換するデータの配列を定義
        Object[] param = {employeeId};

        // クエリを実行
        Map<String, Object> user = jdbcTemplate.queryForMap(sql, param);

        // 取得したデータを返す
        return user;
    }

	//論理削除する
	public int deleteEmployee(String[] e_id) {
		// 削除した件数
		int result = 0;

		for (String id : e_id) {

			// SQL文作成
			String sql = "UPDATE employee SET employee_status = '0', "
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

	//登録
	public int registEmployee(String e_name, String e_category) {

		// 実行結果
		int result = 0;

		// SQL文作成
		String sql = "INSERT INTO employee (employee_name, employee_category, employee_status) VALUES (?, ?, 1)";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { e_name,e_category };

		// クエリを実行
		jdbcTemplate.update(sql, param);

		// 実行結果を返す
		return result;
	}

	//編集
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

