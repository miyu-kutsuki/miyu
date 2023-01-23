package jp.co.kutsuki.safe.controller.Informationchange;

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
 * 名字変更用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class ChangeFamilyNameAction {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value="/ChangeFamilyName", method = RequestMethod.POST)
	public String changePassword(@RequestParam String familyName, RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();

		//変更用のpassword1と確認用のpassword2が空欄かチェック
		if(familyName.isEmpty()) {
			msg.add("変更後欄に入力がありません。");
		}

		if(!(familyName.length() >= 1) || !(familyName.length() <= 30)) {
			msg.add("名字は1〜30桁で入力して下さい。");
		}

		if(msg.size() == 0){
			FormLogin user = (FormLogin)session.getAttribute("user");
			userRepository.updateFamilyName(user.getUser_id(), familyName);
			msg.add("名字が変更されました。");
		}

		redirectAttributes.addFlashAttribute("msg5", msg);

		return "redirect:UserInformationChangeScreen";
	}
}
