/**
 *
 */
package jp.co.nexus.model;

import java.util.Map;

/**
 * 報告書管理機能で使用する面談情報を管理するクラス
 * Report.java
 *
 * @author 氏名を記載すること
 *
 */
public class Report {

	private String createEmployeeId; // 報告者名ID
	private String ccgId; // 営業担当ID
	private String eventId; // 面談ID
	private String eventDate; // 面談日
	private String eventStartTime; // 面談開始時間
	private String eventEndTime; // 面談終了時間
	private String createDate; // 報告日
	private String createEmployee; // 報告者氏名
	private String clientId; // 顧客ID
	private String clientName; // 顧客社名
	private String contactName; // 担当者名(敬称略)
	private String eventMember; // 面談参加者(敬称略)
	private String eventLocation; // 面談場所
	private String eventProject; // 案件概要
	private String eventSession; // 質疑応答
	private String eventReport; // 考察
	private String eventFeedbackByCCG; // CCG評価
	private String eventFeedbackContent; // 管理者所見
	private String eventFeedbackEmployeeId; // 所見返信担当ID
	private String eventStatus; // 状態区分

	public String getCcgId() {
		return ccgId;
	}

	public void setCcgId(String ccgId) {
		this.ccgId = ccgId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public String getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(String createEmployee) {
		this.createEmployee = createEmployee;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEventMember() {
		return eventMember;
	}

	public void setEventMember(String eventMember) {
		this.eventMember = eventMember;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getEventProject() {
		return eventProject;
	}

	public void setEventProject(String eventProject) {
		this.eventProject = eventProject;
	}

	public String getEventSession() {
		return eventSession;
	}

	public void setEventSession(String eventSession) {
		this.eventSession = eventSession;
	}

	public String getEventReport() {
		return eventReport;
	}

	public void setEventReport(String eventReport) {
		this.eventReport = eventReport;
	}

	public String getEventFeedbackByCCG() {
		return eventFeedbackByCCG;
	}

	public void setEventFeedbackByCCG(String eventFeedbackByCCG) {
		this.eventFeedbackByCCG = eventFeedbackByCCG;
	}

	public String getCreateEmployeeId() {
		return createEmployeeId;
	}

	public void setCreateEmployeeId(String createEmployeeId) {
		this.createEmployeeId = createEmployeeId;
	}

	public String getEventFeedbackContent() {
		return eventFeedbackContent;
	}

	public void setEventFeedbackContent(String eventFeedbackContent) {
		this.eventFeedbackContent = eventFeedbackContent;
	}

	public String getEventFeedbackEmployeeId() {
		return eventFeedbackEmployeeId;
	}

	public void setEventFeedbackEmployeeId(String eventFeedbackEmployeeId) {
		this.eventFeedbackEmployeeId = eventFeedbackEmployeeId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public void setEntity(Map<String, Object> reportMap) {
		this.createEmployeeId = reportMap.get("event_entry_employee_id").toString();
		this.ccgId = reportMap.get("event_sales_employee_id").toString();
		this.eventId = reportMap.get("event_id").toString();
		this.eventDate = reportMap.get("event_date").toString();
		this.eventStartTime = reportMap.get("event_start_time").toString();
		this.eventEndTime = reportMap.get("event_end_time").toString();
		this.createDate = reportMap.get("createdate").toString().substring(0,10);
		this.createEmployee = reportMap.get("employee_name").toString();
		this.clientId = reportMap.get("event_client_id").toString();
		this.clientName = reportMap.get("client_name").toString();
		this.contactName = reportMap.get("event_contact").toString();
		this.eventMember = reportMap.get("event_member").toString();
		this.eventLocation = reportMap.get("event_location").toString();
		this.eventProject = reportMap.get("event_project").toString();
		this.eventSession = reportMap.get("event_session").toString();
		this.eventReport = reportMap.get("event_report").toString();
		this.eventFeedbackByCCG = reportMap.get("event_feedback_byccg").toString();
		try {
			this.eventFeedbackEmployeeId = reportMap.get("event_feedback_employee_id").toString();
		} catch(NullPointerException e) {
			System.out.println("管理者所見担当IDがnullです。空文字に変換します。");
			this.eventFeedbackEmployeeId = "";
		}
		try {
			this.eventFeedbackContent = reportMap.get("event_feedback_content").toString();
		} catch(NullPointerException e) {
			System.out.println("管理者所見がnullです。空文字に変換します。");
			this.eventFeedbackContent = "";
		}
		this.eventStatus = reportMap.get("event_status").toString();

	}

}
