package jp.co.nexus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nexus.repository.ClientDao;
import jp.co.nexus.repository.EmployeeDao;
import jp.co.nexus.repository.ReportDao;

@Service
public class ClientService{

	@Autowired
	ReportDao reportDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	ClientDao clientDao;

	//全検索する
	public List<Map<String, Object>> searchAll() {
		List<Map<String, Object>> list = clientDao.searchAll();
		return list;
	}

	//論理削除する
    public int deleteClient(String[] c_id) {
        int result = clientDao.deleteClient(c_id);
        return result;
    }

    //登録する
    public int registClient(String c_name) {
    	int result = clientDao.registClient(c_name);
    	return result;
    }

    //編集する
    public void editClient(String c_name, String c_id) {
    	clientDao.editClient(c_name,c_id);
    }

    //顧客を検索する
    public Map<String, Object> searchClient(Integer clientId){
    	Map<String, Object> ee = clientDao.searchClient(clientId);
    	return ee;
    }



}