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

		//セッションからユーザー情報を取得
		User userInformation = (User) session.getAttribute("user");
		
		//入力されたuser_idとusersテーブルのuser_idが一致した場合は該当のuser_idとpasswordを取得し代入
		//一致しない場合はid=null,user_id,password=none,end_flag=falseを代入
		User userCheck = userRepository.getUserIdTable(userInformation.getUser_id());
		
		//ユーザーIDの重複不可のためnone(null)かチェック
		if(userCheck.getUser_id().equals("none")) {
			//usersテーブルにuser_id,passwordを登録
			userRepository.setUserTable(userInformation);
			//登録後の会員情報を取得(会員番号がデータベースでの連番付与のため)
			User user = userRepository.getUserIdTable(userInformation.getUser_id());
			//登録完了メールを送信
			imMailSendService.mailSend(user, "会員登録完了のお知らせ" ,"/mail/mailRegisterTemplate.txt");
		}
		
		model.addAttribute("title", "登録完了");
		model.addAttribute("msg", "ご登録が完了しました。");
		return "exit";
	}
}
