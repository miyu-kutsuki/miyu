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
 * パスワード変更用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class ChangePasswordAction {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value="/ChangePassword", method = RequestMethod.POST)
	public String changePassword(@RequestParam String password1, 
			@RequestParam String password2, RedirectAttributes redirectAttributes) {
		
		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}
		
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		//変更用のpassword1と確認用のpassword2が空欄かチェック
		if(password1.isEmpty()) {
			msg.add("変更後欄に入力がありません。");
		}
		if(password2.isEmpty()) {
			msg.add("確認用欄に入力がありません。");
		}
		
		if(!(password1.length() >= 5) || !(password1.length() <= 20)) {
			msg.add("パスワードは5〜20桁で入力して下さい。");
		}
		
		if(!password1.matches("^[A-Za-z0-9_-]+$")) {
			msg.add("パスワードは『半角英数』『_』『-』のみ使用可能です。");
		}
		
		//変更用のpassword1と確認用のpassword2が一致してるかチェック
		if(!password1.equals(password2)) {
			msg.add("確認用欄に入力されたパスワードが違います。");
		}
		
		if(msg.size() == 0){
			FormLogin user = (FormLogin)session.getAttribute("user");
			userRepository.updatePassword(user.getUser_id(), password1);
			msg.add("パスワードが変更されました。");
		}
		
		redirectAttributes.addFlashAttribute("msg", msg);
		
		//画面の遷移先
		if(!(msg.size() == 0)){
			if(session.getAttribute("flag") == null) {
				return "redirect:Login";
			}else {
				return "redirect:ResettingPassword";
			}
		}
		
		return "redirect:UserInformations";
	}
}
