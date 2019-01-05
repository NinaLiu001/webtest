package utils;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * @Description ��װ����selenium�Ĳ����Լ�ͨ�÷������������д�����
 */
public class SeleniumUtil {
	/** ʹ��Log4j����һ�����ǻ�ȡ��־��¼���������¼�������������־��Ϣ */
	public static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public ITestResult it = null;
	public WebDriver driver = null;
	public WebDriver window = null;

	/***
	 * �������������ҳ��
	 */
	public void launchBrowser(String browserName, ITestContext context, String webUrl, int timeOut) {
		SelectBrowser select = new SelectBrowser();
		driver = select.selectExplorerByName(browserName, context);
		try {
			maxWindow(browserName);
			waitForPageLoading(timeOut);
			get(webUrl);
		} catch (TimeoutException e) {
			logger.warn("ע�⣺ҳ��û����ȫ���س�����ˢ�����ԣ���");
			refresh();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String status = (String) js.executeScript("return document.readyState");

			logger.info("��ӡ״̬��" + status);
		}

	}

	// ------------------------------- �Դ��ڽ��в���
	// -----------------------------------
	/**
	 * ������������
	 */
	public void maxWindow(String browserName) {
		logger.info("��������:" + browserName);
		driver.manage().window().maximize();
	}

	/**
	 * �趨��������ڴ�С�� ������������ڵĴ�С�����������Ƚϳ�������;��<br>
	 * 1����ͳһ���������С���������������ԱȽ����׵ĸ�һЩ����ͼ��ȶԵĹ��߽��н��
	 * ���������Ե�����Լ��ձ������ԡ�������Ը�sikuli��ϣ�ʹ��sikuli����flash��<br>
	 * 2���ڲ�ͬ���������С�·��ʲ���վ�㣬�Բ���ҳ���ͼ�����棬Ȼ��۲��ʹ��ͼ��ȶԹ��߶Ա���ҳ���ǰ����ʽ�������⡣
	 * ������Խ���������ó��ƶ��˴�С(320x480)��Ȼ������ƶ�վ�㣬������ʽ����������<br>
	 */
	public void setBrowserSize(int width, int height) {
		driver.manage().window().setSize(new Dimension(width, height));
	}

	/**
	 * get������װ
	 */
	public void get(String url) {
		driver.get(url);
		logger.info("�򿪲���ҳ��:[" + url + "]");
	}

	/**
	 * close������װ
	 */
	public void close() {
		driver.close();
	}

	/**
	 * �˳�
	 */
	public void quit() {
		driver.quit();
	}

	/**
	 * ˢ�·�����װ
	 */
	public void refresh() {
		driver.navigate().refresh();
		logger.info("ҳ��ˢ�³ɹ���");
	}

	/**
	 * ���˷�����װ
	 */
	public void back() {
		driver.navigate().back();
	}

	/**
	 * ǰ��������װ
	 */
	public void forward() {
		driver.navigate().forward();
	}

	/**
	 * ���ҳ��ı���
	 */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * �ȴ�alert����
	 */
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert) throws NoAlertPresentException {
		final int ONE_ROUND_WAIT = 200;
		NoAlertPresentException lastException = null;

		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;

		for (long i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {

			try {
				Alert alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				lastException = e;
			}
			pause(ONE_ROUND_WAIT);

			if (System.currentTimeMillis() > endTime) {
				break;
			}
		}
		throw lastException;
	}

	/**
	 * @Description ����windows GUI������Ҫ�������û���������ʱ��
	 *              seleniumm����ֱ�Ӳ�������Ҫ����http://modifyusername:
	 *              modifypassword@yoururl ���ַ���
	 * 
	 */
	public void loginOnWinGUI(String username, String password, String url) {
		driver.get(username + ":" + password + "@" + url);
	}

	// ============================== �Դ��ڽ��в��� ==================================

	// ------------------------------ ����Ԫ�� -------------------------------------
	/**
	 * ��װ����Ԫ�صķ��� element
	 */
	public WebElement findElementBy(By by) {
		return driver.findElement(by);
	}

	/**
	 * ��װ����Ԫ�صķ��� elements
	 */
	public List<WebElement> findElementsBy(By by) {
		return driver.findElements(by);
	}

	/**
	 * ����һ����ͬ��elements�� ѡ�� ���з��� һ�� Ȼ�������ѡ������ ������λ
	 */
	public WebElement getOneElement(By bys, By by, int index) {
		return findElementsBy(bys).get(index).findElement(by);
	}

	// ============================= ����Ԫ��
	// =========================================

	// --------------------------- �ж�Ԫ��״̬
	// ----------------------------------------
	/**
	 * �ж��ı��ǲ��Ǻ�����Ҫ����ı�һ��
	 **/
	public void isTextCorrect(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			logger.error("������������ [" + expected + "] �����ҵ��� [" + actual + "]");
			Assert.fail("������������ [" + expected + "] �����ҵ��� [" + actual + "]");

		}
		logger.info("�ҵ�������������: [" + expected + "]");
	}

	/**
	 * �жϱ༭���ǲ��ǿɱ༭
	 */
	public void isInputEdit(WebElement element) {

	}

	/** ���Ԫ���Ƿ���� */
	public boolean isEnabled(WebElement element) {
		boolean isEnable = false;
		if (element.isEnabled()) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is enabled");
			isEnable = true;
		} else if (element.isDisplayed() == false) {
			logger.warn("The element: [" + getLocatorByElement(element, ">") + "] is not enable");
			isEnable = false;
		}
		return isEnable;
	}

	/** ���Ԫ���Ƿ���ʾ */
	public boolean isDisplayed(WebElement element) {
		boolean isDisplay = false;
		if (element.isDisplayed()) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is displayed");
			isDisplay = true;
		} else if (element.isDisplayed() == false) {
			logger.warn("The element: [" + getLocatorByElement(element, ">") + "] is not displayed");

			isDisplay = false;
		}
		return isDisplay;
	}

	/** ���Ԫ���ǲ��Ǵ��� */
	public boolean isElementExist(By byElement) {
		try {
			findElementBy(byElement);
			return true;
		} catch (NoSuchElementException nee) {
			return false;
		}
	}

	/** ���Ԫ���Ƿ񱻹�ѡ */
	public boolean isSelected(WebElement element) {
		boolean flag = false;
		if (element.isSelected() == true) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is selected");
			flag = true;
		} else if (element.isSelected() == false) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is not selected");
			flag = false;
		}
		return flag;
	}

	/**
	 * �ж�ʵ���ı�ʱ����������ı�
	 * 
	 * @param actual
	 *            ʵ���ı�
	 * @param expect
	 *            �����ı�
	 */
	public void isContains(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			logger.error("The [" + actual + "] is not contains [" + expect + "]");
			Assert.fail("The [" + actual + "] is not contains [" + expect + "]");
		}
		logger.info("The [" + actual + "] is contains [" + expect + "]");
	}

	/** ���checkbox�ǲ��ǹ�ѡ */
	public boolean isCheckboxSelected(By elementLocator) {
		if (findElementBy(elementLocator).isSelected() == true) {
			logger.info("CheckBox: " + getLocatorByElement(findElementBy(elementLocator), ">") + " ����ѡ");
			return true;
		} else
			logger.info("CheckBox: " + getLocatorByElement(findElementBy(elementLocator), ">") + " û�б���ѡ");
		return false;

	}

	/**
	 * �ڸ�����ʱ����ȥ����Ԫ�أ����û�ҵ���ʱ���׳��쳣
	 */
	public void waitForElementToLoad(int timeOut, final By By) {
		logger.info("��ʼ����Ԫ��[" + By + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(By);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			logger.error("��ʱ!! " + timeOut + " ��֮��û�ҵ�Ԫ�� [" + By + "]");
			Assert.fail("��ʱ!! " + timeOut + " ��֮��û�ҵ�Ԫ�� [" + By + "]");

		}
		logger.info("�ҵ���Ԫ�� [" + By + "]");
	}

	/**
	 * pageLoadTimeout��ҳ�����ʱ�ĳ�ʱʱ�䡣��Ϊwebdriver���ҳ���������ڽ��к���Ĳ�����
	 * �������ҳ���������ʱʱ����û�м�����ɣ���ôwebdriver�ͻ��׳��쳣
	 */
	public void waitForPageLoading(int pageLoadTime) {
		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
	}
	// ========================== �ж�Ԫ��״̬ =======================================

	// -------------------------- ��Ԫ�ز��� ----------------------------------------
	/**
	 * ���Ԫ�ص��ı�
	 */
	public String getText(By elementLocator) {
		return driver.findElement(elementLocator).getText().trim();
	}

	/**
	 * ��õ�ǰselectѡ���ֵ
	 */
	public List<WebElement> getCurrentSelectValue(By by) {
		List<WebElement> options = null;
		Select s = new Select(driver.findElement(by));
		options = s.getAllSelectedOptions();
		return options;
	}

	/**
	 * ���������ֵ ������� �����ĳЩinput����� û��value���ԣ���������ȡ��input�� ֵ�÷���
	 */
	public String getInputValue(String chose, String choseValue) {
		String value = null;
		switch (chose.toLowerCase()) {
		case "name":
			String jsName = "return document.getElementsByName('" + choseValue + "')[0].value;"; // ��JSִ�е�ֵ
																									// ���س�ȥ
			value = (String) ((JavascriptExecutor) driver).executeScript(jsName);
			break;

		case "id":
			String jsId = "return document.getElementById('" + choseValue + "').value;"; // ��JSִ�е�ֵ
																							// ���س�ȥ
			value = (String) ((JavascriptExecutor) driver).executeScript(jsId);
			break;

		default:
			logger.error("δ�����chose:" + chose);
			Assert.fail("δ�����chose:" + chose);
		}
		return value;

	}

	/** ���CSS value */
	public String getCSSValue(WebElement e, String key) {

		return e.getCssValue(key);
	}

	/**
	 * ���Ԫ�� ���Ե��ı�
	 */
	public String getAttributeText(By elementLocator, String attribute) {
		return driver.findElement(elementLocator).getAttribute(attribute).trim();
	}

	/** ����Ԫ������ȡ��Ԫ�صĶ�λֵ */
	public String getLocatorByElement(WebElement element, String expectText) {
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(expectText) + 1, text.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to find the string [" + expectText + "]");
		}
		return expect;
	}

	/**
	 * ��װ�������
	 */
	public void click(By byElement) {

		try {
			clickTheClickable(byElement, System.currentTimeMillis(), 2500);
		} catch (StaleElementReferenceException e) {
			logger.error("The element you clicked:[" + byElement + "] is no longer exist!");
			Assert.fail("The element you clicked:[" + byElement + "] is no longer exist!");
		} catch (Exception e) {
			logger.error("Failed to click element [" + byElement + "]");
			Assert.fail("Failed to click element [" + byElement + "]", e);
		}
		logger.info("���Ԫ�� [" + byElement + "]");
	}

	/**
	 * ��װ�������
	 */
	public void clear(By byElement) {
		try {
			findElementBy(byElement).clear();
		} catch (Exception e) {
			logger.error("���Ԫ�� [" + byElement + "] �ϵ�����ʧ��!");
		}
		logger.info("���Ԫ�� [" + byElement + "]�ϵ�����");
	}

	/**
	 * ���������������
	 */
	public void type(By byElement, String key) {
		try {
			findElementBy(byElement).sendKeys(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("���� [" + key + "] �� Ԫ��[" + byElement + "]ʧ��");
			Assert.fail("���� [" + key + "] �� Ԫ��[" + byElement + "]ʧ��");
		}
		logger.info("���룺[" + key + "] �� [" + byElement + "]");
	}

	/**
	 * ģ����̲�����,����Ctrl+A,Ctrl+C�� ������⣺<br>
	 * 1��WebElement element - Ҫ��������Ԫ�� <br>
	 * 2��Keys key- �����ϵĹ��ܼ� ����ctrl ,alt�� <br>
	 * 3��String keyword - �����ϵ���ĸ
	 */
	public void pressKeysOnKeyboard(WebElement element, Keys key, String keyword) {

		element.sendKeys(Keys.chord(key, keyword));
	}

	/**
	 * �л�frame - ����String���ͣ�frame���֣�
	 */
	public void inFrame(String frameId) {
		driver.switchTo().frame(frameId);
	}

	/**
	 * �л�frame - ����frame�ڵ�ǰҳ���е�˳������λ
	 */
	public void inFrame(int frameNum) {
		driver.switchTo().frame(frameNum);
	}

	/**
	 * �л�frame - ����ҳ��Ԫ�ض�λ
	 */
	public void switchFrame(WebElement element) {
		try {
			logger.info("��������frame:[" + getLocatorByElement(element, ">") + "]");
			driver.switchTo().frame(element);
		} catch (Exception e) {
			logger.info("����frame: [" + getLocatorByElement(element, ">") + "] ʧ��");
			Assert.fail("����frame: [" + getLocatorByElement(element, ">") + "] ʧ��");
		}
		logger.info("����frame: [" + getLocatorByElement(element, ">") + "]�ɹ� ");
	}

	/** ����frame */
	public void outFrame() {
		driver.switchTo().defaultContent();
	}

	/**
	 * ѡ������ѡ�� -����value
	 */
	public void selectByValue(By by, String value) {
		Select s = new Select(driver.findElement(by));
		s.selectByValue(value);
	}

	/**
	 * ѡ������ѡ�� -����index�Ǳ�
	 */
	public void selectByIndex(By by, int index) {
		Select s = new Select(driver.findElement(by));
		s.selectByIndex(index);
	}

	/**
	 * ѡ������ѡ�� -�����ı�����
	 */
	public void selectByText(By by, String text) {
		Select s = new Select(driver.findElement(by));
		s.selectByVisibleText(text);
	}

	/**
	 * ִ��JavaScript ����
	 */
	public void executeJS(String js) {
		((JavascriptExecutor) driver).executeScript(js);
		logger.info("ִ��JavaScript��䣺[" + js + "]");
	}

	/**
	 * ִ��JavaScript �����Ͷ��� �÷���seleniumUtil.executeJS("arguments[0].click();",
	 * seleniumUtil.findElementBy(MyOrdersPage.MOP_TAB_ORDERCLOSE));
	 */
	public void executeJS(String js, Object... args) {
		((JavascriptExecutor) driver).executeScript(js, args);
		logger.info("ִ��JavaScript��䣺[" + js + "]");
	}

	/**
	 * ��װseleniumģ�������� - ����ƶ���ָ��Ԫ��
	 */
	public void mouseMoveToElement(By by) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.moveToElement(driver.findElement(by));
		mouse.perform();
	}

	/**
	 * ��װseleniumģ�������� - ����ƶ���ָ��Ԫ��
	 */
	public void mouseMoveToElement(WebElement element) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.moveToElement(element);
		mouse.perform();
	}

	/**
	 * ��װseleniumģ�������� - ����һ�
	 */
	public void mouseRightClick(By element) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.contextClick(findElementBy(element));
		mouse.perform();
	}

	/**
	 * �ϴ��ļ�����Ҫ��������ϴ���Ƭ�Ĵ��ڲ���
	 * 
	 * @param brower
	 *            ʹ�õ����������
	 * @param file
	 *            ��Ҫ�ϴ����ļ����ļ���
	 */
	public void handleUpload(String browser, File file) {
		String filePath = file.getAbsolutePath();
		String executeFile = "res/script/autoit/Upload.exe";
		String cmd = "\"" + executeFile + "\"" + " " + "\"" + browser + "\"" + " " + "\"" + filePath + "\"";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ===================== ��Ԫ�ؽ��в��� ============================

	/**
	 * ���cookies,���Զ���½�ı�Ҫ����
	 */
	public void addCookies(int sleepTime) {
		pause(sleepTime);
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie c : cookies) {
			System.out.println(c.getName() + "->" + c.getValue());
			if (c.getName().equals("logisticSessionid")) {
				Cookie cook = new Cookie(c.getName(), c.getValue());
				driver.manage().addCookie(cook);
				System.out.println(c.getName() + "->" + c.getValue());
				System.out.println("��ӳɹ�");
			} else {
				System.out.println("û���ҵ�logisticSessionid");
			}

		}

	}

	// webdriver�п������úܶ�ĳ�ʱʱ��
	/** implicitlyWait��ʶ�����ʱ�ĳ�ʱʱ�䡣�������ʱ���������û�ҵ��Ļ��ͻ��׳�NoSuchElement�쳣 */
	public void implicitlyWait(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	/** setScriptTimeout���첽�ű��ĳ�ʱʱ�䡣webdriver�����첽ִ�нű�������������첽ִ�нű��ű����ؽ���ĳ�ʱʱ�� */
	public void setScriptTimeout(int timeOut) {
		driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
	}

	/** �����Ļ�ķֱ��� - �� */
	public static double getScreenWidth() {
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	/**
	 * ��ͣ��ǰ������ִ�У���ͣ��ʱ��Ϊ��sleepTime
	 */
	public void pause(int sleepTime) {
		if (sleepTime <= 0) {
			return;
		}
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** ���ܵ��ʱ�����Ե������ */
	private void clickTheClickable(By byElement, long startTime, int timeOut) throws Exception {
		try {
			findElementBy(byElement).click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.warn(byElement + " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				logger.warn(byElement + " is unclickable, try again");
				clickTheClickable(byElement, startTime, timeOut);
			}
		}
	}

}
