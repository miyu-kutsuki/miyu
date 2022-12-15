package jp.co.kutsuki.safe.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * パスワードを忘れた場合の変更用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class ChecUserIdAction {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/CheckUserId", method = RequestMethod.POST)
	public String changePassword(@RequestParam String user_id, @RequestParam String familyName, @RequestParam String firstName,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "birthday", required = false) LocalDate birthday, 
			@RequestParam String email, @RequestParam Integer questions, @RequestParam String answer,
			RedirectAttributes redirectAttributes) {
		
		//ユーザー情報を取得
		User userList = userRepository.getUserIdTable(user_id);
		
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		//空欄チェック
		if(user_id.isEmpty()) {
			msg.add("ユーザーIDが入力されていません。");
		}
		
		if(familyName.isEmpty()) {
			msg.add("名字が入力されていません。");
		}
		
		if(firstName.isEmpty()) {
			msg.add("名前が入力されていません。");
		}
		
		if(birthday == null) {
			msg.add("生年月日入力されていません。");
		}

		if(email.isEmpty()) {
			msg.add("メールアドレスが入力されていません。");
		}
		
		if(questions == null) {
			msg.add("秘密の質問が選択されていません。");
		}
		
		if(answer.isEmpty()) {
			msg.add("秘密の質問の答えが入力されていません。");
		}
		
		//空欄が一つでも合った場合、入力画面へ遷移する
		if(!(msg.size() == 0)) {
			redirectAttributes.addFlashAttribute("msg", msg);
			return "redirect:ForgetUserPassword";
		}
		
		//入力項目が登録内容と誤りがないかチェック
		if(!user_id.equals(userList.getUser_id())) {
			msg.add("ユーザーIDが違います。");
		}
		
		if(!familyName.equals(userList.getFamilyName())) {
			msg.add("名字が違います。");
		}
		
		if(!firstName.equals(userList.getFirstName())) {
			msg.add("名前が違います。");
		}
		
		if(!birthday.equals(userList.getBirthday())) {
			msg.add("生年月日が違います。");
		}

		if(!email.equals(userList.getEmail())) {
			msg.add("メールアドレスが違います。");
		}
		
		if(!questions.equals(userList.getQuestion_id())) {
			msg.add("秘密の質問が違います。");
		}
		
		if(!answer.equals(userList.getAnswer())) {
			msg.add("秘密の質問の答えが違います。");
		}
		
		//誤りが一つでも合った場合、入力画面へ遷移する
		if(!(msg.size() == 0)) {
			redirectAttributes.addFlashAttribute("msg", msg);
			return "redirect:ForgetUserPassword";
		}
		
		FormLogin user = new FormLogin();
		user.setId(userList.getId());
		user.setUser_id(userList.getUser_id());
		user.setPassword(userList.getPassword());
		session.setAttribute("user", user);
		session.setAttribute("flag", "noPass");
		
		return "redirect:ResettingPassword";
	}

}
