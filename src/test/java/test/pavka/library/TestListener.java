package test.pavka.library;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestListener extends TestListenerAdapter {
  private static Logger LOGGER = LogManager.getLogger(TestListener.class);

  @Override
  public void onTestSuccess(ITestResult tr) {
    LOGGER.info(tr.getTestClass().getName() + " Success!");
  }

  @Override
  public void onTestFailure(ITestResult tr) {
    LOGGER.error(tr.getStatus() + " " + tr.getMethod() + " Failed!");
  }

  @Override
  public void onStart(ITestContext testContext) {
    LOGGER.info("Test Started... " + testContext.getName() + " " + testContext.getStartDate());
  }

  @Override
  public void onFinish(ITestContext testContext) {
    LOGGER.info("Test Finished... " + testContext.getEndDate());
  }

  @Override
  public void onTestStart(ITestResult result) {
    LOGGER.info("test " + result.getName() + " started, priority " + result.getMethod().getPriority());
  }
}
