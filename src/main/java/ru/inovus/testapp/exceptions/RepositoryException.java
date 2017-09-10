package ru.inovus.testapp.exceptions;

/**
 * Исключение при работе с бд
 */
public class RepositoryException extends Exception {

	/** Идентификатор для сериализации */
	private static final long serialVersionUID = -8532328747868117364L;

	/**
	 * Конструктор
	 * 
	 * @param aMessage сообщение об ошибке
	 */
	public RepositoryException(String aMessage) {
		super(aMessage);
	}

	/**
	 * Конструктор
	 * 
	 * @param aMessage сообщение об ошибке
	 * @param aThrowable ошибка
	 */
	public RepositoryException(String aMessage, Throwable aThrowable) {
		super(aMessage, aThrowable);
	}

}