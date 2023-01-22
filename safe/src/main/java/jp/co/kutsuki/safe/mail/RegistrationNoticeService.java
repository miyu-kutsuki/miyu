package jp.co.kutsuki.safe.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.im.service.ImMailSendService;
import jp.co.kutsuki.safe.im.service.ImRegistrationNoticeService;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;
/**
 * 通知メールの業務ロジック
 * @author kutsuki
 *
 */
@Service
public class RegistrationNoticeService implements ImRegistrationNoticeService{
	
	@Autowired
	MissingPersonsRepository missingPersonsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ImMailSendService mailSendService;
	
	@Autowired
	HttpSession session;

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
		//ログイン中のuser_idを取得
		FormLogin userInformation = (FormLogin) session.getAttribute("user");
		
		//入力された県・市とmissing_personsデータベースに保管されている県・市が一致しているレコードがあるかチェック
		//一致したらMapに追加する
		for(int i = 0; i < missingPersonsList.size(); i++) {
			//県名が一致しているかチェック
			if(missingPersonsSightings.getPrefectures().equals(missingPersonsList.get(i).getPrefectures())) {
				//市区町村が一致しているかチェック
				if(missingPersonsSightings.getMunicipalities().equals(missingPersonsList.get(i).getMunicipalities())) {
					user = userRepository.getUserIdTable(missingPersonsList.get(i).getUser_id());
					users.put(user.getUser_id(), user.getEmail());
				}
			}
		}
		
		// keySetでListを作成
		List<String> keys = new ArrayList<>(users.keySet());
		
		//Mapに保存されているuserに通知メールを送る
		//重複送信防止のためMapを使用
		for(int i = 0; i < keys.size(); i++) {
			//データの登録者以外にメールを送信する
			if(!keys.get(i).equals(userInformation.getUser_id())) {
				user = userRepository.getUserIdTable(keys.get(i));
				mailSendService.mailSend(user, "行方不明者目撃情報の新着のお知らせ", "/mail/missingPersonsRegistrationNoticeTemplate.txt");
			}
		}
	}
	
	/** 不審者情報の登録時、
	 * 目撃場所の県・市と不審者情報の通知設定がtrueかつ県・市が一致したユーザーにメールでお知らせする */
	@Override
	public void suspiciousPersonSightingsRegistrationNoticeMailSend(SuspiciousPersonSightings suspiciousPersonSightings) {
		//user_idを保管するMap
		Map<String, String> users = new HashMap<>();
		//データベースからend_flagがfalse以外の情報を全て取得
		ArrayList<User> usersList = userRepository.getAllUserTable();
		//ユーザー情報の一時保管用
		User user = new User();
		//ログイン中のuser_idを取得
		FormLogin userInformation = new FormLogin();
		if(session.getAttribute("user") == null) {
			userInformation = (FormLogin) session.getAttribute("userInformation");
		}else {
			userInformation = (FormLogin) session.getAttribute("user");
		}
		
		//入力された県・市とusersデータベースに保管されている県・市が一致しているレコードがあるかチェック
		//一致したらMapに追加する
		for(int i = 0; i < usersList.size(); i++) {
			//通知設定がtrueかチェック
			if(usersList.get(i).getNotification()) {
				//県名が一致しているかチェック
				if(suspiciousPersonSightings.getPrefectures().equals(usersList.get(i).getNotification_p())) {
					//市区町村が一致しているかチェック
					if(suspiciousPersonSightings.getMunicipalities().equals(usersList.get(i).getNotification_m())) {
						users.put(usersList.get(i).getUser_id(), usersList.get(i).getEmail());
					}
				}
			}
		}
		
		// keySetでListを作成
		List<String> keys = new ArrayList<>(users.keySet());
		
		//Mapに保存されているuserに通知メールを送る
		//重複送信防止のためMapを使用
		for(int i = 0; i < keys.size(); i++) {
			//データの登録者以外にメールを送信する
			if(!keys.get(i).equals(userInformation.getUser_id())) {
				user = userRepository.getUserIdTable(keys.get(i));
				mailSendService.mailSend(user, "不審者情報の新着のお知らせ", "/mail/suspiciousPersonSightingsRegistrationNoticeTemplate.txt");
			}
		}
	}

}
