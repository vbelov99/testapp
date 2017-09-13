package ru.inovus.testapp.welcome;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.inovus.testapp.Constants;

/**
 * Приветствие
 */
@WebServlet(urlPatterns = { "/welcome" })
public class WelcomeServlet extends HttpServlet {

	/** Идентификатор для сериализации */
	private static final long serialVersionUID = -4439637135600892326L;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		HttpSession session = aRequest.getSession(false);
		if ((session != null) && (session.getAttribute(Constants.USR_ATTR) != null)) {
			String name = (String) session.getAttribute(Constants.USR_ATTR);
			printWelcome(aResponse.getWriter(), getWelcomeText(name));
		} else {
			aRequest.getRequestDispatcher("/sign-in").forward(aRequest, aResponse);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		doGet(aRequest, aResponse);
	}

	/**
	 * Возвращает приветствие в зависмости от текущего часа
	 * 
	 * @param aName имя пользователя (логин)
	 * @return приветствие в зависмости от текущего часа
	 */
	private String getWelcomeText(String aName) {
		String result;
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if ((hour >= 6) && (hour < 10)) {
			result = "Доброе утро, ";
		} else if ((hour >= 10) && (hour < 18)) {
			result = "Добрый день, ";
		} else if ((hour >= 18) && (hour < 22)) {
			result = "Добрый вечер, ";
		} else {
			result = "Доброй ночи, ";
		}
		result += aName;
		return result;
	}

	/**
	 * Печатает приветствие
	 * 
	 * @param aWriter вывод
	 * @param aWelcomeText текст приветствия
	 */
	private void printWelcome(PrintWriter aWriter, String aWelcomeText) {
		aWriter.println("<html>");
		aWriter.println("<head>");
		aWriter.println("<meta charset=\"UTF-8\">");
		aWriter.println("<title>Welcome</title>");
		aWriter.println("</head>");
		aWriter.println("<body>");
		aWriter.println("<h1>" + aWelcomeText + "</h1>");
		aWriter.println("<form action=\"sign-in\" method=\"get\">");
		aWriter.println("<input type=\"submit\" value=\"Выйти\" name=\"logout\"/>");
		aWriter.println("</form>");
		aWriter.println("</body>");
		aWriter.println("</html>");
		aWriter.close();
	}

}
