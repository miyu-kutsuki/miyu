package jp.co.kutsuki.safe.im.service;

import java.time.LocalDate;
import java.util.List;

import jp.co.kutsuki.safe.entity.User;
/**
 * ChangeCheckServiceクラスのインタフェース
 * @author kutsuki
 *
 */
public interface ImChangeCheckService {
	
	public void nullcheckExcute(List<String> msg, String familyName, String firstName, LocalDate birthday,
			String email, Integer questions, String answer);
	
	public void checkExcute(List<String> msg, User userList, String familyName, String firstName, LocalDate birthday,
			String email, Integer questions, String answer);

}
