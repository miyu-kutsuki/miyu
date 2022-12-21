package jp.co.kutsuki.safe.change;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.im.service.ImChangeCheckService;
/**
 * ユーザーID・パスワード忘れの共通チェック業務ロジッククラス
 * @author kutsuki
 *
 */
@Service
public class ChangeCheckService implements ImChangeCheckService{

	/** 共通nullチェック */
	@Override
	public void nullcheckExcute(List<String> msg, String familyName, String firstName, LocalDate birthday,
			String email, Integer questions, String answer) {

		if(familyName.isEmpty()) {
			msg.add("名字が入力されていません。");
		}

		if(firstName.isEmpty()) {
			msg.add("名前が入力されていません。");
		}

		if(birthday == null) {
			msg.add("生年月日入力されていません。");
		}

		if(email.isEmpty()) {
			msg.add("メールアドレスが入力されていません。");
		}

		if(questions == null) {
			msg.add("秘密の質問が選択されていません。");
		}

		if(answer.isEmpty()) {
			msg.add("秘密の質問の答えが入力されていません。");
		}
	}

	/** 共通チェック */
	@Override
	public void checkExcute(List<String> msg, User userList, String familyName, String firstName, LocalDate birthday,
			String email, Integer questions, String answer) {

		if(!familyName.equals(userList.getFamilyName())) {
			msg.add("名字が違います。");
		}

		if(!firstName.equals(userList.getFirstName())) {
			msg.add("名前が違います。");
		}

		if(!birthday.equals(userList.getBirthday())) {
			msg.add("生年月日が違います。");
		}

		if(!email.equals(userList.getEmail())) {
			msg.add("メールアドレスが違います。");
		}

		if(!questions.equals(userList.getQuestion_id())) {
			msg.add("秘密の質問が違います。");
		}

		if(!answer.equals(userList.getAnswer())) {
			msg.add("秘密の質問の答えが違います。");
		}
	}

}
