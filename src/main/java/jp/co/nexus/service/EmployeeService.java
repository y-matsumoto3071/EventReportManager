package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nexus.repository.EmployeeDao;
import jp.co.nexus.repository.ReportDao;

/**
 * EmployeetService.java
 * 社員管理機能で使用する社員情報の登録・検索・編集・削除に関する処理を
 * EmployeeDaoクラスからEmployeeControllerクラスに提供する
 *
 * @author 本間 洋平
 * @version 21/01/02 コメントの追加　担当者：松本雄樹
 *
 */
@Service
public class EmployeeService{

	@Autowired
	ReportDao reportDao;

	@Autowired
	EmployeeDao employeeDao;

	/**
	 * 社員情報を全件取得してListで返す。
	 * @param なし
	 * @return list 社員情報全件取得結果のList
	 */
	public List<Map<String, Object>> searchAll() {
		List<Map<String, Object>> list = employeeDao.searchAll();
		return list;
	}

	/**
	 * 社員情報の削除フラグ=0以外を抽出してListで返す。
	 * @param なし
	 * @return list 削除フラグ=0以外の社員情報抽出結果のList
	 */
	public List<Map<String, Object>> searchActive() {
		List<Map<String, Object>> list = employeeDao.searchActive();
		return list;
	}

	/**
	 * 指定された社員情報を論理削除する。
	 * @param e_id 削除対象の社員IDのString配列
	 * @return 削除件数
	 */
    public int deleteEmployee(String[] e_id) {
        int result = employeeDao.deleteEmployee(e_id);
        return result;
    }

	/**
	 * 社員情報を新規登録する。
	 * @param e_name 新規登録する社員名のString
	 * @param e_category 新規登録するカテゴリのString
	 * @return 登録件数
	 */
    public int registEmployee(String e_name, String e_category) {
    	int result = employeeDao.registEmployee(e_name,e_category);
    	return result;
    }

	/**
	 * 登録された社員情報を編集する。
	 * @param c_name 編集後の社員名のString
	 * @param e_category 編集後のカテゴリのString
	 * @param c_id 編集対象の社員IDのString
	 */
    public void editEmployee(String e_name, String e_category, String e_id) {
    	employeeDao.editEmployee(e_name,e_category,e_id);
    }

	/**
	 * 社員IDで指定された社員情報を返す。
	 * @param employeeId 抽出対象の社員IDのInteger
	 * @return emp 抽出結果のMap
	 */
    public Map<String, Object> searchEmployee(Integer employeeId){
    	Map<String, Object> emp = employeeDao.searchEmployee(employeeId);
    	return emp;
    }

}