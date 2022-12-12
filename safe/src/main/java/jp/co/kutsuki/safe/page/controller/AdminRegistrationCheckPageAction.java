package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理者アカウント登録確認画面遷移用のコントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("AdminRegistrationCheck")
public class AdminRegistrationCheckPageAction {
	
	@PostMapping
	public String pageView() {
		return "adminRegistrationCheck";
	}
}
