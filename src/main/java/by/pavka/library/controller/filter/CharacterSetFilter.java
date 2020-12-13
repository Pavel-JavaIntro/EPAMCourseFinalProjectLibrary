package by.pavka.library.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * CharacterSetFilter
 * <p>
 * The Filter sets UTF-8 in and out encoding.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class CharacterSetFilter implements Filter {
  private String code;
  private final String ENCODING = "encoding";

  @Override
  public void init(FilterConfig filterConfig) {
    code = filterConfig.getInitParameter(ENCODING);
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    String requestCode = servletRequest.getCharacterEncoding();
    if (code != null && !code.equalsIgnoreCase(requestCode)) {
      servletRequest.setCharacterEncoding(code);
      servletResponse.setCharacterEncoding(code);
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    code = null;
  }
}
