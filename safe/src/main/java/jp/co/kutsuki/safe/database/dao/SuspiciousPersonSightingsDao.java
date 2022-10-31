package jp.co.kutsuki.safe.database.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;

/**
 * suspicious_person_sightingsテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class SuspiciousPersonSightingsDao implements SuspiciousPersonSightingsRepository{
	
	@Autowired
	private JdbcTemplate template;
	
	/** suspicious_person_sightingsテーブルに1件登録 */
	@Transactional
	public void setSuspiciousPersonSightingsTable(SuspiciousPersonSightings suspiciousPersonSightings) {
		//SQL定義
		String sql = " insert into suspicious_person_sightings(date, gender, age, detail, place, user_id) values(?, ?, ?, ?, ?, ?)";
		//missingPersons.getPlace()をString配列に変換
		String[] placeList = suspiciousPersonSightings.getPlace().toArray(new String[suspiciousPersonSightings.getPlace().size()]);
		String place = String.join(",", placeList);
		//SQL実行し登録を実施
		template.update(sql, suspiciousPersonSightings.getDate(), suspiciousPersonSightings.getGender(), suspiciousPersonSightings.getAge(),
				suspiciousPersonSightings.getDetail(), place, suspiciousPersonSightings.getUser_id());
	}
	
	/** suspicious_person_sightingsテーブルから
	 * end_flag==falseのみ全件取得 */
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable() {
		//SQL定義
		String sql = " select * from suspicious_person_sightings where end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)が0の場合elseを処理する
		while(rs.next()) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(rs.getString("gender"));
			suspiciousPersonSightings.setAge(rs.getInt("age"));
			suspiciousPersonSightings.setDetail(rs.getString("detail"));
			//文字列からArrayListに変換
			String placeStr = rs.getString("place");
			List<String> place = Arrays.asList(placeStr.split(","));
			suspiciousPersonSightings.setPlace(place);
			suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
			suspiciousPersonSightingsList.add(suspiciousPersonSightings);
		}
		
		return suspiciousPersonSightingsList;
	}
	
	/** suspicious_person_sightingsテーブルから
	 * 範囲指定された日付＋end_flag==falseのみ全件取得 */
	public ArrayList<SuspiciousPersonSightings> getDateSuspiciousPersonSightingsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = " select * from suspicious_person_sightings where date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)が0の場合elseを処理する
		while(rs.next()) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(rs.getString("gender"));
			suspiciousPersonSightings.setAge(rs.getInt("age"));
			suspiciousPersonSightings.setDetail(rs.getString("detail"));
			//文字列からArrayListに変換
			String placeStr = rs.getString("place");
			List<String> place = Arrays.asList(placeStr.split(","));
			suspiciousPersonSightings.setPlace(place);
			suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
			suspiciousPersonSightingsList.add(suspiciousPersonSightings);
		}
		return suspiciousPersonSightingsList;
	}
}
