package by.pavka.library.model.service.impl;

import by.pavka.library.model.service.*;

/**
 * Simple factory providing singleton instances
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class LibServiceFactory {
  private static InitService initService = new InitServiceImpl();
  private static AuthService authService = new AuthServiceImpl();
  private static ReaderService readerService = new ReaderServiceImpl();
  private static ProcessUserService processUserService = new ProcessUserServiceImpl();
  private static ProcessBookService processBookService = new ProcessBookServiceImpl();
  private static UtilService utilService = new UtilService();

  private LibServiceFactory() {}

  public static InitService getInitService() {
    return initService;
  }

  public static AuthService getAuthService() {
    return authService;
  }

  public static ReaderService getReaderService() {
    return readerService;
  }

  public static ProcessUserService getProcessUserService() {
    return processUserService;
  }

  public static ProcessBookService getProcessBookService() {
    return processBookService;
  }

  public static UtilService getUtilService() {
    return utilService;
  }
}
