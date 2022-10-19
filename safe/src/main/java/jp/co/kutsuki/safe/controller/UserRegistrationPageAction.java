package jp.co.kutsuki.safe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新規登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("/UserRegistration")
public class UserRegistrationPageAction {
	
	@GetMapping
	public String LoginPageView() {
		return "userRegistration";
	}
}
