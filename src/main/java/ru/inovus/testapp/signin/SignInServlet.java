package ru.inovus.testapp.signin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.inovus.testapp.Constants;
import ru.inovus.testapp.exceptions.RepositoryException;
import ru.inovus.testapp.model.User;
import ru.inovus.testapp.repository.DataRepository;

/**
 * Вход
 */
@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {

	/** Идентификатор для сериализации */
	private static final long serialVersionUID = -2338023324483700864L;

	/** Логгер */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignInServlet.class);

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		String login = aRequest.getParameter("username");
		String password = aRequest.getParameter("password");
		aRequest.setAttribute("login", login);

		User user = null;
		try {
			user = DataRepository.getUserByLogin(login);
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e);
			showError(aRequest, aResponse, e.getMessage());
			return;
		}
		if (user != null) {
			if (!password.equals(user.password)) {
				showError(aRequest, aResponse, "Не верно указан пароль");
				return;
			}
			HttpSession session = aRequest.getSession(true);
			session.setAttribute(Constants.USR_ATTR, user.login);
			// session.setMaxInactiveInterval(30 * 60);
			Cookie userName = new Cookie(Constants.USR_ATTR, user.login);
			userName.setSecure(true);
			userName.setMaxAge(session.getMaxInactiveInterval());
			aResponse.addCookie(userName);
			aResponse.sendRedirect(aRequest.getContextPath() + "/welcome");
		} else {
			showError(aRequest, aResponse, "Не найден пользователь");
		}
	}

	/**
	 * Заполняет элемент отображающий ошибку
	 * 
	 * @param aRequest запрос
	 * @param aResponse ответ
	 * @param aMessage сообщение об ошибке
	 * @throws ServletException в случае ошибки
	 * @throws IOException в случае ошибки
	 */
	private void showError(HttpServletRequest aRequest, HttpServletResponse aResponse,
			String aMessage) throws ServletException, IOException {
		aRequest.setAttribute("error", aMessage);
		aRequest.getRequestDispatcher("/sign-in.jsp").forward(aRequest, aResponse);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		Cookie[] cookies = aRequest.getCookies();
		String login = Constants.ZERO_LENGTH_STRING;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (Constants.USR_ATTR.equals(cookies[i].getName())) {
					login = cookies[i].getValue();
					break;
				}
			}
		}
		aRequest.setAttribute("login", login);

		String logout = aRequest.getParameter("logout");
		if (logout != null) {
			HttpSession session = aRequest.getSession(false);
			session.removeAttribute(Constants.USR_ATTR);
			aRequest.setAttribute("logout", "Вы вышли из приложения");
		}
		aRequest.getRequestDispatcher("/sign-in.jsp").forward(aRequest, aResponse);
	}

}
