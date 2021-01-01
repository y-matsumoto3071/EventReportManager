package jp.co.nexus.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ClientDao.java
 * 顧客管理機能で使用する顧客情報の登録・検索・編集・削除に関するSQLを
 * 作成して実行するクラス
 *
 * @author 本間 洋平
 * @version 21/01/01 コメントの追加　担当者：松本雄樹
 *
 */
@Repository
public class ClientDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 顧客情報を全検索してListで返すSQLを実行する
	 * @param なし
	 * @return list 全検索結果のList
	 */
	public List<Map<String, Object>> searchAll() {

		// SQL文作成
		String sql = "SELECT * from client";

		// クエリを実行
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		// 取得したリストを返す
		return list;
	}

	/**
	 * 指定された顧客情報を論理削除するSQLを実行する
	 * @param c_id 削除対象の顧客IDのString配列
	 * @return 削除件数
	 */
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

	/**
	 * 指定された顧客情報を抽出するSQLを実行する。
	 * @param clientId 抽出対象の顧客IDのInteger
	 * @return clt 抽出結果のMap
	 */
	public Map<String, Object> searchClient(Integer clientId) {

		// SQL文作成
		String sql = "SELECT * FROM client WHERE client_id= ?";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { clientId };

		// クエリを実行
		Map<String, Object> clt = jdbcTemplate.queryForMap(sql, param);

		// 取得したデータを返す
		return clt;
	}

	/**
	 * 指定された顧客情報を抽出するSQLを実行する。
	 * @param search 抽出対象の顧客名のString
	 * @return list 抽出結果のList
	 */
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

	/**
	 * 顧客情報を新規登録するSQLを実行する。
	 * @param c_name 登録する顧客名のString
	 * @return 登録件数
	 */
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

	/**
	 * 登録された顧客情報を編集するSQLを実行する
	 * @param c_name 編集後の顧客名のString
	 * @param c_id 編集対象の顧客IDのString
	 */
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
