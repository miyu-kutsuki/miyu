package jp.co.kutsuki.safe.page.controller.user;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;
/**
 * ユーザー情報変更ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("UserInformationChangeScreen")
public class UserInformationChangePageAction {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;

	@GetMapping
	public String menuPageView(RedirectAttributes redirectAttributes, Model model) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//ログイン中のuser_idを取得
		FormLogin userInformation = (FormLogin) session.getAttribute("user");
		//ユーザー情報の取得
		User user = userRepository.getUserIdTable(userInformation.getUser_id());
		model.addAttribute("user", user);

		return "userInformationChangeScreen";
	}
}
