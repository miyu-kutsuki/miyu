package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String Withdrawal(RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//更新するデータのidを取得
		User user = (User) session.getAttribute("user");
		userRepository.Delete(user.getId());
		missingPersonsRepository.DeleteUser(user.getUser_id());
		missingPersonsSightingsRepository.DeleteUser(user.getUser_id());
		suspiciousPersonSightingsRepository.DeleteUser(user.getUser_id());
		session.invalidate();
		return "redirect:Safe";
	}
}
