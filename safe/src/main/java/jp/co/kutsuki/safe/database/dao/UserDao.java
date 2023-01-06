package jp.co.kutsuki.safe.database.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * usersテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class UserDao implements UserRepository{

	@Autowired
	private JdbcTemplate template;

	/** userテーブルから
	 * id, user_id, password
	 * end_flag==falseのみ1件取得  */
	@Override
	public FormLogin getUser(String user_id) {
		//SQL定義
		String sql = " select id, pgp_sym_decrypt(user_id, get_passwd())as user_id,"
				+ "pgp_sym_decrypt(password, get_passwd())as password"
				+ " from users where pgp_sym_decrypt(user_id, get_passwd()) = ? and end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql,user_id);
		//結果を取得
		FormLogin user = new FormLogin();
		while(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
		}

		//条件に一致するuser_idがなかった場合
		//NullPointerExceptionを回避するために"none"を代入している
		if(user.getId() == null) {
			user.setUser_id("none");
			user.setPassword("none");
		}
		return user;
	}

	/** userテーブルから
	 * end_flag==falseのみ1件取得  */
	@Override
	public User getUserIdTable(String user_id) {
		String sql = " select id, pgp_sym_decrypt(user_id, get_passwd())as user_id,"
				+ "pgp_sym_decrypt(password, get_passwd())as password, notification, notification_p, notification_m, "
				+ "pgp_sym_decrypt(familyName, get_passwd())as familyName, "
				+ "pgp_sym_decrypt(firstName, get_passwd())as firstName, pgp_sym_decrypt(birthday, get_passwd())as birthday, "
				+ "pgp_sym_decrypt(email, get_passwd())as email, question_id, question, pgp_sym_decrypt(answer, get_passwd())as answer, end_flag"
				+ " from users where pgp_sym_decrypt(user_id, get_passwd()) = ? and end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql,user_id);
		//結果を取得
		User user = new User();
		while(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setNotification(rs.getBoolean("notification"));
			user.setNotification_p(rs.getString("notification_p"));
			user.setNotification_m(rs.getString("notification_m"));
			user.setFamilyName(rs.getString("familyName"));
			user.setFirstName(rs.getString("firstName"));
			try {
				//String型からDate型へ変換
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(rs.getString("birthday"));
				// Date型からLocaldate型へ変換
				LocalDate birthday = new java.sql.Date(date.getTime()).toLocalDate();
				user.setBirthday(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setEmail(rs.getString("email"));
			user.setQuestion_id(rs.getInt("question_id"));
			user.setQuestion(rs.getString("question"));
			user.setAnswer(rs.getString("answer"));
			user.setEnd_flag(rs.getBoolean("end_flag"));
		}

		//条件に一致するuser_idがなかった場合
		//NullPointerExceptionを回避するために"none"を代入している
		if(user.getId() == null) {
			user.setUser_id("none");
			user.setPassword("none");
			user.setNotification(false);
			user.setNotification_p("none");
			user.setNotification_m("none");
			user.setFamilyName("none");
			user.setFirstName("none");
			// Date型からLocaldate型へ変換
			LocalDate birthday = new java.sql.Date(1111-11-11).toLocalDate();
			user.setBirthday(birthday);
			user.setEmail("none");
			user.setQuestion_id(-1);
			user.setQuestion("none");
			user.setAnswer("none");

		}
		return user;
	}

	/** userテーブルから
	 * end_flag==falseのみ1件取得  */
	@Override
	public User getUserPassTable(Integer id, String password) {
		//SQL定義
		String sql = " select id, pgp_sym_decrypt(user_id, get_passwd())as user_id,"
				+ "pgp_sym_decrypt(password, get_passwd())as password, notification, notification_p, notification_m, "
				+ "pgp_sym_decrypt(familyName, get_passwd())as familyName, "
				+ "pgp_sym_decrypt(firstName, get_passwd())as firstName, pgp_sym_decrypt(birthday, get_passwd())as birthday, "
				+ "pgp_sym_decrypt(email, get_passwd())as email, question_id, question, pgp_sym_decrypt(answer, get_passwd())as answer, end_flag"
				+ " from users where id = ? and pgp_sym_decrypt(password, get_passwd()) = ? and end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql,id, password);
		//結果を取得
		User user = new User();
		while(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setNotification(rs.getBoolean("notification"));
			user.setNotification_p(rs.getString("notification_p"));
			user.setNotification_m(rs.getString("notification_m"));			
			user.setFamilyName(rs.getString("familyName"));
			user.setFirstName(rs.getString("firstName"));
			try {
				//String型からDate型へ変換
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(rs.getString("birthday"));
				// Date型からLocaldate型へ変換
				LocalDate birthday = new java.sql.Date(date.getTime()).toLocalDate();
				user.setBirthday(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setEmail(rs.getString("email"));
			user.setQuestion_id(rs.getInt("question_id"));
			user.setQuestion(rs.getString("question"));
			user.setAnswer(rs.getString("answer"));
			user.setEnd_flag(rs.getBoolean("end_flag"));
		}

		//条件に一致するuser_idがなかった場合
		//NullPointerExceptionを回避するために"none"を代入している
		if(user.getId() == null) {
			user.setUser_id("none");
			user.setPassword("none");
			user.setNotification(false);
			user.setNotification_p("none");
			user.setNotification_m("none");
			user.setFamilyName("none");
			user.setFirstName("none");
			// Date型からLocaldate型へ変換
			LocalDate birthday = new java.sql.Date(1111-11-11).toLocalDate();
			user.setBirthday(birthday);
			user.setEmail("none");
			user.setQuestion_id(-1);
			user.setQuestion("none");
			user.setAnswer("none");

		}
		return user;
	}

	/** userテーブルから
	 * end_flag==falseのみ全件取得  */
	@Override
	public ArrayList<User> getAllUserTable() {
		//SQL定義
		String sql = " select id, pgp_sym_decrypt(user_id, get_passwd())as user_id,"
				+ "pgp_sym_decrypt(password, get_passwd())as password, notification, notification_p, notification_m, "
				+ "pgp_sym_decrypt(familyName, get_passwd())as familyName, "
				+ "pgp_sym_decrypt(firstName, get_passwd())as firstName, pgp_sym_decrypt(birthday, get_passwd())as birthday, "
				+ "pgp_sym_decrypt(email, get_passwd())as email, question_id, question, pgp_sym_decrypt(answer, get_passwd())as answer, end_flag"
				+ " from users where end_flag = false order by id ASC";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<User> userList = new ArrayList<>();
		while(rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setNotification(rs.getBoolean("notification"));
			user.setNotification_p(rs.getString("notification_p"));
			user.setNotification_m(rs.getString("notification_m"));			
			user.setFamilyName(rs.getString("familyName"));
			user.setFirstName(rs.getString("firstName"));
			try {
				//String型からDate型へ変換
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(rs.getString("birthday"));
				// Date型からLocaldate型へ変換
				LocalDate birthday = new java.sql.Date(date.getTime()).toLocalDate();
				user.setBirthday(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setEmail(rs.getString("email"));
			user.setQuestion_id(rs.getInt("question_id"));
			user.setQuestion(rs.getString("question"));
			user.setAnswer(rs.getString("answer"));
			user.setEnd_flag(rs.getBoolean("end_flag"));
			userList.add(user);
			}
		return userList;
	}

	/** userテーブルからidで指定されたレコードかつ
	 * end_flag==falseのみ取得  */
	@Override
	public User getOneUserTable(Integer id) {
		//SQL定義
		String sql = " select id, pgp_sym_decrypt(user_id, get_passwd())as user_id,"
				+ "pgp_sym_decrypt(password, get_passwd())as password, notification, notification_p, notification_m, "
				+ "pgp_sym_decrypt(familyName, get_passwd())as familyName, "
				+ "pgp_sym_decrypt(firstName, get_passwd())as firstName, pgp_sym_decrypt(birthday, get_passwd())as birthday, "
				+ "pgp_sym_decrypt(email, get_passwd())as email, question_id, question, pgp_sym_decrypt(answer, get_passwd())as answer, end_flag"
				+ " from users where id = ? and end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, id);
		//結果を取得
		User user = new User();
		while(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setNotification(rs.getBoolean("notification"));
			user.setNotification_p(rs.getString("notification_p"));
			user.setNotification_m(rs.getString("notification_m"));			
			user.setFamilyName(rs.getString("familyName"));
			user.setFirstName(rs.getString("firstName"));
			try {
				//String型からDate型へ変換
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(rs.getString("birthday"));
				// Date型からLocaldate型へ変換
				LocalDate birthday = new java.sql.Date(date.getTime()).toLocalDate();
				user.setBirthday(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setEmail(rs.getString("email"));
			user.setQuestion_id(rs.getInt("question_id"));
			user.setQuestion(rs.getString("question"));
			user.setAnswer(rs.getString("answer"));
			user.setEnd_flag(rs.getBoolean("end_flag"));
		}
		return user;
	}

	/** userテーブルに1件登録 */
	@Transactional
	@Override
	public void setUserTable(User user) {
		//SQL定義
		String sql = " insert into users(user_id, password, notification, notification_p, notification_m, "
				+ "familyName, firstName, birthday, email, question_id, question, answer)"
				+ " values(pgp_sym_encrypt(?, get_passwd()), pgp_sym_encrypt(?, get_passwd()), ?, ?, ?,"
				+ " pgp_sym_encrypt(?, get_passwd()), pgp_sym_encrypt(?, get_passwd()), pgp_sym_encrypt(?, get_passwd()),"
				+ " pgp_sym_encrypt(?, get_passwd()), ?, ?, pgp_sym_encrypt(?, get_passwd()))";

		//データの暗号化の関係上LocalDate型を一度Stringに変換する
		String birthday = user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//SQL実行し登録を実施
		template.update(sql, user.getUser_id(), user.getPassword(),user.getNotification(), user.getNotification_p(), 
				user.getNotification_m(), user.getFamilyName(), 
				user.getFirstName(), birthday, user.getEmail(), user.getQuestion_id(), user.getQuestion(), user.getAnswer());
	}

	/** idで指定された行のend_flagにtrueをセットする */
	@Transactional
	@Override
	public void deleteUser(Integer id) {
		//SQL定義
		String sql = "update users set end_flag = true where id = ?";
		//SQL実行し登録を実施
		template.update(sql, id);
	}

	/** user_idで指定された行のpasswordを変更する */
	@Transactional
	@Override
	public void updatePassword(String user_id, String password) {
		//SQL定義
		String sql = "update users set password = pgp_sym_encrypt(?, get_passwd())"
				+ " where pgp_sym_decrypt(user_id, get_passwd()) = ?";
		//SQL実行し登録を実施
		template.update(sql, password, user_id);
	}

	/** user_idで指定された行の名字を変更する */
	@Transactional
	@Override
	public void updateFamilyName(String user_id, String familyName) {
		//SQL定義
		String sql = "update users set familyName = pgp_sym_encrypt(?, get_passwd())"
				+ " where pgp_sym_decrypt(user_id, get_passwd()) = ?";
		//SQL実行し登録を実施
		template.update(sql, familyName, user_id);
	}

	/** user_idで指定された行のメールアドレスを変更する */
	@Transactional
	@Override
	public void updateMailAddress(String user_id, String mailAddress) {
		//SQL定義
		String sql = "update users set email = pgp_sym_encrypt(?, get_passwd())"
				+ " where pgp_sym_decrypt(user_id, get_passwd()) = ?";
		//SQL実行し登録を実施
		template.update(sql, mailAddress, user_id);
	}

	/** user_idで指定された行の不審者情報の通知設定を変更する */
	@Transactional
	@Override
	public void updateNotification(String user_id, Boolean notification) {
		//SQL定義
		String sql = "update users set notification = ?"
				+ " where pgp_sym_decrypt(user_id, get_passwd()) = ?";
		//SQL実行し登録を実施
		template.update(sql, notification, user_id);
	}
	
	/** user_idで指定された行の不審者情報の通知設定(県名)を変更する */
	@Transactional
	@Override
	public void updateNotificationPrefectures(String user_id, String prefectures) {
		//SQL定義
		String sql = "update users set notification_p = ?"
				+ " where pgp_sym_decrypt(user_id, get_passwd()) = ?";
		//SQL実行し登録を実施
		template.update(sql, prefectures, user_id);
	}
	
	/** user_idで指定された行の不審者情報の通知設定(市区町村)を変更する */
	@Transactional
	@Override
	public void updateNotificationMunicipalities(String user_id, String municipalities) {
		//SQL定義
		String sql = "update users set notification_m = ?"
				+ " where pgp_sym_decrypt(user_id, get_passwd()) = ?";
		//SQL実行し登録を実施
		template.update(sql, municipalities, user_id);
	}
}