package ru.inovus.testapp.model;

/**
 * Пользователь
 */
public final class User {

	/** Логин */
	public final String login;

	/** Пароль */
	public final String password;

	/**
	 * Конструктор
	 * 
	 * @param aLogin Логин
	 * @param aPassword Пароль
	 */
	public User(String aLogin, String aPassword) {
		login = aLogin;
		password = aPassword;
	}

}
