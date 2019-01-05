package TestData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @param fileName
 *            excel�ļ���
 * @param caseName
 *            sheet��
 */

public class ExcelData {
	public String fileName;
	public String caseName;

	public ExcelData(String fileName, String caseName) {
		super();
		this.fileName = fileName;
		this.caseName = caseName;
	}

	/**
	 * ���excel���е�����
	 */
	public Object[][] getExcelData() throws BiffException, IOException {

		Workbook workbook = Workbook.getWorkbook(new File(getPath()));
		Sheet sheet = workbook.getSheet(caseName);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		// Ϊ�˷���ֵ��Object[][],����һ�����е��еĶ�ά����
		@SuppressWarnings("unchecked")
		HashMap<String, String>[][] arrmap = new HashMap[rows - 1][1];
		List<String> arrkey=new ArrayList<String>();
		// ������������Ԫ��hashmap���г�ʼ��
		if (rows > 1) {
			for (int i = 0; i < rows - 1; i++) {
				arrmap[i][0] = new HashMap<String, String>();
			}
		} else {
			System.out.println("excel��û������");
		}

		// ������е���������Ϊhashmap��keyֵ
		for (int c = 0; c < columns; c++) {
			String cellvalue = sheet.getCell(c, 0).getContents();
			arrkey.add(cellvalue);
		}
		
		// �������еĵ�Ԫ���ֵ��ӵ�hashmap��
		for (int r = 1; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				String cellvalue = sheet.getCell(c, r).getContents();
				arrmap[r - 1][0].put(arrkey.get(c), cellvalue);
			}
		}
		return arrmap;
	}


	/**
	 * ���excel�ļ���·��
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getPath() throws IOException {
		File directory = new File(".");
		String sourceFile = directory.getCanonicalPath() + "\\src\\source\\" + fileName + ".xls";
		return sourceFile;
	}
}
