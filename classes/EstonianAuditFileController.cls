public with sharing class EstonianAuditFileController  {
	
	private List<Estonian_Audit_File_Mapping__mdt> mappingList;
	private Map<String, List<Estonian_Audit_File_Mapping__mdt>> mappingRecordByLabelMap;
	private Map<String, Estonian_Audit_File_Mapping__mdt> mappingRecordByDeveloperNameMap;
	private Map<String, c2g__codaTaxRate__c> taxRateByTaxCodeMap;
	private List<c2g__codaInvoice__c> saleLineList;
	private List<c2g__codaPurchaseInvoice__c> purchaseLineList;
	private List<c2g__codaInvoice__c> aruandeReadList;
	private List<c2g__codaTaxRate__c> taxRateList;

	public List<String> yearList{get; set;}
	public List<String> monthList{get; set;}
	public List<SelectOption> yearOptions{get; set;}
	public List<SelectOption> monthOptions{get; set;}
	public List<SelectOption> fileTypeOptions{get; set;}

	private Map<String, List<c2g__codaPeriod__c>> preiodByYearMap;

	public String periodYear{get; set;}
	public String periodMonth{get; set;}
	public String fileType{get; set;}
	public Boolean fileTypeIsSelected{get; set;}
	public String salesAmountLimit;
	public String docLink{get; set;}
	public Decimal saleLineLimit{get; set;}

	private String xmlFileString;
	private Date startDate;
	private Date endDate;
    String fileName;

	public EstonianAuditFileController(){
		initFileTypes();
		getPeriods();
		getFieldMapping();
		sortFieldMapping(mappingList);
	}

	private void createEEVATINF(){
		try{
			if(String.isBlank(periodYear) || String.isBlank(periodMonth)){
				throw new CustomException('Choose Year and Month');
			}else{
				
				getTaxRate();

				String salesAnnex = generateSalesAnnex();
				String purchasesAnnex = generatePurchasesAnnex();

				xmlFileString = '';
				xmlFileString += '<?xml version="1.0" encoding="UTF-8"?>';
				xmlFileString += addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration0').Tag_Name__c,
					generateVATDeclaration() +
					generateDeclarationBody() +
					salesAnnex +
					purchasesAnnex
				);
                fileName = 'Estonian File';
                docLink = ReportsGenerationUtility.generateDocAndAddToFolder(fileName, xmlFileString);
			}
		}catch(Exception e){
			ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
		}
	}

	private void createEEEUVAT(){
		try{
			if(String.isBlank(periodYear) || String.isBlank(periodMonth)){
				throw new CustomException('Choose Year and Month');
			}else{
				String aruandeRead = generateAruandeRead();
				String vdDeclaratsioon = generateVDDeclaratsioon();

				xmlFileString = '';
				xmlFileString += '<?xml version="1.0" encoding="UTF-8"?>';
				xmlFileString += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('VD_deklaratsioon0').Namespace__c + ':' +
					mappingRecordByDeveloperNameMap.get('VD_deklaratsioon0').Tag_Name__c, vdDeclaratsioon +
					addNode(mappingRecordByDeveloperNameMap.get('VD_deklaratsioon4').Tag_Name__c, aruandeRead),
					mappingRecordByDeveloperNameMap.get('VD_deklaratsioon0').Attribute_Name__c,
					mappingRecordByDeveloperNameMap.get('VD_deklaratsioon0').Attribute_Value__c
				);
                fileName = 'Estonian File';
                docLink = ReportsGenerationUtility.generateDocAndAddToFolder(fileName, xmlFileString);
			}
		}catch(Exception e){
			ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
		}
	}

	public void createXML(){
		if(fileTypeIsSelected){
			if(fileType == 'report1'){
				createEEEUVAT();
			}else if(fileType =='report2'){
				createEEVATINF();
			}
		}
	}

	public void initFileTypes(){
		fileTypeOptions = new List<SelectOption>();
		fileTypeOptions.add(new SelectOption('report1', 'EE - EU VAT'));
		fileTypeOptions.add(new SelectOption('report2', 'EE VAT INF'));
	}

	public void getPeriods(){
		ReportsGenerationUtility.getPeriods();
        yearOptions = ReportsGenerationUtility.yearOptions;
        monthOptions = ReportsGenerationUtility.monthOptions;
	}

	public void getFieldMapping(){
		mappingList = new List<Estonian_Audit_File_Mapping__mdt>();
		if(mappingList.isEmpty()){
			mappingList = [
				SELECT	
						Id,
						DeveloperName,
						MasterLabel,
						Default_Value__c,
						Object_Name__c,
						SFDC_Field_Name__c,
						Tag_Name__c,
						Attribute_Name__c ,
						Attribute_Value__c ,
						Namespace__c
				FROM	Estonian_Audit_File_Mapping__mdt
				LIMIT	1000
			];
		}
	}

	private void sortFieldMapping(List<Estonian_Audit_File_Mapping__mdt> unsortedList){
		mappingRecordByLabelMap = new Map<String, List<Estonian_Audit_File_Mapping__mdt>>();
		mappingRecordByDeveloperNameMap = new Map<String, Estonian_Audit_File_Mapping__mdt>();
		if(!unsortedList.isEmpty()){
			for(Estonian_Audit_File_Mapping__mdt item : unsortedList){
				if(mappingRecordByLabelMap.get(item.MasterLabel) == null){
					mappingRecordByLabelMap.put(item.MasterLabel, new List<Estonian_Audit_File_Mapping__mdt>{item});
				}else{
					mappingRecordByLabelMap.get(item.MasterLabel).add(item);
				}
				mappingRecordByDeveloperNameMap.put(item.DeveloperName, item);
			}
		}
	}

	private Set<String> getFieldNameSet(String parentTagName){
		Set<String> fieldNameSet = new Set<String>();
		List<Estonian_Audit_File_Mapping__mdt> mappingRecordList = mappingRecordByLabelMap.get(parentTagName);
		for(Estonian_Audit_File_Mapping__mdt item : mappingRecordList){
			if(String.isNotBlank(item.SFDC_Field_Name__c)){
				fieldNameSet.add(item.SFDC_Field_Name__c);
			}
		}
		return fieldNameSet;
	}

	private String addNode(String tagName, String value){
		return '<' + tagName + '>' + value + '</' + tagName + '>';
	}

	private String addNodeWithAttributes(String tagName, String value, String attributeName, String attributeValue){
		return	'<' + tagName +	' ' + attributeName + '="' + attributeValue + '"' +	'>' + value + '</' + tagName + '>';
	}

	private void getTaxRate(){
		taxRateByTaxCodeMap = new Map<String, c2g__codaTaxRate__c>();
		taxRateList = new List<c2g__codaTaxRate__c>();
		try{
			taxRateList = [
				SELECT	
						Id,
						c2g__TaxCode__c,
						c2g__Rate__c
				FROM	c2g__codaTaxRate__c
				LIMIT	10000
			];

			for(c2g__codaTaxRate__c item : taxRateList){
				taxRateByTaxCodeMap.put(item.c2g__TaxCode__c, item);
			}

		}catch(Exception e){
			System.debug(e.getMessage());
		}
	}

	/*private String generateDeclarationBody(){
		String declarationBody;
		Set<String> declarationBodySFDCFieldSet = new Set<String>(mappingRecordByLabelMap.get('declarationBody'));
		if(mappingRecordByLabelMap != null && !mappingRecordByLabelMap.isEmpty()){
			declarationBody = addNode('declarationBody');
		}
	}*/

	private String generateVATDeclaration(){
		String vatDeclaration = addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration1').Tag_Name__c, mappingRecordByDeveloperNameMap.get('vatDeclaration1').Default_Value__c) +
								addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration2').Tag_Name__c, mappingRecordByDeveloperNameMap.get('vatDeclaration2').Default_Value__c) +
								addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration3').Tag_Name__c, periodYear) +
								addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration4').Tag_Name__c, periodMonth) +
								addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration5').Tag_Name__c, mappingRecordByDeveloperNameMap.get('vatDeclaration5').Default_Value__c);
		return vatDeclaration;
	}

	private String generateDeclarationBody(){
		String declarationBody = '';
		declarationBody += addNode(
			mappingRecordByDeveloperNameMap.get('vatDeclaration6').Tag_Name__c,
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody1').Tag_Name__c, saleLineList.isEmpty() ? 'true' : 'false') +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody2').Tag_Name__c, purchaseLineList.isEmpty() ? 'true' : 'false') +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody3').Tag_Name__c, 'false') +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody4').Tag_Name__c, 'false') +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody5').Tag_Name__c, getTransaction20Amount()) +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody6').Tag_Name__c, getTransaction9Amount()) +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody7').Tag_Name__c, getTransactionZeroAmount()) +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody8').Tag_Name__c, '0.0') +
			addNode(mappingRecordByDeveloperNameMap.get('declarationBody9').Tag_Name__c, '0.0')
		);
		return declarationBody;
	}

	private String getSalesAmount(){
		Decimal salesTotal = 0;
		if(saleLineList != null && !saleLineList.isEmpty()){
			for(c2g__codaInvoice__c item : saleLineList){
				salesTotal += item.c2g__InvoiceTotal__c != null ? item.c2g__InvoiceTotal__c : 0;
			}
		}
		return String.valueOf(salesTotal);
	}

	private String getPurchasesAmount(){
		Decimal purchasesTotal = 0;
		if(purchaseLineList != null && !purchaseLineList.isEmpty()){
			for(c2g__codaPurchaseInvoice__c item : purchaseLineList){
				purchasesTotal += item.c2g__NetTotal__c != null ? item.c2g__NetTotal__c : 0;
			}
		}
		return String.valueOf(purchasesTotal);
	}

	private String getTransactionZeroAmount(){
		/*List<String> taxCodeRateZeroList = new List<String>();
		for(c2g__codaTaxRate__c item : taxRateList){
			if(item.c2g__Rate__c >= 9){
				taxCodeRateZeroList.add(item.c2g__TaxCode__c);
			}
		}
		String whereCondition = 'c2g__TaxCode1__c IN: ' + taxCodeRateZeroList;*/
		AggregateResult[] res1 = [
			SELECT	
					SUM(c2g__InvoiceTotal__c) total
			FROM	c2g__codaInvoice__c
			WHERE	c2g__TaxCode1__c = NULL 
		];

		AggregateResult[] res2 = [
			SELECT	
					SUM(c2g__NetTotal__c) total
			FROM	c2g__codaPurchaseInvoice__c
			WHERE	c2g__TaxCode1__c = NULL
		];

		String totalStr1 = String.valueOf(res1[0].get('total'));
		String totalStr2 = String.valueOf(res2[0].get('total'));

		Decimal total1 = Decimal.valueOf(String.isNotBlank(totalStr1)?totalStr1:'0');
		Decimal total2 = Decimal.valueOf(String.isNotBlank(totalStr2)?totalStr2:'0');
		String total = String.valueOf(total1 + total2);

		if(total == null){
			return '0.0';
		}else{
			return String.valueOf(total);
		}
	}

	private String getTransaction9Amount(){
		List<String> taxCodeRate9List = new List<String>();
		for(c2g__codaTaxRate__c item : taxRateList){
			if(item.c2g__Rate__c >= 9){
				taxCodeRate9List.add(item.c2g__TaxCode__c);
			}
		}
		String whereCondition = 'c2g__TaxCode1__c IN: ' + taxCodeRate9List;
		AggregateResult[] res1 = [
			SELECT	
					SUM(c2g__InvoiceTotal__c) total
			FROM	c2g__codaInvoice__c
			WHERE	c2g__TaxCode1__c IN: taxCodeRate9List 
		];

		AggregateResult[] res2 = [
			SELECT	
					SUM(c2g__NetTotal__c) total
			FROM	c2g__codaPurchaseInvoice__c
			WHERE	c2g__TaxCode1__c IN: taxCodeRate9List 
		];

		String totalStr1 = String.valueOf(res1[0].get('total'));
		String totalStr2 = String.valueOf(res2[0].get('total'));

		Decimal total1 = Decimal.valueOf(String.isNotBlank(totalStr1)?totalStr1:'0');
		Decimal total2 = Decimal.valueOf(String.isNotBlank(totalStr2)?totalStr2:'0');
		String total = String.valueOf(total1 + total2);

		if(total == null){
			return '0.0';
		}else{
			return String.valueOf(total);
		}
	}

	private String getTransaction20Amount(){
		List<String> taxCodeRate20List = new List<String>();
		for(c2g__codaTaxRate__c item : taxRateList){
			if(item.c2g__Rate__c >= 20){
				taxCodeRate20List.add(item.c2g__TaxCode__c);
			}
		}
		String whereCondition = 'c2g__TaxCode1__c IN: ' + taxCodeRate20List;
		AggregateResult[] res1 = [
			SELECT	
					SUM(c2g__InvoiceTotal__c) total
			FROM	c2g__codaInvoice__c
			WHERE	c2g__TaxCode1__c IN: taxCodeRate20List 
		];

		AggregateResult[] res2 = [
			SELECT	
					SUM(c2g__NetTotal__c) total
			FROM	c2g__codaPurchaseInvoice__c
			WHERE	c2g__TaxCode1__c IN: taxCodeRate20List 
		];

		String totalStr1 = String.valueOf(res1[0].get('total'));
		String totalStr2 = String.valueOf(res2[0].get('total'));

		Decimal total1 = Decimal.valueOf(String.isNotBlank(totalStr1)?totalStr1:'0');
		Decimal total2 = Decimal.valueOf(String.isNotBlank(totalStr2)?totalStr2:'0');
		String total = String.valueOf(total1 + total2);

		if(total == null){
			return '0.0';
		}else{
			return String.valueOf(total);
		}
	}

	private List<sObject> getInvoices(String objName, String fieldNameSetSrt, Set<String> additionalFields, String whereCondition){
		Set<String> fieldNameSet = new Set<String>();
		fieldNameSet = getFieldNameSet(fieldNameSetSrt);
		if(additionalFields != null && !additionalFields.isEmpty()){
			fieldNameSet.addAll(additionalFields);
		}
		startDate = Date.newInstance(Integer.valueOf(periodYear), Integer.valueOf(periodMonth), 1);
		Integer daysCount = Date.daysInMonth(Integer.valueOf(periodYear), Integer.valueOf(periodMonth));
		endDate = Date.newInstance(Integer.valueOf(periodYear), Integer.valueOf(periodMonth), daysCount);
		List<sObject> sObjectList = getSFDCRecords(objName, fieldNameSet, whereCondition);
		
        return sObjectList;
	}

	private String generateSalesAnnex(){
		String saleLines = '';
		Set<String> fieldNameSet = new Set<String>();
		/*fieldNameSet = getFieldNameSet('saleLine');*/
		fieldNameSet.add('c2g__TaxCode1__c');
		fieldNameSet.add('c2g__OwnerCompany__r.c2g__ECCountryCode__c');
		/*startDate = Date.newInstance(Integer.valueOf(periodYear), Integer.valueOf(periodMonth), 1);
		Integer daysCount = Date.daysInMonth(Integer.valueOf(periodYear), Integer.valueOf(periodMonth));
		endDate = Date.newInstance(Integer.valueOf(periodYear), Integer.valueOf(periodMonth), daysCount);*/

		String whereCondition = 'c2g__invoiceTotal__c > ' + String.valueOf(saleLineLimit) + 
								' AND c2g__Period__r.c2g__StartDate__c >=:startDate';
		/*System.debug(whereCondition);
		saleLineList = (List<c2g__codaInvoice__c>)getSFDCRecords('c2g__codaInvoice__c', fieldNameSet, whereCondition);*/
		saleLineList = getInvoices('c2g__codaInvoice__c', 'saleLine', fieldNameSet, whereCondition);
		System.debug(saleLineList);
		if(!saleLineList.isEmpty()){
			for(c2g__codaInvoice__c item : saleLineList){
				saleLines += String.isNotBlank(ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('saleLine1').SFDC_Field_Name__c)) ?
					addNode(
						mappingRecordByDeveloperNameMap.get('salesAnnex0').Tag_Name__c,
						addNode(mappingRecordByDeveloperNameMap.get('saleLine1').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('saleLine1').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('saleLine2').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('saleLine2').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('saleLine3').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('saleLine3').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('saleLine4').Tag_Name__c, getDate(ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('saleLine4').SFDC_Field_Name__c))) +
						addNode(mappingRecordByDeveloperNameMap.get('saleLine5').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('saleLine5').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('saleLine6').Tag_Name__c, item.c2g__TaxCode1__c != null ? String.valueOf(taxRateByTaxCodeMap.get(item.c2g__TaxCode1__c).c2g__Rate__c) : '') +
						addNode(mappingRecordByDeveloperNameMap.get('saleLine7').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('saleLine5').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('saleLine8').Tag_Name__c, '')
					) : '';
			}
		}
		String salesAnnex = addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration7').Tag_Name__c, saleLines);
		return salesAnnex;
	}

	private String getDate(String dateString){
		String dateOnly;
		if(String.isNotBlank(dateString)){
			dateOnly = dateString.substringBefore(' ');
		}
		return String.isNotBlank(dateOnly) ? dateOnly : '';
	}

	private String generatePurchasesAnnex(){
		String purchaseLines = '';
		Set<String> fieldNameSet = new Set<String>();
		/*fieldNameSet = getFieldNameSet('purchaseLine');*/
		fieldNameSet.add('c2g__TaxCode1__c');
		/*System.debug(fieldNameSet);*/
		String whereCondition = 'c2g__NetTotal__c > ' + String.valueOf(saleLineLimit) + 
								' AND c2g__Period__r.c2g__StartDate__c >=:startDate';
		/*purchaseLineList = (List<c2g__codaInvoice__c>)getSFDCRecords('c2g__codaInvoice__c', fieldNameSet, whereCondition);*/
		purchaseLineList = (List<c2g__codaPurchaseInvoice__c>)getInvoices('c2g__codaPurchaseInvoice__c', 'purchaseLine', fieldNameSet, whereCondition);
		if(!purchaseLineList.isEmpty()){
			for(c2g__codaPurchaseInvoice__c item : purchaseLineList){
				purchaseLines += String.isNotBlank(ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine1').SFDC_Field_Name__c)) ?
					addNode(
						mappingRecordByDeveloperNameMap.get('purchasesAnnex0').Tag_Name__c,
						addNode(mappingRecordByDeveloperNameMap.get('purchaseLine1').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine1').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('purchaseLine2').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine2').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('purchaseLine3').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine3').SFDC_Field_Name__c)) +
						addNode(mappingRecordByDeveloperNameMap.get('purchaseLine4').Tag_Name__c, getDate(ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine4').SFDC_Field_Name__c))) +
						addNode(mappingRecordByDeveloperNameMap.get('purchaseLine5').Tag_Name__c,
							String.valueOf(Decimal.valueOf(ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine5').SFDC_Field_Name__c)) * (item.c2g__TaxCode1__c != null ? (taxRateByTaxCodeMap.get(item.c2g__TaxCode1__c).c2g__Rate__c + 100)/100 : 0))) +
						addNode(mappingRecordByDeveloperNameMap.get('purchaseLine6').Tag_Name__c,
							String.valueOf(Decimal.valueOf(ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine5').SFDC_Field_Name__c)) * (item.c2g__TaxCode1__c != null ? taxRateByTaxCodeMap.get(item.c2g__TaxCode1__c).c2g__Rate__c/100 : 0))) +
						addNode(mappingRecordByDeveloperNameMap.get('purchaseLine7').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('purchaseLine7').SFDC_Field_Name__c))
					) : '';
			}
		}
		String purchasesAnnex = addNode(mappingRecordByDeveloperNameMap.get('vatDeclaration8').Tag_Name__c, purchaseLines);
		return purchasesAnnex;
	}

	private String generateAruandeRead(){
		String aruandeRead = '';
		Set<String> fieldNameSet = new Set<String>();
		String whereCondition = 'c2g__invoiceTotal__c > ' + String.valueOf(saleLineLimit) + 
								' AND c2g__Period__r.c2g__StartDate__c >=:startDate' +
								' AND c2g__Account__r.c2g__CODAVATRegistrationNumber__c != NULL';
		aruandeReadList = getInvoices('c2g__codaInvoice__c', 'aruandeRida', fieldNameSet, whereCondition);
        
		String countryVATCode = '';
		String VATNumber = '';
		String VATCode = '';
		for(c2g__codaInvoice__c item : aruandeReadList){
			VATCode = ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('aruandeRida1').SFDC_Field_Name__c);
			countryVATCode = VATCode.substring(0,2);
			VATNumber = VATCode.substring(2, VATCode.length());
			aruandeRead += String.isNotBlank(VATNumber) ? addNode(mappingRecordByDeveloperNameMap.get('aruandeRead1').Tag_Name__c,
				addNodeWithAttributes(	mappingRecordByDeveloperNameMap.get('aruandeRida1').Tag_Name__c,
										VATNumber,
										mappingRecordByDeveloperNameMap.get('aruandeRida1').Attribute_Name__c,
										countryVATCode) +
				addNode(mappingRecordByDeveloperNameMap.get('aruandeRida2').Tag_Name__c, ReportsGenerationUtility.getValue(item, mappingRecordByDeveloperNameMap.get('aruandeRida2').SFDC_Field_Name__c))
			) : '';
		}
		return aruandeRead;
	}

	private String generateVDDeclaratsioon(){
		String vdDeclaratsioon = '';
		vdDeclaratsioon =	addNode(mappingRecordByDeveloperNameMap.get('VD_deklaratsioon1').Tag_Name__c, mappingRecordByDeveloperNameMap.get('VD_deklaratsioon1').Default_Value__c) +
							addNode(mappingRecordByDeveloperNameMap.get('VD_deklaratsioon2').Tag_Name__c, periodYear) +
							addNode(mappingRecordByDeveloperNameMap.get('VD_deklaratsioon3').Tag_Name__c, periodMonth);
		return vdDeclaratsioon;
	}

	private String checkIsBlank(String value){
		return String.isNotBlank(value) ? value : '';
	}

	public void selectFileType(){
		fileTypeIsSelected = true;
	}

    private List<SObject> getSFDCRecords(String objName, Set<String> fieldNameSet, String whereCondition){
		if(!fieldNameSet.isEmpty()){
			String query =	'SELECT ' + String.join(new List<String>(fieldNameSet), ',') + 
							' FROM ' + objName +
							(String.isNotBlank(whereCondition) ? ' WHERE ' + whereCondition : '');
			System.debug(query);
			List<SObject> recordsList = new List<SObject>();
			recordsList = Database.query(query);
			return recordsList;
		}else{
			return null;
		}
	}
    
	public class CustomException extends Exception{}   
}