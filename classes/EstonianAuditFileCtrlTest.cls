@isTest 
private class EstonianAuditFileCtrlTest {

	@TestSetup
    private static void setupGeneralTestData() {
        try{
           GenerateReportsTestMethodsUtility.testSetup(); 
        }catch(Exception e){
            System.debug('Exception Message:' + e.getMessage());
        }   
    }
    
	@isTest
	private static void auditFileGenerationTest(){
          Test.startTest();
          Integer lim = 10;
          CreateTestData.postInvoices(lim);
          EstonianAuditFileController auditFileInstance = new EstonianAuditFileController();
          auditFileInstance.periodYear = String.valueOf('2017');
          auditFileInstance.periodMonth = String.valueOf('1');
          auditFileInstance.fileTypeIsSelected = true;
          auditFileInstance.saleLineLimit = 45; 
            
          auditFileInstance.fileType = 'report1';
          auditFileInstance.createXML();
    
          auditFileInstance.fileType = 'report2';
          auditFileInstance.createXML();
          Test.stopTest();       
	}
    
    @isTest
	private static void auditExceptionCheck(){
    	Test.startTest();
        EstonianAuditFileController auditFileInstance = new EstonianAuditFileController();
        auditFileInstance.fileTypeIsSelected = true;
        auditFileInstance.fileType = 'report1';
        auditFileInstance.createXML();
    
        auditFileInstance.fileType = 'report2';
        auditFileInstance.createXML();
        List<Apexpages.Message> msgs = ApexPages.getMessages();
        System.debug(msgs);
        System.assert(msgs.size() >0, 'Through excpetion to Choose Year and Month');
        Test.stopTest();         
	}
}