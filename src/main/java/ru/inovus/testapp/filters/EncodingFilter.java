package ru.inovus.testapp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import ru.inovus.testapp.Constants;

/**
 * Фильтр (кодировка)
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = { "/*" })
public class EncodingFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aChain)
			throws IOException, ServletException {
		aRequest.setCharacterEncoding(Constants.UTF8_ENCODING);
		aResponse.setCharacterEncoding(Constants.UTF8_ENCODING);
		aChain.doFilter(aRequest, aResponse);
	}

}
