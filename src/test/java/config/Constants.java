package config;

public class Constants {
	
//System Variables
//sandbox(dmo)
//	public static final String URL = "http://dvmstr01:9081/maximo/";
//	public static final String URL = "http://10.160.16.54:9081/maximo/";
	
//Geraldine's sandbox	
//	public static final String URL = "http://utmaxp01:9080/maximo/";
	
//TST
//	public static final String URL = "http://10.160.16.59:9080/maximo/";
	public static final String URL = "http://dvmax01:9080/maximo/";
	
//UAT	
//	public static final String URL = "https://maximouat.tranzrail.co.nz/maximo/";
//	public static final String URL = "http://thermopolis.tranzrail.co.nz:9081/maximo";
	
//DEVPR
//	public static final String URL = "http://dvmaxp01:9080/maximo/webclient/login/login.jsp";
//	public static final String URL = "http://10.160.16.55:9080/maximo/webclient/login/login.jsp";
	
	public static final String Path_Screenshot = "H://Documents//workspace//maximotest//Screenshots//";
	public static final String Path_TestData = "H://Documents//workspace//maximotest//src//test//java//dataEngine//DataEngine.xlsx";
	public static final String Path_TestFiles = "H://Documents//workspace//maximotest//target//test-classes//dataEngine//";
	public static final String Path_OR = "H://Documents//workspace//maximotest//src//test//java//config//OR.txt";
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	
//	public static final String Path_Screenshot = "Z://zAutomation Tool//GitRepository//MaxTestTool//Screenshots//";
//	public static final String Path_TestData = "Z://zAutomation Tool//GitRepository//MaxTestTool//src//dataEngine//DataEngine.xlsx";
//	public static final String Path_TestFiles = "Z://zAutomation Tool//GitRepository//MaxTestTool//bin//dataEngine//";
//	public static final String Path_OR = "Z://zAutomation Tool//GitRepository//MaxTestTool//bin//config//OR.txt";
	
//Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID = 1;
	public static final int Col_Component = 4;
	public static final int Col_PageObject = 5;
	public static final int Col_ActionKeyword = 6;
	public static final int Col_RunMode = 3;
	public static final int Col_Result = 4;
	public static final int Col_DataSet = 7;
	public static final int Col_TestStepResult = 8;
		
// Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
	public static final String Sheet_TestData = "Test Data";
	
// Test Data
	public static final String UserName = "maxadmin";
	public static final String Password = "maxadmin";
}
