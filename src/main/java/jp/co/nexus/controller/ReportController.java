/**
 *
 */
package jp.co.nexus.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.nexus.service.ReportService;

/**
 * ReportController.java
 * 報告書管理機能に関するアプリケーション制御を行うクラス
 *
 * @author 中村 美南海
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	ReportService reportService;

	/**
	 * RL-010-010 報告書一覧表示画面遷移
	 * 報告書一覧画面に遷移する
	 */
	@GetMapping("/list")
	public String reportList(Model model) {

		List<Map<String, Object>> list = reportService.searchAll();
		model.addAttribute("report_list", list);

		return "report/report_list";
	}

	/**
	 * RB-010-010 報告書詳細表示画面遷移
	 */
	@GetMapping("/browse")
	public String reportBrowse(@RequestParam("id") Integer eventId, Model model) {

		Map<String, Object> reportMap = reportService.searchReport(eventId);
		model.addAttribute("map", reportMap);

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
