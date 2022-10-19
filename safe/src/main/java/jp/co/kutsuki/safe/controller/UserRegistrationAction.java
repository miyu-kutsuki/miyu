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
 *ユーザー登録用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserRegistrationAction {
	
	@Autowired
	UserDataDao userDao;
	
	@Autowired
	HttpSession session;
	
	/**データベースuserテーブルにユーザー情報を登録後、 
	 * post・リダイレクトでユーザー画面へ遷移 */
	@RequestMapping(value="/UserRegistrationAction", method = RequestMethod.POST)
	public String UserView(@RequestParam String user_id, @RequestParam String password,
			RedirectAttributes redirectAttributes) {
		
		User userInformation = userDao.getUserTable(user_id);
		
		//ユーザーIDの重複不可かつnullではない
		if(userInformation.getUser_id().equals("none")) {
			if(!(user_id == null || password == null)) {
				User user = new User();
				user.setUser_id(user_id);
				user.setPassword(password);
				userDao.setUserTable(user);
				session.setAttribute("user", userInformation);
				return "redirect:UserRegistration";
			}
		}else {
			redirectAttributes.addFlashAttribute("msg", "空欄があります。");
		}
		
		return "redirect:Registration";
	}
	
	/** リダイレクト先の画面 */
	@RequestMapping("/UserRegistration")
	public String PostUserView(Model model) {
		User userInformation = (User) session.getAttribute("user");
		model.addAttribute("user", userInformation);
		
		return "userAuthentication";
	}
	
	/** リダイレクト先の画面 */
	@RequestMapping("/Registration")
	public String PostloginView() {
		return "userRegistration";
	}
}
