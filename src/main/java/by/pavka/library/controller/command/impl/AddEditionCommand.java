package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.entity.impl.Author;
import by.pavka.library.entity.impl.Book;
import by.pavka.library.entity.impl.Edition;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddEditionCommand implements ActionCommand {
  public static final String AUTHOR_SURNAME = "surname";
  public static final String AUTHOR_NAME = "name";
  public static final String AUTHOR_SECOND_NAME = "secondname";

  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.PROCESS_BOOKS);
    HttpSession session = request.getSession();
    String code = (String) session.getAttribute(CODE);
    if (code == null) {
      return pageRouter;
    }
    session.removeAttribute(CODE);
    int genre = Integer.parseInt(request.getParameter(GENRE));
    String yearS = request.getParameter(BOOK_YEAR);
    int year = yearS.isEmpty() ? 0 : Integer.parseInt(yearS);
    String title = request.getParameter(BOOK_TITLE);
    String locationS = request.getParameter(BOOK_LOCATION);
    int locationId = locationS.isEmpty() ? 0 : Integer.parseInt(locationS);
    String[][] authors = new String[3][3];
    for (int i = 0; i < 3; i++) {
      String surname = AUTHOR_SURNAME + i;
      String name = AUTHOR_NAME + i;
      String secondname = AUTHOR_SECOND_NAME + i;
      authors[i][0] =
          request.getParameter(surname).isEmpty() ? null : request.getParameter(surname);
      authors[i][1] = request.getParameter(name).isEmpty() ? null : request.getParameter(name);
      authors[i][2] =
          request.getParameter(secondname).isEmpty() ? null : request.getParameter(secondname);
    }
    Edition edition = new Edition();
    Book book = new Book();
    LibraryService service = LibraryService.getInstance();
    edition.setValue(Edition.STANDARD_NUMBER, code);
    edition.setValue(Edition.GENRE_ID, genre);
    edition.setValue(Edition.TITLE, title);
    if (year != 0) {
      edition.setValue(Edition.YEAR, year);
    }
    if (locationId != 0) {
      book.setValue(Book.LOCATION_ID, locationId);
      book.setValue(Book.STANDARD_LOCATION_ID, locationId);
    }
    int[] ids = new int[3];
    Author[] athrs = new Author[3];
    Criteria[] criteria = new Criteria[3];
    for (int i = 0; i < 3; i++) {
      if (authors[i][0] != null) {
        Author author = new Author();
        author.setValue(Author.SURNAME, authors[i][0]);
        EntityField<String> surname = new EntityField<>(Author.SURNAME);
        surname.setValue(authors[i][0]);
        criteria[i] = new Criteria();
        criteria[i].addConstraint(surname);
        if (authors[i][1] != null) {
          author.setValue(Author.FIRST_NAME, authors[i][1]);
          EntityField<String> name = new EntityField<>(Author.FIRST_NAME);
          name.setValue(authors[i][1]);
          criteria[i].addConstraint(name);
        }
        if (authors[i][2] != null) {
          author.setValue(Author.SECOND_NAME, authors[i][2]);
          EntityField<String> secondName = new EntityField<>(Author.SECOND_NAME);
          secondName.setValue(authors[i][2]);
          criteria[i].addConstraint(secondName);
        }
        athrs[i] = author;
      }
    }

    try {
      int id = service.addEdition(edition);
      book.setValue(Book.EDITION_ID, id);
      service.addBook(book);
      for (int i = 0; i < 3; i++) {
        //if (criteria[i] != null) {
          //List<Author> authorList = service.findAuthors(criteria[i]);
        if (athrs[i] != null) {
          List<Author> authorList = service.findAuthors(athrs[i]);
          if (!authorList.isEmpty()) {
            ids[i] = authorList.get(0).getId();
          } else {
            ids[i] = service.addAuthor(athrs[i]);
          }
        } else {
          ids[i] = 0;
        }
      }
      service.bindEditionAndAuthors(id, ids);
      session.setAttribute(RESULT, PageRouter.RESULT_SUCCESS);
    } catch (ServiceException e) {
      pageRouter.setPage(PageRouter.ERROR);
      LOGGER.error("AddEditionCommand hasn't completed");
    }
    return pageRouter;
  }
}
