package jp.co.kutsuki.safe.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.Admin;
import jp.co.kutsuki.safe.safedb.repository.AdminRepository;
/**
 * adminテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class AdminDao implements AdminRepository{

	@Autowired
	private JdbcTemplate template;

	/** adminテーブルから
	 * end_flag==falseのみ1件取得  */
	@Override
	public Admin getAdminTable(String admin_id) {
		//SQL定義
		String sql = " select * from admin where admin_id = ? and end_flag = false";
		//SQL実行し1件取得を実施
		SqlRowSet rs = template.queryForRowSet(sql,admin_id);
		//結果を取得
		Admin admin = new Admin();
		while(rs.next()) {
			admin.setId(rs.getInt("id"));
			admin.setAdmin_id(rs.getString("admin_id"));
			admin.setPassword(rs.getString("password"));
			admin.setEnd_flag(rs.getBoolean("end_flag"));
		}

		//条件に一致するadmin_idがなかった場合
		//NullPointerExceptionを回避するために"none"を代入している
		if(admin.getId() == null) {
			admin.setAdmin_id("none");
			admin.setPassword("none");
		}
		return admin;
	}

	/** adminテーブルに1件登録 */
	@Transactional
	@Override
	public void setAdminTable(Admin admin) {
		//SQL定義
		String sql = " insert into admin(admin_id, password) values(?, ?)";
		//SQL実行し登録を実施
		template.update(sql, admin.getAdmin_id(), admin.getPassword());
	}

	/** idで指定された行のend_flagにtrueをセットする */
	@Transactional
	@Override
	public void Delete(Integer id) {
		//SQL定義
		String sql = "update admin set end_flag = true where id = ?";
		//SQL実行し登録を実施
		template.update(sql, id);
	}

}
