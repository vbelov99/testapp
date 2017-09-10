package ru.inovus.testapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Обработчик ошибок
 */
@WebServlet("/exceptionHandler")
public class ExceptionHandlerServlet extends HttpServlet {

	/** Идентификатор для сериализации */
	private static final long serialVersionUID = -7724334029680623431L;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		processError(aRequest, aResponse);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		processError(aRequest, aResponse);
	}

	/**
	 * Обрабатывает ошибку
	 * 
	 * @param aRequest запрос
	 * @param aResponse ответ
	 * @throws IOException ошибка
	 */
	private void processError(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws IOException {
		Throwable throwable = (Throwable) aRequest.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) aRequest.getAttribute("javax.servlet.error.status_code");

		aResponse.setContentType("text/html");
		// aResponse.setCharacterEncoding(Constants.UTF8_ENCODING);
		PrintWriter out = aResponse.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Ошибка!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3 style=\"color:red\">Произошла непредвиденная ошибка</h3>");
		if (statusCode != 500) {
			out.println("<h4>Код ошибки: " + statusCode + "</h4>");
		} else {
			out.println("<h4>Текст ошибки: " + throwable.getMessage() + "</h4>");
		}
		out.println("</body>");
		out.println("</html>");
		// out.close();
	}

}
