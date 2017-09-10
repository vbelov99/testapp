package ru.inovus.testapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.inovus.testapp.Constants;
import ru.inovus.testapp.exceptions.RepositoryException;
import ru.inovus.testapp.model.User;

/**
 * Репозиторий
 */
public final class DataRepository {

	/** Логгер */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataRepository.class);

	/** Добавление пользователя */
	private static final String USER_INSERT = "INSERT INTO users_table (login, password) "
			+ " VALUES (?,?)";

	/** Получение пользователя по логину */
	private static final String GET_USER = "SELECT login, password FROM users_table u "
			+ " WHERE u.login like ?";

	/**
	 * Конструктор
	 */
	private DataRepository() {
		super();
	}

	/**
	 * Проверка связи
	 * 
	 * @return дата
	 */
	public static String testConnection() {
		String result = "error";
		try (Connection con = DataSourceHolder.getDataSource().getConnection();
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(
						"SELECT CURRENT_DATE AS today, CURRENT_TIME AS now FROM (VALUES(0))")) {
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Добавляет пользователя
	 *
	 * @param aLogin логин
	 * @param aPassword пароль
	 * @throws RepositoryException в случае ошибки
	 */
	public static void addUser(String aLogin, String aPassword) throws RepositoryException {
		try (Connection con = DataSourceHolder.getDataSource().getConnection();
				PreparedStatement statement = con.prepareStatement(USER_INSERT)) {
			statement.setString(1, aLogin);
			statement.setString(2, aPassword);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
	}

	/**
	 * Получение пользователя по логину
	 *
	 * @param aLogin логин
	 * @return пользователь
	 * @throws RepositoryException в случае ошибки
	 */
	public static User getUserByLogin(String aLogin) throws RepositoryException {
		try (Connection con = DataSourceHolder.getDataSource().getConnection();
				PreparedStatement statement = con.prepareStatement(GET_USER)) {
			statement.setString(1, aLogin);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					String name = rs.getString(1);
					String password = rs.getString(2);
					return new User(name, password);
				}
			}
			return null;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RepositoryException("Ошибка при проверке уникальности логина");
		}
	}

	/**
	 * Выполняет скрипты
	 * 
	 * @param aSt скрипты
	 * @throws RepositoryException в случае ошибки
	 */
	public static void initDB(String... aSt) throws RepositoryException {
		try (Connection con = DataSourceHolder.getDataSource().getConnection();
				Statement statement = con.createStatement()) {
			for (int i = 0; i < aSt.length; i++) {
				if (!Constants.ZERO_LENGTH_STRING.equals(aSt[i].trim())) {
					statement.execute(aSt[i]);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RepositoryException("Ошибка при выполнении скриптов");
		}
	}

}
