/**
 *
 */
package jp.co.nexus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/list")
	public String reportList() {
		return "report/report_list";
	}

	@GetMapping("/browse")
	public String reportBrowse() {
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
