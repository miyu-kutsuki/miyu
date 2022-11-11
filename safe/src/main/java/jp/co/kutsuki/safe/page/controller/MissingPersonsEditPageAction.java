package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;

/**
 * 探し人登録情報編集ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersonsEditPage")
public class MissingPersonsEditPageAction {
	
	@Autowired
	InformationRepository informationRepository;
		
	@Autowired
	HttpSession session;
	
	@ModelAttribute
	public MissingPersons setUpMissingPersons() {
		return new MissingPersons();
	}
	
	@GetMapping
	public String EditPageView(Model model, RedirectAttributes redirectAttributes) {
		
		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}
		
		//更新するデータのidを取得
		String id = (String) session.getAttribute("id");

		Informations informations = new Informations();
		informationRepository.setMissingPersonsTable(informations, id);
		model.addAttribute("missingPersonsList", informations.getMissingPersonsList());
		
		return "missingPersonsEditPage";
	}

}
