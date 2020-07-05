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
@RequestMapping("/client")
public class ClientController {

	@GetMapping("/list")
	public String clientList() {
		return "client/client_list";
	}


}
