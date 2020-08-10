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
 * @author Yuki Matsumoto
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

	//顧客マスタ一覧表示
	@GetMapping("/list")
	public String start(Model model) {

		List<Map<String, Object>> list = clientService.searchAll();
		model.addAttribute("client_list", list);

		//編集に使ったセッションを削除
		session.removeAttribute("c_id");

		return "client/client_list";
	}

	/*
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

		List<Map<String, Object>> list = passwordService.searchPassword();
		String active_pw = list.get(0).get("password_body").toString();

		if (adminPW.equals("")) {
			attr.addFlashAttribute("Result", "パスワードを入力してください。");
			return "redirect:/client/list";
		} else if (c_id == null) {
			attr.addFlashAttribute("Result", "削除する顧客を選択してください。");
			return "redirect:/client/list";
		} else if (adminPW.equals(active_pw)) {
			//論理削除
			int result = clientService.deleteClient(c_id);
			attr.addFlashAttribute("Result", result + "件削除しました。");
		} else {
			attr.addFlashAttribute("Result", "パスワードが間違っています。");
			return "redirect:/client/list";
		}

		// 面談報告書一覧画面に遷移
		return "redirect:/client/list";
	}

	//登録・編集画面の表示
	@GetMapping("/edit")
	public String create() {
		return "client/client_edit";
	}

	//登録・編集時に実行する
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

	//一覧から顧客名を選択したときに実行する（編集）
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
