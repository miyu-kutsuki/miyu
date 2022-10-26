package jp.co.kutsuki.safe.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * missing_personsテーブル用エンティティ
 * @author kutsuki
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissingPersons {
	
	/** missing_personsテーブル用識別ID */
	@Id
	private Integer id;
	
	/** 行方不明日 */
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;
	
	/** 氏名 */
	private String name;
	
	/** 性別 */
	private String gender;
	
	/** 年齢 */
	private Integer age;
	
	/** 服装・背恰好・経緯 */
	private String detail;
	
	/** 最後に立ち寄った可能性のある場所 */
	private List<String> place;
	
	/** ユーザーID */
	private String user_id;

}
