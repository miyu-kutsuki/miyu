package jp.co.kutsuki.safe.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.im.service.ImMailSendService;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 *ユーザー登録用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserRegistrationAction {

	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@Autowired
	ImMailSendService imMailSendService;

	@RequestMapping(value="/UserRegistrationAction", method = RequestMethod.POST)
	public String userView(RedirectAttributes redirectAttributes, Model model) {

		//セッション切れかチェック
		if(session.getAttribute("user") == null) {
			return "redirect:Safe";
		}

		//usersテーブルにuser_id,passwordを登録
		User userInformation = (User) session.getAttribute("user");
		userRepository.setUserTable(userInformation);
		//ユーザー情報を取得
		User userList = userRepository.getUserIdTable(userInformation.getUser_id());
		imMailSendService.mailSend(userList, "会員登録完了のお知らせ" ,"/mail/mailRegisterTemplate.txt");
		model.addAttribute("title", "登録完了");
		model.addAttribute("msg", "ご登録が完了しました。");

		return "exit";

	}
}
