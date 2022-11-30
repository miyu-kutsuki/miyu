package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 行方不明者情報の登録確認画面遷移用のコントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersonsRegistrationCheck")
public class MissingPersonsRegistrationCheckPageAction {
		
	@PostMapping
	public String pageView() {
		return "missingPersonRegistrationCheck";
	}
}
