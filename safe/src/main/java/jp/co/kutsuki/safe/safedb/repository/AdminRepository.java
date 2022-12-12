package jp.co.kutsuki.safe.safedb.repository;

import jp.co.kutsuki.safe.entity.Admin;

/**
 * adminテーブルのリポジトリ
 * @author kutsuki
 *
 */

public interface AdminRepository {

	public Admin getAdminTable(String admin_id);

	public void setAdminTable(Admin admin);

	public void Delete(Integer id);

}
