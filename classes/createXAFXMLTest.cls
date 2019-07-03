@isTest
private class createXAFXMLTest  {
	@TestSetup
	private static void setupData () {
		List<XAFXML_Mapping__c> mappingList = new List<XAFXML_Mapping__c>();
		List<String> mappingFieldsList;
		XAFXML_Mapping__c mapping;
		List<String> mappingStringList = new List<String>{
															'customersSuppliers\\\\Account\\Account1',
															'streetname\\BillingStreet\\Account\\Account10',
															'city\\BillingCity\\Account\\Account11',
															'postalCode\\BillingPostalCode\\Account\\Account12',
															'country\\BillingCountry\\Account\\Account13',
															'postalAddress\\\\Account\\Account14',
															'streetname\\ShippingStreet\\Account\\Account15',
															'city\\ShippingCity\\Account\\Account16',
															'postalCode\\ShippingPostalCode\\Account\\Account17',
															'country\\ShippingCountry\\Account\\Account18',
															'bankAccount\\\\Account\\Account19',
															'customerSupplier\\\\Account\\Account2',
															'bankAccNr\\\\Account\\Account20',
															'bankIdCd\\c2g__CODABankIBANNumber__c\\Account\\Account21',
															'custSupID\\id\\Account\\Account3',
															'custSupName\\name\\Account\\Account4',
															'contact\\c2g__CODAFinanceContact__c\\Account\\Account5',
															'telephone\\Phone\\Account\\Account6',
															'taxRegistrationCountry\\c2g__CODAECCountryCode__c\\Account\\Account7',
															'taxRegIdent\\\\Account\\Account8',
															'streetAddress\\\\Account\\Account9',
															'company\\\\company\\Company1',
															'companyIdent\\\\Company\\Company2',
															'companyName\\Arctix\\Company\\Company3',
															'taxRegistrationCountry\\NL\\Company\\Company4',
															'taxRegIdent\\\\Company\\Company5',
															'streetAddress\\\\c2g__codaCompany__c\\c2g__codaCompany__c1',
															'streetname\\c2g__Street__c\\c2g__codaCompany__c\\c2g__codaCompany__c2',
															'city\\c2g__City__c\\c2g__codaCompany__c\\c2g__codaCompany__c3',
															'postalCode\\c2g__ZipPostCode__c\\c2g__codaCompany__c\\c2g__codaCompany__c4',
															'country\\c2g__Country__c\\c2g__codaCompany__c\\c2g__codaCompany__c5',
															'generalLedger\\\\c2g__codaGeneralLedgerAccount__c\\c2g__codaGeneralLedgerAccount__c1',
															'ledgerAccount\\\\c2g__codaGeneralLedgerAccount__c\\c2g__codaGeneralLedgerAccount__c2',
															'accID\\c2g__ReportingCode__c\\c2g__codaGeneralLedgerAccount__c\\c2g__codaGeneralLedgerAccount__c3',
															'accDesc\\Name\\c2g__codaGeneralLedgerAccount__c\\c2g__codaGeneralLedgerAccount__c4',
															'accTp\\c2g__Type__c\\c2g__codaGeneralLedgerAccount__c\\c2g__codaGeneralLedgerAccount__c5',
															'periods\\\\c2g__codaPeriod__c\\c2g__codaPeriod__c1',
															'period\\\\c2g__codaPeriod__c\\c2g__codaPeriod__c2',
															'periodNumber\\c2g__PeriodNumber__c\\c2g__codaPeriod__c\\c2g__codaPeriod__c3',
															'periodDesc\\c2g__Description__c\\c2g__codaPeriod__c\\c2g__codaPeriod__c4',
															'startDatePeriod\\c2g__StartDate__c\\c2g__codaPeriod__c\\c2g__codaPeriod__c5',
															'startTimePeriod\\\\c2g__codaPeriod__c\\c2g__codaPeriod__c6',
															'endDatePeriod\\c2g__EndDate__c\\c2g__codaPeriod__c\\c2g__codaPeriod__c7',
															'endTimePeriod\\\\c2g__codaPeriod__c\\c2g__codaPeriod__c8',
															'vatCodes\\\\c2g__codaTaxCode__c\\c2g__codaTaxCode__c1',
															'vatCode\\\\c2g__codaTaxCode__c\\c2g__codaTaxCode__c2',
															'vatID\\id\\c2g__codaTaxCode__c\\c2g__codaTaxCode__c3',
															'vatDesc\\Name\\c2g__codaTaxCode__c\\c2g__codaTaxCode__c4',
															'vatToPayAccID\\c2g__GeneralLedgerAccount__c\\c2g__codaTaxCode__c\\c2g__codaTaxCode__c5',
															'vatToClaimAccID\\c2g__GeneralLedgerAccount__c\\c2g__codaTaxCode__c\\c2g__codaTaxCode__c6',
															'transactions\\\\c2g__codaTransaction__c\\c2g__codaTransaction__c1',
															'nr\\Name\\c2g__codaTransaction__c\\c2g__codaTransaction__c10',
															'desc\\Name\\c2g__codaTransaction__c\\c2g__codaTransaction__c11',
															'periodNumber\\c2g__Period__r.c2g__PeriodNumber__c\\c2g__codaTransaction__c\\c2g__codaTransaction__c12',
															'trDt\\c2g__TransactionDate__c\\c2g__codaTransaction__c\\c2g__codaTransaction__c13',
															'linesCount\\\\c2g__codaTransaction__c\\c2g__codaTransaction__c2',
															'totalDebit\\\\c2g__codaTransaction__c\\c2g__codaTransaction__c3',
															'totalCredit\\\\c2g__codaTransaction__c\\c2g__codaTransaction__c4',
															'journal\\\\c2g__codaTransaction__c\\c2g__codaTransaction__c5',
															'jrnID\\\\c2g__codaTransaction__c\\c2g__codaTransaction__c6',
															'desc\\\\c2g__codaTransaction__c\\c2g__codaTransaction__c7',
															'jrnTp\\c2g__TransactionType__c\\c2g__codaTransaction__c\\c2g__codaTransaction__c8',
															'transaction\\Id\\c2g__codaTransaction__c\\c2g__codaTransaction__c9',
															'trLine\\\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r1',
															'nr\\Name\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r2',
															'accID\\c2g__GeneralLedgerAccount__r.c2g__ReportingCode__c\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r3',
															'docRef\\c2g__Transaction__r.c2g__DocumentNumber__c\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r4',
															'effDate\\c2g__Transaction__r.c2g__TransactionDate__c\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r5',
															'desc\\c2g__Transaction__r.c2g__DocumentDescription__c\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r6',
															'amnt\\c2g__HomeTaxTotal__c\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r7',
															'amntTp\\\\c2g__TransactionLineItems__r\\c2g__TransactionLineItems__r8',
															'header\\\\header\\Header1',
															'fiscalYear\\\\Header\\Header2',
															'startDate\\\\Header\\Header3',
															'endDate\\\\Header\\Header4',
															'curCode\\EUR\\Header\\Header5',
															'dateCreated\\\\Header\\Header6',
															'softwareDesc\\Test\\Header\\Header7',
															'softwareVersion\\Test\\Header\\Header8'
														};
		for (String mapp : mappingStringList) {
			mappingFieldsList = mapp.split('\\\\');
			mapping = new XAFXML_Mapping__c(
					name = mappingFieldsList[3],
					Object_Name__c = mappingFieldsList[2],
					SFDC_Field__c = mappingFieldsList[1],
					XAF_Tag__c = mappingFieldsList[0]
			);
			mappingList.add(mapping);
		}
		insert mappingList;

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
	@IsTest
	private static void addNodeTest () {
		String tag = createXAFXML.addNode('Tag', 'value');
		System.assertEquals(tag, '<Tag>value</Tag>');
	}
	@IsTest
	private static void nullTest () {
	}
	@IsTest
	private static void getTagValueTest () {
		Account acc = new Account(Name = 'TestAccount');
		insert acc;
		Contact cont = new Contact(
				LastName = 'TestContact',
				AccountId = acc.Id
		);
		insert cont;
		Contact contAfter = [
				SELECT  Account.Name
				FROM    Contact
				WHERE   Id =: cont.Id
		];
		String accName = createXAFXML.getTagValue('Name', acc);
		System.assertEquals(accName, acc.Name);
		accName = createXAFXML.getTagValue('', acc);
		System.assertEquals(accName, '');
		accName = createXAFXML.getTagValue('Account.Name', contAfter);
		System.assertEquals(accName, acc.Name);
	}
	@IsTest
	private static void createCompanyInOnefileTest () {
		Test.startTest();
		Integer lim = 10;
		CreateTestData.postInvoices(lim);
		Map<String, XAFXML_Mapping__c> allXAFCustSetMap = XAFXML_Mapping__c.getAll();
		createXAFXML createXAF = new createXAFXML();
		createXAF.countryChosen = 'NL';
		createXAF.companyChosen = 'TestCompany0';
		createXAF.startDate = Date.today().addYears(-1);
		createXAF.duration = 24;
		createXAF.createAuditFile();
		
		System.assert(createXAF.docLink != '');
		System.assert(createXAF.batchId == '');
		System.assert(createXAF.queryTr != '');
		System.assert(!createXAF.showInstructionsAll);
		System.assert(createXAF.showInstructionsOne);

		System.debug('c2g__codaTransaction__c: ' + [SELECT COUNT() FROM c2g__codaTransaction__c]);

		Document xafDoc = [
								SELECT	Id,
										Name,
										Body,
										ContentType,
										Type
								FROM	Document
								LIMIT	1
						 ];
		String bodyDoc = xafDoc.Body.toString();
		System.assert(xafDoc.Name.contains('TestCompany0'));
		System.assert(xafDoc.Name.startsWith('Company'));
		System.assertEquals(xafDoc.Type, 'xaf');
		System.assertEquals(xafDoc.ContentType, 'text/xml');
		System.debug('bodyDoc: ' + bodyDoc);

		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header1').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header2').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header3').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header4').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header5').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header6').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header7').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Header8').XAF_Tag__c));

		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Company1').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Company2').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Company3').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Company4').XAF_Tag__c));
		System.assert(bodyDoc.contains(allXAFCustSetMap.get('Company5').XAF_Tag__c));
		
		Test.stopTest();
	}
	@IsTest
	private static void createCompanyNotInOnefileTest () {
		Test.startTest();
		Integer lim = 10;
		CreateTestData.postInvoices(lim);
		Map<String, XAFXML_Mapping__c> allXAFCustSetMap = XAFXML_Mapping__c.getAll();
		createXAFXML createXAF = new createXAFXML();
		createXAF.countryChosen = 'NL';
		createXAF.companyChosen = 'TestCompany0';
		createXAF.oneFile = false;
		createXAF.startDate = Date.today().addYears(-1);
		createXAF.duration = 24;
		createXAF.createAuditFile();
		
		System.debug('c2g__codaTransaction__c: ' + [SELECT COUNT() FROM c2g__codaTransaction__c]);

		System.assert(createXAF.docLink != '');
		System.assert(createXAF.batchId != '');
		System.assert(createXAF.queryTr != '');
		System.assert(createXAF.showInstructionsAll);
		System.assert(!createXAF.showInstructionsOne);

		Test.stopTest();
		System.assert([SELECT COUNT() FROM TransactionAuditFile__c] > 0);
	}
	@IsTest
	private static void debitOrCreditTest () {
		String debitOrCredit;
		createXAFXML createXAF = new createXAFXML();
		debitOrCredit = createXAF.debitOrCredit('10');
		System.assertEquals('D', debitOrCredit);
		debitOrCredit = createXAF.debitOrCredit('0');
		System.assertEquals('D', debitOrCredit);
		debitOrCredit = createXAF.debitOrCredit('-10');
		System.assertEquals('C', debitOrCredit);
	}
}