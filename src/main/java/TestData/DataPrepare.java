package TestData;

import jxl.read.biff.BiffException;

import java.io.IOException;

import org.testng.annotations.DataProvider;
public class DataPrepare {

	// ����browser ��������
	@DataProvider(name = "browser")
	public Object[][] Numbers() throws BiffException, IOException {
		 getActionString = actionData.getActionStr(1);// ��ȡ��һ��������broswer��url��action��
		ExcelData e = new ExcelData("testdata", getActionString.get(2));
		return e.getExcelData();
	}
 
}
