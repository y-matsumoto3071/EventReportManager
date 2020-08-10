package jp.co.nexus.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	//全検索する
	public List<Map<String, Object>> searchAll() {

		// SQL文作成
		String sql = "SELECT * from client";

		// クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		// 取得したリストを返す
		return list;
	}

	//論理削除する
	public int deleteClient(String[] c_id) {
		// 削除した件数
		int result = 0;

		for (String id : c_id) {

			// SQL文作成
			String sql = "UPDATE client SET deleteflg = '0', "
					+ "client_name = concat(client_name, '【廃止】')  "
					+ "WHERE client_id =?";

			// ？の箇所を置換するデータの配列を定義
			Object[] param = { id };

			// クエリを実行
			result += jdbcTemplate.update(sql, param);
		}

		// 実行件数を返す
		return result;
	}

	// 顧客情報をIDで検索する
	public Map<String, Object> searchClient(Integer clientId) {

		// SQL文作成
		String sql = "SELECT * FROM client WHERE client_id= ?";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { clientId };

		// クエリを実行
		Map<String, Object> user = jdbcTemplate.queryForMap(sql, param);

		// 取得したデータを返す
		return user;
	}

	// 顧客情報を名前で検索する
	public List<Map<String, Object>> searchClientList(String search) {
		System.out.println("aadfadfa:" + search);

		// SQL文作成
		String sql = "SELECT * FROM client WHERE client_name LIKE ?";

		// ? の箇所を置換するデータの配列を定義
		Object[] param = { "%" + search + "%" };

		// クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, param);

		// 取得したリストを返す
		return list;
	}

	//顧客登録用
	public int registClient(String c_name) {

		// 実行結果
		int result = 0;

		// SQL文作成
		String sql = "INSERT INTO client (client_name, deleteflg) VALUES (?, 1)";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { c_name };

		// クエリを実行
		jdbcTemplate.update(sql, param);

		// 実行結果を返す
		return result;
	}

	//編集する
	public int editClient(String c_name, String c_id) {
		// 編集した件数
		int result = 0;

		// SQL文作成
		String sql = "UPDATE client SET client_name = ? WHERE client_id =?";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { c_name,c_id };

		// クエリを実行
		result += jdbcTemplate.update(sql, param);

		// 実行件数を返す
		return result;
	}
}
