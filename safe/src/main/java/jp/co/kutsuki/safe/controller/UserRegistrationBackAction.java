package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.User;

/**
 * ユーザー登録確認画面から入力画面へ戻る用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserRegistrationBackAction {

	@Autowired
	HttpSession session;

	@RequestMapping(value="/UserRegistrationBackAction", method = RequestMethod.POST)
	public String UserView(RedirectAttributes redirectAttributes, Model model) {

		//セッション切れかチェック
		if(session.getAttribute("user") == null) {
			return "redirect:Safe";
		}

		//入力情報を保持してユーザー登録画面へ遷移
		User userInformation = (User) session.getAttribute("user");
		redirectAttributes.addFlashAttribute("user", userInformation);
		return "redirect:UserRegistration";

	}
}
