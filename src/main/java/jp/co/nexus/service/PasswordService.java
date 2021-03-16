package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nexus.repository.PasswordDao;

/**
 *
 * @author 本間 洋平
 * @version 21/01/01 コメントの追加、メソッドの追加 担当者：松本雄樹
 *
 */
@Service
public class PasswordService{

	@Autowired
	PasswordDao passwordDao;

	/**
	 * 適用期間中のパスワード情報を抽出してListを返す
	 * @param なし
	 * @return list 適用期間中のパスワード情報
	 */
	public List<Map<String, Object>> searchPassword() {
		List<Map<String, Object>> list = passwordDao.searchPassword();
		return list;
	}

	/**
	 * 指定したパスワード種別で、適用期間中のパスワードを返す
	 * @param type パスワード種別
	 * @return pass 抽出したパスワードのString
	 */
	public String getPassword(int type) {

		String pass = new String();

		Map<String, Object> map = passwordDao.getPassword(type);
		pass = map.get("password_body").toString();

		// 取得したパスワードを返す
		return pass;
	}

	/**
	 * 報告書所見返信時、入力されたIDとパスワードが適切か判定
	 * @param passId 入力されたIDのString
	 * @param pass 入力されたパスワードのString
	 * @return 判定結果のString
	 */
	public String matchPassword(String passId, String pass) {

		//有効期限内パスワードを全件取得
		List<Map<String, Object>> list = passwordDao.searchPassword();

		//returnで返すString変数
		String message = "管理者IDまたはパスワードが間違っています。";

		for(Map<String, Object> map : list) {
			if(String.valueOf(map.get("password_id")).equals(passId) &&
			   String.valueOf(map.get("password_body")).equals(pass) &&
			   String.valueOf(map.get("password_type")).equals("1")) {
				message = "OK";
				break;
			}
		}
		return message;
	}
}