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
 * @author Yuki Matsumoto
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

	/*
	 * ★論理削除時に実行
	 * 以下の時はエラーを発出
	 * ・パスワード未入力
	 * ・チェックボックス未選択
	 * ・パスワードご入力
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
			//論理削除
			int result = employeeService.deleteEmployee(e_id);
			attr.addFlashAttribute("Result", result + "件削除しました。");
		} else {
			attr.addFlashAttribute("Result", "パスワードが間違っています。");
			return "redirect:/employee/list";
		}

		// 面談報告書一覧画面に遷移
		return "redirect:/employee/list";
	}

	@GetMapping("/edit")
	public String employeeEdit() {
		return "employee/employee_edit";
	}

	//登録・編集時に実行する
	@PostMapping("/edit")
	public String createEmployee(@RequestParam(name="e_name", defaultValue = "") String e_name,
			@RequestParam(name="e_group", defaultValue = "") String e_group,
			@RequestParam(name="e_team", defaultValue = "") String e_team,
			@RequestParam(name = "employee_id", defaultValue = "") String e_id,
			Model model,
			RedirectAttributes attr) {

		//社員名が重複していると発生するDB例外のための例外処理
		try {
			if (e_name.equals("")) {
				attr.addFlashAttribute("Result", "名前を入力してください。");
				return "redirect:/employee/edit";
			}

			String e_category = e_group + e_team;

			//★カテゴリ組み合わせチェック
			int check = ecm.categoryCheck(e_category);
			if(check==1) {
				attr.addFlashAttribute("Result", "適切な組み合わせを選んでください。");
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

			attr.addFlashAttribute("Result", "社名が重複しています。");
			return "redirect:/employee/edit";

		}
		return "redirect:/employee/list";
	}

	//一覧から社員名を選択したときに実行する（編集）
	@GetMapping("/edit/{employee_id}")
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
