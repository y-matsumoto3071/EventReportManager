/**
 *
 */
package jp.co.nexus.service;

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
 * @author 氏名を記載すること
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
	 * 報告書IDで指定された報告書情報を返す
	 * @param eventId 抽出対象の報告書IDのInteger
	 * @return report 抽出対象のreport
	 */
	public Report searchReport(Integer eventId) {
		Report report = new Report();
		Map<String, Object> reportMap = dao.searchReport(eventId);

		report.setEntity(reportMap);

		return report;

		}

}
