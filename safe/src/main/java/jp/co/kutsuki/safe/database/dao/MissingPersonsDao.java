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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		String sql = " insert into missing_persons(date, name, gender, age, detail, place, user_id) values(?, ?, ?, ?, ?, ?, ?)";
		//missingPersons.getPlace()をString配列に変換
		String[] placeList = missingPersons.getPlace().toArray(new String[missingPersons.getPlace().size()]);
		String place = String.join(",", placeList);
		//SQL実行し登録を実施
		template.update(sql, missingPersons.getDate(), missingPersons.getName(), missingPersons.getGender(), missingPersons.getAge(),
				missingPersons.getDetail(), place, missingPersons.getUser_id());
	}
	
	/** missing_personsテーブルから
	 * end_flag==falseのみ全件取得 */
	public ArrayList<MissingPersons> getMissingPersonsTable(RedirectAttributes redirectAttributes) {
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
				//文字列からArrayListに変換
				String placeStr = rs.getString("place");
				List<String> place = Arrays.asList(placeStr.split(","));
				missingPersons.setPlace(place);
				missingPersons.setUser_id(rs.getString("user_id"));
				missingPersonsList.add(missingPersons);
			}
		}else {
			redirectAttributes.addFlashAttribute("msg", "該当データがありません。");
		}
		return missingPersonsList;
	}
}
