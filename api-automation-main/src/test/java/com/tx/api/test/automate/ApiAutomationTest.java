package com.tx.api.test.automate;

import com.tx.api.test.automate.execute.ExecuteApiTest;
import com.tx.api.test.automate.utils.ExcelUtil;
import com.tx.api.test.automate.utils.TestExecutionContext;
import com.tx.api.test.automate.utils.TestExecutionContextManager;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;

public class ApiAutomationTest {

    @DataProvider(name = "apiTestData")
    public Iterator<TestExecutionContext> getApiTestData(ITestContext context) throws IOException {
        String dataFile = context.getCurrentXmlTest().getParameter("data-file-path");
        return ExcelUtil.excelDataReader(0, dataFile);
    }

    @Test(dataProvider = "apiTestData")
    public void executeApi(TestExecutionContext testExecutionContext) throws AssertionError {
        TestExecutionContextManager.setTestExecutionContext(testExecutionContext);
        ExecuteApiTest.runTest();
    }
}
