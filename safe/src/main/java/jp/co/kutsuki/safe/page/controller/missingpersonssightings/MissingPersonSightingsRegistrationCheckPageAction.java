package jp.co.kutsuki.safe.page.controller.missingpersonssightings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 行方不明者目撃情報の登録確認画面遷移用のコントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersonSightingsRegistrationCheckAction")
public class MissingPersonSightingsRegistrationCheckPageAction {

	@PostMapping
	public String pageView() {
		return "missingPersonSightingsRegistrationCheck";
	}
}
