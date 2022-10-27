package jp.co.kutsuki.safe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;

/**
 * 検索ボタン用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class SearchAction {
	
	@Autowired
	InformationRepository informationRepository;
	
	/** post・リダイレクトで検索画面へ遷移  */
	@RequestMapping(value="/Search", method = RequestMethod.POST)
	public String SearchPageView(Model model, RedirectAttributes redirectAttributes){
		Informations informations = new Informations();
		informationRepository.setInformationTable(informations, redirectAttributes);
		redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
		return "redirect:Informations";
	}
}
