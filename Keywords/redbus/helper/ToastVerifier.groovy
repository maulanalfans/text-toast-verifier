package redbus.helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

class ToastVerifier {
	/**
	 * Check if element present in timeout
	 * @param to Katalon test object
	 * @param timeout time to wait for element to show up
	 * @return true if element present, otherwise false
	 */
	@Keyword

	//	toast verifier with scan screen to OCR then verify specific text
	def verifyToast(String expectedText, String outputFileName) {
		Mobile.delay(1)
		String screenshotPath = Mobile.takeScreenshot('Screenshots/toast_capture_'+outputFileName+'.png')

		// Jalankan script Python untuk OCR dengan expected text sebagai argumen
		def pythonScriptPath = 'scripts/detect_toast.py'
		def command = "python ${pythonScriptPath} ${screenshotPath} \"${expectedText}\""

		Process p = Runtime.getRuntime().exec(command)
		p.waitFor()

		def exitCode = p.exitValue()
		def output = p.inputStream.text.trim()
		def errorOutput = p.errorStream.text.trim()

		return exitCode
	}
}
