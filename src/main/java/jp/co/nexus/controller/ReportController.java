/**
 *
 */
package jp.co.nexus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yuki Matsumoto
 *
 */

@Controller
@RequestMapping("/report")
public class ReportController {

	@GetMapping("/list")
	public String reportList() {
		return "report/report_list";
	}

}
