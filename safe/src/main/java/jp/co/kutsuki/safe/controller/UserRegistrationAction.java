package jp.co.kutsuki.safe.controller;

import java.util.ArrayList;
import java.util.List;

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
			RedirectAttributes redirectAttributes, Model model) {
		
		//入力されたuser_idとusersテーブルのuser_idが一致した場合は該当のuser_idとpasswordを取得し代入
		//一致しない場合はid=null,user_id,password=none,end_flag=falseを代入
		User userInformation = userDao.getUserTable(user_id);
		
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		//user_idが4以内16桁以上かチェック
		if(5 > user_id.length() || user_id.length() > 15) {
			msg.add("ユーザーIDは5〜15桁で入力して下さい。");
		}
		
		//user_idが半角英数、"_"、"-"のみかチェック
		if(!(user_id.matches("^[A-Za-z0-9_-]+$"))) {
			msg.add("ユーザーIDは『半角英数』『_』『-』のみ使用可能です。");
		}
		
		//passwaordが4以内21桁以上かチェック
		if(5 > password.length() || password.length() > 20) {
			msg.add("パスワードは5〜20桁で入力して下さい。");
		}
		
		//passwaordが半角英数、"_"、"-"のみかチェック
		if(!password.matches("^[A-Za-z0-9_-]+$")) {
			msg.add("パスワードは『半角英数』『_』『-』のみ使用可能です。");
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
			User user = new User();
			user.setUser_id(user_id);
			user.setPassword(password);
			userDao.setUserTable(user);
			session.setAttribute("user", userInformation);
			userInformation = (User) session.getAttribute("user");
			model.addAttribute("user", userInformation);
			return "redirect:login";
		}else {
			redirectAttributes.addFlashAttribute("msg", msg);
			return "redirect:Registration";
		}
	}
		
	/** リダイレクト先の画面
	 * 登録失敗 */
	@RequestMapping("/Registration")
	public String PostloginView() {
		return "userRegistration";
	}
}
