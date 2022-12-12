package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.User;

/**
 * usersテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface UserRepository {
	
	public FormLogin getUser(String user_id);

	public User getUserTable(String user_id);
	
	public ArrayList<User> getAllUserTable();
	
	public User getOneUserTable(Integer id);

	public void setUserTable(User user);

	public void deleteUser(Integer id);
	
	public void updatePassword(String user_id, String password);
}
