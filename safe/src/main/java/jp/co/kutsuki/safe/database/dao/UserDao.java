package jp.co.kutsuki.safe.database.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	 * end_flag==falseのみ1件取得  */
	@Override
	public User getUserTable(String user_id) {
		//SQL定義
		String sql = " select * from users where user_id = ? and end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql,user_id);
		//結果を取得
		User user = new User();
		while(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setEnd_flag(rs.getBoolean("end_flag"));
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
	 * end_flag==falseのみ全件取得  */
	@Override
	public ArrayList<User> getAllUserTable() {
		//SQL定義
		String sql = " select * from users where end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<User> userList = new ArrayList<>();
		while(rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
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
		String sql = " select * from users where id = ? and end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, id);
		//結果を取得
		User user = new User();
		while(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setEnd_flag(rs.getBoolean("end_flag"));
		}
		return user;
	}

	/** userテーブルに1件登録 */
	@Transactional
	@Override
	public void setUserTable(User user) {
		//SQL定義
		String sql = " insert into users(user_id, password) values(?, ?)";
		//SQL実行し登録を実施
		template.update(sql, user.getUser_id(), user.getPassword());
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

}