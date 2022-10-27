package jp.co.kutsuki.safe.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.DateSearch;
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
	public String SearchPageView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "startDate", required = false)LocalDate startDate, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "endDate", required = false)LocalDate endDate,
			Model model, RedirectAttributes redirectAttributes){
		
		Informations informations = new Informations();
		DateSearch dateSearch = new DateSearch();
		dateSearch.setStartDate(startDate);
		dateSearch.setEndDate(endDate);
		
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		if(dateSearch.getStartDate() == null) {
			msg.add("開始日付を入力してください。");
		}
		
		if(dateSearch.getEndDate() == null) {
			msg.add("終了日付を入力してください。");
		}
		
		//msgのサイズ0かチェック
		if(msg.size() == 0) {
			informationRepository.setDateInformationTable(informations, dateSearch, redirectAttributes);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
		}else if(msg.size() == 1) {
			redirectAttributes.addFlashAttribute("msg", msg);
		}else if(msg.size() == 2) {//日付の範囲指定がない場合、end_flag==true以外を全件取得
			informationRepository.setInformationTable(informations, redirectAttributes);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
		}
		
		return "redirect:Informations";
	}
}
