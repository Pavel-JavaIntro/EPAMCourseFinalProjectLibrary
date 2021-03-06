package by.pavka.library.tag;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.model.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

/**
 * This extension of SimpleTagSupport presents a user friendly info about a book.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class BookTagHandler extends SimpleTagSupport {
  private static final Logger LOGGER = LogManager.getLogger(BookTagHandler.class);
  private Book book;
  private boolean detailed;

  @Override
  public void doTag() throws JspException, IOException {
    if (book != null) {
      int id = book.getId();
      String language = (String) getJspContext().findAttribute(ActionCommand.SESSION_ATTRIBUTE_LANGUAGE);
      Locale locale = language == null ? Locale.getDefault() : new Locale(language);
      String bookInfo = MessageManager.getProperty("message.bookinfo", locale);
      String details = "";
      if (detailed) {
        try {
          int userId = 0;
          Object user = book.fieldForName(Book.READER_ID).getValue();
          if (user != null) {
            userId = (int) user;
          }
          LocalDate date = null;
          Object d = book.fieldForName(Book.DUE_DATE).getValue();
          if (d != null) {
            date = ((Date) d).toLocalDate();
          }
          String detailedInfo = MessageManager.getProperty("message.overdueinfo", locale);
          details = String.format(detailedInfo, userId, date);
        } catch (LibraryEntityException e) {
          LOGGER.error("Cannot read user-info tag");
          throw new SkipPageException("Cannot read user-info tag");
        }
      }
      getJspContext().getOut().print(String.format(bookInfo, id) + details);
    }
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public void setDetailed(boolean detailed) {
    this.detailed = detailed;
  }
}
