package ru.inovus.testapp.signup;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
 * Регистрация
 */
@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

	/** Паттерн проверки логина */
	public static final String LOGIN_PATTERN = "([0-9a-zA-Z]){4,20}";

	/** Паттерн проверки пароля */
	public static final String PASS_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]*${8,20}";

	/** Идентификатор для сериализации */
	private static final long serialVersionUID = 4361260417231589793L;

	/** Логгер */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignUpServlet.class);

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		aRequest.getRequestDispatcher("/sign-up.jsp").forward(aRequest, aResponse);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {

		String username = aRequest.getParameter("username").trim();
		String loginError = checkLogin(username);
		if (!loginError.isEmpty()) {
			showError(aRequest, aResponse, loginError);
			return;
		}

		String password = aRequest.getParameter("password");
		String passwordRepeat = aRequest.getParameter("passwordRepeat");
		String passError = checkPassword(password, passwordRepeat);
		if (!passError.isEmpty()) {
			showError(aRequest, aResponse, passError);
			return;
		}

		// проверка на уникальность
		try {
			User user = DataRepository.getUserByLogin(username);
			if (user != null) {
				showError(aRequest, aResponse,
						"Пользователь с данным логином уже зарегистрирован.");
				return;
			}
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e);
			showError(aRequest, aResponse, "Возникла ошибка при проверке уникальности логина.");
			return;
		}

		// сохранение и перенаправление на страницу приветствия
		try {
			DataRepository.addUser(username, password);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Добавлен пользователь с логином " + username);
			}
			HttpSession session = aRequest.getSession(true);
			session.setAttribute("user", username);
			aResponse.sendRedirect(aRequest.getContextPath() + "/welcome");
		} catch (RepositoryException e) {
			LOGGER.error(e.getMessage(), e);
			showError(aRequest, aResponse, "Возникла ошибка при создании пользователя.");
			return;
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
		aRequest.getRequestDispatcher("/sign-up.jsp").forward(aRequest, aResponse);
	}

	/**
	 * Проверка корректности пароля
	 * 
	 * @param aPassword пароль
	 * @param aPasswordRepeat повтор пароля
	 * @return пустая строка, если нет ошибок, иначе текст ошибки
	 */
	private String checkPassword(String aPassword, String aPasswordRepeat) {
		if (!aPassword.equals(aPasswordRepeat)) {
			return "Пароль и повтор пароля не совпадают";
		}
		if (!aPassword.matches(PASS_PATTERN)) {
			return "Пароль недостаточно сложен: должны быть цифры, заглавные "
					+ "и строчные буквы и длина минимум 8 символов";
		}
		return Constants.ZERO_LENGTH_STRING;
	}

	/**
	 * Проверка корректности логина
	 * 
	 * @param aLogin логин
	 * @return пустая строка, если нет ошибок, иначе текст ошибки
	 */
	private String checkLogin(String aLogin) {
		if (!aLogin.matches(LOGIN_PATTERN)) {
			return "Имя пользователя должно быть длиннее 4 символов и состоять из цифр "
					+ "и букв английского алфавита";
		}
		return Constants.ZERO_LENGTH_STRING;
	}

}
