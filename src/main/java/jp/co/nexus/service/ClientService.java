package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nexus.repository.ClientDao;
import jp.co.nexus.repository.EmployeeDao;
import jp.co.nexus.repository.ReportDao;

/**
 * ClientService.java
 * 顧客管理機能で使用する顧客情報の登録・検索・編集・削除に関する処理を
 * ClientDaoクラスからClientControllerクラスに提供する
 *
 * @author 本間 洋平
 * @version 21/01/01 コメントの追加　担当者：松本雄樹
 *
 */
@Service
public class ClientService{

	@Autowired
	ReportDao reportDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	ClientDao clientDao;

	/**
	 * 顧客情報を全検索してListで返す。
	 * @param なし
	 * @return list 全検索結果のList
	 */
	public List<Map<String, Object>> searchAll() {
		List<Map<String, Object>> list = clientDao.searchAll();
		return list;
	}

	/**
	 * 指定された顧客情報を論理削除する。
	 * @param c_id 削除対象の顧客IDのString配列
	 * @return 削除件数
	 */
    public int deleteClient(String[] c_id) {
        int result = clientDao.deleteClient(c_id);
        return result;
    }

	/**
	 * 顧客情報を新規登録する。
	 * @param c_name 登録する顧客名のString
	 * @return 登録件数
	 */
    public int registClient(String c_name) {
    	int result = clientDao.registClient(c_name);
    	return result;
    }

	/**
	 * 登録された顧客情報を編集する。
	 * @param c_name 編集後の顧客名のString
	 * @param c_id 編集対象の顧客IDのString
	 */
    public void editClient(String c_name, String c_id) {
    	clientDao.editClient(c_name,c_id);
    }

	/**
	 * 指定された顧客情報を返す。
	 * @param clientId 抽出対象の顧客IDのInteger
	 * @return clt 抽出結果のMap
	 */
    public Map<String, Object> searchClient(Integer clientId){
    	Map<String, Object> clt = clientDao.searchClient(clientId);
    	return clt;
    }



}