package pages;

/**
 * @Description ���Կ�ʼ �� ���Խ��� �Ĳ���
 * 
 * */
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.LogConfiguration;
import utils.SeleniumUtil;

public class PageParpare {
	// �����ҳ����־ ��ʼ��
	static Logger logger = Logger.getLogger(PageParpare.class.getName());
	protected SeleniumUtil seleniumUtil = null;
	// ��ӳ�Ա��������ȡbeforeClass�����context����
	protected ITestContext testContext = null;
	protected String webUrl = "";
	protected int timeOut = 0;

	@BeforeClass
	/** ������������򿪲���ҳ�� */
	public void startTest(ITestContext context) {
		LogConfiguration.initLog(this.getClass().getSimpleName());
		seleniumUtil = new SeleniumUtil();
		// ����õ���contextֵ
		this.testContext = context;
		// ��testng.xml�ļ��л�ȡ�����������ֵ
		String browserName = context.getCurrentXmlTest().getParameter("browserName");
		timeOut = Integer.valueOf(context.getCurrentXmlTest().getParameter("timeOut"));
		webUrl = context.getCurrentXmlTest().getParameter("testurl");

		try {
			// ���������launchBrowser���������Լ���������Ҫ�Ǵ��������������Ե�ַ������󻯴���
			seleniumUtil.launchBrowser(browserName, context, webUrl, timeOut);
		} catch (Exception e) {
			logger.error("������������������������ǲ��Ǳ��ֶ��رջ�������ԭ��", e);
		}
		// ����һ��testng���������ԣ���driver��������֮�����ʹ��context��ʱȡ������Ҫ���ṩarrow
		// ��ȡdriver����ʹ�õģ���Ϊarrow��ͼ������Ҫһ��driver����
		testContext.setAttribute("SELENIUM_DRIVER", seleniumUtil.driver);
	}

	@AfterClass
	/** �������Թر������ */
	public void endTest() {
		if (seleniumUtil.driver != null) {
			// �˳������
			seleniumUtil.quit();
		} else {
			logger.error("�����driverû�л�ö���,�˳�����ʧ��");
			Assert.fail("�����driverû�л�ö���,�˳�����ʧ��");
		}
	}
}
