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
@RequestMapping("/employee")
public class EmployeeController {

	@GetMapping("/list")
	public String employeeList() {
		return "employee/employee_list";
	}


}
