package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * パスワードを忘れた場合の変更画面の遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("ResettingPassword")
public class ResettingPasswordPageAction {
	
	@GetMapping
	public String pageView() {
		
		return "resettingPassword";
	}
}
