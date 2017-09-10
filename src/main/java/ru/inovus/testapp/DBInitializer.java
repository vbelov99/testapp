package ru.inovus.testapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.inovus.testapp.exceptions.RepositoryException;
import ru.inovus.testapp.repository.DataRepository;

/**
 * Подготовка бд
 */
@WebListener
public class DBInitializer implements ServletContextListener {

	/** Логгер */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBInitializer.class);

	/*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String s;
		StringBuilder sb = new StringBuilder();
		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("script.sql");
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);) {

			while ((s = br.readLine()) != null) {
				sb.append(s);
			}

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			// throw new RuntimeException(e);
			return;
		}
		String[] scripts = sb.toString().split(";");
		if (scripts.length > 0) {
			try {
				DataRepository.initDB(scripts);
			} catch (RepositoryException e) {
				LOGGER.error("Ошибка при инициализации бд", e);
				// throw new RuntimeException(e);
			}
		} else {
			LOGGER.warn("Отсутствуют скрипты развертывания.");
		}
	}

}
