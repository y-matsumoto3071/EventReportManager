/**
 *
 */
package jp.co.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.nexus.model.Report;
import jp.co.nexus.service.ReportService;

/**
 * ReportController.java
 * 報告書管理機能に関するアプリケーション制御を行うクラス
 *
 * @author 氏名を記載すること
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	ReportService reportService;

	@GetMapping("/list")
	public String reportList() {
		return "report/report_list";
	}

	/**
	 * RB-010-010 報告書詳細表示画面遷移
	 * 報告書詳細画面に遷移する
	 */
	@GetMapping("/browse")
	public String reportBrowse(@RequestParam("event_id") Integer eventId, Model model) {

		Report report = reportService.searchReport(eventId);
		model.addAttribute("report", report);

		return "report/report_browse";
	}
	@GetMapping("/create")
	public String reportCreate() {
		return "report/report_create";
	}

	@GetMapping("/edit")
	public String reportEdit() {
		return "report/report_edit";
	}

	@GetMapping("/confirm")
	public String reportConfirm() {
		return "report/report_confirm";
	}

}
