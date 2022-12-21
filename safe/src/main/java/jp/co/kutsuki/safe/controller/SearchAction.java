package jp.co.kutsuki.safe.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import jp.co.kutsuki.safe.im.service.ImSearchService;
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

	@Autowired
	ImSearchService imSearchService;

	@Autowired
	HttpSession session;


	@RequestMapping(value="/Search", method = RequestMethod.POST)
	public String searchPageView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "startDate", required = false)LocalDate startDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "endDate", required = false)LocalDate endDate,
			@RequestParam String searchPlace, @RequestParam(name = "user_id", required = false) String user_id, Model model, RedirectAttributes redirectAttributes){

		//遷移先画面の判定用セッションの取得
		String transition =(String)session.getAttribute("transition");

		//検索日付の取得
		DateSearch dateSearch = new DateSearch();
		dateSearch.setStartDate(startDate);
		dateSearch.setEndDate(endDate);
		if(searchPlace.isEmpty()) {
			dateSearch.setSearchPlace(null);
		}else {
			dateSearch.setSearchPlace(searchPlace);
		}

		//各情報を保管するクラス
		Informations informations = new Informations();

		//errorメッセージ用リスト
		List<String> msgList = new ArrayList<>();
		//エラーメッセージ用変数
		String msg = "該当データがありません。";

		//null判定用変数
		boolean missingPersons = true;
		boolean missingPersonsSightings = true;
		boolean suspiciousPersonSightings = true;
		boolean place = true;

		//検索日付が開始・終了ともに入力されているか
		if(dateSearch.getStartDate() == null) {
			msgList.add("開始日付を入力してください。");
		}
		if(dateSearch.getEndDate() == null) {
			msgList.add("終了日付を入力してください。");
		}

		//検索日付の終了日を開始日より未来ではないか
		if(!(dateSearch.getStartDate() == null) && !(dateSearch.getEndDate() == null)) {
			if(dateSearch.getEndDate().isBefore(dateSearch.getStartDate())) {
				msgList.add("検索日付に誤りがあります。");
			}
		}

		//開始・終了のどちらかの日付が指定されていない場合
		if(msgList.size() == 1) {

			//エラーメッセージリストをセット
			redirectAttributes.addFlashAttribute("msg", msgList);

			if(!(session.getAttribute("userInformation") == null)) {
				//リダイレクトでゲスト用不審者のみの検索ページへ遷移
				return "redirect:GuestsSuspiciousPersonSightingsSearch";
			}

			if(transition == null) {
				if(!(session.getAttribute("admin") == null)) {
					//リダイレクトで管理者用の掲載情報表示ページへ遷移
					return "redirect:InformationsAdmin";
				}
				//リダイレクトでall情報表示ページへ遷移
				return "redirect:Informations";
			}

			//リダイレクトで個別検索ページへ遷移
			switch(transition) {
			case "missingPersons":
				return "redirect:MissingPersonsSearch";

			case "missingPersonSightings":
				return "redirect:MissingPersonSightingsSearch";

			case "suspiciousPersonSightings":
				return "redirect:SuspiciousPersonSightingsSearch";
			}
		}

		//searchPlaceがnullかチェック
		if(dateSearch.getSearchPlace() == null) {
			place = false;
		}

		if(msgList.size() == 0 && !place) {
			informationRepository.setDateInformationTable(informations, dateSearch);
		}else if(msgList.size() == 2 && !place) {
			informationRepository.setInformationTable(informations);
		}else if(msgList.size() == 0 && place) {
			informationRepository.setDatePlaceInformationTable(informations, dateSearch);
		}else if(msgList.size() == 2 && place) {
			informationRepository.setPlaceInformationTable(informations, dateSearch);
		}

		//missing_personsがnullかチェック
		if(informations.getMissingPersonsList().size() == 0) {
			missingPersons = false;
		}

		//missing_persons_sightingsがnullかチェック
		if(informations.getMissingPersonsSightingsList().size() == 0) {
			missingPersonsSightings = false;
		}

		//suspicious_person_sightingsがnullかチェック
		if(informations.getSuspiciousPersonSightingsList().size() == 0) {
			suspiciousPersonSightings = false;
		}

		//検索の実行
		imSearchService.allSearchExcute(msgList, informations, missingPersons, missingPersonsSightings, suspiciousPersonSightings,
				place, redirectAttributes);

		//user_idの検索
		imSearchService.userIdSearchExcute(user_id, informations, redirectAttributes);

		//各リストのサイズが０の場合メッセージを出力
		if(informations.getMissingPersonsList().size() == 0) {
			redirectAttributes.addFlashAttribute("msg1", msg);
		}
		if(informations.getMissingPersonsSightingsList().size() == 0) {
			redirectAttributes.addFlashAttribute("msg2", msg);
		}
		if(informations.getSuspiciousPersonSightingsList().size() == 0) {
			redirectAttributes.addFlashAttribute("msg3", msg);
		}

		//画面遷移
		if(!(session.getAttribute("userInformation") == null)) {
			//リダイレクトでゲスト用不審者のみの検索ページへ遷移
			return "redirect:GuestsSuspiciousPersonSightingsSearch";
		}

		if(!(transition == null)) {
			//リダイレクトで個別検索ページへ遷移
			switch(transition) {
			case "missingPersons":
				return "redirect:MissingPersonsSearch";

			case "missingPersonSightings":
				return "redirect:MissingPersonSightingsSearch";

			case "suspiciousPersonSightings":
				return "redirect:SuspiciousPersonSightingsSearch";
			}
		}

		if(!(session.getAttribute("admin") == null)) {
			//リダイレクトで管理者用の掲載情報管理ページへ遷移
			return "redirect:InformationsAdmin";
		}

		//リダイレクトでall情報表示ページへ遷移
		return "redirect:Informations";
	}
}