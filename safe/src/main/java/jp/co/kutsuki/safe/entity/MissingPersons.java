package jp.co.kutsuki.safe.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
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
	@NotNull(message = "日付が入力されていません。")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;
	
	/** 氏名 */
	@NotBlank(message = "氏名が入力されていません。")
	private String name;
	
	/** 性別 */
	@NotBlank(message = "性別が選択されていません。")
	private String gender;
	
	/** 年齢 */
	@NotNull(message = "年齢が入力されていません。")
	@Range(min=0, max=120, message = "年齢は{min}〜{max}歳の範囲で入力して下さい。")
	private Integer age;
	
	/** 服装・背恰好・経緯 */
	@NotBlank(message = "服装・背恰好・経緯が入力されていません。")
	private String detail;
	
	/** 最後に立ち寄った可能性のある場所 
	 * 都道府県*/
	@NotBlank(message = "都道府県名が入力されていません。")
	private String prefectures;
	
	/** 最後に立ち寄った可能性のある場所 
	 * 市区町村*/
	@NotBlank(message = "市区町村が入力されていません。")
	private String municipalities;
	
	/** 最後に立ち寄った可能性のある場所 
	 * 番地等*/
	@NotBlank(message = "番地等が入力されていません。")
	private String other;
	
	/** ユーザーID */
	private String user_id;

}
