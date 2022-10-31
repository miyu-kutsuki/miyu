package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersons;

/**
 * missing_personsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface MissingPersonsRepository {
	
	public void setMissingPersonsTable(MissingPersons missingPersons) ;
	
	public ArrayList<MissingPersons> getMissingPersonsTable() ;
	
	public ArrayList<MissingPersons> getDateMissingPersonsTable(DateSearch dateSearch) ;
	
}
