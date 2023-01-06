package jp.co.kutsuki.safe.page.controller.user;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.Questions;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.QuestionsCrudRepository;

/**
 * 新規登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("UserRegistration")
public class UserRegistrationPageAction {

	@Autowired
	QuestionsCrudRepository questionsCrudRepository;

	@ModelAttribute
	public User setUpUser() {
		return new User();
	}

	@GetMapping
	public String pageView(Model model) {

		//プルダウンの秘密の質問を取得
		ArrayList<Questions> questionsList = (ArrayList<Questions>) questionsCrudRepository.findAll();
		model.addAttribute("questionsList", questionsList);

		return "userRegistration";
	}
}
