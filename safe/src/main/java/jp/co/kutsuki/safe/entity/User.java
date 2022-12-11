package jp.co.kutsuki.safe.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * usersテーブル用エンティティ
 * @author kutsuki
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	/** userテーブル用識別ID */
	@Id
	private Integer id;

	/** ユーザーID */
	@NotBlank(message = "ユーザーIDが入力されていません。")
	@Length(min=5, max=15, message = "ユーザーIDは{min}〜{max}桁で入力して下さい。")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "ユーザーIDは『半角英数』『_』『-』のみ使用可能です。")
	private String user_id;

	/** パスワード */
	@NotBlank(message = "パスワードが入力されていません。")
	@Length(min=5, max=20, message = "パスワードは{min}〜{max}桁で入力して下さい。")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "パスワードは『半角英数』『_』『-』のみ使用可能です。")
	private String password;
	
	/** 名字 */
	@NotBlank(message = "名字が入力されていません。")
	@Length(min=1, max=30, message = "名字は{min}〜{max}桁で入力して下さい。")
	private String familyName;
	
	/** 名前 */
	@NotBlank(message = "名前が入力されていません。")
	@Length(min=1, max=30, message = "名前は{min}〜{max}桁で入力して下さい。")
	private String firstName;
	
	/** 生年月日 */
	@NotNull(message = "生年月日が入力されていません。")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate birthday;
	
	/** メールアドレス */
	@NotBlank(message = "メールアドレスが入力されていません。")
	@Length(min=5, max=100, message = "メールアドレスは{min}〜{max}桁で入力して下さい。")
	@Pattern(regexp = "^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$", 
	message = "メールアドレスが正しくありません。")
	private String email;
	
	/** 秘密の質問 */
	@NotNull(message = "秘密の質問が選択されていません。")
	private Integer question_id;
	
	/** 秘密の質問 */
	private String question;
	
	/** 秘密の質問 */
	@NotBlank(message = "秘密の質問の答えが入力されていません。")
	private String answer;


	/** ユーザーの削除フラグ
	 * false:使用中
	 * true:終了 */
	private Boolean end_flag;

}
