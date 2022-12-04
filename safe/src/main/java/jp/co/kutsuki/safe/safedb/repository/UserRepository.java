package jp.co.kutsuki.safe.safedb.repository;

import jp.co.kutsuki.safe.entity.User;

/**
 * usersテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface UserRepository {

	public User getUserTable(String user_id);

	public void setUserTable(User user);

	public void Delete(Integer id);
}
