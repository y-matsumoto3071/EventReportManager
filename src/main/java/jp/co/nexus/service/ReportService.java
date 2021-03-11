/**
 *
 */
package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jp.co.nexus.model.Report;
import jp.co.nexus.repository.ReportDao;

/**
 * ReportService.java
 * 報告書管理機能で使用する報告書情報の登録・検索・編集・削除に関する処理を
 * ReportDaoクラスからReportControllerクラスに提供する
 *
 * @author 中村 美南海
 *
 */
@Service
public class ReportService {
	private final ReportDao dao;

	@Autowired
	public ReportService(ReportDao dao) {
		this.dao = dao;
	}

	public void save(Report report) {
		dao.insertReport(report);
	}

	/**
	 * 状態区分=0以外の面談報告書を取得してListで返す
	 * @param なし
	 * @return list 取得結果のList
	 */
	public List<Map<String, Object>> searchAll(){
		List<Map<String, Object>> list = dao.searchAll();

		//案件概要文字数制限メソッド呼び出し
		eventProjectNameSet(list);
		return list;
	}

	/**
	 * 報告書一覧表示のHTMLで、案件概要は25文字まで表示可能とし、表示しきれない部分があれば"…"で省略表示
	 * mapの値を更新する
	 * @param list
	 * @return なし
	 */
	public void eventProjectNameSet(List<Map<String, Object>> list) {

		//listから１件ずつMapを取り出し、event_projectを取得
		for(Map<String, Object> map : list) {
			String eventProject = String.valueOf(map.get("event_project"));

			//event_projectの文字数が25文字以上か判定
			if(eventProject.length() > 25) {

				//StringBuilderインスタンスを生成
				StringBuilder eventProjectName = new StringBuilder();

				//event_projectの25文字目までを切り出し
				eventProjectName.append(eventProject.substring(0,25));

				//切り出した文字列に「...」を追加
				eventProjectName.append("…");

				//mapのevent_projectを上書き
				map.put("event_project", eventProjectName);
			}
		}
	}

	/**
	 * 報告書IDで指定された顧客情報を返す
	 * @param eventId 抽出対象の報告書IDのInteger
	 * @return reportMap 抽出対象のMap
	 */
	public Map<String, Object> searchReport(Integer eventId){
		Map<String, Object> reportMap = dao.searchReport(eventId);
		return reportMap;
	}

	/**
	 * 顧客IDで指定された顧客名を返す
	 * @param clientId 抽出対象の顧客IDのInteger
	 * @return result 抽出結果のString
	 */
	public String searchClient(String clientId) {
		String result;
		try {
			Map<String, Object> clientMap = dao.searchClient(clientId);

			//取得したMapから顧客名を取得しキャスト
			result = String.valueOf(clientMap.get("client_name"));

		}catch(EmptyResultDataAccessException e){
			result = "0";
		}
		return result;
	}

	/**
	 * 社員IDで指定された社員名を返す
	 * @param employeeId 抽出対象の社員IDのInteger
	 * @return result 抽出結果のString
	 */
	public String searchEmployee(String employeeId) {
		String result;
		try {
			Map<String, Object> employeeMap = dao.searchEmployee(employeeId);

			//取得したMapから社員名を取得しキャスト
			result = String.valueOf(employeeMap.get("employee_name"));

		}catch(EmptyResultDataAccessException e) {
			result = "0";
		}
		return result;
	}

	/**
	 * 報告書新規登録をする
	 * @param report reportオブジェクト
	 * @return message 登録完了メッセージ
	 */
	public String registReport(Report report) {

		int result = dao.registReport(report);

		//メッセージ文言を格納する変数
		String message = "";

		//daoでreturnされた実行件数が１の場合、正しく登録されているため、登録完了メッセージを代入する
		if(result == 1) {
			message = "登録が完了しました。";
		}

		return message;
	}

	/**
	 * 指定された報告書を論理削除する
	 * @param r_id 削除対象の報告書IDnoString配列
	 * @return result 削除件数
	 */
	public int reportDelete(String[] r_id) {
		int result = dao.reportDelete(r_id);
		return result;
	}

	/**
	 * 最後に登録された報告書IDを取得する
	 * @param なし
	 * @return eventId 取得した報告書ID
	 */
	public String searchLastReport() {
		Map<String, Object> map = dao.searchLastReport();
		String eventId = String.valueOf(map.get("MAX(event_id)"));
		return eventId;
	}


}
