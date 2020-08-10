package jp.co.nexus.model;

public class EmployeeCategoryModel {

	//★カテゴリ文字列変換
	public String categorySet(String ctg) {
		String str = "";

		switch (ctg) {
		case "ccgt0":
			str = "CCG,東京エリア";
			break;
		case "ccgh0":
			str = "CCG,浜松エリア";
			break;
		case "scgh1":
			str = "SCG,浜松1チーム";
			break;
		case "scgh2":
			str = "SCG,浜松2チーム";
			break;
		case "scgh3":
			str = "SCG,浜松3チーム";
			break;
		case "scgh4":
			str = "SCG,浜松4チーム";
			break;
		case "scgh5":
			str = "SCG,浜松5チーム";
			break;
		case "scgt1":
			str = "SCG,東京1チーム";
			break;
		case "scgt2":
			str = "SCG,東京2チーム";
			break;
		case "scgt3":
			str = "SCG,東京3チーム";
			break;
		case "scgt4":
			str = "SCG,東京4チーム";
			break;
		case "scgt5":
			str = "SCG,東京5チーム";
			break;
		case "scgt6":
			str = "SCG,東京6チーム";
			break;
		default:
			str = "*所属エラー*,*所属エラー*";
		}

		return str;
	}

	/*
	 * ★カテゴリ組み合わせチェック
	 * 以下の条件外の場合は入力できない
	 * ・scgなら末尾が0ではない
	 * ・ccgなら末尾が0である
	 */
	public int categoryCheck(String e_category) {
		int check = 0;
		if(e_category.substring(0,3).equals("scg")) {
			if(e_category.substring(4,5).equals("0")){
				check = 1;
			}
		} else {
			if(!(e_category.substring(4,5).equals("0"))){
				check = 1;
			}
		}
		return check;
	}
}
