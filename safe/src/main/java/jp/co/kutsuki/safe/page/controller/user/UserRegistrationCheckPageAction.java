package jp.co.kutsuki.safe.page.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * ユーザー登録確認画面遷移用のコントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("UserRegistrationCheck")
public class UserRegistrationCheckPageAction {

	@PostMapping
	public String pageView() {
		return "userRegistrationCheck";
	}

}
