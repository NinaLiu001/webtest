package Products;

import java.util.HashMap;

//���ٱ�
public class KSB {
	public String productName;
	public String productCode;
	// �ò�Ʒ�������������
	public HashMap<Integer, String> InsuranceVarieties;

	public KSB() {
		productName = "���ٱ�";
		productCode = "152";
		InsuranceVarieties = new HashMap<Integer, String>();
		InsuranceVarieties.put(120, "���ٱ���ȫ����");
		// ��������
	}
}