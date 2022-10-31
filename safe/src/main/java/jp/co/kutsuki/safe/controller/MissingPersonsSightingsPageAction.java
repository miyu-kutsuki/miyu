package jp.co.kutsuki.safe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 探し人の目撃情報登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersonsSightings")
public class MissingPersonsSightingsPageAction {
	
	@GetMapping
	public String LoginPageView() {
		return "missingPersonsSightingsRegistration";
	}
}
