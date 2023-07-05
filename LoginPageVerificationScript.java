package mahFinance;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class LoginPageVerificationScript {
/*
 * Assumptions:
 * url: https://testingwebsite.com
 * valid username: Vikrant
 * valid password: Vikrant@123
 * email id (for password recovery):vikrantchougule@gmail.com 
 * newly set password: Vikrant@999
 * 
 * PLEASE NOTE, i have validated all points in the following sequence
 * validate that login button is disabled when both username and password text-fields are empty
 * validate that login button is enabled after entering valid credentials
 * validate that you logged in successfully after entering valid credentials
 * validate that after entering invalid credentials, error message is displayed
 * validate that even after entering invalid credentials, entered credentials are retained on login page
 * validate that when clicked on forgot password link, it redirects to password recovery page
 * Validate the password recovery functionality by following given steps in question
 */
	public static void main(String[] args) {
//Launch the browser
	WebDriver driver = new ChromeDriver();
		
//maximize browser window
	driver.manage().window().maximize();
				
//enter url
	driver.get("https://testingwebsite.com"); //assumed for test-scripting purpose
				
//implicitly Wait
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//as of now both username and password text-fields are empty
//validate that login button is disabled when both username and password text-fields are empty
	
	WebElement loginBtn1 = driver.findElement(By.name("login"));
	boolean loginBtnEnabledOrNot = loginBtn1.isEnabled(); // returns false
	System.out.println("Is login button enabled? "+loginBtnEnabledOrNot); //false
			
//enter username
	driver.findElement(By.id("username")).sendKeys("Vikrant");
				
//enter password
	driver.findElement(By.id("password")).sendKeys("Vikrant@123");
				
	WebElement loginBtn = driver.findElement(By.name("login"));
	
//validate for that login button is enabled after entering valid credentials
	boolean loginBtnEnabled = loginBtn.isEnabled();
	Assert.assertTrue(loginBtnEnabled, "true");
	System.out.println("Login button is enabled now");
	
//click on login button
	loginBtn.click();

//now we are redirected to home page
//to get the url of home page
	String homePageUrl = driver.getCurrentUrl(); //https://testingwebsite.com/home-page/
				
//validation for successful login with valid credentials --> url string contains "home-page"
	Assert.assertTrue(homePageUrl.contains("home-page"));
	System.out.println("Logged into app Successfully");
	
//click on logout button to move back to login page
	WebElement logoutBtn = driver.findElement(By.name("logout"));
	logoutBtn.click();
	
//testing for invalid credentials -->1) invalid username and valid password
//enter invalid username
	WebElement usernameTF1 = driver.findElement(By.id("username"));
	usernameTF1.sendKeys("Vik"); //invalid username
					
//enter valid password
	WebElement passwordTF1 = driver.findElement(By.id("password"));
	passwordTF1.sendKeys("Vikrant@123");	//valid password
	
//click on login button
	driver.findElement(By.name("login")).click();
	
//capture the error message -->"Invalid credentials"
	String errorMsg1 = driver.findElement(By.id("error")).getText(); //"Invalid credentials"
	
//Validation for error message	
	Assert.assertTrue(errorMsg1.contains("Invalid credentials"));
	System.out.println("Validated for invalid username and valid password");
	
//clear both username and password text-fields
	usernameTF1.clear();
	passwordTF1.clear();

//testing for invalid credentials -->2) invalid username and invalid password
//enter invalid username
	WebElement usernameTF2 = driver.findElement(By.id("username"));
	usernameTF2.sendKeys("Vit"); //invalid username		
	
//enter invalid password
	WebElement passwordTF2 = driver.findElement(By.id("password"));
	passwordTF2.sendKeys("Vi@012");	//invalid password	
	
//click on login button
	driver.findElement(By.name("login")).click();
		
//capture the error message -->"Invalid credentials"
	String errorMsg2 = driver.findElement(By.id("error")).getText(); //"Invalid credentials"
		
//Validation for error message	
	Assert.assertTrue(errorMsg2.contains("Invalid credentials"));
	System.out.println("Validated for invalid username and valid password");

//clear both username and password text-fields
	usernameTF2.clear();
	passwordTF2.clear();
	
//testing for invalid credentials -->3) valid username and invalid password

//enter valid username
	WebElement usernameTF3 = driver.findElement(By.id("username"));
	usernameTF3.sendKeys("Vikrant"); // valid username
	
//enter invalid password
	WebElement passwordTF3 = driver.findElement(By.id("password"));
	passwordTF3.sendKeys("Vikrant@456"); //invalid password	
		
//click on login button
	driver.findElement(By.name("login")).click();
		
//get / capture the error message -->"Invalid credentials"
	String errorMsg3 = driver.findElement(By.id("error")).getText(); //"Invalid credentials"
		
//Validation for error message
	Assert.assertTrue(errorMsg3.contains("Invalid credentials"));
	System.out.println("Validated for valid username and invalid password");		

//validation for ensuring username text-field and password text-field 
//retains entered credentials even after entering invalid credentials
	
//validation for username entered
	String usernameEntered = usernameTF3.getAttribute("value");
	Assert.assertTrue(usernameEntered.equals("Vikrant"));

//validation for password entered
	String passwordEntered = passwordTF3.getAttribute("value");
	Assert.assertTrue(passwordEntered.equals("Vikrant@456"));
			
//click on forgot password link, it redirect to forgot password recovery page
	WebElement forgotPasswordLink = driver.findElement(By.linkText("forgot-password"));
	forgotPasswordLink.click();
	
//get the url of the page --->https://testingwebsite.com/password-recovery-page/
	String passwordRecovery = driver.getCurrentUrl();
					
//validation--> we landed on password recovery page --> url string contains "password-recovery-page"
	Assert.assertTrue(passwordRecovery.contains("password-recovery-page"));
	System.out.println("We are on password recovery page");	
	
//enter mail id in given text-field on 	
	WebElement emailId = driver.findElement(By.id("enter-your-email-id"));
	emailId.sendKeys("vikrantchougule@gmail.com");

//click on submit button
	driver.findElement(By.id("submit-btn")).click();
	
//validating success message showing text 
//which says "Congrats! an email with password recovery instructions has been sent successfully"	
	String successMsg = driver.findElement(By.id("success-message")).getText();
	Assert.assertTrue(successMsg.equals("Congrats! an email with password recovery instructions has been sent successfully"));

//here you are redirected to password re-set page 
//it contains two text-fields- enter new password and confirm new password
//here you have to enter new password and then confirm it

//enter new password - "Vikrant@999"
	 driver.findElement(By.id("new-password")).sendKeys("Vikrant@999");
	  
//confirm new password	
	WebElement newPwdTF = driver.findElement(By.id("confirm-new-password"));
	newPwdTF.sendKeys("Vikrant@999");
	
//store new password in a variable
	String newPassword = newPwdTF.getAttribute("value");
	
//click on submit button
	driver.findElement(By.name("submit-new-password")).click();
	
//Now, you are redirected to login page
//validate that you can successfully login using new password
	
//enter username
	driver.findElement(By.id("username")).sendKeys("Vikrant");
					
//enter new password 
	driver.findElement(By.id("password")).sendKeys(newPassword);

//click on login button
	driver.findElement(By.name("login")).click();	

//it goes to home page -->get the url of the page
	String homePageUrl1 = driver.getCurrentUrl(); //https://testingwebsite.com/home-page/
					
//verification --> url string contains "home-page"
	Assert.assertTrue(homePageUrl1.contains("home-page"));
	System.out.println("Logged into app Successfully");
	
//close browser
	driver.close();
		
	}
}
