package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;

/**
 * EmptyCommand
 * <p>
 * The command is executed in the request parameter "command" doesn't correspond to any other ActionCommand
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class EmptyCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    return new PageRouter();
  }
}
