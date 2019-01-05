package TestCase;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestData.ExcelData;
import jxl.read.biff.BiffException;
import pages.PageParpare;

/**
 * @description 登录之后验证用户名是不是正确的
 */

public class LoginSuccess extends BaseAction {
	
	@BeforeTest 
	public void testPrepare()  {
		caseName= this.getClass().getName().toString().split("_")[0];
		ExcelData e=new ExcelData(fileName, caseName);
		try {
			e.getExcelData();
		} catch (BiffException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    }


	@Test(dataProvider = "action")
	public void testAction(HashMap<String, String> data) throws BiffException, IOException {
		try {
			Class<?> MyClass = Class.forName(packageName + "." + getActionString.get(2));
			Method method = MyClass.getMethod(getActionString.get(2), WebDriver.class);
			@SuppressWarnings("unused")
			String[] results = (String[]) method.invoke(null, driver);
			String ExpObject = data.get("ExpectedObject");
			String ExpObject_by = ExpObject.split(";")[0].toString();
			String ExpObject_Desc = ExpObject.split(";")[1].toString();
			if (ExpObject_by.length() > 0) {
				Assert.assertEquals(driver.findElement(By.id(ExpObject_by)).getText(), data.get("ExpectedData"),
						getActionString.get(2) + data.get("ID") + "验证结果：");
			} else if (ExpObject_Desc.length() > 0) {
				Assert.assertEquals(driver.findElement(By.xpath(ExpObject_Desc)).getText(), data.get("ExpectedData"),
						getActionString.get(2) + data.get("ID") + "验证结果：");
			}

			WebDriverDemo.WebSleep(500);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}