package jp.co.kutsuki.safe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログインページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("/login")
public class LoginPageAction {
	
	@GetMapping
	public String LoginPageView() {
		return "login";
	}
}
