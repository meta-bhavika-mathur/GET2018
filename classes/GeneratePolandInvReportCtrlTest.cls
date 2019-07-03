@isTest
public class GeneratePolandInvReportCtrlTest {
    @TestSetup
    private static void setupGeneralTestData() {
    	try{
               GenerateReportsTestMethodsUtility.testSetup(); 
        }catch(Exception e){
            System.debug('Exception Message:' + e.getMessage());
        }
	}
    
    @isTest
    private static void polandAuditFile_VATReportTest(){
        try{
            Test.startTest();
            
            Integer lim = 10;
            CreateTestData.postInvoices(lim);
            GeneratePolandInvoicesReportController generatePolandReportController = new GeneratePolandInvoicesReportController();
            generatePolandReportController.startDate = Date.today().addYears(-1);
            generatePolandReportController.endDate = Date.today();  
            generatePolandReportController.fileType = 'report1';
            generatePolandReportController.createXml();
            
        	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception in test class:' + e.getStackTraceString());
        }
    } 
    
    @isTest
    private static void polandAuditFile_FASalesReportTest(){
        try{
            Test.startTest();
            
            Integer lim = 10;
            CreateTestData.postInvoices(lim);
            GeneratePolandInvoicesReportController generatePolandReportController = new GeneratePolandInvoicesReportController();
            generatePolandReportController.startDate = Date.today().addYears(-1);
            generatePolandReportController.endDate = Date.today();  
            generatePolandReportController.fileType = 'report2';
            generatePolandReportController.createXml();
            
        	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception in test class:' + e.getStackTraceString());
        }
    } 
    
    @isTest
    private static void polandAuditFile_KRAllEntriesReportTest(){
        try{
            Test.startTest();
            
            Integer lim = 10;
            CreateTestData.postInvoices(lim);
            GeneratePolandInvoicesReportController generatePolandReportController = new GeneratePolandInvoicesReportController();
            generatePolandReportController.startDate = Date.today().addYears(-1);
            generatePolandReportController.endDate = Date.today();  
            generatePolandReportController.fileType = 'report3';
            generatePolandReportController.createXml();
            
        	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception in test class:' + e.getStackTraceString());
        }
    }
}