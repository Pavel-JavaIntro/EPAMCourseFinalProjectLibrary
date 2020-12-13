package by.pavka.library.tag;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.entity.order.BookOrder;
import by.pavka.library.entity.order.EditionInfo;
import by.pavka.library.model.util.ConstantManager;
import by.pavka.library.model.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;

/**
 * This extension of SimpleTagSupport presents a user friendly info about a book order.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class OrderTagHandler extends SimpleTagSupport {
  private static final Logger LOGGER = LogManager.getLogger(OrderTagHandler.class);
  private BookOrder order;
  private EditionInfo edition;
  private boolean standard;

  @Override
  public void doTag() throws IOException {
    int userId = order.getUserId();
    int bookId = edition.getBook().getId();
    String editionInfo = edition.toString();
    String location = ConstantManager.getLocationById(edition.getLocationId());
    String standardLocation = ConstantManager.getLocationById(edition.getStandardLocationId());
    String language = (String) getJspContext().findAttribute(ActionCommand.SESSION_ATTRIBUTE_LANGUAGE);
    Locale locale = language == null ? Locale.getDefault() : new Locale(language);
    String process = MessageManager.getProperty("message.process", locale);
    String result;
    if (!standard) {
      result = String.format(process, userId, bookId, editionInfo, location);
    } else {
      result = String.format(process, userId, bookId, editionInfo, standardLocation);
      String transfer = MessageManager.getProperty("message.transfer", locale);
      result += String.format(transfer, location);
    }
    getJspContext().getOut().print(result);
  }

  public void setOrder(BookOrder order) {
    this.order = order;
  }

  public void setEdition(EditionInfo edition) {
    this.edition = edition;
  }

  public void setStandard(boolean standard) {
    this.standard = standard;
  }
}
