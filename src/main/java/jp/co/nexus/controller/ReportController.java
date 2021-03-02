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
		//各項目をフラッシュスコープに保存
		attr.addFlashAttribute("createEmployeeId", report.getCreateEmployeeId());
		attr.addFlashAttribute("ccgId", report.getCcgId());
		attr.addFlashAttribute("clientId", report.getClientId());

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
				attr.addFlashAttribute("clientName", clientResult);
				attr.addFlashAttribute("createEmployee", createResult);
				res = "redirect:/report/edit";
			}
		}
		return res;
	}

	/**
	 * RC-010-020 新規登録入力内容妥当性判定1
	 * 入力内容判定がOKだった場合の画面遷移先
	 */
	@GetMapping("/edit")
	public String reportEdit(@ModelAttribute Report report,Model model) {

		//報告日（作成日）を取得し、スコープに保存
		LocalDate createDate = LocalDate.now();
		model.addAttribute("createDate", createDate);

		return "report/report_edit";
	}

	/**
	 * RC-020-010 新規登録入力内容妥当性判定2
	 * 各項目エラーチェック
	 */
	@PostMapping("/edit")
	public String reportEdit(@ModelAttribute Report report, RedirectAttributes attr) {

		//各項目をフラッシュスコープに保存
		attr.addFlashAttribute("clientId", report.getClientId());
		attr.addFlashAttribute("ccgId", report.getCcgId());
		attr.addFlashAttribute("createEmployeeId", report.getCreateEmployeeId());
		attr.addFlashAttribute("eventDate", report.getEventDate());
		attr.addFlashAttribute("eventStartTime", report.getEventStartTime());
		attr.addFlashAttribute("eventEndTime", report.getEventEndTime());
		attr.addFlashAttribute("createDate", report.getCreateDate());
		attr.addFlashAttribute("createEmployee", report.getCreateEmployee());
		attr.addFlashAttribute("clientName", report.getClientName());
		attr.addFlashAttribute("contactName", report.getContactName());
		attr.addFlashAttribute("eventMember", report.getEventMember());
		attr.addFlashAttribute("eventLocation", report.getEventLocation());
		attr.addFlashAttribute("eventProject", report.getEventProject());
		attr.addFlashAttribute("eventSession", report.getEventSession());
		attr.addFlashAttribute("eventReport", report.getEventReport());
		attr.addFlashAttribute("eventFeedbackByCCG", report.getEventFeedbackByCCG());

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
	 * 入力内容判定がOKだった場合の画面遷移先
	 */
	@GetMapping("/confirm")
	public String reportConfirm() {
		return "report/report_confirm";
	}

	/**
	 * RC-030-010 報告書入力内容DB登録
	 */
	@PostMapping("/confirm")
	public String reportConfirm(@ModelAttribute Report report, RedirectAttributes attr) {

		//DB登録処理
		String message = reportService.registReport(report.getCcgId(),
												    report.getEventDate(),
												    report.getEventStartTime(),
												    report.getEventEndTime(),
												    report.getCreateDate(),
												    report.getClientId(),
												    report.getContactName(),
												    report.getEventMember(),
												    report.getEventLocation(),
												    report.getEventProject(),
												    report.getEventSession(),
												    report.getEventReport(),
												    report.getEventFeedbackByCCG(),
												    report.getCreateEmployeeId());
		attr.addFlashAttribute("message", message);

		return "redirect:/report/list";
	}

}
