package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    return new PageRouter();
  }
}
