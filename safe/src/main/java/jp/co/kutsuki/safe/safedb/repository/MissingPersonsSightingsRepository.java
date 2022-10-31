package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;

/**
 * missing_persons_sightingsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface MissingPersonsSightingsRepository {
	
	public void setMissingPersonsSightingsTable(MissingPersonsSightings missingPersonsSightings);
	
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable();
	
	public ArrayList<MissingPersonsSightings> getDateMissingPersonsSightingsTable(DateSearch dateSearch);
}
