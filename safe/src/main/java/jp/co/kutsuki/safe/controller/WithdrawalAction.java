package jp.co.kutsuki.safe.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsSightingsRepository;
import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * ユーザー退会用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class WithdrawalAction {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MissingPersonsRepository missingPersonsRepository;

	@Autowired
	MissingPersonsSightingsRepository missingPersonsSightingsRepository;

	@Autowired
	SuspiciousPersonSightingsRepository suspiciousPersonSightingsRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/WithdrawalAction", method = RequestMethod.POST)
	public String withdrawal(@RequestParam (name="end")Integer id, RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check && session.getAttribute("admin") == null) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Safe";
		}

		//更新するデータをidから取得
		User userList = userRepository.getOneUserTable(id);
		FormLogin user = new FormLogin();
		user.setId(userList.getId());
		user.setUser_id(userList.getUser_id());
		user.setPassword(userList.getPassword());

		//削除するユーザーIDで各情報の登録があればユーザーIDを"guests"に変更する
		if(!(missingPersonsRepository.getMissingPersonsTable(user).size() == 0)) {
			missingPersonsRepository.deleteUser(user.getUser_id());
		}

		if(!(missingPersonsSightingsRepository.getMissingPersonsSightingsTable(user).size() == 0)) {
			missingPersonsSightingsRepository.deleteUser(user.getUser_id());
		}

		if(!(suspiciousPersonSightingsRepository.getSuspiciousPersonSightingsTable(user).size() == 0)) {
			suspiciousPersonSightingsRepository.deleteUser(user.getUser_id());
		}

		//ユーザーIDを削除
		userRepository.deleteUser(id);

		//画面の遷移先
		if(!(session.getAttribute("admin") == null)) {
			//リダイレクトで管理者用の掲載情報管理ページへ遷移
			ArrayList<User> userAllList = userRepository.getAllUserTable();
			session.setAttribute("userList", userAllList);
			return "redirect:UsersInformationsAdmin";
		}

		//セッション破棄
		session.invalidate();
		return "redirect:Safe";
	}
}
