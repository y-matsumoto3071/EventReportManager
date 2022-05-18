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

import jp.co.nexus.service.ClientService;
import jp.co.nexus.service.PasswordService;

/**
 * ClientController.java
 * 顧客情報管理機能に関するアプリケーション制御を行うクラス
 *
 * @author 本間 洋平
 * @version 21/01/01 コメントの追加　担当者：松本雄樹
 * @version 21/01/18 バグ修正(CE-010-010 顧客編集画面遷移)　担当者：松本雄樹
 *
 */
@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	ClientService clientService;

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

		List<Map<String, Object>> list = clientService.searchActive();
		model.addAttribute("client_list", list);

		return res;
	}

	/**
	 * CD-010-010 顧客削除処理
	 * ★論理削除時に実行
	 * 以下の時はエラーを発出
	 * ・パスワード未入力
	 * ・チェックボックス未選択
	 * ・パスワード誤入力
	 */
	@PostMapping("/list")
	public String deleteClient(@RequestParam(name = "selectCheck", required = false) String[] c_id,
			@RequestParam(name = "adminPW", defaultValue = "") String adminPW,
			RedirectAttributes attr) {

		// 画面遷移先を顧客情報一覧画面へのリダイレクトに指定
		String res = "redirect:/client/list";

		// エラーメッセージを格納する変数をインスタンス化
		String attributeValue = new String();

		String active_pw = passwordService.getPassword(1);

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
	 * CE-010-010 顧客編集画面遷移
	 */
	@GetMapping("/edit")
	public String editClient(@RequestParam(name = "id", defaultValue = "") Integer c_id,
			Model model) {

		// 画面遷移先を顧客情報登録画面に指定
		String res = "client/client_edit";

		// 既存社員情報の編集時判定
		if (c_id != null) {
			//選択された顧客情報を抽出
			Map<String, Object> clt = clientService.searchClient(c_id);

			//編集画面に顧客名を表示
			model.addAttribute("client_name", clt.get("client_name"));

			//編集に利用する社員IDをスコープに保存
			model.addAttribute("c_id", c_id);
		}

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

		// 画面遷移先を顧客情報一覧画面へのリダイレクトに指定
		String res = "redirect:/client/list";

		// 編集中かどうかを判定するフラグ
		boolean isUpdating = !(c_id.equals(""));

		// エラーメッセージを格納する変数をインスタンス化
		String attributeValue = new String();

		// 未入力チェック
		if (c_name.equals("")) {
			attributeValue = "名前を入力してください。";
			if (isUpdating) {
				res = "redirect:/client/edit?id=" + c_id;
			} else {
				res = "redirect:/client/edit";

			}

		// 未入力以外
		} else {
			// 編集時はUPDATE、新規登録時はINSERTを実行
			attributeValue = clientService.registJudge(c_name, c_id);

			// 社名が重複している場合はリダイレクト先を編集画面へ指定
			if (attributeValue.equals("社名が重複しています。")) {
				if (isUpdating) {
					res = "redirect:/client/edit?id=" + c_id;
				} else {
					res = "redirect:/client/edit";
				}
			}
		}

		attr.addFlashAttribute("Result", attributeValue);
		return res;
	}

}
