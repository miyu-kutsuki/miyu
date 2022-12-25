package jp.co.kutsuki.safe.page.controller.forget;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.Questions;
import jp.co.kutsuki.safe.safedb.repository.QuestionsCrudRepository;

/**
 * パスワードを忘れた場合の変更前チェック画面への遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("ForgetUserId")
public class ForgetUserIdPageAction {

	@Autowired
	QuestionsCrudRepository questionsCrudRepository;

	@GetMapping
	public String pageView(Model model) {

		//プルダウンの秘密の質問を取得
		ArrayList<Questions> questionsList = (ArrayList<Questions>) questionsCrudRepository.findAll();
		model.addAttribute("questionsList", questionsList);

		return "forgetUserId";
	}
}
