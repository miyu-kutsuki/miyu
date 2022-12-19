package jp.co.kutsuki.safe.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.im.service.ImChangeCheckService;
import jp.co.kutsuki.safe.im.service.ImMailSendService;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * user_idを忘れた場合の変更用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class CheckPasswordAction {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ImMailSendService imMailSendService;
	
	@Autowired
	ImChangeCheckService imChangeCheckService;
	
	@RequestMapping(value="/CheckPassword", method = RequestMethod.POST)
	public String userReissue(@RequestParam(name = "id", required = false) Integer id, @RequestParam String password, @RequestParam String familyName, @RequestParam String firstName,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "birthday", required = false) LocalDate birthday, 
			@RequestParam String email, @RequestParam Integer questions, @RequestParam String answer,
			RedirectAttributes redirectAttributes, Model model) {
		
		//ユーザー情報を取得
		User userList = userRepository.getUserPassTable(id, password);
		
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		//空欄チェック
		if(id == null) {
			msg.add("会員番号が入力されていません。");
		}

		if(password.isEmpty()) {
			msg.add("パスワードが入力されていません。");
		}
		
		imChangeCheckService.nullcheckExcute(msg, familyName, firstName, birthday, email, questions, answer);
		
		//空欄が一つでも合った場合、入力画面へ遷移する
		if(!(msg.size() == 0)) {
			redirectAttributes.addFlashAttribute("msg", msg);
			return "redirect:ForgetUserPassword";
		}
		
		//入力項目が登録内容と誤りがないかチェック
		if(!id.equals(userList.getId())) {
			msg.add("会員番号が違います。");
		}

		if(!password.equals(userList.getPassword())) {
			msg.add("パスワードが違います。");
		}
		
		imChangeCheckService.checkExcute(msg, userList, familyName, firstName, birthday, email, questions, answer);
		
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
		imMailSendService.mailSend(userList, "ユーザーIDお問い合せの件" ,"/mail/mailReissueTemplate.txt");
		model.addAttribute("title", "お問い合わせ完了");
		model.addAttribute("msg", "ご登録のメールアドレスをご確認ください。");
		
		return "exit";
	}
}
