package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.database.dao.UserDataDao;
import jp.co.kutsuki.safe.entity.User;

/**
 * ログインアクション用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class LoginAction {
	
	@Autowired
	UserDataDao userDao;
	
	@Autowired
	HttpSession session;
	
	/** post・リダイレクトでユーザー画面へ遷移 */
	@RequestMapping(value="/loginAction", method = RequestMethod.POST)
	public String UserView(@RequestParam String user_id, @RequestParam String password,
			RedirectAttributes redirectAttributes) {
		
		User userInformation = userDao.getUserTable(user_id);
		
		if(!(userInformation.getId() == null)) {
			if(userInformation.getPassword().equals(password)) {
				session.setAttribute("user", userInformation);
				return "redirect:/user";
			}
		}else {
			redirectAttributes.addFlashAttribute("msg", "該当のユーザーが見つかりません。");
		}
		return "redirect:login";
	}
	
	/** リダイレクト先の画面 */
	@RequestMapping("/user")
	public String PostUserView(Model model) {
		User userInformation = (User) session.getAttribute("user");
		model.addAttribute("user", userInformation);
		return "user";
	}
	
	/** リダイレクト先の画面 */
	@RequestMapping("/login")
	public String PostloginView() {
		return "login";
	}
}
