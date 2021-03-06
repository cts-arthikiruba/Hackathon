package pageClass;

import java.util.List;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseClass.BaseUI;

public class CarWashPage extends BaseUI {

	WebDriverWait wait = new WebDriverWait(driver, 30);
	static int count = 0;

	// XPath to Select Car Wash Functionality
	By autoCare = By.xpath("//span[text() = 'Auto care']");
	By carWash = By.xpath("//span[text() = 'Car Wash']");

	// XPath to filter by Location, Ratings and Votes
	By location = By.xpath("//a[@class = ' lng_srtfltr'] [text() = 'Location']");
	By enterLocation = By.xpath("//input[@id = 'sortbydist'] [@name = 'sortbydist']");
	By clickGo = By.xpath("//button[@id = 'sortbydistbtn'] [text() = 'Go']");
	By rating = By.xpath("//li[@id = 'rating']");

	// XPath for Location Verification
	By verifyLocation = By.xpath("(//span[@class='jcn'])[2]");
	By verifyLocationPage = By.xpath("(//span[@class='lng_add'])[1]");

	// XPath to print the details
	By shopNameXpath = By.xpath("//*[contains(@class, 'lng_cont_name')]");
	By starXpath = By.xpath("//*[contains(@class, 'green-box')]");
	By voteXpath = By.xpath("//p[@class='newrtings ']/child::a/span[@class='rt_count lng_vote']");

	// Automate Auto Care
	public void autoCarMenu() {
		try {
			// Creating Test report for "autoCarMenu" function
			logger = report.createTest("Car Wash -Click Auto Car");
			driver.findElement(autoCare).click();
			reportPass("Auto Car Selected");

		} catch (Exception e) {
			
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Auto Car not Selected" + e.getMessage());
		}
	}

	// Automate Car Wash
	public void selectCarWash() {
		try {
			// Creating Test report for "selectCarWash" function
			logger = report.createTest("Car Wash -Click Car Wash");
			wait.until(ExpectedConditions.elementToBeClickable(carWash));
			driver.findElement(carWash).click();
			reportPass("Car Wash Selected");

		} catch (Exception e) {
			
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Car Wash not Selected");
		}
	}

	// Check Title of Car Wash
	public void carWashTitleCheck() {
		try {
			logger = report.createTest("Car Wash -Verify title");
			String title = driver.getTitle();
			if (title.contains("Car Washing Services")) {
				// Print if the Test Case Passed
				reportInfo("Title matches");
			} else {
				reportInfo("Title not matches");
			}
		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
		}
	}

	// Enter Location
	public void location() {
		try {
			logger = report.createTest("Car Wash -Click Location");
			wait.until(ExpectedConditions.elementToBeClickable(location));
			driver.findElement(location).click();
			wait.until(ExpectedConditions.elementToBeClickable(enterLocation));
			WebElement location = driver.findElement(enterLocation);
			location.clear();
			location.sendKeys("Guindy");
			// Click "Go" button
			driver.findElement(clickGo).click();
			// Print if the Test Case Passed
			reportPass("Location selected");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Location not selected");
		}
	}

	// Verify Ratings
	public void ratingIsDisplayed() {
		try {
			logger = report.createTest("Car Wash -Ratings");
			driver.findElement(rating).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement rateCheck = driver.findElement(starXpath);
			String rateStr = rateCheck.getText();
			if (rateStr.isBlank()) {
				// Print if the Test Case Passed
				reportInfo("Rate is not displayed");
			} else {
				reportInfo("Rate is displayed");
				driver.navigate().back();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Verify Votes
	public void voteIsDisplayed() {
		try {
			logger = report.createTest("Car Wash -Votes");
			WebElement voteCheck = driver.findElement(voteXpath);
			String voteStr = voteCheck.getText();
			if (voteStr.isBlank()) {
				reportInfo("Vote is not displayed");
			} else {
				reportInfo("Vote is displayed");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Verify whether location is displayed correctly
	public void locationResultTest() {
		try {
			logger = report.createTest("Verify Location Result Page");

			driver.findElement(verifyLocation).click();
			WebElement location_check = wait.until(ExpectedConditions.visibilityOfElementLocated(verifyLocationPage));
			String location = location_check.getText();
			if (location.contains("Guindy")) {
				reportInfo("Location is true");
			} else {
				reportInfo("Location is false");
			}
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Print all the required details
	public void displayDetails() {
		try {
			logger = report.createTest("Print all the details");
			List<WebElement> shopName = driver.findElements(shopNameXpath);
			List<WebElement> star = driver.findElements(starXpath);
			List<WebElement> vote = driver.findElements(voteXpath);
			for (int i = 0; i < shopName.size(); i++) {
				float ratings = Float.parseFloat(star.get(i).getText());
				String number = vote.get(i).getText().split(" ")[0];
				int votes = Integer.parseInt(number);
				if (count < 5) {
					if (ratings > 4 && votes > 20) {
						reportInfo(i + 1 + ". " + shopName.get(i).getText() + "||" + ratings + "||"
								+ vote.get(i).getText());
						count++;
					}
				}
			}
			// Print if the Test Case Passed
			reportPass("Details displayed");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Details not displayed");
		}
	}
}