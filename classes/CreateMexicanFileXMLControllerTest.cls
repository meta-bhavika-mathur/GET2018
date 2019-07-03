@isTest
private class CreateMexicanFileXMLControllerTest {
	
	@TestSetup
    private static void setupGeneralTestData() {
        try{
           GenerateReportsTestMethodsUtility.testSetup(); 
        }catch(Exception e){
            System.debug('Exception Message:' + e.getMessage());
        }   
    }
	
	@isTest
	private static void xmlGenerationForCoAReportTest(){		
        try{
            	Test.startTest();              
            	CreateMexicanFileXMLController controller = new CreateMexicanFileXMLController();
           		controller.periodYear = String.valueOf('2017');
				controller.periodMonth = String.valueOf('1');
				controller.fileTypeIsSelected = true;
				controller.fileType = 'report1';           
                controller.createXML();              
            	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception occured in test class:' + e.getMessage());
        }
	}
    
    @isTest
	private static void xmlGenerationForCoAReportExceptionTest(){		
        try{
            	Test.startTest();              
            	CreateMexicanFileXMLController controller = new CreateMexicanFileXMLController();
				controller.fileTypeIsSelected = true;
				controller.fileType = 'report1';             
                controller.createXML();
           		List<Apexpages.Message> msgs = ApexPages.getMessages();               
            	System.assert(msgs.size() > 0, 'Throw exception to Choose Year and Month');
            	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception occured in test class:' + e.getMessage());
        }
	}
    
    @isTest
	private static void getMapOfAttributesAndValByLabelTest(){		
        try{
            	Test.startTest();              
            	CreateMexicanFileXMLController controller = new CreateMexicanFileXMLController();
           		controller.periodYear = String.valueOf('2017');
				controller.periodMonth = String.valueOf('1');
				controller.fileTypeIsSelected = true;
				controller.fileType = 'report1';
           		
            	c2g__codaGeneralLedgerAccount__c gla = [SELECT 
                                                        	Id,
                                                        	Name, 
                                                        	c2g__ReportingCode__c, 
                                                        	c2g__TrialBalance1__c, 
                                                        	c2g__TrialBalance2__c, 
                                                        	c2g__TrialBalance3__c
                                                       FROM c2g__codaGeneralLedgerAccount__c LIMIT 1];
            
                Map<String, String> mapOfAttributesAndValues  = controller.getMapOfAttributesAndValByLabel('Ctas', gla);
            
            	String expectedNumCta, expectedCodAgrup, expectedNivel, expectedSubCtaDe, expectedDesc;
            	expectedNumCta = expectedCodAgrup = gla.c2g__ReportingCode__c;
            	expectedNivel = '1';
               
            	System.assertEquals(expectedNumCta, mapOfAttributesAndValues.get('NumCta'));
                System.assertEquals(expectedCodAgrup, mapOfAttributesAndValues.get('CodAgrup'));
                System.assertEquals(expectedNivel, mapOfAttributesAndValues.get('Nivel'));           
            	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception occured in test class:' + e.getMessage());
        }
	}
    
     @isTest
	private static void getMapOfAttributesAndValByLabelNivelValueTest(){		
        try{
            	Test.startTest();              
            	CreateMexicanFileXMLController controller = new CreateMexicanFileXMLController();
           		controller.periodYear = String.valueOf('2017');
				controller.periodMonth = String.valueOf('1');
				controller.fileTypeIsSelected = true;
				controller.fileType = 'report1';
           		
            	c2g__codaGeneralLedgerAccount__c gla = [SELECT 
                                                        	Id,
                                                        	Name, 
                                                        	c2g__ReportingCode__c, 
                                                        	c2g__TrialBalance1__c, 
                                                        	c2g__TrialBalance2__c, 
                                                        	c2g__TrialBalance3__c
                                                       FROM c2g__codaGeneralLedgerAccount__c LIMIT 1];
            
            	gla.c2g__ReportingCode__c = '1111.1200';
            	gla.Name = '0613-Retained Earnings';
           		gla.c2g__TrialBalance1__c = 'Assets';
                gla.c2g__TrialBalance2__c = 'Intangible Assets';
                gla.c2g__TrialBalance3__c = 'Accumulated amortisation: Website';
            	
            	update gla;
                        
                Map<String, String> mapOfAttributesAndValues  = controller.getMapOfAttributesAndValByLabel('Ctas', gla);            
            	
            	String expectedNumCta, expectedCodAgrup, expectedNivel, expectedSubCtaDe, expectedDesc, expectedNatur;
            	expectedNumCta = expectedCodAgrup = '1111.1200';
            	expectedNivel = '2';
            	expectedSubCtaDe = '1111';
            	expectedDesc = 'Retained Earnings';
            	expectedNatur = 'A';
               
            	System.assertEquals(expectedNumCta, mapOfAttributesAndValues.get('NumCta'));
                System.assertEquals(expectedCodAgrup, mapOfAttributesAndValues.get('CodAgrup'));
                System.assertEquals(expectedNivel, mapOfAttributesAndValues.get('Nivel')); 
             	System.assertEquals(expectedSubCtaDe, mapOfAttributesAndValues.get('SubCtaDe')); 
            	System.assertEquals(expectedNatur, mapOfAttributesAndValues.get('Natur'));
            	System.assertEquals(expectedDesc, mapOfAttributesAndValues.get('Desc'));           
            	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception occured in test class:' + e.getMessage());
        }
	} 
    
    @isTest
	private static void xmlGenerationForTBReportTest(){		
        try{
            	Test.startTest();
                
                Integer lim = 10;
                CreateTestData.postInvoices(lim);
            	CreateMexicanFileXMLController controller = new CreateMexicanFileXMLController();
           		controller.periodYear = String.valueOf('2019');
				controller.periodMonth = String.valueOf('1');
				controller.fileTypeIsSelected = true;
				controller.fileType = 'report2';
            	controller.createXML();
          
            	Test.stopTest();
        }catch(Exception e){
            System.debug('Exception occured in test class:' + e.getMessage());
        }
	}
}