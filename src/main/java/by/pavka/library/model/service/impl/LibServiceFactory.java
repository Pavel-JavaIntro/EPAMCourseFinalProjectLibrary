package by.pavka.library.model.service.impl;

import by.pavka.library.model.service.*;

public class LibServiceFactory {
  private static InitService initService = new InitServiceImpl();
  private static GeneralService generalService = new GeneralServiceImpl();
  private static ReaderService readerService = new ReaderServiceImpl();
  private static ProcessUserService processUserService = new ProcessUserServiceImpl();
  private static ProcessBookService processBookService = new ProcessBookServiceImpl();

  private LibServiceFactory() {}

  public static InitService getInitService() {
    return initService;
  }

  public static GeneralService getAuthService() {
    return generalService;
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
}
