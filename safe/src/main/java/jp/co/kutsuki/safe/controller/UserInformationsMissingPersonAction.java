package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;

/**
 * 探し人情報の編集アクション・終了アクション用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserInformationsMissingPersonAction {

	@Autowired
	MissingPersonsRepository missingPersonsRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/UserInformationsMissingPersonAction", method = RequestMethod.POST)
	public String userInformationsView(@RequestParam(name = "edit", required = false) String edit,
			@RequestParam(name = "end", required = false) String end, RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			if(session.getAttribute("admin") == null) {
				//リダイレクトで管理者用ログインページへ遷移
				redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
				return "redirect:LoginAdmin";
			}else if(session.getAttribute("admin") == null && check) {
				redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
				return "redirect:Login";
			}
		}

		//編集ボタンが押下されたら指定されたidのデータを更新
		if(!(edit == null)) {
			session.setAttribute("id", edit);
			return "redirect:MissingPersonsEditPage";
		}

		//終了ボタンが押下されたら指定されたidのカラムend_flagにtrueをセットする
		if(!(end == null)) {
			missingPersonsRepository.delete(end);
		}

		//画面の遷移先
		if(!(session.getAttribute("admin") == null)) {
			//リダイレクトで管理者用の掲載情報管理ページへ遷移
			return "redirect:InformationsAdmin";
		}

		return "redirect:UserInformations";
	}
}
