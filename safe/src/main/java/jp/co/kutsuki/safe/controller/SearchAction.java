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
import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
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
	HttpSession session;


	@RequestMapping(value="/Search", method = RequestMethod.POST)
	public String SearchPageView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "startDate", required = false)LocalDate startDate,
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

		//msgのサイズ0かチェック
		if(msgList.size() == 0 && missingPersons && missingPersonsSightings && suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingsテーブルがnullではない場合
			//end_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && suspiciousPersonSightings && !missingPersons && !missingPersonsSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightings・missing_personsテーブルがnullの場合
			//suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && !missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);

		}else if(msgList.size() == 2 && missingPersons && missingPersonsSightings && suspiciousPersonSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullではない場合
			//end_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && suspiciousPersonSightings && !missingPersons && !missingPersonsSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightings・missing_personsテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && !missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
		}else if(msgList.size() == 0 && missingPersons && missingPersonsSightings && suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingsテーブルがnullではない場合
			//end_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && suspiciousPersonSightings && !missingPersons && !missingPersonsSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightings・missing_personsテーブルがnullの場合
			//suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && !missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);

		}else if(msgList.size() == 2 && missingPersons && missingPersonsSightings && suspiciousPersonSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullではない場合
			//end_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && suspiciousPersonSightings && !missingPersons && !missingPersonsSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightings・missing_personsテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg3", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && !missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && place) {
			//日付の範囲を指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			redirectAttributes.addFlashAttribute("msg1", msg);
			redirectAttributes.addFlashAttribute("msg2", msg);
			redirectAttributes.addFlashAttribute("msg3", msg);
		}
		
		//user_idの検索
		if(!(user_id == null)) {
			//一時保管用のリスト
			ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
			ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
			ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
			
			//探し人のリストから"user_id"が一致するものを抽出する
			for(int i = 0; i < informations.getMissingPersonsList().size(); i++) {
				if(informations.getMissingPersonsList().get(i).getUser_id().contains(user_id)) {
					missingPersonsList.add(informations.getMissingPersonsList().get(i));
				}
			}
			//データの入れ替え
			informations.getMissingPersonsList().clear();
			informations.setMissingPersonsList(missingPersonsList);
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			
			//探し人目撃情報リストから"user_id"が一致するものを抽出する
			for(int i = 0; i < informations.getMissingPersonsSightingsList().size(); i++) {
				if(informations.getMissingPersonsSightingsList().get(i).getUser_id().contains(user_id)) {
					missingPersonsSightingsList.add(informations.getMissingPersonsSightingsList().get(i));
				}
			}
			//データの入れ替え
			informations.getMissingPersonsSightingsList().clear();
			informations.setMissingPersonsSightingsList(missingPersonsSightingsList);
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			
			//不審者目撃情報リストから"user_id"が一致するものを抽出する
			for(int i = 0; i < informations.getSuspiciousPersonSightingsList().size(); i++) {
				if(informations.getSuspiciousPersonSightingsList().get(i).getUser_id().contains(user_id)) {
					suspiciousPersonSightingsList.add(informations.getSuspiciousPersonSightingsList().get(i));
				}
			}
			//データの入れ替え
			informations.getSuspiciousPersonSightingsList().clear();
			informations.setSuspiciousPersonSightingsList(suspiciousPersonSightingsList);
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());
			
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