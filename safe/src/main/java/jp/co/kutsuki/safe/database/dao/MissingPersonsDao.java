package jp.co.kutsuki.safe.database.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;

/**
 * missing_personsテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class MissingPersonsDao implements MissingPersonsRepository{
	
	@Autowired
	private JdbcTemplate template;
	
	/** missing_personsテーブルに1件登録 */
	@Transactional
	public void setMissingPersonsTable(MissingPersons missingPersons) {
		//SQL定義
		String sql = " insert into missing_persons(date, name, gender, age, detail, prefectures, municipalities, other, user_id) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//SQL実行し登録を実施
		template.update(sql, missingPersons.getDate(), missingPersons.getName(), missingPersons.getGender(), missingPersons.getAge(),
				missingPersons.getDetail(), missingPersons.getPrefectures(), 
				missingPersons.getMunicipalities(), missingPersons.getOther(), missingPersons.getUser_id());
	}
	
	/** missing_personsテーブルから
	 * end_flag==falseのみ全件取得 */
	public ArrayList<MissingPersons> getMissingPersonsTable() {
		//SQL定義
		String sql = " select * from missing_persons where end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)が0の場合elseを処理する
		if(!rs.isLast()) {
			while(rs.next()) {
				MissingPersons missingPersons = new MissingPersons();
				missingPersons.setId(rs.getInt("id"));
				// Date型からLocaldate型へ変換
				LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
				missingPersons.setDate(date);
				missingPersons.setName(rs.getString("name"));
				missingPersons.setGender(rs.getString("gender"));
				missingPersons.setAge(rs.getInt("age"));
				missingPersons.setDetail(rs.getString("detail"));
				missingPersons.setPrefectures(rs.getString("prefectures"));
				missingPersons.setMunicipalities(rs.getString("municipalities"));
				missingPersons.setOther(rs.getString("other"));
				missingPersons.setUser_id(rs.getString("user_id"));
				missingPersonsList.add(missingPersons);
			}
		}
		return missingPersonsList;
	}
	
	/** missing_personsテーブルから
	 * 範囲指定された日付＋end_flag==falseのみ全件取得 */
	public ArrayList<MissingPersons> getDateMissingPersonsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = " select * from missing_persons where date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)が0の場合elseを処理する
		if(!rs.isLast()) {
			while(rs.next()) {
				MissingPersons missingPersons = new MissingPersons();
				missingPersons.setId(rs.getInt("id"));
				// Date型からLocaldate型へ変換
				LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
				missingPersons.setDate(date);
				missingPersons.setName(rs.getString("name"));
				missingPersons.setGender(rs.getString("gender"));
				missingPersons.setAge(rs.getInt("age"));
				missingPersons.setDetail(rs.getString("detail"));
				missingPersons.setPrefectures(rs.getString("prefectures"));
				missingPersons.setMunicipalities(rs.getString("municipalities"));
				missingPersons.setOther(rs.getString("other"));
				missingPersons.setUser_id(rs.getString("user_id"));
				missingPersonsList.add(missingPersons);
			}
		}
		return missingPersonsList;
	}
}
