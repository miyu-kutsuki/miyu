package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;

/**
 * missing_persons_sightingsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface MissingPersonsSightingsRepository {

	public void setMissingPersonsSightingsTable(MissingPersonsSightings missingPersonsSightings);

	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable();

	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable(FormLogin user_id);

	public ArrayList<MissingPersonsSightings> getDateMissingPersonsSightingsTable(DateSearch dateSearch);

	public ArrayList<MissingPersonsSightings> getPlaceMissingPersonsSightingsTable(DateSearch  dateSearch);

	public ArrayList<MissingPersonsSightings> getDatePlaceMissingPersonsSightingsTable(DateSearch dateSearch);

	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsIdTable(String id);

	public void update(String id, MissingPersonsSightings missingPersonsSightings);

	public void delete(String id);

	public void deleteUser(String user_id);
}