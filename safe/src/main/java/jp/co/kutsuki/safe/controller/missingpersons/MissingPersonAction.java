package jp.co.kutsuki.safe.controller.missingpersons;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;

/**
 * missing_personsテーブル登録用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class MissingPersonAction {

	@Autowired
	MissingPersonsRepository  missingPersonsRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/MissingPersonsRegistration", method = RequestMethod.POST)
	public String missingPersonView(RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//missing_personsテーブルに登録
		MissingPersons missingPersons = (MissingPersons)session.getAttribute("missingPersons");
		missingPersonsRepository.setMissingPersonsTable(missingPersons);

		return "redirect:MissingPersons";
	}
}
