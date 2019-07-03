@isTest
public class CreateFrenchAuditTest  {
	@TestSetup
	private static void setupData () {
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
	private static void test1 () {
		Test.startTest();
		Integer lim = 10;
		CreateTestData.postInvoices(lim);
		Test.stopTest();
		CreateFrenchAudit cfa = new CreateFrenchAudit();
		cfa.startDate = Date.today();
		cfa.duration = 1;
		cfa.companyChosen = 'TestCompany0';
		cfa.createAuditFile();
		System.assert(cfa.docURL.contains(cfa.docId));
		List<Document> docList = [
									SELECT	Id, 
											Body 
									FROM	Document 
									WHERE	Id = :cfa.docId
								 ];
		System.assertEquals(1, docList.size());
		List<c2g__codaTransactionLineItem__c> trLineList = [
																SELECT	Id, 
																		c2g__Transaction__r.Name 
																FROM	c2g__codaTransactionLineItem__c
														   ];
		/*System.assertEquals(2, trLineList.size());
		for (c2g__codaTransactionLineItem__c trLine: trLineList) {
			System.assert(docList[0].Body.toString().contains(trLine.c2g__Transaction__r.Name));
		}*/
		cfa.getInteger();
	}
}