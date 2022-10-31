package jp.co.kutsuki.safe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 不審者の目撃情報登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("SuspiciousPersonSightings")
public class SuspiciousPersonSightingsPageAction {
	
	@GetMapping
	public String LoginPageView() {
		return "suspiciousPersonSightingsRegistration";
	}
}
