package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 不審者目撃情報の登録確認画面遷移用のコントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("SuspiciousPersonSightingsRegistrationCheckAction")
public class SuspiciousPersonSightingsRegistrationCheckPageAction {
	
	@PostMapping
	public String pageView() {
		return "suspiciousPersonSightingsRegistrationCheck";
	}

}
