package jp.co.kutsuki.safe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * メールアドレス変更用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class ChangeMailAddressAction {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value="/ChangeMailAddress", method = RequestMethod.POST)
	public String changePassword(@RequestParam String email, RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();

		//変更用のpassword1と確認用のpassword2が空欄かチェック
		if(email.isEmpty()) {
			msg.add("変更後欄に入力がありません。");
		}

		if(!(email.length() >= 5) || !(email.length() <= 100)) {
			msg.add("名字は5〜100桁で入力して下さい。");
		}

		if(!email.matches("^([a-zA-Z0-9])+([a-zA-Z0-9\\\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\\\._-]+)+$")) {
			msg.add("メールアドレスが正しくありません。");
		}

		if(msg.size() == 0){
			FormLogin user = (FormLogin)session.getAttribute("user");
			userRepository.updateMailAddress(user.getUser_id(), email);
			msg.add("メールアドレスが変更されました。");
		}

		redirectAttributes.addFlashAttribute("msg6", msg);

		return "redirect:UserInformations";
	}
}
