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
		
		DateSearch dateSearch = new DateSearch();
		dateSearch.setStartDate(startDate);
		dateSearch.setEndDate(endDate);
		Informations informations = new Informations();
		
		//errorメッセージ用リスト
		List<String> msgList = new ArrayList<>();
		//エラーメッセージ用変数
		String msg = "該当データがありません。";
		
		//null判定用変数
		boolean missingPersons = true;
		boolean missingPersonsSightings = true;
		
		if(dateSearch.getStartDate() == null) {
			msgList.add("開始日付を入力してください。");
		}
		
		if(dateSearch.getEndDate() == null) {
			msgList.add("終了日付を入力してください。");
		}
		
		if(msgList.size() == 1) {
			//開始・終了のどちらかの日付が指定されていない場合
			//リダイレクトで情報表示ページへ遷移
			redirectAttributes.addFlashAttribute("msg", msgList);
			return "redirect:Informations";
		}
		
		if(msgList.size() == 0) {
			informationRepository.setDateInformationTable(informations, dateSearch, redirectAttributes);
		}else if(msgList.size() == 2) {
			informationRepository.setInformationTable(informations, redirectAttributes);
		}
				
		//missing_personsがnullかチェック
		if(informations.getMissingPersonsList().size() == 0) {
			missingPersons = false;
		}
		
		//missing_persons_sightingsがnullかチェック
		if(informations.getMissingPersonsSightingsList().size() == 0) {
			missingPersonsSightings = false;
		}
		
		//msgのサイズ0かチェック
		if(msgList.size() == 0 && missingPersons && missingPersonsSightings) {
			//日付の範囲を指定しているかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullではない場合
			//end_flag==true以外を全件取得
			informationRepository.setDateInformationTable(informations, dateSearch, redirectAttributes);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			
		}else if(msgList.size() == 0 && missingPersons && !missingPersonsSightings) {
			//日付の範囲を指定しているかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			informationRepository.setDateInformationTable(informations, dateSearch, redirectAttributes);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			
		}else if(msgList.size() == 0 && missingPersonsSightings && !missingPersons) {
			//日付の範囲を指定しているかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			informationRepository.setDateInformationTable(informations, dateSearch, redirectAttributes);
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			
		}else if(msgList.size() == 0 && !missingPersons && !missingPersonsSightings) {
			//日付の範囲を指定しているかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			
		}else if(msgList.size() == 2 && missingPersons && missingPersonsSightings) {
			//日付の範囲指定がないかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullではない場合
			//end_flag==true以外を全件取得
			informationRepository.setInformationTable(informations, redirectAttributes);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			
		}else if(msgList.size() == 2 && missingPersons && !missingPersonsSightings) {
			//日付の範囲指定がないかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			informationRepository.setInformationTable(informations, redirectAttributes);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			
		}else if(msgList.size() == 2 && missingPersonsSightings && !missingPersons) {
			//日付の範囲指定がないかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			informationRepository.setInformationTable(informations, redirectAttributes);
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			
		}else if(msgList.size() == 2 && !missingPersons && !missingPersonsSightings) {
			//日付の範囲を指定がないかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
		}
		
		return "redirect:Informations";
	}
}
