/**
 *
 */
package jp.co.nexus.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.nexus.model.EmployeeCategoryModel;
import jp.co.nexus.service.EmployeeService;
import jp.co.nexus.service.PasswordService;

/**
 * EmployeeController.java
 * 社員情報管理機能に関するアプリケーション制御を行うクラス
 *
 * @author 本間 洋平
 * @version 21/01/02 コメントの追加　担当者：松本雄樹
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	HttpSession session;

	@Autowired
	PasswordService passwordService;

	EmployeeCategoryModel ecm = new EmployeeCategoryModel();

	/**
	 * EL-010-010 社員一覧画面遷移
	 * 社員情報一覧画面に遷移する
	 */
	@GetMapping("/list")
	public String employeeList(Model model) {

		List<Map<String, Object>> list = employeeService.searchAll();

		/*
		 * ★listの各employe_categoryを表示用の文字列に変換する
		 * lsはlistの各要素の参照変数であるため、この処理によってlistの値も更新される
		 */
		for(Map<String, Object> ls : list) {
			String ctg = ls.get("employee_category").toString();
			ls.replace("employee_category", ecm.categorySet(ctg));
		}

		model.addAttribute("employee_list", list);

		//編集に使ったセッションを削除
		session.removeAttribute("e_id");

		return "employee/employee_list";
	}

	/**
	 * ED-010-010 社員削除処理
	 * ★論理削除時に実行
	 * 以下の時はエラーを発出
	 * ・パスワード未入力
	 * ・チェックボックス未選択
	 * ・パスワード誤入力
	 */
	@PostMapping("/list")
	public String deleteEmployee(@RequestParam(name = "selectCheck", required = false) String[] e_id,
			@RequestParam(name = "adminPW", defaultValue = "") String adminPW,
			RedirectAttributes attr) {

		List<Map<String, Object>> list = passwordService.searchPassword();
		String active_pw = list.get(0).get("password_body").toString();

		if (adminPW.equals("")) {
			attr.addFlashAttribute("Result", "パスワードを入力してください。");
			return "redirect:/employee/list";
		} else if(e_id == null){
			attr.addFlashAttribute("Result", "削除する社員を選択してください。");
			return "redirect:/employee/list";
		} else if (adminPW.equals(active_pw)) {
			//★論理削除を実行
			int result = employeeService.deleteEmployee(e_id);
			attr.addFlashAttribute("Result", result + "件削除しました。");
		} else {
			attr.addFlashAttribute("Result", "パスワードが間違っています。");
			return "redirect:/employee/list";
		}

		//社員一覧画面に遷移
		return "redirect:/employee/list";
	}

	/**
	 * EE-010-010 社員新規登録画面遷移
	 */
	@GetMapping("/edit")
	public String employeeEdit() {
		return "employee/employee_edit";
	}

	/**
	 * EC-010-020 社員新規登録内容DB登録
	 * EE-010-020 社員情報編集内容DB登録
	 */
	@PostMapping("/edit")
	public String createEmployee(@RequestParam(name="e_name", defaultValue = "") String e_name,
			@RequestParam(name="e_group", defaultValue = "") String e_group,
			@RequestParam(name="e_team", defaultValue = "") String e_team,
			@RequestParam(name = "employee_id", defaultValue = "") String e_id,
			Model model,
			RedirectAttributes attr) {

		//社員名が重複していると発生するExceptionのための例外処理
		try {
			if (e_name.equals("")) {
				attr.addFlashAttribute("Result", "名前を入力してください。");
				return "redirect:/employee/edit";
			}

			String e_category = e_group + e_team;

			//★カテゴリ組み合わせチェック
			int check = ecm.categoryCheck(e_category);
			if(check==1) {
				attr.addFlashAttribute("Result", "適切な組み合わせを選んでください。<br>CCG : 〇〇エリア、SCG : 〇〇チーム");
				return "redirect:/employee/edit";
			}

			//編集分岐点
			if (!(e_id.equals(""))) {
				employeeService.editEmployee(e_name, e_category, e_id);
				attr.addFlashAttribute("Result", "編集が完了しました。");

				//編集に使ったセッションを削除
				session.removeAttribute("e_id");
				return "redirect:/employee/list";
			}

			//社員1件登録
			employeeService.registEmployee(e_name,e_category);

			//フラッシュスコープに完了メッセージを設定
			attr.addFlashAttribute("Result", "登録が完了しました。");

		} catch (Exception e) {
			e.printStackTrace();

			attr.addFlashAttribute("Result", "社員名が重複しています。");
			return "redirect:/employee/edit";

		}
		return "redirect:/employee/list";
	}

	/**
	 * EE-010-020_社員編集画面遷移
	 * @param e_id 編集対象の社員ID
	 */
	@GetMapping("/edit{employee_id}")
	public String editEmployee(@PathVariable("employee_id") Integer e_id,
			Model model) {

		//選択された社員の情報を検索
		Map<String, Object> emp = employeeService.searchEmployee(e_id);

		//編集画面に社員名を表示
		model.addAttribute("employee_name", emp.get("employee_name"));

		//社員の所属をスコープに保存
		String ec = emp.get("employee_category").toString();
		model.addAttribute("employee_category", ec);

		//編集に利用
		session.setAttribute("e_id", e_id);

		return "employee/employee_edit";
	}

}
