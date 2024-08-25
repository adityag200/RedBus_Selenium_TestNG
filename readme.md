RedBus Automation Project

Overview
This project contains automated tests for the RedBus website. It utilizes Selenium WebDriver, TestNG, and ExtentReports for test automation and reporting.

Project Structure

AdityaGaur_3185083_Selenium
│   .gitignore
│   cmdFile.bat
│   pom.xml
│   testng.xml
│   README.md
│
├───.idea
│       IntelliJ IDEA project files
│
├───ArchievedResults
│       Archived test results
│
├───CurrentTestResults
│       logs folder for storing current logs
|       ExtentReport folder for storing current test reports
│
├───FailedTestsScreenshots
│       Screenshots of failed tests
│
├───src
│   ├───main
│   │   ├───java
│   │   │   └───redbus
│   │   │   |   ├───pages
│   │   │   |   │      HomePage.java
│   │   │   |   │      BusSearchPage.java
│   │   │   |   │      TrainSearchPage.java
│   │   │   |   │───pagesUtil
│   │   │   |   │       HelperClass.java
│   │   └───resources
│   │           TestDataForRedBus.xlsx
│   │           testng.xml
│   │           config.properties
│   │           log4j.properties
│   │   │
│   ├───test
│   │   ├───java
│   │   │   └───redbus
│   │   │       └───tests
│   │   │       |        BaseTest.java
│   │   │       |        BusSearchPageTest.java
│   │   │       |        HomePageTest.java
│   │   │       |        TrainSearchPageTest.java
|   |   |       |--Utils
|   |   |           |--CommonUtils.java
|   |   |           |--ExcelFileIO.java
|   |   |           |--RetryAnalyzer.java
|   |   |           |--ScreenshotUtil.java
│   │   
│   │
└───target

Compiled classes and generated files

Setup
    Install dependencies: mvn clean install
    Configure config.properties with your test settings (e.g., URLs, browser configurations).
    Ensure WebDriver executables (chromedriver, msedgedriver) are available in the drivers directory.

Running Tests
    To run tests, execute mvn test command.
    TestNG XML configuration is available in testng.xml.
    Also, From CMD, run cmdFile.bat file.

Reporting
    Test reports are generated using ExtentReports and stored in the Report directory.

Contributors
Aditya Gaur