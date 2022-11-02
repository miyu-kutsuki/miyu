package jp.co.kutsuki.safe.database.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsSightingsRepository;

/**
 * missing_persons_sightingsテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class MissingPersonsSightingsDao implements MissingPersonsSightingsRepository{
	
	@Autowired
	private JdbcTemplate template;
	
	/** missing_persons_sightingsテーブルに1件登録 */
	@Transactional
	public void setMissingPersonsSightingsTable(MissingPersonsSightings missingPersonsSightings) {
		//SQL定義
		String sql = "insert into missing_persons_sightings(date, gender, age, detail, prefectures, municipalities, other, user_id) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		//SQL実行し登録を実施
		template.update(sql, missingPersonsSightings.getDate(), missingPersonsSightings.getGender(), missingPersonsSightings.getAge(),
				missingPersonsSightings.getDetail(), missingPersonsSightings.getPrefectures(), 
				missingPersonsSightings.getMunicipalities(), missingPersonsSightings.getOther(), missingPersonsSightings.getUser_id());
	}
	
	/** missing_persons_sightingsテーブルから
	 * end_flag==falseのみ全件取得 */
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable() {
		//SQL定義
		String sql = " select * from missing_persons_sightings where end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)が0の場合elseを処理する
		while(rs.next()) {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(rs.getString("gender"));
			missingPersonsSightings.setAge(rs.getInt("age"));
			missingPersonsSightings.setDetail(rs.getString("detail"));
			missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
			missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
			missingPersonsSightings.setOther(rs.getString("other"));
			missingPersonsSightings.setUser_id(rs.getString("user_id"));
			missingPersonsSightingsList.add(missingPersonsSightings);
		}
		
		return missingPersonsSightingsList;
	}
	
	/** missing_persons_sightingsテーブルから
	 * 範囲指定された日付＋end_flag==falseのみ全件取得 */
	public ArrayList<MissingPersonsSightings> getDateMissingPersonsSightingsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = " select * from missing_persons_sightings where date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)が0の場合elseを処理する
		while(rs.next()) {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(rs.getString("gender"));
			missingPersonsSightings.setAge(rs.getInt("age"));
			missingPersonsSightings.setDetail(rs.getString("detail"));
			missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
			missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
			missingPersonsSightings.setOther(rs.getString("other"));
			missingPersonsSightings.setUser_id(rs.getString("user_id"));
			missingPersonsSightingsList.add(missingPersonsSightings);
		}
		return missingPersonsSightingsList;
	}
}
