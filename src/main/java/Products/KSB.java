package Products;

import java.util.HashMap;

//康寿宝
public class KSB {
	public String productName;
	public String productCode;
	// 该产品包括的险种组合
	public HashMap<Integer, String> InsuranceVarieties;

	public KSB() {
		productName = "康寿宝";
		productCode = "152";
		InsuranceVarieties = new HashMap<Integer, String>();
		InsuranceVarieties.put(120, "康寿宝两全保险");
		// ××××
	}
}