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
 * @version 21/01/18 バグ修正（EE-010-020_社員編集画面遷移）　担当者：松本雄樹
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PasswordService passwordService;

	EmployeeCategoryModel ecm = new EmployeeCategoryModel();

	/**
	 * EL-010-010 社員一覧画面遷移
	 * 社員情報一覧画面に遷移する
	 */
	@GetMapping("/list")
	public String employeeList(Model model) {

		// 画面遷移先を社員情報一覧画面に指定
		String res = "employee/employee_list";

		List<Map<String, Object>> list = employeeService.searchActive();

		/*
		 * ★listの各employe_categoryを表示用の文字列に変換する
		 * lsはlistの各要素の参照変数であるため、この処理によってlistの値も更新される
		 */
		for(Map<String, Object> ls : list) {
			String ctg = ls.get("employee_category").toString();
			ls.replace("employee_category", ecm.categorySet(ctg));
		}

		model.addAttribute("employee_list", list);

		return res;
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

		// 画面遷移先を社員情報一覧画面へのリダイレクトに指定
		String res = "redirect:/employee/list";

		// エラーメッセージを格納する変数をインスタンス化
		String attributeValue = new String();

		String active_pw = passwordService.getPassword(1);

		// パスワードが未入力の場合
		if (adminPW.equals("")) {
			attributeValue = "パスワードを入力してください。";

		// 削除対象が選択されていない場合
		} else if(e_id == null){
			attributeValue = "削除する社員を選択してください。";

		// 正規入力されている場合
		} else if (adminPW.equals(active_pw)) {
			//★論理削除を実行
			int result = employeeService.deleteEmployee(e_id);
			attributeValue = result + "件削除しました。";

		// 上記条件に合致しない場合、パスワード誤入力と判定
		} else {
			attributeValue = "パスワードが間違っています。";

		}

		attr.addFlashAttribute("Result", attributeValue);

		return res;
	}

	/**
	 * EE-010-010 社員新規登録画面遷移
	 * EE-010-020_社員編集画面遷移
	 * @param e_id 編集対象の社員ID
	 */
	@GetMapping("/edit")
	public String employeeEdit(@RequestParam(name="id", defaultValue = "") Integer e_id,
			Model model) {

		// 画面遷移先を社員情報一覧画面に指定
		String res = "employee/employee_edit";

		// 既存社員情報の編集時判定
		if (e_id != null) {
			//選択された社員の情報を検索
			Map<String, Object> emp = employeeService.searchEmployee(e_id);

			//編集画面に社員名を表示
			model.addAttribute("employee_name", emp.get("employee_name"));

			//社員の所属をスコープに保存
			String ec = emp.get("employee_category").toString();
			model.addAttribute("employee_category", ec);

			//編集に利用する社員IDをスコープに保存
			model.addAttribute("e_id", e_id);
		}
		return res;
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

		// 画面遷移先を社員情報一覧画面に指定
		String res = "redirect:/employee/list";

		// 編集中かどうかを判定するフラグ
		boolean isUpdating = !(e_id.equals(""));

		// エラーメッセージを格納する変数をインスタンス化
		String attributeValue = new String();

		String e_category = e_group + e_team;

		//カテゴリ組み合わせチェック用
		int check = ecm.categoryCheck(e_category);

		// 未入力チェック
		if (e_name.equals("")) {
			attributeValue = "名前を入力してください。";
			if (isUpdating) {
				res = "redirect:/employee/edit?id="+e_id;
			} else {
				res = "redirect:/employee/edit";
			}

		//★カテゴリ組み合わせチェック
		} else if(check==1) {
			attributeValue = "適切な組み合わせを選んでください。";
			if (isUpdating) {
				res = "redirect:/employee/edit?id="+e_id;
			} else {
				res = "redirect:/employee/edit";
			}

		// DB問い合わせ前のエラーチェック通過後の処理
		} else {
			// 編集時はUPDATE、新規登録時はINSERTを実行
			attributeValue = employeeService.registJudge(e_name, e_category, e_id);

			// 社員名が重複している場合はリダイレクト先を編集画面へ指定
			if (attributeValue.equals("社員名が重複しています。")) {
				if (isUpdating) {
					res = "redirect:/employee/edit?id="+e_id;
				} else {
					res = "redirect:/employee/edit";
				}

			}
		}

		attr.addFlashAttribute("Result", attributeValue);
		return res;
	}

}
