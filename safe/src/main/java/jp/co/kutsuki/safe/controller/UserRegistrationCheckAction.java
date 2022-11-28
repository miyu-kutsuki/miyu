package jp.co.kutsuki.safe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;
/**
 * ユーザー登録確認用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserRegistrationCheckAction {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/UserRegistrationCheckAction", method = RequestMethod.POST)
	public String UserView(@RequestParam String user_id, @RequestParam String password,
			@Validated @ModelAttribute User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
				
		//入力されたuser_idとusersテーブルのuser_idが一致した場合は該当のuser_idとpasswordを取得し代入
		//一致しない場合はid=null,user_id,password=none,end_flag=falseを代入
		User userInformation = userRepository.getUserTable(user_id);
		
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		//バリデーションの入力チェック
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("user", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", bindingResult);
			return "redirect:UserRegistration";
		}
		
		//ユーザーIDの重複不可のためnone(null)かチェック
		if(userInformation.getUser_id().equals("none")) {
			//user_idがnullかチェック
			if(user_id.length() == 0) {
				msg.add("ユーザーIDが入力されていません。");
			}
			
			//passwordがnullかチェック
			if(password.length() == 0) {
				msg.add("パスワードが入力されていません。");
			}
		}else {
			msg.add(user_id + "は使用できません。");
		}
				
		//msgのサイズ0かチェック
		if(msg.size() == 0) {
			User newUser = new User();
			newUser.setUser_id(user_id);
			newUser.setPassword(password);
			session.setAttribute("user", newUser);
			model.addAttribute("user", newUser);
			return "UserRegistrationCheck";
		}else {
			redirectAttributes.addFlashAttribute("msg", msg);
			return "redirect:UserRegistration";
		}
	}
}
