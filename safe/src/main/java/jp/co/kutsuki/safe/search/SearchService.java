package jp.co.kutsuki.safe.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.im.service.ImSearchService;

/**
 * SearchActioクラスの業務ロジッククラス
 * @author kutsuki
 *
 */
@Service
public class SearchService implements ImSearchService{

	/** 全体の検索を実行するメソッド */
	@Override
	public void allSearchExcute(List<String> msgList, Informations informations, boolean missingPersons,boolean missingPersonsSightings, 
			boolean suspiciousPersonSightings, boolean place, RedirectAttributes redirectAttributes) {
		
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
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && !place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

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
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && !place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしてないかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

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
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 0 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 0 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && place) {
			//日付の範囲を指定しているかつ
			//場所名の指定はしているかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

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
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());

		}else if(msgList.size() == 2 && missingPersons && !missingPersonsSightings && !suspiciousPersonSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_personsがnullではないかつ
			//missing_persons_sightings・suspicious_person_sightingテーブルがnullの場合
			//missing_personsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && !missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons_sightingsがnullではないかつ
			//missing_persons・suspicious_person_sightingテーブルがnullの場合
			//missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersonsSightings && missingPersons && !suspiciousPersonSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons・missing_persons_sightingsがnullではないかつ
			//suspicious_person_sightingsテーブルがnullの場合
			//missing_persons・missing_persons_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());

		}else if(msgList.size() == 2 && missingPersons && suspiciousPersonSightings && !missingPersonsSightings && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons・suspicious_person_sightingsがnullではないかつ
			//missing_persons_sightingsテーブルがnullの場合
			//missing_persons・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonsList", informations.getMissingPersonsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());
			
		}else if(msgList.size() == 2 && missingPersonsSightings && suspiciousPersonSightings && !missingPersons && place) {
			//日付の範囲指定がないかつ
			//場所名の指定はしているかつ
			//missing_persons_sightings・suspicious_person_sightingsがnullではないかつ
			//missing_personsテーブルがnullの場合
			//missing_persons_sightings・suspicious_person_sightingsのend_flag==true以外を全件取得
			redirectAttributes.addFlashAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
			redirectAttributes.addFlashAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());
		}
	}

	/** user_idの検索を実行するメソッド */
	@Override
	public void userIdSearchExcute(String user_id, Informations informations, RedirectAttributes redirectAttributes) {
		
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
		}		
	}
}
