package by.pavka.library.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DirectAccessFilter
 * <p>
 * The filter prevents access to JSP directly from the address line
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
@WebFilter(
    urlPatterns = {"/jsp/*"},
    initParams = {@WebInitParam(name = "error", value = "denied.jsp")})
public class DirectAccessFilter implements Filter {
  private String errorPage;

  @Override
  public void init(FilterConfig filterConfig) {
    errorPage = filterConfig.getInitParameter("error");
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(errorPage);
    dispatcher.forward(request, response);
  }

  @Override
  public void destroy() {
    errorPage = null;
  }
}
