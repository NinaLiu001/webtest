package TestCase;

import java.io.IOException;

import TestData.DataPrepare;
import TestData.ExcelData;
import jxl.read.biff.BiffException;

public class BaseAction {
	// ��testNG �ж�ȡfileName��caseName
	public static String fileName = "TestData";
	public static String caseName = null;
	// actions ���ֺ� sheet ������ͬ
	public ExcelData ed = new ExcelData(fileName, caseName);

	//��ȡҳ����Ϣ��������������ͣ�url ��
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
					System.out.println("�Բ������ݱ���û�����øò�Ʒ��Ϣ��");
			}
		}
		return pageInfo;
	}
	
}
