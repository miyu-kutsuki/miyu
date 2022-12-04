package jp.co.kutsuki.safe.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * セッション管理チェック用Filterクラス
 * @author kutsuki
 *
 */
@Component
public class UserCheckFilter implements Filter{

	@Autowired
	HttpSession session;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		boolean check = false;

        //セッションの有効チェック
        if(session.getAttribute("user") == null) {
        	 check = true;
        }
        session.setAttribute("check", check);
        chain.doFilter(request, response);
	}

	@Override
    public void destroy() {

	}
}
