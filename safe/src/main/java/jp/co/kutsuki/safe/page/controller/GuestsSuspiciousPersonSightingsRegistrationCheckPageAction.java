package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ゲスト用不審者目撃情報の登録確認画面遷移用のコントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("GuestsSuspiciousPersonSightingsRegistrationCheckAction")
public class GuestsSuspiciousPersonSightingsRegistrationCheckPageAction {
	
	@PostMapping
	public String pageView() {
		return "guestsSuspiciousPersonSightingsRegistrationCheck";
	}

}
