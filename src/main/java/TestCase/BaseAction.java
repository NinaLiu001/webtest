package TestCase;

import java.io.IOException;

import TestData.DataPrepare;
import TestData.ExcelData;
import jxl.read.biff.BiffException;

public class BaseAction {
	// 从testNG 中读取fileName，caseName
	public static String fileName = "TestData";
	public static String caseName = null;
	// actions 名字和 sheet 名字相同
	public ExcelData ed = new ExcelData(fileName, caseName);

	//获取页面信息，包括浏览器类型，url 等
	public Object[] getPage(String productCode,String channel) {
		caseName = "page";
		Object[][] pageInfo = null;
		try {
			pageInfo = ed.getExcelData();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (productCode != null) {
			for (int i = 0; i < pageInfo[0].length; i++) {
				if (pageInfo[i][2].toString().equals(productCode)&&pageInfo[i][4].toString().equals(channel)) {
					return pageInfo[i];
				} else
					System.out.println("对不起，数据表中没有配置该产品信息！");
			}
		}
		return pageInfo;
	}
	
}
