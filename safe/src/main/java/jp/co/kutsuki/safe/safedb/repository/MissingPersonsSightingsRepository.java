package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.User;

/**
 * missing_persons_sightingsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface MissingPersonsSightingsRepository {
	
	public void setMissingPersonsSightingsTable(MissingPersonsSightings missingPersonsSightings);
	
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable();
	
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable(User user_id);
	
	public ArrayList<MissingPersonsSightings> getDateMissingPersonsSightingsTable(DateSearch dateSearch);
	
	public ArrayList<MissingPersonsSightings> getPlaceMissingPersonsSightingsTable(DateSearch  dateSearch);
	
	public ArrayList<MissingPersonsSightings> getDatePlaceMissingPersonsSightingsTable(DateSearch dateSearch);
	
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsIdTable(String id);
	
	public void Update(String id, MissingPersonsSightings missingPersonsSightings);
	
	public void Delete(String id);
}