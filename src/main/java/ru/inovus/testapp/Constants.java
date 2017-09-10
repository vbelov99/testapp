package ru.inovus.testapp;

import java.util.concurrent.TimeUnit;

/**
 * Константы
 */
public final class Constants {

	/** Атрибут сессии - пользователь */
	public static final String USR_ATTR = "user";

	/** Пустая строка */
	public static final String ZERO_LENGTH_STRING = "";

	/** Кодировка UTF-8 */
	public static final String UTF8_ENCODING = "UTF-8";

	/** Таймаут сессии */
	public static final long SESSION_TIMEOUT = TimeUnit.MINUTES.toSeconds(30);

	/**
	 * Конструктор
	 */
	private Constants() {
		super();
	}
}
