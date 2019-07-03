public without sharing class CreateFAIAXML{

	public  String FAIAXMLFile;
	public  List<FAIAXML_Mapping__mdt> XMLMappingRecordsList;
	public  Map<String[], List<FAIAXML_Mapping__mdt>> mappingRecordsByParentPathMap;
	public  Map<String, FAIAXML_Mapping__mdt> mappingRecordsByDeveloperNameMap;
	public  Map<String, Set<String>> mappingRecordsByObjectNameMap;
	public  Map<String, Map<String, List<FAIAXML_Mapping__mdt>>> mappingRecordsByEntryLevelMap;
	public  Map<String, c2g__codaTaxRate__c> taxRateByLineItemIdMap;
	Map<String, List<c2g__codaTransaction__c>> transactionByTypeMap;
	Map<String, c2g__codaTransaction__c> transactionByAccountIdMap;
	Map<String, CreditDebitBalances> balanceByAccount;

	public List<c2g__codaPeriod__c> periodsList = new List<c2g__codaPeriod__c>();
	public List<Integer> yearsList = new List<Integer>();
	public Map<String, List<c2g__codaPeriod__c>> periodByYearMap;
	public Map<String, Map<String, List<c2g__codaPeriod__c>>> periodByCompanyMap = new Map<String, Map<String, List<c2g__codaPeriod__c>>>();
	public List<String> transactionTypePicklist;

	public  String  queryAllTransactions;
	public  String folderId;
	public  Decimal duration {get; set;}
	public  Boolean oneFile {get; set;}
	public  String companyChosen {get; set;}
	public  List<SelectOption> companyList {get; set;}
	public  String countryChosen {get; set;}
	public  String docLink {get; set;}
	public  String batchId {get; set;}
	public  String queryTr {get; set;}
	public  Boolean showInstructionsOne {get; set;}
	public  Boolean showInstructionsAll {get; set;}
	public  Boolean error {get; set;}
	public  String errorMessage {get; set;}
	public  String appName = 'Luxembourg Audit';
	public	String selectedYear {get; set;}
	public  List<String> selectedPeriodList {get; set;}
	public  List<c2g__codaPeriod__c> periodList {get; set;}
	public  List<String> companyReturnList;

	public  String firstName, lastName, userEmail, fax, mobilePhone;

	/*public PageReference checkLicense () {
		CloudTeamLicence ctl = new CloudTeamLicence();
		Boolean bool = ctl.checkLicence(appName, UserInfo.getOrganizationId());
		if (bool == true) {
			return null;
		} else {
			PageReference pr = new PageReference('/apex/CloudTeamLicenseAccessDenied');
			pr.setRedirect(true);
			return pr;
		}
		return null;
	}*/

	public  String addNode(String tagName, String tagValue){
		return '<' + tagName + '>' + tagValue + '</' + tagName + '>';
	}

	public  String createDoc (String docName, String docBody, String folderId) {
		Document doc = new Document();
		doc.FolderId = folderId;
		doc.Body = Blob.valueOf(docBody);
		doc.Name = docName;
		doc.ContentType = 'text/xml';
		doc.Type = 'xml';
		insert doc;
        return doc.Id;
	}

	public CreateFAIAXML(){
		selectedPeriodList = new List<String>();
		initPeriodsMap();
		countryChosen = '';
		queryAllTransactions = '';
		showInstructionsOne = false;
		showInstructionsAll = false;
		docLink = '';
		batchId = '';
		error = false;
		errorMessage = '';
		duration = 12;
		oneFile = true;
		selectedYear = (new List<String>(periodByCompanyMap.get(companyChosen).keySet())[0]);

		XMLMappingRecordsList = new List<FAIAXML_Mapping__mdt>();
		try{
			User u =	[
							SELECT	FirstName,
									LastName,
									Fax,
									MobilePhone,
									Email
							FROM	User
							WHERE	Id =: UserInfo.getUserId()
						];

			firstName = u.FirstName;
			lastName = u.LastName;
			userEmail = u.Email;
			fax = u.Fax;
			mobilePhone = u.MobilePhone;

			XMLMappingRecordsList =	[
										SELECT		Id,
													MasterLabel,
													Entry_Level__c,
													DeveloperName,
													FAIA_Tag__c,
													Parent_Path__c,
													SFDC_Field_Name__c,
													SObject_Name__c
										FROM		FAIAXML_Mapping__mdt
										ORDER BY	Entry_Level__c
										ASC									
									];
			
			mappingRecordsByDeveloperNameMap = new Map<String, FAIAXML_Mapping__mdt>();
			mappingRecordsByObjectNameMap = new Map<String, Set<String>>();
			for(FAIAXML_Mapping__mdt item : XMLMappingRecordsList){
				mappingRecordsByDeveloperNameMap.put(item.MasterLabel, item);
				if(mappingRecordsByObjectNameMap.get(item.SObject_Name__c) == null){
					if(item.SFDC_Field_Name__c != null){
						mappingRecordsByObjectNameMap.put(item.SObject_Name__c, new Set<String>{item.SFDC_Field_Name__c});
					}
				}else {
					if(item.SFDC_Field_Name__c != null){
						mappingRecordsByObjectNameMap.get(item.SObject_Name__c).add(item.SFDC_Field_Name__c);
					}
				}
			}
		}catch(Exception e){
			System.debug('EXCEPTION WHILE QUERYING CUSTOM METADATA TYPE RECORDS: ' + e.getMessage() + ' ' + e.getStackTraceString());
		}
	}

	public List<SelectOption> getYearOptions(){
		List<SelectOption> options = new List<SelectOption>();
		for(String year : periodByCompanyMap.get(companyChosen).keySet()){
			options.add(new SelectOption(year, year));
		}
		System.debug('options: ' + options);
		return options;
	}

	public List<SelectOption> getPeriodOptions(){
		List<SelectOption> options = new List<SelectOption>();
		System.debug(selectedYear);
		System.debug(companyChosen);
		System.debug(periodByCompanyMap.get(companyChosen));
		System.debug(periodByCompanyMap.get(companyChosen).get(selectedYear));
		if(periodByCompanyMap.get(companyChosen).get(selectedYear) != null){
			for(c2g__codaPeriod__c item : periodByCompanyMap.get(companyChosen).get(selectedYear)){
				options.add(new SelectOption(item.id, item.c2g__StartDate__c.format() + ' - ' + item.c2g__EndDate__c.format()));
				//options.add(new SelectOption(item.id, item.id));
			}
		}
		return options;
	}

	public void initPeriodsMap(){
		Set<String> periodNamesSet = new Set<String>();
		try{
			List<c2g__codaCompany__c> periodCompaniesList = new List<c2g__codaCompany__c>();
			periodCompaniesList =	[
										SELECT	Id,
												Name
										FROM	c2g__codaCompany__c
									];

			periodsList =	[
								SELECT	Id,
										c2g__YearName__r.Name,
										c2g__PeriodNumber__c,
										c2g__OwnerCompany__r.Name,
										c2g__StartDate__c,
										c2g__EndDate__c
								FROM	c2g__codaPeriod__c
								WHERE	c2g__Description__c = 'Trading Period'
							];
			for(c2g__codaCompany__c item : periodCompaniesList){
				periodByCompanyMap.put(item.Name, new Map<String, List<c2g__codaPeriod__c>>());
			}
			//periodByYearMap = new Map<String, List<c2g__codaPeriod__c>>();
			for(c2g__codaPeriod__c item : periodsList){
				if(Integer.valueOf(item.c2g__YearName__r.Name) <= System.now().year()){
					if(periodByCompanyMap.get(item.c2g__OwnerCompany__r.Name) == null){
						periodByCompanyMap.get(item.c2g__OwnerCompany__r.Name).putAll(new Map<String, List<c2g__codaPeriod__c>>());
					}
					if(periodByCompanyMap.get(item.c2g__OwnerCompany__r.Name).get(item.c2g__YearName__r.Name) == null){
						periodByCompanyMap.get(item.c2g__OwnerCompany__r.Name).put(item.c2g__YearName__r.Name, new List<c2g__codaPeriod__c>{item});
					}else{
						periodByCompanyMap.get(item.c2g__OwnerCompany__r.Name).get(item.c2g__YearName__r.Name).add(item);
					}
				}
			}
			for (String companyKey: periodByCompanyMap.keySet()) {
				if (periodByCompanyMap.get(companyKey) == null || periodByCompanyMap.get(companyKey).keySet().size() == 0) {
					periodByCompanyMap.remove(companyKey);
				}
			}
			companyList = new List<SelectOption>();
			for (String companyKey: periodByCompanyMap.keySet()) {
				companyList.add(new SelectOption(companyKey, companyKey));
			}
			companyChosen = new List<String>(periodByCompanyMap.keySet())[0];

		}catch(Exception e){
			System.debug(e.getMessage() + ' ' + e.getStackTraceString());
		}
	}

	public  String createGeneralLedgerEntriesBlock (Boolean oneFile){
		AggregateResult queryGeneralDetails;
		String transactions = '';
		Decimal totalDebit = 0;
		Decimal totalCredit = 0;
		Integer numberOfEntries = 0;
		List<String> transactionLineItemFieldSetList = new List<String>();
		List<String> transactionFieldSetList = new List<String>();
		//System.debug(String.valueOf(mappingRecordsByObjectNameMap.get('c2g__codaTransactionLineItem__c')));
		transactionLineItemFieldSetList.addAll(mappingRecordsByObjectNameMap.get('c2g__codaTransactionLineItem__c'));
		transactionFieldSetList.addAll(mappingRecordsByObjectNameMap.get('c2g__codaTransaction__c'));
		String transactionLineItemfieldSetString = String.join(transactionLineItemFieldSetList, ',');
		String transactionfieldSetString = String.join(transactionFieldSetList, ',');
		System.debug('selectedPeriodList: ' + selectedPeriodList);
		queryAllTransactions =  'SELECT ' + transactionfieldSetString +	', ' +
											'c2g__Journal__c, ' +
											'c2g__Journal__r.Name, ' +
											'c2g__Journal__r.c2g__JournalDescription__c, ' +
											'(' +
												'SELECT ' + transactionLineItemfieldSetString + ', ' +
															'c2g__TaxCode1__c ' +
												'FROM       c2g__TransactionLineItems__r' +
											') ' +
								'FROM       c2g__codaTransaction__c ' +
								'WHERE      c2g__OwnerCompany__r.Name = \'' + companyChosen + '\' AND ' +
											'c2g__Period__c IN: selectedPeriodList AND ' +
											'c2g__Period__r.c2g__YearName__r.Name = \'' + selectedYear +'\'';
		System.debug('queryAllTransactions: ' + queryAllTransactions);
		List<String> taxCodeIdList = new List<String>();
		Map<String, String> LineItemIdBytaxCodeIdMap = new Map<String, String>();
		List<c2g__codaTransaction__c> transactionList = Database.query(queryAllTransactions);
		System.debug('transactionList: ' + transactionList);
		System.debug('transactionList.size: ' + transactionList.size());
		transactionTypePicklist = new List<String>();
		Schema.DescribeFieldResult fieldResult = c2g__codaTransaction__c.c2g__TransactionType__c.getDescribe();
		List<Schema.PicklistEntry> ple = fieldResult.getPicklistValues();
		for(Schema.PicklistEntry pickListVal : ple){
			transactionTypePicklist.add(pickListVal.getLabel());
		}     
		/*
		Map<String, List<c2g__codaTransactionLineItem__c>> transactionsMap = new Map<String, List<c2g__codaTransactionLineItem__c>>();
		for(c2g__codaTransaction__c item : transactionList){
			if (transactionsMap.get(item.c2g__Transaction__c) == null){
				transactionsMap.put(item.c2g__Transaction__c, new List<c2g__codaTransactionLineItem__c>{item});
			} else {
				transactionsMap.get(item.c2g__Transaction__c).add(item);
			}
		}
		*/

		transactionByTypeMap = new Map <String, List<c2g__codaTransaction__c>>();
		transactionByAccountIdMap = new Map<String, c2g__codaTransaction__c>();

		for(c2g__codaTransaction__c item : transactionList){
			if(transactionByTypeMap.get(item.c2g__TransactionType__c) == null){
				transactionByTypeMap.put(item.c2g__TransactionType__c, new List<c2g__codaTransaction__c>{item});
			}else{
				transactionByTypeMap.get(item.c2g__TransactionType__c).add(item);
			}
			for(c2g__codaTransactionLineItem__c lineItem : item.c2g__TransactionLineItems__r){
				taxCodeIdList.add(lineItem.c2g__TaxCode1__c);
				LineItemIdBytaxCodeIdMap.put(lineItem.c2g__TaxCode1__c, lineItem.Id);
			}
		}

		List<c2g__codaTaxRate__c> taxRateList = [	
													SELECT	Id, 
															c2g__Rate__c,
															c2g__TaxCode__c
													FROM	c2g__codaTaxRate__c 
													WHERE	c2g__TaxCode__c IN:	taxCodeIdList
												];

		taxRateByLineItemIdMap = new Map<String, c2g__codaTaxRate__c>();
		for(c2g__codaTaxRate__c item : taxRateList){
			taxRateByLineItemIdMap.put(LineItemIdBytaxCodeIdMap.get(item.c2g__TaxCode__c), item );
		}

		queryGeneralDetails =	[	
									SELECT	SUM(c2g__Debits__c), 
											SUM(c2g__Credits__c),
											count(Id) 
									FROM	c2g__codaTransaction__c 
									WHERE 	c2g__OwnerCompany__r.Name =: companyChosen AND c2g__Period__c IN: selectedPeriodList AND c2g__Period__r.c2g__YearName__r.Name =: selectedYear
								];

		//if(oneFile == true){
			Integer i = 0;
			String journalId = '';
			System.debug('TYPE: ' + transactionByTypeMap);
			for(String item : transactionTypePicklist){
				journalId = 'JNL' + i;
				i++;
				if(transactionByTypeMap.get(item) != null){
					transactions += getJournalItem(transactionByTypeMap.get(item), journalId, 'Invoice');
				}
		}
		/*}else{*/
			queryGeneralDetails =	[	
										SELECT	SUM(c2g__Debits__c), 
												SUM(c2g__Credits__c),
												count(Id) 
										FROM	c2g__codaTransaction__c 
										WHERE 	c2g__OwnerCompany__r.Name =: companyChosen AND
												c2g__Period__c IN: selectedPeriodList AND
												c2g__Period__r.c2g__YearName__r.Name =: selectedYear
									];		
			
		//}
		String generalLedgerEntriesBlock = addNode(mappingRecordsByDeveloperNameMap.get('GeneralLedgerEntries1').FAIA_Tag__c,
					addNode(mappingRecordsByDeveloperNameMap.get('GeneralLedgerEntries2').FAIA_Tag__c, String.valueOf(queryGeneralDetails.get('expr2'))) +
					addNode(mappingRecordsByDeveloperNameMap.get('GeneralLedgerEntries3').FAIA_Tag__c, String.valueOf(queryGeneralDetails.get('expr0')) != null ? String.valueOf(queryGeneralDetails.get('expr0')) : '0') +
					addNode(mappingRecordsByDeveloperNameMap.get('GeneralLedgerEntries4').FAIA_Tag__c, String.valueOf(queryGeneralDetails.get('expr1')) != null ? String.valueOf(queryGeneralDetails.get('expr1')).remove('-') : '0') + transactions);
		return generalLedgerEntriesBlock;
	}

	public String getJournalItem(List<c2g__codaTransaction__c> trList, String journalId, String trType){
		if(oneFile){
			return addNode('Journal', 
						addNode('JournalID', journalId) +	
						addNode('Description', 'DESCRIPTION') +
						addNode('Type', trType) +
						getTransactionItem(trList));
		} else {
			return addNode('Journal', 
						addNode('JournalID', journalId) +	
						addNode('Description', 'DESCRIPTION') +
						addNode('Type', trType));
		}
	}

	public String getTransactionItem(List<c2g__codaTransaction__c> trList){
		String generatedTransaction = '';
		for(c2g__codaTransaction__c item : trList){		 
			generatedTransaction +=	addNode(mappingRecordsByDeveloperNameMap.get('Transaction16').FAIA_Tag__c, 
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction2')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction3')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction4')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction5'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction5').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction5')))))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction6'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction6').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction6')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction7'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction7').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction7')))) : '')+
										addNode(mappingRecordsByDeveloperNameMap.get('Transaction8').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction8'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction8')) : 'DESCRIPTION')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction9'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction9').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction9')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction10'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction10').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction10')))))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction11'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction11').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction11')))))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction12'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction12').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction12')).substring(0, 15))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction13'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction13').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction13')).substring(0, 15))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction14'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction14').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction14')))) : '')+
										getTransactionLineItems(item));
		}
		return generatedTransaction;
	}

	public String getTransactionItemBatch(c2g__codaTransaction__c item){
			return	addNode(mappingRecordsByDeveloperNameMap.get('Transaction16').FAIA_Tag__c, 
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction2')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction3')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction4')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction5'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction5').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction5')))))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction6'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction6').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction6')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction7'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction7').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction7')))) : '')+
					addNode(mappingRecordsByDeveloperNameMap.get('Transaction8').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction8'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction8')) : 'DESCRIPTION')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction9'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction9').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction9')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction10'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction10').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction10')))))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction11'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction11').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction11')))))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction12'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction12').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction12')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction13'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction13').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction13')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction14'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Transaction14').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Transaction14')))) : '')+
					getTransactionLineItems(item));
	}

	public String getTransactionLineItems(c2g__codaTransaction__c tr){
		String transactionLineItems = '';
		for(c2g__codaTransactionLineItem__c item : tr.c2g__TransactionLineItems__r){
			transactionLineItems += addNode(mappingRecordsByDeveloperNameMap.get('Transaction15').FAIA_Tag__c,
			(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Line2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line2')))) : '') +
			(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Line3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line3')))) : '') +
			addNode(mappingRecordsByDeveloperNameMap.get('Line4').FAIA_Tag__c, 
				//(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Analysis1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Analysis1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Analysis1')))) : 'type')+
				addNode(mappingRecordsByDeveloperNameMap.get('Analysis1').FAIA_Tag__c, 'type')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Analysis2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Analysis2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Analysis2')))) : '')+
				addNode(mappingRecordsByDeveloperNameMap.get('Analysis3').FAIA_Tag__c, 
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('AnalysisAmount1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount1')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('AnalysisAmount2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount2')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('AnalysisAmount3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount3')))) : '')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('AnalysisAmount4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('AnalysisAmount4')))) : ''))) +
			(String.isNotBlank(getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('Line5'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Line5').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('Line5')))))) : '') +
			(String.isNotBlank(getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('Line6'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Line6').FAIA_Tag__c, String.valueOf(getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('Line6')))) : '') +
			(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line7'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Line7').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line7')).substring(0, 15))) : '') +
			(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line8'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Line8').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Line8')).substring(0, 15))) : '') +
			addNode(mappingRecordsByDeveloperNameMap.get('Line9').FAIA_Tag__c, String.isNotBlank(getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('Line9'))) ? getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('Line9')) : 'DESCRIPTION') +
			(validateDebitValue(item) ? addNode(mappingRecordsByDeveloperNameMap.get('Line10').FAIA_Tag__c,  
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('DebitAmount1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount1')))) : '')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('DebitAmount2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount2')))) : '')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('DebitAmount3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount3')))) : '')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('DebitAmount4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('DebitAmount4')))) : '')) : '') +
			(!validateDebitValue(item) ? addNode(mappingRecordsByDeveloperNameMap.get('Line11').FAIA_Tag__c, 
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('CreditAmount1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount1')))) : '').remove('-')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('CreditAmount2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount2')))) : '')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('CreditAmount3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount3')))) : '').remove('-')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('CreditAmount4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('CreditAmount4')))) : '')) : '') +
			(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Line12').FAIA_Tag__c, 
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxInformation1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation1')))) : '')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxInformation2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation2')))) : '')+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxInformation3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation3')))) : addNode(mappingRecordsByDeveloperNameMap.get('TaxInformation3').FAIA_Tag__c, '0.00'))+
				(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxInformation4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxInformation4')))) : '')+
				addNode(mappingRecordsByDeveloperNameMap.get('TaxInformation5').FAIA_Tag__c, 
					addNode(mappingRecordsByDeveloperNameMap.get('TaxAmount1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount1')) : '0')+
					addNode(mappingRecordsByDeveloperNameMap.get('TaxAmount2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount2')) : 'EUR')+
					addNode(mappingRecordsByDeveloperNameMap.get('TaxAmount3').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount3'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount3')) : '0')+
					(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxAmount4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxAmount4')))) : ''))+
				(String.isNotBlank(getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('TaxInformation6'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxInformation6').FAIA_Tag__c, String.valueOf(getFieldValue(tr, mappingRecordsByDeveloperNameMap.get('TaxInformation6')))) : '')) : ''));
		} 

		return transactionLineItems;
	}

	public Boolean validateDebitValue(c2g__codaTransactionLineItem__c item){
		if(item.c2g__HomeValue__c > 0){
			return true;
		}else{
			return false;
		}
	}

	public  String createHeaderBlock (){
		periodList =	[
							SELECT	Id,
									c2g__YearName__r.Name,
									c2g__PeriodNumber__c,
									c2g__OwnerCompany__r.Name,
									c2g__StartDate__c,
									c2g__EndDate__c
							FROM	c2g__codaPeriod__c
							WHERE	id IN: selectedPeriodList		
						];
		List<String> fieldSetList = new List<String>();
		fieldSetList.addAll(mappingRecordsByObjectNameMap.get('c2g__codaCompany__c'));
		String fieldSetString = String.join(fieldSetList, ',');
		String query = 'SELECT ' + fieldSetString +	', (SELECT Id, Name FROM c2g__AccountingCurrencies__r WHERE c2g__Home__c = true LIMIT 1) FROM c2g__codaCompany__c WHERE Name =: companyChosen';
		c2g__codaCompany__c company = Database.query(query);
		String headerBlock = addNode(mappingRecordsByDeveloperNameMap.get('Header1').FAIA_Tag__c, 
						addNode(mappingRecordsByDeveloperNameMap.get('Header2').FAIA_Tag__c, 'v.2.01')+
						addNode(mappingRecordsByDeveloperNameMap.get('Header3').FAIA_Tag__c, String.valueOf(countryChosen))+
						addNode(mappingRecordsByDeveloperNameMap.get('Header5').FAIA_Tag__c, String.valueOf(getDateString(System.now().date())))+
						addNode(mappingRecordsByDeveloperNameMap.get('Header6').FAIA_Tag__c, 'Cloud Team')+
						addNode(mappingRecordsByDeveloperNameMap.get('Header7').FAIA_Tag__c, 'Cloud_Team_FAIA_Generator')+
						addNode(mappingRecordsByDeveloperNameMap.get('Header8').FAIA_Tag__c, 'v1.0')+
						addNode(mappingRecordsByDeveloperNameMap.get('Header9').FAIA_Tag__c, 
							(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Company1').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company1')))) : '')+
							(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Company2').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company2')))) : '')+
							addNode(mappingRecordsByDeveloperNameMap.get('Company3').FAIA_Tag__c,
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Address1').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address1')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Address2').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address2')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Address3').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address3')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Address4').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Address4')))) : ''))+
							addNode(mappingRecordsByDeveloperNameMap.get('Company4').FAIA_Tag__c,
								addNode(mappingRecordsByDeveloperNameMap.get('Contact1').FAIA_Tag__c, 
									addNode(mappingRecordsByDeveloperNameMap.get('ContactPerson1').FAIA_Tag__c, String.isNotBlank(firstName) ? firstName : 'FIRST_NAME')+
									addNode(mappingRecordsByDeveloperNameMap.get('ContactPerson2').FAIA_Tag__c, String.isNotBlank(firstName) ? firstName : 'LAST_NAME'))+
								addNode(mappingRecordsByDeveloperNameMap.get('Contact2').FAIA_Tag__c, String.isNotBlank(mobilePhone) ? mobilePhone : '+31 00 000 0000')+
								addNode(mappingRecordsByDeveloperNameMap.get('Contact3').FAIA_Tag__c, String.isNotBlank(fax) ? fax : '+31 00 000 0000')+
								addNode(mappingRecordsByDeveloperNameMap.get('Contact4').FAIA_Tag__c, String.isNotBlank(userEmail) ? userEmail : 'example@email.com'))+
							(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Company5').FAIA_Tag__c,
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration1').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company1')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('TaxRegistration2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration2').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('TaxRegistration2')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration3').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Company1')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('TaxRegistration4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration4').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('TaxRegistration4')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('TaxRegistration5'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration5').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('TaxRegistration5')))) : '')) : '')+
							(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Company6').FAIA_Tag__c,
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('BankAccount1').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount1')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('BankAccount2').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount2')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('BankAccount3').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount3')))) : '')+
								(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('BankAccount4').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('BankAccount4')))) : '')) : ''))+
						addNode(mappingRecordsByDeveloperNameMap.get('Header10').FAIA_Tag__c, ''+company.c2g__AccountingCurrencies__r[0].Name) +
						addNode(mappingRecordsByDeveloperNameMap.get('Header11').FAIA_Tag__c, 
							(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria1').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria1')))) : '')+
							(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria2').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria2')))) : '')+
							addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria3').FAIA_Tag__c, String.valueOf(getDateString(periodList[0].c2g__StartDate__c)))+
							addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria4').FAIA_Tag__c, String.valueOf(getDateString(periodList[periodList.size() - 1].c2g__EndDate__c)))+
							/*addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria5').FAIA_Tag__c, String.valueOf(periodList[0].c2g__PeriodNumber__c))+
							addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria6').FAIA_Tag__c, String.valueOf(periodList[0].c2g__YearName__r.Name))+
							addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria7').FAIA_Tag__c, String.valueOf(periodList[periodList.size() - 1].c2g__PeriodNumber__c))+
							addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria8').FAIA_Tag__c, String.valueOf(periodList[periodList.size() - 1].c2g__YearName__r.Name))+*/
							addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria9').FAIA_Tag__c, String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria9'))) ? getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria9')) : 'XML')+
							addNode(mappingRecordsByDeveloperNameMap.get('SelectionCriteria10').FAIA_Tag__c, String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria10'))) ? getFieldValue(company, mappingRecordsByDeveloperNameMap.get('SelectionCriteria10')) : 'Other'))+
						addNode(mappingRecordsByDeveloperNameMap.get('Header13').FAIA_Tag__c, 'Invoice Accounting')+
						(String.isNotBlank(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Header14'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Header14').FAIA_Tag__c, String.valueOf(getFieldValue(company, mappingRecordsByDeveloperNameMap.get('Header14')))) : ''));
		return headerBlock;
	}

	public  String createMasterFilesBlock(){
		String taxTableEntries = generateTaxTable();
		String masterFilesBlock =	addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles1').FAIA_Tag__c, 
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles2').FAIA_Tag__c, generateMFGeneralLedgerAccounts())+
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles4').FAIA_Tag__c, generateMFCustomers())+
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles5').FAIA_Tag__c, generateMFSuppliers())+
										(String.isNotBlank(taxTableEntries) ? addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles6').FAIA_Tag__c, taxTableEntries) : ''));
			
		/*String masterFilesBlock =	addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles1').FAIA_Tag__c, 
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles2').FAIA_Tag__c, generateMFGeneralLedgerAccounts())+
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles3').FAIA_Tag__c, generateMFTaxonomies())+
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles4').FAIA_Tag__c, generateMFCustomers())+
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles5').FAIA_Tag__c, generateMFSuppliers())+
										addNode(mappingRecordsByDeveloperNameMap.get('MasterFiles10').FAIA_Tag__c, generateMFProducts()));*/
		return masterFilesBlock;
	}

	public  String generateMFGeneralLedgerAccounts(){
		
		balanceByAccount = new Map<String, CreditDebitBalances>();
		List<c2g__codaPeriod__c> currYearPeriods =	[
														SELECT	Id,
																c2g__PeriodNumber__c,
																c2g__StartDate__c,
																c2g__EndDate__c
														FROM	c2g__codaPeriod__c
														WHERE	c2g__YearName__r.Name =: selectedYear
														ORDER BY c2g__StartDate__c ASC
													];

		List<c2g__codaPeriod__c> selectedPeriodRecordsList =	[
																	SELECT	Id,
																			c2g__PeriodNumber__c,
																			c2g__StartDate__c,
																			c2g__EndDate__c
																	FROM	c2g__codaPeriod__c
																	WHERE	Id	IN: selectedPeriodList
																	ORDER BY c2g__StartDate__c ASC
																];

		System.debug('Start Date: ' + selectedPeriodRecordsList[0].c2g__StartDate__c);
		System.debug('End Date: ' + selectedPeriodRecordsList[selectedPeriodRecordsList.size() - 1].c2g__EndDate__c);
		//chnaged to c2g__HomeValue__c because of the issue with the calculations of account opening balance
		List<AggregateResult> openingBalanceResult =	[	
															SELECT	SUM(c2g__HomeValue__c),
																	c2g__GeneralLedgerAccount__c accountId
															FROM	c2g__codaTransactionLineItem__c 
															//WHERE	c2g__Transaction__r.c2g__Period__r.c2g__YearName__r.Name =: selectedYear
															WHERE	c2g__Transaction__r.c2g__Period__r.c2g__StartDate__c <: selectedPeriodRecordsList[0].c2g__StartDate__c 
															//AND		c2g__Transaction__r.c2g__Period__r.c2g__StartDate__c >: currYearPeriods[0].c2g__StartDate__c
															GROUP BY c2g__GeneralLedgerAccount__c
														];
		System.debug('openingBalanceResult: ' + openingBalanceResult);
		//chnaged to c2g__HomeValue__c because of the issue with the calculations of account closing balance
		List<AggregateResult> closingBalanceResult =	[	
															SELECT	SUM(c2g__HomeValue__c),
																	c2g__GeneralLedgerAccount__c accountId
															FROM	c2g__codaTransactionLineItem__c 
															WHERE	c2g__Transaction__r.c2g__Period__r.c2g__EndDate__c <=: currYearPeriods[currYearPeriods.size() - 1].c2g__EndDate__c 
															GROUP BY c2g__GeneralLedgerAccount__c
														];
		System.debug('closingBalanceResult: ' + closingBalanceResult);
		CreditDebitBalances balance;
		Map<Id, Decimal> openingBalanceMap = new Map<Id, Decimal>();
		Map<Id, Decimal> closingBalanceMap = new Map<Id, Decimal>();
		Set<Id> accIdSet = new Set<Id>();
		for(AggregateResult item : openingBalanceResult){
			openingBalanceMap.put((String)item.get('accountId'), (Decimal)item.get('expr0'));
			accIdSet.add((Id)item.get('accountId'));
		}
		for(AggregateResult item : closingBalanceResult){
			closingBalanceMap.put((String)item.get('accountId'), (Decimal)item.get('expr0'));
			accIdSet.add((Id)item.get('accountId'));
		}
		for(Id accId : accIdSet){
			balance = new CreditDebitBalances();
			if (openingBalanceMap.containsKey(accId) && openingBalanceMap.get(accId) != null) {
				balance.openingBalance = openingBalanceMap.get(accId);
			} else {
				balance.openingBalance = 0;
			}
			if (closingBalanceMap.containsKey(accId) && closingBalanceMap.get(accId) != null) {
				balance.closingBalance = closingBalanceMap.get(accId);
			} else {
				balance.closingBalance = 0;
			}
			balanceByAccount.put(accId,balance);
		}
		System.debug('balanceByAccount: ' + balanceByAccount);

		/*for(String key : transactionByAccountIdMap.keySet()){
			CreditDebitBalances balance = new CreditDebitBalances();
			if(transactionByAccountIdMap.get(key) != null){
				balanceByAccount.get(key).closingCreditBalance = balanceByAccount.get(key).openingCreditBalance + transactionByAccountIdMap.get(key).c2g__HomeValueTotal__c;
				balanceByAccount.get(key).closingDebitBalance = balanceByAccount.get(key).openingDebitBalance + transactionByAccountIdMap.get(key).c2g__HomeValueTotal__c;
			}
		}*/

		System.debug('balanceByAccount: ' + balanceByAccount);

		List<String> fieldSetList = new List<String>();
		fieldSetList.addAll(mappingRecordsByObjectNameMap.get('c2g__codaGeneralLedgerAccount__c'));
		String fieldSetString = String.join(fieldSetList, ',');
		String query = 'SELECT ' + fieldSetString +	' FROM c2g__codaGeneralLedgerAccount__c';
		List<c2g__codaGeneralLedgerAccount__c> generalLedgerAccountList = Database.query(query);
		String generalLedgerAccounts = '';
		for(c2g__codaGeneralLedgerAccount__c item : generalLedgerAccountList){
			generalLedgerAccounts += addNode(mappingRecordsByDeveloperNameMap.get('GeneralLedgerAccounts1').FAIA_Tag__c,
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Account1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account1')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Account2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account2'))).replace('<', 'less than')) : '')+ //replace('<', 'less than')) : '') is for replacing special character < for XML format
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Account3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account3')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Account4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account4')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account5'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Account5').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account5')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account6'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Account6').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account6')))) : '')+
										(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account7'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Account7').FAIA_Tag__c, String.valueOf(getDateString(Date.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Account7')))))) : '')+
										(validateOpeningBalanceValues(item))+
										(validateClosingBalanceValues(item)));
		}
		return generalLedgerAccounts;
	}

	public String validateOpeningBalanceValues(c2g__codaGeneralLedgerAccount__c item){
		if(balanceByAccount.containsKey(item.Id)){
			if(balanceByAccount.get(item.Id).openingBalance >= 0){
				return addNode(mappingRecordsByDeveloperNameMap.get('Account8').FAIA_Tag__c, String.valueOf(balanceByAccount.get(item.Id).openingBalance));
			} else {
				return addNode(mappingRecordsByDeveloperNameMap.get('Account9').FAIA_Tag__c, String.valueOf(balanceByAccount.get(item.Id).openingBalance).remove('-'));
			}
		} else {
			return addNode(mappingRecordsByDeveloperNameMap.get('Account8').FAIA_Tag__c, '0');
		}
	}

	public String validateClosingBalanceValues(c2g__codaGeneralLedgerAccount__c item){
		if(balanceByAccount.containsKey(item.Id)){
			if(balanceByAccount.get(item.Id).closingBalance >= 0){
				return addNode(mappingRecordsByDeveloperNameMap.get('Account10').FAIA_Tag__c, String.valueOf(balanceByAccount.get(item.Id).closingBalance));
			} else {
				return addNode(mappingRecordsByDeveloperNameMap.get('Account11').FAIA_Tag__c, String.valueOf(balanceByAccount.get(item.Id).closingBalance).remove('-'));
			}
		} else {
			return addNode(mappingRecordsByDeveloperNameMap.get('Account10').FAIA_Tag__c, '0');
		}
	}

	/*public  String generateMFTaxonomies(){
		List<String> fieldSetList = new List<String>();
		fieldSetList.addAll(mappingRecordsByObjectNameMap.get('c2g__codaTaxCode__c'));
		String fieldSetString = String.join(fieldSetList, ',');
		String query = 'SELECT ' + fieldSetString +	' FROM c2g__codaTaxCode__c';
		List<c2g__codaTaxCode__c> taxCodeList = Database.query(query);
		String taxCodeString = '';
		for(c2g__codaTaxCode__c item : taxCodeList){
			taxCodeString += addNode(mappingRecordsByDeveloperNameMap.get('Taxonomies1').FAIA_Tag__c,
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Taxonomy1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Taxonomy1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Taxonomy1')))) : '')+
								addNode(mappingRecordsByDeveloperNameMap.get('Taxonomy2').FAIA_Tag__c, 
									(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxonomyElement1').FAIA_Tag__c,  String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement1')))) : '')+
									(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxonomyElement2').FAIA_Tag__c,  String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement2')))) : '')+
									(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxonomyElement3').FAIA_Tag__c,  String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement3')))) : '')+
									(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('TaxonomyElement4').FAIA_Tag__c,  String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxonomyElement4')))) : '')));
		}
		return taxCodeString;
	}*/

	public  String generateMFCustomers(){
		List<String> fieldSetList = new List<String>();
		fieldSetList.addAll(mappingRecordsByObjectNameMap.get('Account'));
		String fieldSetString = String.join(fieldSetList, ',');
		String query = 'SELECT c2g__CODAAccountsReceivableControl__r.c2g__ReportingCode__c, ' + fieldSetString + ' FROM Account WHERE c2g__CODAAccountsReceivableControl__c != NULL';
		List<Account> accountList = Database.query(query);
		String accountString = '';
		for(Account item : accountList){
			accountString += addNode(mappingRecordsByDeveloperNameMap.get('Customers1').FAIA_Tag__c,
								addNode(mappingRecordsByDeveloperNameMap.get('Customer1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer1')) : '0000000000')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer2')) : 'EXAMPLE_NAME')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer3').FAIA_Tag__c, 
									/*addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress1')) : 'NR_EXAMPLE_STREET')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress2')) : 'NR_NUMBER')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress3').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress3'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress3')) : 'NR_ADDITIONAL_DETAILS')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress4').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress4'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress4')) : 'NR_BUILDING')+
									*/addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress5').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress5'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress5')) : 'REQ_CITY')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress6').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress6'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress6')) : '00000'))+
									/*addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress7').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress7'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress7')) : 'NR_REGION')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress8').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress8'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress8')) : 'EX')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress9').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress9'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress9')) : 'StreetAddress PostalAddress, BillingAddress'))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer4').FAIA_Tag__c, 
									addNode(mappingRecordsByDeveloperNameMap.get('Contact1').FAIA_Tag__c, 
										addNode(mappingRecordsByDeveloperNameMap.get('ContactPerson1').FAIA_Tag__c, String.isNotBlank(firstName) ? firstName : 'FIRST_NAME')+
										addNode(mappingRecordsByDeveloperNameMap.get('ContactPerson2').FAIA_Tag__c, String.isNotBlank(lastName) ? lastName : 'LAST_NAME'))+
									addNode(mappingRecordsByDeveloperNameMap.get('Contact2').FAIA_Tag__c,String.isNotBlank(mobilePhone) ? mobilePhone : '+31 00 000 0000')+
									addNode(mappingRecordsByDeveloperNameMap.get('Contact3').FAIA_Tag__c, String.isNotBlank(fax) ? fax : 'EXAMPLE_FAX')+
									addNode(mappingRecordsByDeveloperNameMap.get('Contact4').FAIA_Tag__c, String.isNotBlank(userEmail) ? userEmail : 'example@email.com'))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer5').FAIA_Tag__c, 
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration1')) : '0000000000')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration2')) : 'TAX_TYPE')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration3').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration3'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration3')) : 'TAX_NUMBER')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration4').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration4'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration4')) : 'TAX_AUTHORITY')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration5').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration5'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration5')) : '2018-01-01'))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer6').FAIA_Tag__c, 
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount1')) : 'NL00AAAA0123456789'))+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount2')) : '0123456789')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount3').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount3'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount3')) : 'BANK_ACC_NAME')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount4').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount4'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount4')) : 'EX_SORT_CODE'))+*/
								addNode(mappingRecordsByDeveloperNameMap.get('Customer7').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer7'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer7')) : String.valueOf(item.id).substring(0, 15))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer8').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : 'EX_TYPE')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer9').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer9'))) ? item.c2g__CODAAccountsReceivableControl__r.c2g__ReportingCode__c : String.valueOf(item.id)));
								/*addNode(mappingRecordsByDeveloperNameMap.get('Customer10').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer10'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '1')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer11').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer11'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '-1')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer12').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer12'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '2')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer13').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer13'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '-2'));*/
		}
		return accountString;
	}

	public  String generateMFSuppliers(){
		List<String> fieldSetList = new List<String>();
		fieldSetList.addAll(mappingRecordsByObjectNameMap.get('Account'));
		String fieldSetString = String.join(fieldSetList, ',');
		String query = 'SELECT c2g__CODAAccountsPayableControl__r.c2g__ReportingCode__c, ' + fieldSetString +	' FROM Account WHERE c2g__CODAAccountsPayableControl__c != NULL';
		List<Account> accountList = Database.query(query);
		String accountString = '';
		for(Account item : accountList){
			accountString += addNode(mappingRecordsByDeveloperNameMap.get('Suppliers1').FAIA_Tag__c,
								addNode(mappingRecordsByDeveloperNameMap.get('Customer1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer1')) : '0000000000')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer2')) : 'EXAMPLE_NAME')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer3').FAIA_Tag__c, 
									/*addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress1')) : 'NR_EXAMPLE_STREET')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress2')) : 'NR_NUMBER')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress3').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress3'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress3')) : 'NR_ADDITIONAL_DETAILS')+
									*/addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress4').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress4'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress4')) : 'NR_BUILDING')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress5').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress5'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress5')) : 'REQ_CITY')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress6').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress6'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress6')) : '00000'))+
									/*addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress7').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress7'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress7')) : 'NR_REGION')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress8').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress8'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress8')) : 'EX')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerAddress9').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress9'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerAddress9')) : 'StreetAddress PostalAddress, BillingAddress'))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer4').FAIA_Tag__c, 
									addNode(mappingRecordsByDeveloperNameMap.get('Contact1').FAIA_Tag__c, 
										addNode(mappingRecordsByDeveloperNameMap.get('ContactPerson1').FAIA_Tag__c, String.isNotBlank(firstName) ? firstName : 'FIRST_NAME')+
										addNode(mappingRecordsByDeveloperNameMap.get('ContactPerson2').FAIA_Tag__c, String.isNotBlank(lastName) ? lastName : 'LAST_NAME'))+
									addNode(mappingRecordsByDeveloperNameMap.get('Contact2').FAIA_Tag__c,String.isNotBlank(mobilePhone) ? mobilePhone : '+31 00 000 0000')+
									addNode(mappingRecordsByDeveloperNameMap.get('Contact3').FAIA_Tag__c, String.isNotBlank(fax) ? fax : 'EXAMPLE_FAX')+
									addNode(mappingRecordsByDeveloperNameMap.get('Contact4').FAIA_Tag__c, String.isNotBlank(userEmail) ? userEmail : 'example@email.com'))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer5').FAIA_Tag__c, 
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration1')) : '0000000000')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration2')) : 'TAX_TYPE')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration3').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration3'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration3')) : 'TAX_NUMBER')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration4').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration4'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration4')) : 'TAX_AUTHORITY')+
									addNode(mappingRecordsByDeveloperNameMap.get('TaxRegistration5').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration5'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('TaxRegistration5')) : '2018-01-01'))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer6').FAIA_Tag__c, 
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount1').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount1'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount1')) : 'NL00AAAA0123456789'))+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount2').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount2'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount2')) : '0123456789')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount3').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount3'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount3')) : 'BANK_ACC_NAME')+
									addNode(mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount4').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount4'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('MFCustomerBankAccount4')) : 'EX_SORT_CODE'))+*/
								addNode(mappingRecordsByDeveloperNameMap.get('Supplier7').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Supplier7'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Supplier7')) : String.valueOf(item.id).substring(0, 15))+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer8').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : 'EX_TYPE')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer9').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer9'))) ? item.c2g__CODAAccountsPayableControl__r.c2g__ReportingCode__c : String.valueOf(item.id)));
								/*addNode(mappingRecordsByDeveloperNameMap.get('Customer10').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer10'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '1')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer11').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer11'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '-1')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer12').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer12'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '2')+
								addNode(mappingRecordsByDeveloperNameMap.get('Customer13').FAIA_Tag__c, String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer13'))) ? getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Customer8')) : '-2'));*/
		}
		return accountString;
	}

	public String generateTaxTable(){
		List<c2g__codaTaxCode__c> taxCodesList = new List<c2g__codaTaxCode__c>();
		Try{
			taxCodesList =	[
								SELECT	Name
								FROM	c2g__codaTaxCode__c
							];
		}catch(Exception e){
			return '';
		}

		String taxEntries = '';
		for(c2g__codaTaxCode__c item : taxCodesList){
			taxEntries += addNode(mappingRecordsByDeveloperNameMap.get('TaxTable1').FAIA_Tag__c, 
							addNode(mappingRecordsByDeveloperNameMap.get('TaxTableEntry1').FAIA_Tag__c, 'TVA')+
							addNode(mappingRecordsByDeveloperNameMap.get('TaxTableEntry2').FAIA_Tag__c, 'Taxe sur la valeur ajoutée')+
							addNode(mappingRecordsByDeveloperNameMap.get('TaxTableEntry3').FAIA_Tag__c, 
								addNode(mappingRecordsByDeveloperNameMap.get('TaxCodeDetails1').FAIA_Tag__c, item.Name)+
								addNode(mappingRecordsByDeveloperNameMap.get('TaxTableEntry2').FAIA_Tag__c, 'Taxe sur la valeur ajoutée')+
								addNode(mappingRecordsByDeveloperNameMap.get('TaxCodeDetails2').FAIA_Tag__c, countryChosen)));
		}
		return taxEntries;
	}

	/*public  String generateMFProducts(){
	List<String> fieldSetList = new List<String>();
		fieldSetList.addAll(mappingRecordsByObjectNameMap.get('Product2'));
		String fieldSetString = String.join(fieldSetList, ',');
		String query = 'SELECT ' + fieldSetString +	' FROM Product2';
		List<Product2> productList = Database.query(query);
		String productString = '';
		for(Product2 item : productList){
			productString += addNode(mappingRecordsByDeveloperNameMap.get('Products1').FAIA_Tag__c, 
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product1')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product2')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product3'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product3').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product3')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product4'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product4').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product4')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product5'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product5').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product5')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product6'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product6').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product6')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product7'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product7').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product7')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product8'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product8').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product8')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product9'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product9').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product9')))) : '')+
								(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product10'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Product10').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Product10')))) : '')+
								addNode(mappingRecordsByDeveloperNameMap.get('Product11').FAIA_Tag__c, 
									(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Tax1'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Tax1').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Tax1')))) : '')+
									(String.isNotBlank(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Tax2'))) ? addNode(mappingRecordsByDeveloperNameMap.get('Tax2').FAIA_Tag__c, String.valueOf(getFieldValue(item, mappingRecordsByDeveloperNameMap.get('Tax2')))) : '')));
		}
		return productString;
	}*/

	public  String getDateString (Date d) {
		return Datetime.newInstance(d.year(), d.month(), d.day()).format('yyyy-MM-dd');
	}

	public  void createXMLFile (){
		String folderId =	[
									SELECT	Id
									FROM	Folder
									WHERE	Name = 'FinancialForceDocuments'
									LIMIT	1
							].Id;
		String xmlVersion = '<?xml version="1.0" encoding="UTF-8"?>'; 
		String auditString = '<AuditFile xmlns="urn:OECD:StandardAuditFile-Taxation/2.00" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:OECD:StandardAuditFile-Taxation/2.00 FAIA_v_2.01_full.xsd ">';
		companyReturnList = new List<String>();
		String mainBlock = createHeaderBlock() + createMasterFilesBlock() + createGeneralLedgerEntriesBlock(oneFile);
		
		FAIAXMLFile = xmlVersion + auditString + mainBlock + '</AuditFile>';
		String docId = createDoc('FAIA XML: "' + companyChosen + '" - (' + getDateString(periodList[0].c2g__StartDate__c) + ' - ' + getDateString(periodList[periodList.size() - 1].c2g__EndDate__c) + ') ' + String.valueOf(Date.today()), FAIAXMLFile, folderId);
		
		if (!oneFile) {
			batchId = Database.executeBatch(new FAIA_AuditTransactionBatch(this, queryAllTransactions, docId), 2000);
			
			queryTr = 'SELECT Audit_File_Transaction_Text__c, Transaction__r.c2g__TransactionType__c FROM TransactionAuditFile__c WHERE DocumentId__c = \'' + docId + '\' ORDER BY Transaction__r.c2g__TransactionType__c';	
		}
		docLink = '/' + docId;
		if (mainBlock != null && mainBlock.length() > 0) {
			if (oneFile == true) {
				showInstructionsOne = true;
				showInstructionsAll = false;
			} else {
				showInstructionsOne = false;
				showInstructionsAll = true;
			}
		}
	}

	private  String getFieldValue (sObject tr, FAIAXML_Mapping__mdt auditField) {
        //fields with dot +
        //get field type +
        //format date +
        //debit credit
        String fieldValue = '';
        if (auditField.SFDC_Field_Name__c != null && String.isNotBlank(auditField.SFDC_Field_Name__c)) {
            if (auditField.SFDC_Field_Name__c.contains('.')) {
                String[] strList = auditField.SFDC_Field_Name__c.split('\\.');
                SObject obj = tr.getSObject(strList[0]);
                if (obj != null) {
                    fieldValue = String.valueOf(obj.get(strList[1]));
                } else {
                    fieldValue = '';
                }
            } else {
				if(auditField.FAIA_Tag__c == 'TaxPercentage'){
					if(taxRateByLineItemIdMap.get(tr.Id) != null){
						fieldValue = String.valueOf(taxRateByLineItemIdMap.get(tr.Id).c2g__Rate__c);
					}
				}else{
					fieldValue = String.valueOf(tr.get(auditField.SFDC_Field_Name__c));
				}
            }
            /*if (String.isNotBlank(fieldValue)) {
                try{            
                    Date convertedD = Date.valueOf(String.valueOf(fieldValue));
                    fieldValue = String.valueOf(Datetime.newInstance(convertedD, timeD).format('yyyyMMdd'));
                } catch (TypeException e){
                    //do nothing
                }
            }*/
            if (auditField.FAIA_Tag__c == 'Debit' || auditField.FAIA_Tag__c == 'Credit') {
                if (auditField.FAIA_Tag__c == 'Debit') {
                    if (Decimal.valueOf(fieldValue) < 0) {
                        fieldValue = '0.00';
                    } else if (Decimal.valueOf(fieldValue) == 0) {
						fieldValue = '0.0001';
					}
                } else if (auditField.FAIA_Tag__c == 'Credit') {
                    if (Decimal.valueOf(fieldValue) > 0) {
                        fieldValue = '0.00';
                    } else if (Decimal.valueOf(fieldValue) < 0) {
                        fieldValue = fieldValue.remove('-');
                    } else if (Decimal.valueOf(fieldValue) == 0) {
						fieldValue = '0.00';
					}
                }
            }
			if(auditField.FAIA_Tag__c == 'TaxPercentage'){
				if(taxRateByLineItemIdMap.get(tr.Id) != null){
					fieldValue = String.valueOf(taxRateByLineItemIdMap.get(tr.Id).c2g__Rate__c);
				}
			}
            if (fieldValue == null) {
                fieldValue = '';
            }
        }
        
		fieldValue = fieldValue.replaceAll('\\n\\r', '; ');
        fieldValue = fieldValue.replaceAll('\\n', '; ');
        fieldValue = fieldValue.replaceAll('\\r', '; ');
		if(fieldValue.contains('&')){
			fieldValue = fieldValue.replaceAll('&', '&amp;');
		}
        return fieldValue;
    }

	/*private String formatDate(String dt){
		Date formattedDate = Date.parse(dt);
		return formattedDate.year() + '-' + formattedDate.month() + '-' + formattedDate.day();
	}*/

	public class CreditDebitBalances{
		public Decimal openingBalance;
		public Decimal closingBalance;

		public CreditDebitBalances(){
			this.openingBalance = 0;
			this.closingBalance = 0;
		}
	}
}