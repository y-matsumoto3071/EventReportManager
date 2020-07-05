/**
 *
 */
package jp.co.nexus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nexus.repository.ReportDao;

/**
 * @author Yuki Matsumoto
 *
 */

@Service
public class ReportService {
	private final ReportDao dao;

	@Autowired
	public ReportService(ReportDao dao) {
		this.dao = dao;
	}
}
