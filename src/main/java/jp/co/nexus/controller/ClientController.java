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

import jp.co.nexus.service.ClientService;
import jp.co.nexus.service.PasswordService;

/**
 * ClientController.java
 * 顧客情報管理機能に関するアプリケーション制御を行うクラス
 *
 * @author 本間 洋平
 * @version 21/01/01 コメントの追加　担当者：松本雄樹
 *
 */
@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	ClientService clientService;

	@Autowired
	HttpSession session;

	@Autowired
	PasswordService passwordService;

	/**
	 * CL-010-010 顧客一覧画面遷移
	 * 顧客情報一覧画面に遷移する
	 */
	@GetMapping("/list")
	public String start(Model model) {

		// 画面遷移先を顧客情報一覧画面に指定
		String res = "client/client_list";

		List<Map<String, Object>> list = clientService.searchAll();
		model.addAttribute("client_list", list);

		//編集に使ったセッションを削除
		session.removeAttribute("c_id");

		return res;
	}

	/**
	 * CD-010-010 顧客削除処理
	 * ★論理削除時に実行
	 * 以下の時はエラーを発出
	 * ・パスワード未入力
	 * ・チェックボックス未選択
	 * ・パスワードご入力
	 */
	@PostMapping("/list")
	public String deleteClient(@RequestParam(name = "selectCheck", required = false) String[] c_id,
			@RequestParam(name = "adminPW", defaultValue = "") String adminPW,
			RedirectAttributes attr) {

		// 画面遷移先を顧客情報一覧画面へのリダイレクトに指定
		String res = "redirect:/client/list";

		// エラーメッセージを格納する変数をインスタンス化
		String attributeValue = new String();

		List<Map<String, Object>> list = passwordService.searchPassword();
		String active_pw = list.get(0).get("password_body").toString();

		// パスワードが未入力の場合
		if (adminPW.equals("")) {
			attributeValue = "パスワードを入力してください。";

		// 削除対象が選択されていない場合
		} else if (c_id == null) {
			attributeValue = "削除する顧客を選択してください。";

		// 正規入力されている場合
		} else if (adminPW.equals(active_pw)) {
			//論理削除実行
			int result = clientService.deleteClient(c_id);
			attributeValue = result + "件削除しました。";

		// 上記条件に合致しない場合、パスワード誤入力と判定
		} else {
			attributeValue = "パスワードが間違っています。";

		}

		attr.addFlashAttribute("Result", attributeValue);

		return res;
	}

	/**
	 * CC-010-010 顧客情報登録画面遷移
	 */
	@GetMapping("/edit")
	public String create() {

		// 画面遷移先を顧客情報登録画面に指定
		String res = "client/client_edit";

		return res;
	}

	/**
	 * CC-010-020_顧客新規登録内容DB登録
	 * CE-010-020_顧客編集内容DB登録
	 */
	@PostMapping("/edit")
	public String createClient(@RequestParam("c_Name") String c_name,
			@RequestParam(name = "client_id", defaultValue = "") String c_id,
			Model model,
			RedirectAttributes attr) {

		//顧客名が重複していると発生するDB例外のための例外処理
		try {
			if (c_name.equals("")) {
				attr.addFlashAttribute("Result", "名前を入力してください。");
				return "redirect:/client/edit";
			}

			//編集分岐点
			if (!(c_id.equals(""))) {
				clientService.editClient(c_name, c_id);
				attr.addFlashAttribute("Result", "編集が完了しました。");

				//編集に使ったセッションを削除
				session.removeAttribute("c_id");
				return "redirect:/client/list";
			}

			//面談報告書1件登録
			clientService.registClient(c_name);

			//フラッシュスコープに完了メッセージを設定
			attr.addFlashAttribute("Result", "登録が完了しました。");
		} catch (Exception e) {
			attr.addFlashAttribute("Result", "社名が重複しています。");
			return "redirect:/client/edit";
		}
		return "redirect:/client/list";
	}

	/**
	 * CE-010-010 顧客編集画面遷移
	 */
	@GetMapping("/edit/{client_id}")
	public String editClient(@PathVariable("client_id") Integer c_id,
			Model model) {

		Map<String, Object> clt = clientService.searchClient(c_id);

		//編集画面に顧客名を表示
		model.addAttribute("client_name", clt.get("client_name"));

		//編集に利用
		session.setAttribute("c_id", c_id);

		return "client/client_edit";
	}

}
