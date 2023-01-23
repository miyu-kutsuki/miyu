package jp.co.kutsuki.safe.controller.Informationchange;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * 不審者情報の通知設定変更用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class ChangeNotificationAction {
	
	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/ChangeNotification", method = RequestMethod.POST)
	public String changeNotification(@RequestParam(name = "notification", required = false) Boolean notification, @RequestParam String notification_p,
			@RequestParam String notification_m, RedirectAttributes redirectAttributes) {
		
		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}
		
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		if(!(notification == null)) {
			FormLogin user = (FormLogin)session.getAttribute("user");
			userRepository.updateNotification(user.getUser_id(), notification);
			msg.add("不審者情報の通知設定が変更されました。");
		}
		
		if(!notification_p.isEmpty()) {
			FormLogin user = (FormLogin)session.getAttribute("user");
			//全角スペースを空文字に置換
			notification_p = notification_p.replaceAll("　", "");
			//文字の間の半角スペースを空文字に変換
			notification_p = notification_p.replaceAll(" ", "");
			userRepository.updateNotificationPrefectures(user.getUser_id(), notification_p);
			msg.add("不審者情報の通知設定が変更されました。");
		}
		
		if(!notification_m.isEmpty()) {
			FormLogin user = (FormLogin)session.getAttribute("user");
			//全角スペースを空文字に置換
			notification_m = notification_m.replaceAll("　", "");
			//文字の間の半角スペースを空文字に変換
			notification_m = notification_m.replaceAll(" ", "");
			userRepository.updateNotificationMunicipalities(user.getUser_id(), notification_m);
			msg.add("不審者情報の通知設定が変更されました。");
		}
		
		return "redirect:UserInformationChangeScreen";
	}
}
