/**
 *
 */
package jp.co.nexus.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.nexus.model.Report;
import jp.co.nexus.service.ClientService;
import jp.co.nexus.service.EmployeeService;
import jp.co.nexus.service.PasswordService;
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

	@Autowired
	ClientService clientService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PasswordService passwordService;

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
	 * 報告書詳細画面に遷移する
	 */
	@GetMapping("/browse")
	public String reportBrowse(@RequestParam("id") Integer eventId, Model model) {

		Map<String, Object> reportMap = reportService.searchReport(eventId);
		model.addAttribute("map", reportMap);

		return "report/report_browse";
	}

	/**
	 * RC-010-010 報告書新規登録画面遷移
	 * 報告書新規登録画面に遷移する
	 */
	@GetMapping("/create")
	public String reportCreate(@ModelAttribute Report report, Model model) {

		//論理削除を除く顧客データ取得
		List<Map<String, Object>> clientList = clientService.searchActive();
		model.addAttribute("client_list", clientList);

		//論理削除を除く社員データ取得
		List<Map<String, Object>> employeeList = employeeService.searchActive();
		model.addAttribute("employee_list", employeeList);

		return "report/report_create";
	}

	/**
	 * RC-010-020 新規登録入力内容妥当性判定1
	 * 各項目エラーチェック
	 */
	@PostMapping("/create")
	public String reportCreate(@ModelAttribute Report report, Model model,
							   RedirectAttributes attr) {
		//returnで返す画面を格納する変数
		String res = "";

		//未入力チェック
		if(report.getCreateEmployeeId().isEmpty() ||
		   report.getCcgId().isEmpty() ||
		   report.getClientId().isEmpty()) {

			attr.addFlashAttribute("message", "未入力の項目があります。");
			res = "redirect:/report/create";
		}else {
			//入力された各値が存在（有効）IDであるかDBに問合せ
			String clientResult = reportService.searchClient(report.getClientId());
			String ccgResult = reportService.searchEmployee(report.getCcgId());
			String createResult = reportService.searchEmployee(report.getCreateEmployeeId());

			if(clientResult.equals("0") || ccgResult.equals("0") || createResult.equals("0")) {
				attr.addFlashAttribute("message", "適切な数値を入力してください。");
				res = "redirect:/report/create";
			}else {
				report.setClientName(clientResult);
				report.setCreateEmployee(createResult);
				res = "redirect:/report/edit";
			}
		}
		//reportオブジェクトをフラッシュスコープに保存
		attr.addFlashAttribute("report", report);

		return res;
	}

	/**
	 * RC-010-020 新規登録入力内容妥当性判定1
	 * 入力内容判定がOKだった場合の画面遷移先
	 *
	 * RE-010-010 報告書編集画面遷移
	 * DB登録済みの状態区分=1の指定された報告書IDの編集画面を表示する
	 */
	@GetMapping("/edit")
	public String reportEdit(@ModelAttribute Report report,
							 @RequestParam(name = "id", defaultValue = "") Integer r_id,
							 Model model) {
		//returnで返す画面を格納する変数
		String res = "report/report_edit";

		//報告日（作成日）を取得
		LocalDate createDate = LocalDate.now();

		//新規登録時
		if(r_id == null) {
			report.setCreateDate(String.valueOf(createDate));
		//編集時
		}else {
			report = reportService.searchEditReport(r_id);
			if(report == null) {
				//不正パラメータ入力時は一覧表示画面にリダイレクト
				res = "redirect:/report/list";
			}else {
				model.addAttribute("report", report);
				report.setCreateDate(String.valueOf(createDate));
			}
		}

		return res;
	}

	/**
	 * RC-020-010 新規登録入力内容妥当性判定2
	 * RE-010-020 編集内容妥当性判定
	 * 各項目エラーチェック
	 */
	@PostMapping("/edit")
	public String reportEdit(@ModelAttribute Report report, RedirectAttributes attr) {

		//フォームをフラッシュスコープに保存
		attr.addFlashAttribute("report", report);

		//returnで返す画面を格納する変数
		String res = "";

		//未入力チェック
		if(report.getEventDate().isEmpty() ||
		   report.getEventStartTime().isEmpty() ||
		   report.getEventEndTime().isEmpty() ||
		   report.getContactName().isEmpty() ||
		   report.getEventMember().isEmpty() ||
		   report.getEventLocation().isEmpty() ||
		   report.getEventProject().isEmpty() ||
		   report.getEventSession().isEmpty() ||
		   report.getEventReport().isEmpty() ||
		   report.getEventFeedbackByCCG().isEmpty()) {

			attr.addFlashAttribute("message", "未入力の項目があります。");
			res = "redirect:/report/edit";
		}else {
			res = "redirect:/report/confirm";
		}
		return res;
	}

	/**
	 * RC-020-010 新規登録入力内容妥当性判定2
	 * RE-010-020 編集内容妥当性判定
	 * 入力内容判定がOKだった場合の画面遷移先
	 */
	@GetMapping("/confirm")
	public String reportConfirm() {
		return "report/report_confirm";
	}

	/**
	 * RC-030-010 報告書入力内容DB登録
	 * RE-010-030 報告書編集内容DB登録
	 */
	@PostMapping("/confirm")
	public String reportConfirm(@ModelAttribute Report report, RedirectAttributes attr) {

		//登録または更新後の遷移先詳細表示URLのIDを格納する変数
		String eventId;

		if(report.getEventId().isEmpty()) {
			//DB登録処理
			String message = reportService.registReport(report);
			attr.addFlashAttribute("message", message);

			//最後に登録された報告書IDを取得
			eventId = reportService.searchLastReport();
		}else {
			//報告書更新
			String message = reportService.updateReport(report);
			attr.addFlashAttribute("message", message);
			eventId = report.getEventId();
		}

		return "redirect:/report/browse?id="+eventId;
	}

	/**
	 *RD-010-010 報告書削除処理
	 */
	@PostMapping(value = "/list", params = "delete")
	public String reportDelete(@RequestParam(name = "selectCheck", required = false) String[] r_id,
							   RedirectAttributes attr) {
		//未入力チェック
		if(r_id == null) {
			attr.addFlashAttribute("message", "削除する報告書を選択してください。");
		}else {
			//論理削除実行
			int result = reportService.reportDelete(r_id);
			attr.addFlashAttribute("message", result+"件削除しました。");
		}

		return "redirect:/report/list";
	}

	/**
	 * RR-010-010 報告書所見返信
	 */
	@PostMapping("/findings")
	public String findings(@RequestParam("eventId") String r_id,
						   @RequestParam("passwordId") String passId,
						   @RequestParam("passwordBody") String pass,
						   @RequestParam("eventFeedbackContent") String feedback,
						   @RequestParam("approvalResult") String approval,
						   RedirectAttributes attr) {
		//リダイレクト後に表示するメッセージを格納する変数
		String message;

		//未入力チェック
		if(passId.isEmpty() || pass.isEmpty() || feedback.isEmpty()) {
			message = "未入力の項目があります。";
		}else {
			//有効パスワードチェック
			message = passwordService.matchPassword(passId, pass);

			//パスワードチェックがOKだった場合更新処理を実行
			if(message.equals("OK")) {
				message = reportService.findings(r_id, passId, feedback, approval);
			}
		}
		//メッセージをスコープに保存
		attr.addFlashAttribute("message", message);

		//エラーでリダイレクト時は各項目の値をスコープに保存
		if(!message.equals("更新が完了しました。")) {
			attr.addFlashAttribute("passwordId", passId);
			attr.addFlashAttribute("passwordBody", pass);
			attr.addFlashAttribute("eventFeedbackContent", feedback);
		}

		return "redirect:/report/browse?id="+r_id;
	}

}
