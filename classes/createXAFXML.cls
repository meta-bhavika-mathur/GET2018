/**
 * Created by xandervanluijk on 23/06/17.
 */

public with sharing class createXAFXML {
	private Map<String, List<XAFXML_Mapping__c>> mappingMap;
	private Map<String, XAFXML_Mapping__c> allXAFCustSetMap;
	private String folderId;
	private static final String XML_VERSION = '<?xml version="1.0" encoding="UTF-8"?>';
    private String transactionsSOQL;

	public Date startDate {get; set;}
	public Decimal duration {get; set;}
	public Boolean oneFile {get; set;}
	public String companyChosen {get; set;}
	public List<SelectOption> companyList {get; set;}
	public String countryChosen {get; set;}
	public String docLink {get; set;}
	public String batchId {get; set;}
	public String queryTr {get; set;}
	public Boolean showInstructionsOne {get; set;}
	public Boolean showInstructionsAll {get; set;}
	public Boolean error {get; set;}
	public String errorMessage {get; set;}
	@TestVisible
	private String appName = 'Dutch Audit';

	public /*PageReference*/ void checkLicense () {
		/*CloudTeamLicence ctl = new CloudTeamLicence();
		Boolean bool = ctl.checkLicence(appName, UserInfo.getOrganizationId());
		if (bool == true) {
			return null;
		} else {
			PageReference pr = new PageReference('/apex/CloudTeamLicenseAccessDenied');
			pr.setRedirect(true);
			return pr;
		}*/
	}
	public createXAFXML () {
		List<c2g__codaCompany__c> cList = [
												SELECT	Name 
												FROM	c2g__codaCompany__c
												ORDER BY Name
										  ];
		companyList = new List<SelectOption>();
		for (c2g__codaCompany__c c: cList) {
			companyList.add(new SelectOption(c.Name,c.Name));
		}
		countryChosen = XAFXML_Mapping__c.getValues('Company4').SFDC_Field__c;
		showInstructionsOne = false;
		showInstructionsAll = false;
		docLink = '';
		batchId = '';
		error = false;
		errorMessage = '';
		startDate = Date.today();
		duration = 12;
		oneFile = true;
	}
	public void createAuditFile () {
		error = false;
		errorMessage = '';
		docLink = '';
		batchId = '';
		queryTr = '';
		showInstructionsOne = false;
		showInstructionsAll = false;
		XAFXML_Mapping__c country = XAFXML_Mapping__c.getValues('Company4');
		country.SFDC_Field__c = countryChosen;
		update country;
		XAFXML_Mapping__c company = XAFXML_Mapping__c.getValues('Company3');
		company.SFDC_Field__c = companyChosen;
		update company;
		List<String> companyRetList;
		try{        
			companyRetList = createCompany(startDate, startDate.addMonths(Integer.valueOf(duration)));
		}
		catch (Exception e){
			error = true;
			errorMessage = 'Couldn\'t create the file. Please reload the page and try again, or contact your system administrator.\n' + e.getMessage();
			//ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR,'why not working here?\n'+e)); 
		}
		if (companyRetList != null && companyRetList.size() > 0) {
			if (oneFile == true) {
				showInstructionsOne = true;
			} else {
				showInstructionsAll = true;
			}
			docLink = companyRetList[0];
			batchId = companyRetList[1];
			queryTr = companyRetList[2];
		} else {
			error = true;
			errorMessage = 'Couldn\'t create the file. Please reload the page and try again, or contact your system administrator.';
		}
		
	}
/////////////////////////////////////////////// Helpel methods ////////////////////////////////////////////////////////////////
	@TestVisible
	private String getTransactions(Boolean oneFile, Date startDate, Date endDate) {
		if (oneFile == false) {
			return getJournaltransaction(allXAFCustSetMap.get('Company3').SFDC_Field__c, startDate, endDate);
		} else {
			return getJournaltransactionDetails(allXAFCustSetMap.get('Company3').SFDC_Field__c, startDate, endDate);
		}
	}
	@TestVisible
	private static String getDateString (Date d) {
		return Datetime.newInstance(d.year(), d.month(), d.day()).format('yyyy-MM-dd');
	}
	@TestVisible
	private static String getTagValue (String sfdcField, SObject obj) {
		String value = '';
		List<String> strList;
		if (sfdcField != null && sfdcField.trim() != ''){// && obj.getPopulatedFieldsAsMap().containsKey(sfdcField)) {
			for (String field: sfdcField.split(',')) {
				strList = field.split('\\.');
				if (strList.size() > 1) {
					if (String.isNotBlank(String.valueOf(obj.getSObject(strList[0]).get(strList[1])))) {
						value = String.valueOf(obj.getSObject(strList[0]).get(strList[1]));
						break;
					}
				} else if (String.isNotBlank(String.valueOf(obj.get(field)))) {
					value = String.valueOf(obj.get(field));
					break;
				}
			}
		}
		return value;
	}
	@TestVisible
	private static String createDoc (String docName, String docBody, String folderId) {
		Document doc = new Document();
		doc.FolderId = folderId;
		doc.Body = Blob.valueOf(docBody);
		doc.Name = docName;
		doc.ContentType = 'text/xml';
		doc.Type = 'xaf';
		insert doc;
        return doc.Id;
	}
	@TestVisible
	private String createHeader(Date startDate, Date endDate){
		String xafFile =	addNode(allXAFCustSetMap.get('Header1').XAF_Tag__c,
				addNode(allXAFCustSetMap.get('Header2').XAF_Tag__c, String.valueOf(startDate.year())) +
						addNode(allXAFCustSetMap.get('Header3').XAF_Tag__c, getDateString(startDate)) +
						addNode(allXAFCustSetMap.get('Header4').XAF_Tag__c, getDateString(endDate)) +
						addNode(allXAFCustSetMap.get('Header5').XAF_Tag__c, allXAFCustSetMap.get('Header5').SFDC_Field__c) +
						addNode(allXAFCustSetMap.get('Header6').XAF_Tag__c, String.valueOf(Date.today())) +
						addNode(allXAFCustSetMap.get('Header7').XAF_Tag__c, allXAFCustSetMap.get('Header7').SFDC_Field__c) +
						addNode(allXAFCustSetMap.get('Header8').XAF_Tag__c, allXAFCustSetMap.get('Header8').SFDC_Field__c)
		);
		return xafFile;
	}
	@TestVisible
	private static String addNode(String name, String value) {
		return '<'+name+'>'+value+'</'+name+'>';
	}
	@TestVisible
	private String getValue(String key, String fieldName) {
		if(allXAFCustSetMap.containsKey(key)) {
			return (String) allXAFCustSetMap.get(key).get(fieldName);
		}
		return '';
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<String> createCompany(Date startDate, Date endDate){
		System.debug('createCompany1');
		mappingMap = new Map<String, List<XAFXML_Mapping__c>>();
		allXAFCustSetMap = XAFXML_Mapping__c.getAll();
		folderId = [
				SELECT	Id
				FROM	Folder
				WHERE	Name = 'FinancialForceDocuments'
				LIMIT	1
		].Id;

		List<XAFXML_Mapping__c> xafMapList = allXAFCustSetMap.values();
		List<XAFXML_Mapping__c> xafObjMapList;
		Set<String> mapObjectSet = new Set<String>();

		for (XAFXML_Mapping__c csMap: xafMapList) {
			mapObjectSet.add(csMap.Object_Name__c);
		}

		for (String objSet: mapObjectSet) {
			xafObjMapList = new List<XAFXML_Mapping__c>();
			for (XAFXML_Mapping__c csMap: xafMapList) {
				if (csMap.Object_Name__c.equals(objSet)) {
					xafObjMapList.add(csMap);
				}
			}
			mappingMap.put(objSet, xafObjMapList);
		}
		String header = createHeader(startDate, endDate);
		String company =	addNode(allXAFCustSetMap.get('Company1').XAF_Tag__c,
                                addNode(allXAFCustSetMap.get('Company2').XAF_Tag__c, allXAFCustSetMap.get('Company2').SFDC_Field__c) +
                                    addNode(allXAFCustSetMap.get('Company3').XAF_Tag__c, allXAFCustSetMap.get('Company3').SFDC_Field__c) +
                                    addNode(allXAFCustSetMap.get('Company4').XAF_Tag__c, allXAFCustSetMap.get('Company4').SFDC_Field__c) +
                                    addNode(allXAFCustSetMap.get('Company5').XAF_Tag__c, allXAFCustSetMap.get('Company5').SFDC_Field__c) +
                                    streetAddress(allXAFCustSetMap.get('Company3').SFDC_Field__c) +
                                    getcustomersSuppliers(startDate, endDate) +
                                    getGeneralLedger() +
                                    getVatCodes('') +
                                    getPeriods(allXAFCustSetMap.get('Company3').SFDC_Field__c, startDate, endDate) + 
                                    getTransactions(oneFile, startDate, endDate)
                    		);
		String auditString = '<auditfile xmlns="http://www.auditfiles.nl/XAF/3.2" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.auditfiles.nl/XAF/3.2 C:/XAF/201404~1/XmlAuditfileFinancieel3.2.xsd">';
        String xafFile = XML_VERSION + auditString + header + company + '</auditfile>';
		System.debug('xafFile: ' + xafFile);
		System.debug('createDoc');
		String docId = createDoc('Company "' + allXAFCustSetMap.get('Company3').SFDC_Field__c + '" - (' + getDateString(startDate) + ' - ' + getDateString(endDate) + ') ' + String.valueOf(Date.today()), xafFile, folderId); //
        System.debug('Doc: ' + docId);
		// call batch here
        String batchId = '';
		if (oneFile == false) {
			batchId = Database.executeBatch(new TransactionsInsertBatch(transactionsSOQL, docId, allXAFCustSetMap), 2000);
		}

		List<String> companyReturnList = new List<String>();
		companyReturnList.add('/' + docId);
		companyReturnList.add(batchId);
		companyReturnList.add('SELECT Audit_File_Transaction_Text__c, Transaction__r.c2g__TransactionType__c FROM TransactionAuditFile__c WHERE DocumentId__c = \'' + docId + '\' ORDER BY Transaction__r.c2g__TransactionType__c');
		System.debug('companyReturnList: ' + companyReturnList);
		return companyReturnList;
	}
    private String streetAddress(String companyName) {	
        Set<String> fieldsSet = new Set<String>();
        for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__codaCompany__c')) {
            if (csMap.SFDC_Field__c != null) {
                fieldsSet.add(csMap.SFDC_Field__c);
            }
        }
        String fields = String.join(new List<String>(fieldsSet), ',');
        
        String taxCodesSelect = 'SELECT ' + fields + ' ' +
                                'FROM 	c2g__codaCompany__c ' + 
                                'WHERE 	Name = \'' + companyName + '\'' + 
                                'LIMIT 	1';
        c2g__codaCompany__c company = Database.query(taxCodesSelect);
    
        String xafFile = '';
        xafFile += 	addNode(allXAFCustSetMap.get('c2g__codaCompany__c1').XAF_Tag__c, 
                        addNode(allXAFCustSetMap.get('c2g__codaCompany__c2').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaCompany__c2').SFDC_Field__c, company)) + 
                        addNode(allXAFCustSetMap.get('c2g__codaCompany__c3').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaCompany__c3').SFDC_Field__c, company)) + 
                        addNode(allXAFCustSetMap.get('c2g__codaCompany__c4').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaCompany__c4').SFDC_Field__c, company)) + 
                        addNode(allXAFCustSetMap.get('c2g__codaCompany__c5').XAF_Tag__c, allXAFCustSetMap.get('Company4').SFDC_Field__c) //getTagValue(allXAFCustSetMap.get('c2g__codaCompany__c5').SFDC_Field__c, company))
                    );
        return xafFile;
    }
	private String getCustomersSuppliers(Date startDate, Date endDate) {
		Set<String> fieldsSet = new Set<String>();
		for (XAFXML_Mapping__c csMap: mappingMap.get('Account')) {
			if (csMap.SFDC_Field__c != null) {
				fieldsSet.add(csMap.SFDC_Field__c);
			}
		}
		String fields = String.join(new List<String>(fieldsSet), ',');

		String taxCodesSelect = 'SELECT ' + fields + ' ' +
								'FROM Account ' +
								'WHERE 	Id IN ( ' +
												'SELECT	c2g__Account__c ' +
												'FROM 	c2g__codaPurchaseInvoice__c ' +
												'WHERE 	c2g__InvoiceDate__c >= ' + getDateString(startDate) + ' AND ' +
														'c2g__InvoiceDate__c < ' + getDateString(endDate) +
											 ')';
		List<Account> vendorList = Database.query(taxCodesSelect);

		String xafFile = '';
		for (Account acc: vendorList) {
			xafFile +=	addNode(allXAFCustSetMap.get('Account2').XAF_Tag__c,
					addNode(getValue('Account3', 'XAF_Tag__c'), getTagValue(getValue('Account3', 'SFDC_Field__c'), acc)) +
							addNode(getValue('Account4', 'XAF_Tag__c'), getTagValue(getValue('Account4', 'SFDC_Field__c'), acc)) +
							addNode(getValue('Account5', 'XAF_Tag__c'), getTagValue(getValue('Account5', 'SFDC_Field__c'), acc)) +
							addNode(getValue('Account6', 'XAF_Tag__c'), getTagValue(getValue('Account6', 'SFDC_Field__c'), acc)) +
							//addNode(getValue('Account7', 'XAF_Tag__c'), getTagValue(getValue('Account7', 'SFDC_Field__c'), acc)) +
							addNode(getValue('Account8', 'XAF_Tag__c'), getTagValue(getValue('Account8', 'SFDC_Field__c'), acc)) +
							addNode(getValue('Account9', 'XAF_Tag__c'), 
								addNode(getValue('Account10', 'XAF_Tag__c'), getTagValue(getValue('Account10', 'SFDC_Field__c'), acc)) + 
                            	addNode(getValue('Account11', 'XAF_Tag__c'), getTagValue(getValue('Account11', 'SFDC_Field__c'), acc)) +
                                addNode(getValue('Account12', 'XAF_Tag__c'), getTagValue(getValue('Account12', 'SFDC_Field__c'), acc))// +
                                //addNode(getValue('Account13', 'XAF_Tag__c'), getTagValue(getValue('Account13', 'SFDC_Field__c'), acc))
                            ) +
							addNode(getValue('Account14', 'XAF_Tag__c'), 
                            	addNode(getValue('Account15', 'XAF_Tag__c'), getTagValue(getValue('Account15', 'SFDC_Field__c'), acc)) +
                                addNode(getValue('Account16', 'XAF_Tag__c'), getTagValue(getValue('Account16', 'SFDC_Field__c'), acc)) +
                                addNode(getValue('Account17', 'XAF_Tag__c'), getTagValue(getValue('Account17', 'SFDC_Field__c'), acc))// +
                                //addNode(getValue('Account18', 'XAF_Tag__c'), getTagValue(getValue('Account18', 'SFDC_Field__c'), acc))
                            ) +
							addNode(allXAFCustSetMap.get('Account19').XAF_Tag__c,
                                    addNode(allXAFCustSetMap.get('Account20').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('Account20').SFDC_Field__c, acc)) +
                                    addNode(allXAFCustSetMap.get('Account21').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('Account21').SFDC_Field__c, acc))
							)
			);
		}
		xafFile = addNode(allXAFCustSetMap.get('Account1').XAF_Tag__c, xafFile);
		return xafFile;
	}
	@TestVisible
	private String getGeneralLedger() {
		Map<String, String> glaTypeMap = new Map<String, String>();
		glaTypeMap.put('Balance Sheet','B');
		glaTypeMap.put('Profit and Loss','P');
		glaTypeMap.put('Retained Earnings','M');
		Set<String> fieldsSet = new Set<String>();
		for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__codaGeneralLedgerAccount__c')) {
			if (csMap.SFDC_Field__c != null) {
				fieldsSet.add(csMap.SFDC_Field__c);
			}
		}
		String fields = String.join(new List<String>(fieldsSet), ',');

		String glaSelect =	'SELECT ' + fields + ' ' +
							'FROM	c2g__codaGeneralLedgerAccount__c ';// +
							//'WHERE 	c2g__OwnerCompany__r.name = \'' + companyName + '\'';
		List<c2g__codaGeneralLedgerAccount__c> glaList = Database.query(glaSelect);

		String xafFile = '';
		for (c2g__codaGeneralLedgerAccount__c gla: glaList) {
			xafFile += addNode(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c2').XAF_Tag__c,
                                addNode(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c3').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c3').SFDC_Field__c, gla)) +
                                addNode(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c4').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c4').SFDC_Field__c, gla)) +
                                addNode(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c5').XAF_Tag__c, glaTypeMap.get(getTagValue(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c5').SFDC_Field__c, gla)))
					   );
		}
		xafFile = addNode(allXAFCustSetMap.get('c2g__codaGeneralLedgerAccount__c1').XAF_Tag__c, xafFile);
		return xafFile;
	}
	private String getVatCodes(String companyName) {
		Set<String> fieldsSet = new Set<String>();
		for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__codaTaxCode__c')) {
			if (csMap.SFDC_Field__c != null) {
				fieldsSet.add(csMap.SFDC_Field__c);
			}
		}
		String fields = String.join(new List<String>(fieldsSet), ',');

		String taxCodesSelect = 'SELECT ' + fields + ' ' +
				'FROM c2g__codaTaxCode__c ';// +
		//'WHERE 	c2g__OwnerCompany__r.name = \'' + companyName + '\'';
		List<c2g__codaTaxCode__c> taxcodes = Database.query(taxCodesSelect);

		String xafFile = '';
		for (c2g__codaTaxCode__c taxcode: taxcodes) {
			xafFile += addNode(allXAFCustSetMap.get('c2g__codaTaxCode__c2').XAF_Tag__c,
					addNode(allXAFCustSetMap.get('c2g__codaTaxCode__c3').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaTaxCode__c3').SFDC_Field__c, taxcode)) +
							addNode(allXAFCustSetMap.get('c2g__codaTaxCode__c4').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaTaxCode__c4').SFDC_Field__c, taxcode)) +
							addNode(allXAFCustSetMap.get('c2g__codaTaxCode__c5').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaTaxCode__c5').SFDC_Field__c, taxcode)) +
							addNode(allXAFCustSetMap.get('c2g__codaTaxCode__c6').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaTaxCode__c6').SFDC_Field__c, taxcode))
			);
		}
		xafFile = addNode(allXAFCustSetMap.get('c2g__codaTaxCode__c1').XAF_Tag__c, xafFile);
		return xafFile;
	}
	private String getPeriods(String companyName, Date startDate, Date endDate) {
		Set<String> fieldsSet = new Set<String>();
		for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__codaPeriod__c')) {
			if (csMap.SFDC_Field__c != null) {
				fieldsSet.add(csMap.SFDC_Field__c);
			}
		}
		String fields = String.join(new List<String>(fieldsSet), ',');

		String periodsSelect =	'SELECT ' + fields + ' ' +
				'FROM	c2g__codaPeriod__c ' +
				'WHERE 	c2g__OwnerCompany__r.name = \'' + companyName + '\' AND ' +
						'c2g__StartDate__c >= ' + getDateString(startDate) + ' AND ' + 
						'c2g__StartDate__c <= ' + getDateString(endDate);
		List<c2g__codaPeriod__c> periodsList = Database.query(periodsSelect);

		String xafFile = '';
		for (c2g__codaPeriod__c per: periodsList) {
			xafFile +=	addNode(allXAFCustSetMap.get('c2g__codaPeriod__c2').XAF_Tag__c,
							addNode(allXAFCustSetMap.get('c2g__codaPeriod__c3').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaPeriod__c3').SFDC_Field__c, per)) +
							addNode(allXAFCustSetMap.get('c2g__codaPeriod__c4').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaPeriod__c4').SFDC_Field__c, per)) +
							addNode(allXAFCustSetMap.get('c2g__codaPeriod__c5').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaPeriod__c5').SFDC_Field__c, per).split(' ')[0]) +
							addNode(allXAFCustSetMap.get('c2g__codaPeriod__c6').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaPeriod__c5').SFDC_Field__c, per).split(' ')[1]) +
							addNode(allXAFCustSetMap.get('c2g__codaPeriod__c7').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaPeriod__c7').SFDC_Field__c, per).split(' ')[0]) +
							addNode(allXAFCustSetMap.get('c2g__codaPeriod__c8').XAF_Tag__c, getTagValue(allXAFCustSetMap.get('c2g__codaPeriod__c7').SFDC_Field__c, per).split(' ')[1])
			);
		}
		xafFile = addNode(allXAFCustSetMap.get('c2g__codaPeriod__c1').XAF_Tag__c, xafFile);
		return xafFile;
	}
	private String getJournaltransactionDetails(String companyName, Date startDate, Date endDate) {

		Set<String> trFieldsSet = new Set<String>();
		Set<String> trLinesFieldsSet = new Set<String>();
		for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__codaTransaction__c')) {
			if (csMap.SFDC_Field__c != null) {
				trFieldsSet.add(csMap.SFDC_Field__c);
			}
		}
		for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__TransactionLineItems__r')) {
			if (csMap.SFDC_Field__c != null) {
				trLinesFieldsSet.add(csMap.SFDC_Field__c);
			}
		}

		String trFields = String.join(new List<String>(trFieldsSet), ',');
		String trLinesFields = String.join(new List<String>(trLinesFieldsSet), ',');

		String trSelect =	'SELECT ' + trFields + ', ' +
                                        '(' +
                                            'SELECT ' + trLinesFields + ' ' +
                                            'FROM	c2g__TransactionLineItems__r ' +
                                        ') ' +
                            'FROM	c2g__codaTransaction__c ' +
                            'WHERE 	c2g__OwnerCompany__r.Name = \'' + companyName + '\' AND ' +
                            		'c2g__Period__r.c2g__StartDate__c >= ' + getDateString(startDate) + ' AND ' + 
									'c2g__Period__r.c2g__EndDate__c <= ' + getDateString(endDate);
		System.debug(trSelect);
		AggregateResult total = [
									SELECT	SUM(c2g__Debits__c), 
											SUM(c2g__Credits__c) 
									FROM	c2g__codaTransaction__c 
									WHERE 	c2g__OwnerCompany__r.Name = :companyName AND 
											c2g__Period__r.c2g__StartDate__c >= :startDate AND
											c2g__Period__r.c2g__EndDate__c <= :endDate
							    ];
		System.debug(total);
		List<c2g__codaTransaction__c> transactionList = Database.query(trSelect);
        Set<String> trTypeSet = new Set<String>();
        List<c2g__codaTransaction__c> tempTrList;
        Map<String, List<c2g__codaTransaction__c>> trMap = new Map<String, List<c2g__codaTransaction__c>>();
        
        for (c2g__codaTransaction__c tr: transactionList) {
            trTypeSet.add(tr.c2g__TransactionType__c);
        }
        for (String type: trTypeSet) {
            tempTrList = new List<c2g__codaTransaction__c>();
            for (c2g__codaTransaction__c tr: transactionList) {
                if (type == tr.c2g__TransactionType__c) {
                    tempTrList.add(tr);
                }
            }
            trMap.put(type, tempTrList);
        }
        
        String xafString = '';
		try{	        
			for (String type: trTypeSet) {
				xafString += addNode(getValue('c2g__codaTransaction__c5', 'XAF_Tag__c'),
								addNode(getValue('c2g__codaTransaction__c6', 'XAF_Tag__c'), type) +
								addNode(getValue('c2g__codaTransaction__c7', 'XAF_Tag__c'), type) +
								addNode(getValue('c2g__codaTransaction__c8', 'XAF_Tag__c'), 'M') +
								generatingTrString(trMap.get(type))
							 );
				}
				xafString = addNode(getValue('c2g__codaTransaction__c1', 'XAF_Tag__c'),
								addNode(getValue('c2g__codaTransaction__c2', 'XAF_Tag__c'), String.valueOf(transactionList.size())) +
								addNode(getValue('c2g__codaTransaction__c3', 'XAF_Tag__c'), String.valueOf(total.get('expr0'))) +
								addNode(getValue('c2g__codaTransaction__c4', 'XAF_Tag__c'), String.valueOf(total.get('expr1'))) +
								xafString
			);
			return xafString;
		}
		catch (Exception e){
			error = true;
			errorMessage = 'Couldn\'t create the file. Please reload the page and try again, or contact your system administrator.\n' + e.getMessage();
			return null;
		}
		
		
	}
	private String generatingTrString (List<c2g__codaTransaction__c> trList) {
        String xafString = '';
        for (c2g__codaTransaction__c tr: trList) {
            xafString += addNode(getValue('c2g__codaTransaction__c9', 'XAF_Tag__c'),
                            addNode(getValue('c2g__codaTransaction__c10', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c10', 'SFDC_Field__c'), tr)) +	
                            addNode(getValue('c2g__codaTransaction__c11', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c11', 'SFDC_Field__c'), tr)) +	
                            addNode(getValue('c2g__codaTransaction__c12', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c12', 'SFDC_Field__c'), tr)) +	
                            addNode(getValue('c2g__codaTransaction__c13', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c13', 'SFDC_Field__c'), tr).split(' ')[0]) +	
                            generateTrLinesString(tr.c2g__TransactionLineItems__r)
                         );
        }
        return xafString;
    }
    private String generateTrLinesString (List<c2g__codaTransactionLineItem__c> trLineList) {
        String xafString = '';
        for (c2g__codaTransactionLineItem__c trLine: trLineList) {
            xafString += addNode(getValue('c2g__TransactionLineItems__r1', 'XAF_Tag__c'),
                            addNode(getValue('c2g__TransactionLineItems__r2', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r2', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r3', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r3', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r4', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r4', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r5', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r5', 'SFDC_Field__c'), trLine).split(' ')[0]) +	
                            addNode(getValue('c2g__TransactionLineItems__r6', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r6', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r7', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r7', 'SFDC_Field__c'), trLine)) +
                            addNode(getValue('c2g__TransactionLineItems__r8', 'XAF_Tag__c'), debitOrCredit(getTagValue(getValue('c2g__TransactionLineItems__r7', 'SFDC_Field__c'), trLine)))
                         );
        }
        return xafString;
    }
	@TestVisible
	private String debitOrCredit (String homeValue) {
		String value = 'D';
		if (Double.valueOf(homeValue) < 0) {
			value = 'C';
		}
		return value;
	}
    
    private String getJournaltransaction(String companyName,Date startDate, Date endDate) {
        Set<String> trFieldsSet = new Set<String>();
		Set<String> trLinesFieldsSet = new Set<String>();
		for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__codaTransaction__c')) {
			if (csMap.SFDC_Field__c != null) {
				trFieldsSet.add(csMap.SFDC_Field__c);
			}
		}
		for (XAFXML_Mapping__c csMap: mappingMap.get('c2g__TransactionLineItems__r')) {
			if (csMap.SFDC_Field__c != null) {
				trLinesFieldsSet.add(csMap.SFDC_Field__c);
			}
		}

		String trFields = String.join(new List<String>(trFieldsSet), ',');
		String trLinesFields = String.join(new List<String>(trLinesFieldsSet), ',');

		transactionsSOQL =	'SELECT ' + trFields + ', ' +
                                        '(' +
                                            'SELECT ' + trLinesFields + ' ' +
                                            'FROM	c2g__TransactionLineItems__r ' +
                                        ') ' +
                            'FROM	c2g__codaTransaction__c ' +
                            'WHERE 	c2g__OwnerCompany__r.Name = \'' + companyName + '\' AND ' +
                            		'c2g__Period__r.c2g__StartDate__c >= ' + getDateString(startDate) + ' AND ' + 
									'c2g__Period__r.c2g__EndDate__c <= ' + getDateString(endDate);
        
        List<String> pickListValuesList= new List<String>();
		Schema.DescribeFieldResult fieldResult = c2g__codaTransaction__c.c2g__TransactionType__c.getDescribe();
		List<Schema.PicklistEntry> ple = fieldResult.getPicklistValues();
		for(Schema.PicklistEntry pickListVal : ple){
			pickListValuesList.add(pickListVal.getLabel());
		}     
		AggregateResult total = [
									SELECT	SUM(c2g__Debits__c), 
											SUM(c2g__Credits__c) 
									FROM	c2g__codaTransaction__c 
									WHERE 	c2g__OwnerCompany__r.Name = :companyName AND 
											c2g__Period__r.c2g__StartDate__c >= :startDate AND
											c2g__Period__r.c2g__EndDate__c <= :endDate
							    ];
        String xafString = '';
        for (String type: pickListValuesList) {
            xafString += addNode(getValue('c2g__codaTransaction__c5', 'XAF_Tag__c'),
                            addNode(getValue('c2g__codaTransaction__c6', 'XAF_Tag__c'), type) +
                            addNode(getValue('c2g__codaTransaction__c7', 'XAF_Tag__c'), type) +
                            addNode(getValue('c2g__codaTransaction__c8', 'XAF_Tag__c'), 'M')
                         );
        }
        xafString = addNode(getValue('c2g__codaTransaction__c1', 'XAF_Tag__c'),
                            addNode(getValue('c2g__codaTransaction__c2', 'XAF_Tag__c'), null) +
                            addNode(getValue('c2g__codaTransaction__c3', 'XAF_Tag__c'), String.valueOf(total.get('expr0'))) +
                            addNode(getValue('c2g__codaTransaction__c4', 'XAF_Tag__c'), String.valueOf(total.get('expr1'))) +
                            xafString
                    );        
        return xafString;
    }
}