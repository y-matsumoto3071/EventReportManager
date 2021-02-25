/**
 *
 */
package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
		return list;
	}

}
