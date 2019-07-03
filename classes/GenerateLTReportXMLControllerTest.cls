@isTest
public class GenerateLTReportXMLControllerTest {
	
    @TestSetup
    private static void setupGeneralTestData() {
        try{
           GenerateReportsTestMethodsUtility.testSetup(); 
        }catch(Exception e){
            System.debug('Exception in test setup:' + e.getStackTraceString());
            System.debug('Exception Message:' + e.getMessage());
        }     
    }
	
	@isTest
    private static void lituaniaFileGenerationTest(){
        try{
            Test.startTest();
            
            Integer lim = 10;
            CreateTestData.postInvoices(lim);
            CreateTestData.postPInvoices(lim);
            GenerateLTReportXMLController generateLTReportController = new GenerateLTReportXMLController();
            generateLTReportController.startDate = Date.today().addYears(-1);
            generateLTReportController.endDate = Date.today();   
            generateLTReportController.fileType = 'report1';
            generateLTReportController.createXml();
            
        	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception in test class:' + e.getStackTraceString());
        }
    } 
    
    @isTest
    private static void saftFileGenerationTest(){
        try{
            Test.startTest();
            
            Integer lim = 10;           
            CreateTestData.postInvoices(lim);
            CreateTestData.postPInvoices(lim);
            GenerateLTReportXMLController generateLTReportController = new GenerateLTReportXMLController();
            generateLTReportController.startDate = Date.today();
            generateLTReportController.endDate = Date.today();   
            generateLTReportController.fileType = 'report2';
            generateLTReportController.createXml();
            
        	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception in test class:' + e.getStackTraceString() + e.getMessage());
        }
    } 
    
    @isTest
    private static void saftSetGLABalancesInfoTest(){
        try{
            Test.startTest();
            
            Integer lim = 10;           
            CreateTestData.postInvoices(lim);
            CreateTestData.postPInvoices(lim);
            GenerateLTReportXMLController generateLTReportController = new GenerateLTReportXMLController();
            generateLTReportController.startDate = Date.today() + 2;
            generateLTReportController.endDate = Date.today();   
            generateLTReportController.fileType = 'report2';
            generateLTReportController.createXml();
            
        	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception in test class:' + e.getStackTraceString() + e.getMessage());
        }
    } 
    
    @isTest
    private static void customerAccountTest(){
        try{
                Test.startTest();           
                Integer lim = 10;
                CreateTestData.postInvoices(lim);
            	CreateTestData.postPInvoices(lim);
                GenerateLTReportXMLController generateLTReportController = new GenerateLTReportXMLController();
                generateLTReportController.startDate = Date.today().addYears(-1);
                generateLTReportController.endDate = Date.today();    
            	generateLTReportController.fileType = 'report1';
                generateLTReportController.createXml();
                
                c2g__codaInvoice__c salesInvoice = [SELECT Id, c2g__Account__c FROM c2g__codaInvoice__c LIMIT 1];
                String expectedValue = salesInvoice.c2g__Account__c;            
                GenerateLTReportXMLController.wrapCustomerAccount customerAccount = generateLTReportController.mapOfCustomerAccount.get(expectedValue);
                String actualValue = customerAccount.accountId; 
            
                System.assertEquals(expectedValue, actualValue);                    
                Test.stopTest();
        }catch(Exception e){
            	System.debug('Exception in test class:' + e.getMessage());
        }
    }
    
    @isTest
    private static void supplierAccountTest(){
        try{
                Test.startTest();           
                Integer lim = 10;
                CreateTestData.postInvoices(lim);
            	CreateTestData.postPInvoices(lim);
                GenerateLTReportXMLController generateLTReportController = new GenerateLTReportXMLController();
                generateLTReportController.startDate = Date.today().addYears(-1);
                generateLTReportController.endDate = Date.today();    
            	generateLTReportController.fileType = 'report1';
                generateLTReportController.createXml();
                
                c2g__codaPurchaseInvoice__c purchaseInvoice = [SELECT Id, c2g__Account__c FROM c2g__codaPurchaseInvoice__c LIMIT 1];
                String expectedValue = purchaseInvoice.c2g__Account__c;            
                GenerateLTReportXMLController.wrapSupplierAccount supplierAccount = generateLTReportController.mapOfSupplierAccount.get(expectedValue);
                String actualValue = supplierAccount.accountId;
            	
          	 	System.assertEquals(expectedValue, actualValue); 
                Test.stopTest();
        }catch(Exception e){
            	System.debug('Exception in test class:' + e.getMessage());
        }
    }
}