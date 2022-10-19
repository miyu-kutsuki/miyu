package jp.co.kutsuki.safe.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import jp.co.kutsuki.safe.entity.User;

/**
 * usersテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class UserDataDao {
	
	@Autowired
	private JdbcTemplate template;
	
	/** userテーブルから1件取得  */
	public User getUserTable(String user_id) {
		//SQL定義
		String sql = " select * from users where user_id = ? ";
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
	
	/** userテーブルに1件登録 
	 * rsが０なら正常、０以外なら異常終了 */
	public int setUserTable(User user) {
		//SQL定義
		String sql = " insert into users(user_id, password) values(?, ?)";
		//SQL実行し登録を実施
		int rs = template.update(sql, user.getUser_id(), user.getPassword());
		
		return rs;
	}

}
