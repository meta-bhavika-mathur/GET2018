@isTest
public class GenerateLatvianAuditFileControllerTest {
	
    @TestSetup
    private static void setupGeneralTestData() {
        try{
           GenerateReportsTestMethodsUtility.testSetup(); 
        }catch(Exception e){
            System.debug('Exception in test setup:' + e.getStackTraceString());
        }       
    }
	
	@isTest
    private static void latvianFileGenerationTest(){
        try{
            Test.startTest();
            
            Integer lim = 10;
            CreateTestData.postInvoices(lim);
            CreateTestData.postPInvoices(lim);
            GenerateLatvianAuditFileController generateLatvianReportController = new GenerateLatvianAuditFileController();    
            generateLatvianReportController.periodMonth = String.valueOf(Date.today().month());
            generateLatvianReportController.periodYear = String.valueOf(Date.today().year());
        
            generateLatvianReportController.createXml();
            c2g__codaInvoice__c salesInvoice = [SELECT Id, c2g__Account__r.Name FROM c2g__codaInvoice__c LIMIT 1];
            String expectedValue = salesInvoice.c2g__Account__r.Name;  
            Boolean containsVal = false;
            for(GenerateLatvianAuditFileController.transactionSalesInvWrapper salesTrn : generateLatvianReportController.listOfTrnsctnSalesInv){
                if(salesTrn.accName == expectedValue){
                    containsVal = true;
                }
            }
            System.assert(containsVal);
        	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception in test class:' + e.getStackTraceString());
        }
    }  
}