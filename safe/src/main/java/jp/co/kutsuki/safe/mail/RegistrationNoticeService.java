package jp.co.kutsuki.safe.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.im.service.ImMailSendService;
import jp.co.kutsuki.safe.im.service.ImRegistrationNoticeService;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

public class RegistrationNoticeService implements ImRegistrationNoticeService{
	
	@Autowired
	MissingPersonsRepository missingPersonsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ImMailSendService mailSendService;

	/** 行方不明者目撃情報の登録時、
	 * 目撃場所の県・市と行方不明者情報の県・市が一致したユーザーにメールでお知らせする */
	@Override
	public void missingPersonsRegistrationNoticeMailSend(MissingPersonsSightings missingPersonsSightings) {
		//user_idを保管するMap
		Map<String, String> users = new HashMap<>();
		//データベースからend_flagがfalse以外の情報を全て取得
		ArrayList<MissingPersons> missingPersonsList = missingPersonsRepository.getMissingPersonsTable();
		//ユーザー情報の一時保管用
		User user = new User();
		
		//入力された県・市とmissing_personsデータベースに保管されている県・市が一致しているレコードがあるかチェック
		//一致したMapに追加する
		for(int i = 0; i < missingPersonsList.size(); i++) {
			if(missingPersonsSightings.getPrefectures().equals(missingPersonsList.get(i).getPrefectures())) {
				user = userRepository.getUserIdTable(missingPersonsList.get(i).getUser_id());
				users.put(user.getUser_id(), user.getEmail());
			}
		}
		
		//Mapに保存されているuserに通知メールを送る
		for(int i = 0; i < users.size(); i++) {
			mailSendService.mailSend(user, "行方不明者目撃情報の新着のお知らせ", "/mail/registrationNoticeTemplate.txt");
		}
	}

}
