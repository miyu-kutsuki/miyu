package jp.co.kutsuki.safe.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import jp.co.kutsuki.safe.entity.User;

/**
 * userテーブルのDAOクラス
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
		//SQL実行し1件取得を実施、結果を取得
		SqlRowSet rs = template.queryForRowSet(sql,user_id);
		
		System.out.println("--1件取得開始--");
		User user = new User();
		while(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUser_id(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setEnd_flag(rs.getBoolean("end_flag"));
		}
		System.out.println("--1件取得終了--");
		
		return user;
	}

}
