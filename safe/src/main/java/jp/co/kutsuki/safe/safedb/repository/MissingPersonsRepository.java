package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.entity.User;

/**
 * missing_personsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface MissingPersonsRepository {
	
	public void setMissingPersonsTable(MissingPersons missingPersons);
	
	public ArrayList<MissingPersons> getMissingPersonsTable();
	
	public ArrayList<MissingPersons> getMissingPersonsTable(User user_id);
	
	public ArrayList<MissingPersons> getDateMissingPersonsTable(DateSearch dateSearch);
	
	public ArrayList<MissingPersons> getMissingPersonsIdTable(String id);
	
	public void Update(String id, MissingPersons missingPersons);
	
	public void Delete(String id);
	
}
