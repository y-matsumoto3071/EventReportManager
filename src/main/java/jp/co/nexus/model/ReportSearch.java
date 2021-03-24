package jp.co.nexus.model;

/**
* 報告書管検索機能で使用する検索情報を管理するクラス
* ReportSearch.java
*
* @author 中村 美南海
*
*/

public class ReportSearch {
	//検索種別（顧客/社員）
	private String s_radio;

	//検索キーワード
	private String s_keyword;

	//日付フィルター（有効/無効）
	private boolean s_dateFilter;

	//検索範囲開始日付
	private String s_startDate;

	//検索範囲終了日付
	private String s_endDate;

	//setter/getter-----------------
	public String getS_radio() {
		return s_radio;
	}

	public void setS_radio(String s_radio) {
		this.s_radio = s_radio;
	}

	public String getS_keyword() {
		return s_keyword;
	}

	public void setS_keyword(String s_keyword) {
		this.s_keyword = s_keyword;
	}

	public boolean isS_dateFilter() {
		return s_dateFilter;
	}

	public void setS_dateFilter(boolean s_dateFilter) {
		this.s_dateFilter = s_dateFilter;
	}

	public String getS_startDate() {
		return s_startDate;
	}

	public void setS_startDate(String s_startDate) {
		this.s_startDate = s_startDate;
	}

	public String getS_endDate() {
		return s_endDate;
	}

	public void setS_endDate(String s_endDate) {
		this.s_endDate = s_endDate;
	}

	/**
	 * 不正入力判定メソッド
	 */
	public boolean inputCheck(ReportSearch reportSearch) {
		// returnで返す判定結果変数
		boolean result = true;

		//ラジオボタン選択で検索テキスト未入力
		if(reportSearch.s_radio != null && reportSearch.s_keyword.isEmpty()||
		//ラジオボタン未選択で検索テキスト入力
			reportSearch.s_radio == null && !reportSearch.s_keyword.isEmpty()	||
		//日付フィルター選択で開始日付、終了日付ともに未選択
			reportSearch.s_dateFilter && reportSearch.s_startDate.isEmpty() && reportSearch.s_endDate.isEmpty() ||
		//日付フィルター未選択で開始日付が入力
			!reportSearch.s_dateFilter && !reportSearch.s_startDate.isEmpty() ||
		//日付フィルター未選択で終了日付が入力
			!reportSearch.s_dateFilter && !reportSearch.s_endDate.isEmpty() ||
		//全ての項目が未入力
			reportSearch.s_radio == null && reportSearch.s_keyword.isEmpty() &&
			!reportSearch.s_dateFilter && reportSearch.s_startDate.isEmpty() && reportSearch.s_endDate.isEmpty()) {

			result = false;
		}
		return result;
	}
}
