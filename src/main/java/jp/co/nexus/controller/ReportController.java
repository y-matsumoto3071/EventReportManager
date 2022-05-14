/**
 *
 */
package jp.co.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	/**
	 * モックアップ確認用。実装後は削除してOK
	 */
	@GetMapping("/create")
	public String reportCreate() {
		return "report/report_create";
	}

	/**
	 * モックアップ確認用。実装後は削除してOK
	 */
	@GetMapping("/edit")
	public String reportEdit() {
		return "report/report_edit";
	}

	/**
	 * モックアップ確認用。実装後は削除してOK
	 */
	@GetMapping("/confirm")
	public String reportConfirm() {
		return "report/report_confirm";
	}

	/**
	 * RC-030-010 報告書入力内容DB登録
	 * 報告書確認画面にて「登録」ボタン押下後の処理
	 * 報告書の入力内容をDB登録後、登録した報告書の詳細表示画面に遷移
	 * （ヒントとして枠のみ作成。中身は自分で実装してみましょう！）
	 */
	@PostMapping("/regist")
	public String reportRegist() {
		//登録または更新後の遷移先詳細表示URLのIDを格納する変数
		String eventId = "1";
		
		return "redirect:/report/browse?event_id=" + eventId;
	}

}
