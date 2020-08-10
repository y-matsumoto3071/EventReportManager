package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nexus.repository.EmployeeDao;
import jp.co.nexus.repository.ReportDao;

@Service
public class EmployeeService{

	@Autowired
	ReportDao reportDao;

	@Autowired
	EmployeeDao employeeDao;

	//全検索する
	public List<Map<String, Object>> searchAll() {
		List<Map<String, Object>> list = employeeDao.searchAll();
		return list;
	}

	//論理削除する
    public int deleteEmployee(String[] e_id) {
        int result = employeeDao.deleteEmployee(e_id);
        return result;
    }

    //登録する
    public int registEmployee(String e_name, String e_category) {
    	int result = employeeDao.registEmployee(e_name,e_category);
    	return result;
    }

    //編集する
    public void editEmployee(String e_name, String e_category, String e_id) {
    	employeeDao.editEmployee(e_name,e_category,e_id);
    }

    //社員を検索する
    public Map<String, Object> searchEmployee(Integer employeeId){
    	Map<String, Object> emp = employeeDao.searchEmployee(employeeId);
    	return emp;
    }

}