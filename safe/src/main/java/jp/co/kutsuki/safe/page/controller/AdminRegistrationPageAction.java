package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.Admin;

/**
 * 管理者アカウント新規登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("AdminRegistration")
public class AdminRegistrationPageAction {
	
	@ModelAttribute
	public Admin setUpAdmin() {
		return new Admin();
	}

	@GetMapping
	public String pageView() {
		return "adminRegistration";
	}
}
