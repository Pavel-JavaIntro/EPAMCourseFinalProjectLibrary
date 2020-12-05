package by.pavka.library.tag;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.impl.User;
import by.pavka.library.model.util.ConstantManager;
import by.pavka.library.model.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;

public class UserTagHandler extends SimpleTagSupport {
  private static final Logger LOGGER = LogManager.getLogger(UserTagHandler.class);
  private User user;

  @Override
  public void doTag() throws JspException, IOException {
    try {
      String name = user.getName();
      String surname = user.getSurname();
      String email = user.getEmail();
      int roleId = user.getRoleId();
      String role = ConstantManager.getRoleById(roleId);
      String language = (String)getJspContext().findAttribute(ActionCommand.SESSION_ATTRIBUTE_LANGUAGE);
      Locale locale = language == null ? Locale.getDefault() : new Locale(language);
      String userInfo = MessageManager.getProperty("message.userinfo", locale);
      getJspContext().getOut().print(String.format(userInfo, name, surname, email, role));
    } catch (LibraryEntityException e) {
      LOGGER.error("Cannot read user-info tag");
      throw new SkipPageException("Cannot read user-info tag");
    }
  }

  public void setUser(User user) {
    this.user = user;
  }
}
