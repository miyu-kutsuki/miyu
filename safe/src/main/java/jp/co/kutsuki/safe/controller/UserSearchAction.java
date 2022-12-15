package jp.co.kutsuki.safe.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * 管理者画面用のユーザー検索ボタン用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserSearchAction {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/UserSearch", method = RequestMethod.POST)
	public String pageView(@RequestParam String user_id, RedirectAttributes redirectAttributes, Model model) {
		
		//セッション有効チェック
		if(session.getAttribute("admin") == null) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:LoginAdmin";
		}
		
		//エラーメッセージ用変数
		String msg = "登録データがありません。";

		//ユーザーテーブルを全件取得
		ArrayList<User> userList = userRepository.getAllUserTable();
		
		//user_idの検索
		if(!user_id.isEmpty()) {
			//一時保管用のリスト
			ArrayList<User> user = new ArrayList<>();
			
			//ユーザー情報リストから"user_id"が一致するものを抽出する
			for(int i = 0; i < userList.size(); i++) {
				if(userList.get(i).getUser_id().contains(user_id)) {
					user.add(userList.get(i));
				}
			}
			//データの入れ替え
			userList.clear();
			userList.addAll(user);
			
			//各リストのサイズが０の場合メッセージを出力
			if(userList.size() == 0) {
				redirectAttributes.addFlashAttribute("msg1", msg);
			}
		}
		session.setAttribute("userList", userList);
		
		return "redirect:UsersInformationsAdmin";
	}
}
