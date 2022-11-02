package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentRepository;

public class EndToEndGradeBookTest {
	
	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final int SLEEP_DURATION = 1000; // 1 second.
	public static final String TEST_ASSIGNMENT_NAME = "Test Assignment";
	public static final String TEST_COURSE_TITLE = "Test Course";
	public static final String TEST_STUDENT_CLASS = "Test Class";
	
	

	@Autowired
	AssignmentGradeRepository assignnmentGradeRepository;

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Test 
	public void AddAssignmentTest() throws Exception {
		Assignment a = new Assignment();
		a.setId(99999);
		a.getId();
		a.setName(TEST_ASSIGNMENT_NAME);
		a.setDueDate(new java.sql.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
		a.setNeedsGrading(1);
		
		a = assignmentRepository.save(a);
		
		AssignmentGrade ag = null;
		
		// set the driver location and start driver
				//@formatter:off
				// browser	property name 				Java Driver Class
				// edge 	webdriver.edge.driver 		EdgeDriver
				// FireFox 	webdriver.firefox.driver 	FirefoxDriver
				// IE 		webdriver.ie.driver 		InternetExplorerDriver
				//@formatter:on
				
				/*
				 * initialize the WebDriver and get the home page. 
				 */

				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
				WebDriver driver = new ChromeDriver();
				// Puts an Implicit wait for 10 seconds before throwing exception
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				driver.get(URL);
				Thread.sleep(SLEEP_DURATION);
				
				try {
					
					
					List<WebElement> elements  = driver.findElements(By.xpath("//div[@data-field='assignmentName']/div"));
					boolean found = false;
					for (WebElement we : elements) {
						System.out.println(we.getText()); // for debug
						if (we.getText().equals(TEST_ASSIGNMENT_NAME)) {
							found=true;
							we.findElement(By.xpath("descendant::input")).click();
							break;
						}
					}
					assertTrue( found, "Unable to locate TEST ASSIGNMENT in list of assignments to be graded.");

					/*
					 *  Locate and click Grade button to indicate to grade this assignment.
					 */
					
					driver.findElement(By.xpath("//a")).click();
					Thread.sleep(SLEEP_DURATION);

				
					
					
					/*
					 *  Locate submit button and click
					 */
					driver.findElement(By.xpath("//button[@id='Submit']")).click();
					Thread.sleep(SLEEP_DURATION);

					

					

				} catch (Exception ex) {
					throw ex;
				} finally {

					/*
					 *  clean up database so the test is repeatable.
					 */
					ag = assignnmentGradeRepository.findByAssignmentIdAndStudentEmail(a.getId(), TEST_USER_EMAIL);
					if (ag!=null) assignnmentGradeRepository.delete(ag);
					assignmentRepository.delete(a);
					

					driver.quit();
				}
		
		
	}
	

}
