package com.tx.api.test.automate.listeners;

import com.tx.api.test.automate.utils.ApiAutomationConstants;
import com.tx.api.test.automate.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;

@Slf4j
public class ApiAutomationListeners implements ITestListener, ISuiteListener {
  @Override
  public void onStart(ISuite suite) {
    ConfigUtil.setUserContext(suite.getParameter(ApiAutomationConstants.PARAMETER_CONFIG));

  }

  @Override
  public void onFinish(ISuite suite) {
    ConfigUtil.removeUserContext();
  }


}