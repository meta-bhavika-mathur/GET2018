@isTest 
private class TST_CreateFAIAXML {

	@TestSetup
	private static void testSetup() {
		Integer numOfCompanies = 1;
		String companyName = 'TestCompany';
		Integer currentCompany = 1;
		String taxRegistrationNumber = 'TAX11234';
		User someUser = new User(Id = UserInfo.getUserId());
		Integer accsPerGLA = 1;
		Integer numOfInvoicesPerAccount = 1;

		Test.startTest();
		CreateTestData.setupFullTestData(numOfCompanies, companyName, currentCompany, taxRegistrationNumber, someUser, accsPerGLA, numOfInvoicesPerAccount);
		Test.stopTest();
	}

	@isTest
	private static void auditFileGenerationTest(){
		Test.startTest();
		Integer lim = 10;
		CreateTestData.postInvoices(lim);
		CreateFAIAXML auditFileInstance = new CreateFAIAXML();
		auditFileInstance.selectedYear = String.valueOf(Date.today().year());
		auditFileInstance.companyChosen = 'TestCompany0';
		System.debug([SELECT Id, Name FROM c2g__codaCompany__c]);
		List<c2g__codaPeriod__c> periodList =	[
													SELECT	Id
													FROM	c2g__codaPeriod__c
													WHERE	c2g__YearName__r.Name =: String.valueOf(Date.today().year())		
												];
		for(c2g__codaPeriod__c item : periodList){
			auditFileInstance.selectedPeriodList.add(item.id);
		}
		auditFileInstance.createXMLFile();
		auditFileInstance.oneFile = false;
		auditFileInstance.createXMLFile();
		auditFileInstance.getYearOptions();
		auditFileInstance.getPeriodOptions();
		Test.stopTest();
	}
}