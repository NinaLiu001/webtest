package TestData;

import jxl.read.biff.BiffException;

import java.io.IOException;

import org.testng.annotations.DataProvider;
public class DataPrepare {

	// 调用browser 的用例表
	@DataProvider(name = "browser")
	public Object[][] Numbers() throws BiffException, IOException {
		 getActionString = actionData.getActionStr(1);// 获取第一个场景的broswer、url、action名
		ExcelData e = new ExcelData("testdata", getActionString.get(2));
		return e.getExcelData();
	}
 
}
