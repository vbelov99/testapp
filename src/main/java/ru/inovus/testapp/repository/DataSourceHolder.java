package ru.inovus.testapp.repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Хранит источник данных
 */
public final class DataSourceHolder {

	/** Логгер */
	private static final Logger Logger = LoggerFactory.getLogger(DataSourceHolder.class);

	/** Источник данных */
	// @Resource(name = "jdbc/test")
	private static DataSource _dataSource;

	/**
	 * Конструктор
	 */
	private DataSourceHolder() {
		super();
	}

	/**
	 * Возвращает источник данных
	 * 
	 * @return источник данных
	 */
	public static DataSource getDataSource() {
		synchronized (DataSourceHolder.class) {
			if (null == _dataSource) {
				try {
					Context initContext = new InitialContext();
					Context envContext = (Context) initContext.lookup("java:/comp/env");
					_dataSource = (DataSource) envContext.lookup("jdbc/test");
				} catch (NamingException e) {
					Logger.error("Ошибка получения источника данных", e);
				}
			}
		}
		return _dataSource;
	}

}
