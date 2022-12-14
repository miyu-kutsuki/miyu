package jp.co.kutsuki.safe.mail;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * メール送信のクラス
 */
@Service
public class MailSenderService {
	
	@Autowired
	MailSender mailSender;
	
	@Autowired
	UserRepository userRepository;

		
	public void mailSend() {
		
		//ユーザー情報の取得
		User user = userRepository.getOneUserTable(3);
		
		
		//メール送信内容作成して設定
		 SimpleMailMessage message = new SimpleMailMessage();
		 message.setTo(user.getEmail());
		 message.setFrom("doconano2023@gmail.com");
		 message.setSubject("ユーザーIDお問い合せの件");
		 
		//テンプレートエンジンを使用するための設定インスタンスを生成
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		//テンプレートエンジンの種類を指定
		//メールテンプレートとして使用するためテキストを指定
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		//テンプレートファイルとして読み込む文字エンコードを指定
		templateResolver.setCharacterEncoding("UTF-8");
		//テンプレートエンジンを使用するためのインスタンスを生成
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver);
		
		//メールテンプレートに設定するパラメータを設定
		Map<String, Object> variables = new HashMap<>();
		variables.put("name", user.getFamilyName());
		variables.put("user_id", user.getUser_id());
		variables.put("display", true);
		
		//テンプレートエンジンを実行してテキストを取得
		Context context = new Context();
		context.setVariables(variables);
		
		//使用するテンプレートのファイル名とパラメータ情報を設定
		String text = engine.process("/mail/mailTemplate.txt", context);
		message.setText(text);
		//メール送信を実施
		mailSender.send(message);
	}
}
