/**
 *
 */
package jp.co.nexus.model;

/**
 * 報告書管理機能で使用する面談情報を管理するクラス
 * Report.java
 *
 * @author 氏名を記載すること
 *
 */
public class Report {
	// 報告者名ID
	private String createEmployeeId;

	// 営業担当ID
	private String ccgId;

	// 面談ID
	private String eventId;

	// 面談日
	private String eventDate;

	// 面談開始時間
	private String eventStartTime;

	// 面談終了時間
	private String eventEndTime;

	// 報告日
	private String createDate;

	// 報告者氏名
	private String createEmployee;

	// 顧客ID
	private String clientId;

	// 顧客社名
	private String clientName;

	// 担当者名(敬称略)
	private String contactName;

	// 面談参加者(敬称略)
	private String eventMember;

	// 面談場所
	private String eventLocation;

	// 案件概要
	private String eventProject;

	// 質疑応答
	private String eventSession;

	// 考察
	private String eventReport;

	// CCG評価
	private String eventFeedbackByCCG;

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

}
