/**
 * Created by vitek on 09.10.2018.
 */

public with sharing class GermanAuditFile {
	private Map<String, List<German_Mapping__c>> mappingMap;
	private Map<String, German_Mapping__c> allGermanSetMap;
	private String folderId;
	public String language {get; set;}
	private Map<String, String> languageMap;
	private static final String SEPARATOR = ',';
	public String docLink {get; set;}
	public Boolean docLinkPrepared {get; set;}
	private Document doc;
	private Set<Id> taxCodeIdSet;

	public List<c2g__codaCompany__c> companiesList {get; set;}
	public List<String> yearsList {get; set;}
	public List<String> periodsList {get; set;}

	private Map<String, Map<String, List<c2g__codaPeriod__c>>> yearByCompanyMap;
	private Map<String, List<c2g__codaPeriod__c>> periodByYearMap;

	public List<String> selectedPeriodList {get; set;}
	public String selectedYear {get; set;}
	public String selectedCompany {get; set;}

	public GermanAuditFile(){
		init();
	}

	public void generateGermanAudit () {
		if(!selectedPeriodList.isEmpty()){
			getLanguage();
			getMapping();
			getFolder();
			generateXML(
							generateSuppliers(),
							generateSuppliersBookings(),
							generateCustomers(),
							generateCustomersBookings(),
							generateGLA(),
							generateGLABookings(),
							generateSalesTaxCode(),
							generateVATGroups(),
							generateVATEntries()
						);
		}else{
			throw new CustomException('Select period.');
		}
	}
	private void generateXML (
								Document suppliers,
								Document suppliersBookings,
								Document customers,
								Document customersBookings,
								Document gla,
								Document glaBookings,
								Document salesTaxCodes,
								Document vatGroups,
								Document vatEntries
	) {
		Map<String, Document> csvMap = new Map<String, Document>();
		csvMap.put('Suppliers', suppliers);
		csvMap.put('SupplierBookings', suppliersBookings);
		csvMap.put('Customers', customers);
		csvMap.put('CustomerBookings', customersBookings);
		csvMap.put('GLAs', gla);
		csvMap.put('GLABookings', glaBookings);
		csvMap.put('SalesTaxCode', salesTaxCodes);
		csvMap.put('VATGroups', vatGroups);
		csvMap.put('VATEntries', vatEntries);
		Zippex zip = new Zippex();

		List<c2g__codaPeriod__c> periodList = [
													SELECT  c2g__StartDate__c,
															c2g__EndDate__c
													FROM    c2g__codaPeriod__c
													WHERE   Id IN :selectedPeriodList
													ORDER BY c2g__StartDate__c
											  ];

		DOM.Document doc = new DOM.Document();
		dom.XmlNode dataSet;
		dom.XmlNode dataSupplier;
		dom.XmlNode media;
		dom.XmlNode table;
		dom.XmlNode validity;
		dom.XmlNode range;
		dom.XmlNode varLen;
		dom.XmlNode varKey;

		dataSet = doc.createRootElement('DataSet', null, null);

		dataSet.addChildElement('Version', null, null).addTextNode('1.0');
		dataSupplier = dataSet.addChildElement('DataSupplier', null, null);
		media = dataSet.addChildElement('Media', null, null);

		//DataSupplier children
		dataSupplier.addChildElement('Name', null, null).addTextNode('Glaswerk AG');
		dataSupplier.addChildElement('Location', null, null).addTextNode('Singen/Deutschland');
		dataSupplier.addChildElement('Comment', null, null).addTextNode('Test comment');

		//Media children
		media.addChildElement('Name', null, null).addTextNode('CD Nummer 1');

		for (String csvKey: csvMap.keySet()) {
			zip.addFile(csvMap.get(csvKey).Name, csvMap.get(csvKey).Body, null);

			table = media.addChildElement('Table', null, null);
			/*
			* Commented by Stan Shaban due to duplicate '.csv' type in the XML table urls. 
			* URL alredy contains extension type
			*/
			//table.addChildElement('URL', null, null).addTextNode(csvMap.get(csvKey).Name + '.' + csvMap.get(csvKey).Type);
			table.addChildElement('URL', null, null).addTextNode(csvMap.get(csvKey).Name);
			table.addChildElement('Name', null, null).addTextNode(csvKey);
			table.addChildElement('Description', null, null).addTextNode('Test Desc');

			validity = table.addChildElement('Validity', null, null);
			range = validity.addChildElement('Range', null, null);
			range.addChildElement('From', null, null).addTextNode(periodList.size() > 0 ? getDateString(periodList[0].c2g__StartDate__c) : '');
			range.addChildElement('To', null, null).addTextNode(periodList.size() > 0 ? getDateString(periodList[periodList.size() - 1].c2g__EndDate__c) : '');
			validity.addChildElement('Format', null, null).addTextNode('YYYYMMDD');

			table.addChildElement('DecimalSymbol', null, null).addTextNode('.');
			table.addChildElement('DigitGroupingSymbol', null, null).addTextNode(',');

			varLen = table.addChildElement('VariableLength', null, null);
			for (German_Mapping__c gm: allGermanSetMap.values()) {
				if (gm.Name.contains(csvKey)) {
					varKey = varLen.addChildElement('VariablePrimaryKey', null, null);
					varKey.addChildElement('Name', null, null).addTextNode((String)gm.get(languageMap.get(language)));
					varKey.addChildElement(gm.Field_Type__c, null, null); //TODO get field type
				}
			}
		}
		Document xmlDoc = insertDoc(doc.toXmlString(),'Index', 'text/xml', 'xml');
		zip.addFile(xmlDoc.Name, xmlDoc.Body, null);
		//TODO add 'DTD' file to archive

		Blob zipBlob = zip.getZipArchive();
		Document zipDoc = insertDoc('','GermanAuditFile ZIP ' + String.valueOf(Datetime.now()), 'application/zip', 'zip');
		zipDoc.Body = zipBlob;
		insert zipDoc;

		docLink = '/' + zipDoc.Id;
		if(!String.isNotBlank(zipDoc.Id)){
			throw new CustomException('Document has not been generated.');
		}else {
			docLinkPrepared = true;
		}
	}
	private Document generateSuppliers () {
		List<Account> suppliersList = Database.query(getSOQLQuery('Account', 'c2g__CODAAccountsPayableControl__c != null', null));
		Document doc = createDocument('Account', 'Suppliers', 'Suppliers', suppliersList);
		return doc;
	}
	private Document generateSuppliersBookings () {
		List<c2g__codaTransactionLineItem__c> suppliersBookingList = Database.query(getSOQLQuery('c2g__codaTransactionLineItem__c', 'c2g__Account__r.c2g__CODAAccountsPayableControl__c != null  AND c2g__LineType__c = \'Account\' AND c2g__Transaction__r.c2g__Period__c IN :selectedPeriodList', null));
		Document doc = createDocument('c2g__codaTransactionLineItem__c', 'SupplierBooking', 'Suppliers Booking', suppliersBookingList);
		return doc;
	}
	private Document generateCustomers () {
		List<Account> customersList = Database.query(getSOQLQuery('Account', 'c2g__CODAAccountsReceivableControl__c != null', null));
		Document doc = createDocument('Account', 'Customers', 'Customers', customersList);
		return doc;
	}
	private Document generateCustomersBookings () {
		List<c2g__codaTransactionLineItem__c> customersBookingList = Database.query(getSOQLQuery('c2g__codaTransactionLineItem__c', 'c2g__Account__r.c2g__CODAAccountsReceivableControl__c != null AND c2g__LineType__c = \'Account\' AND c2g__Transaction__r.c2g__Period__c IN :selectedPeriodList', null));
		Document doc = createDocument('c2g__codaTransactionLineItem__c', 'CustomerBooking', 'Customers Booking', customersBookingList);
		return doc;
	}
	private Document generateGLA () {
		List<c2g__codaGeneralLedgerAccount__c> glaList = Database.query(getSOQLQuery('c2g__codaGeneralLedgerAccount__c', null, null));
		Document doc = createDocument('c2g__codaGeneralLedgerAccount__c', 'GLAs', 'General Ledger Account', glaList);
		return doc;
	}
	private Document generateGLABookings () {
		List<c2g__codaTransactionLineItem__c> glaBookingList = Database.query(getSOQLQuery('c2g__codaTransactionLineItem__c', 'c2g__Account__c = null AND c2g__Transaction__r.c2g__Period__c IN :selectedPeriodList', null));
		Document doc = createDocument('c2g__codaTransactionLineItem__c', 'GLABooking', 'General Ledger Account Booking', glaBookingList);
		return doc;
	}
	private Document generateSalesTaxCode () {
		getTaxCodeIds();
		List<c2g__codaTaxCode__c> salesTaxCodeList = Database.query(getSOQLQuery('c2g__codaTaxCode__c', 'Id IN :taxCodeIdSet', null));
		Document doc = createDocument('c2g__codaTaxCode__c', 'SalesTaxCode', 'Sales TaxCode', salesTaxCodeList);
		return doc;
	}
	private Document generateVATGroups () {
		List<c2g__codaTaxCode__c> vatGroupsList = Database.query(getSOQLQuery('c2g__codaTaxCode__c', null, null));
		Document doc = createDocument('c2g__codaTaxCode__c', 'VATGroups', 'VAT Groups', vatGroupsList);
		return doc;
	}
	private Document generateVATEntries () {
		List<c2g__codaTransactionLineItem__c> vatEntriesList = Database.query(getSOQLQuery('c2g__codaTransactionLineItem__c', 'c2g__LineType__c = \'Account\' AND c2g__Transaction__r.c2g__Period__c IN :selectedPeriodList', null));
		Document doc = createDocument('c2g__codaTransactionLineItem__c', 'VATEntries', 'VAT Entries', vatEntriesList);
		return doc;
	}
	// Helper methods
	private void getLanguage () {
		languageMap = new Map<String, String>();
		languageMap.put('G', 'German_Column_Name__c');
		languageMap.put('E', 'English_Column_Name__c');
	}
	private void getMapping () {
		mappingMap = new Map<String, List<German_Mapping__c>>();
		allGermanSetMap = German_Mapping__c.getAll();

		List<German_Mapping__c> germanMapList = allGermanSetMap.values();
		List<German_Mapping__c> germanObjMapList;
		Set<String> mapObjectSet = new Set<String>();

		for (German_Mapping__c csMap: germanMapList) {
			mapObjectSet.add(csMap.SF_Object_Name__c);
		}

		for (String objSet: mapObjectSet) {
			germanObjMapList = new List<German_Mapping__c>();
			for (German_Mapping__c csMap: germanMapList) {
				if (String.isNotBlank(csMap.SF_Object_Name__c) && csMap.SF_Object_Name__c.equals(objSet)) {
					germanObjMapList.add(csMap);
				}
			}
			mappingMap.put(objSet, germanObjMapList);
		}
	}
	private void getFolder () {
		folderId = [
						SELECT	Id
						FROM	Folder
						WHERE	Name = 'FinancialForceDocuments'
						LIMIT	1
				   ].Id;
	}
	private String getSOQLQuery (String ObjectName, String whereClause, String lim) {
		Set<String> fieldsSet = new Set<String>();
		for (German_Mapping__c csMap: mappingMap.get(ObjectName)) {
			if (csMap.SF_Field_Name__c != null) {
				fieldsSet.add(csMap.SF_Field_Name__c);
			}
		}
		String fields = String.join(new List<String>(fieldsSet), ',');
		String query =  'SELECT     Id, ' + fields + ' ' +
						'FROM ' +   ObjectName + ' ' +
						(String.isNotBlank(whereClause) ? 'WHERE ' + whereClause : ' ') +
						(String.isNotBlank(lim) ? 'LIMIT ' + lim : ' ');
		return query;
	}
	private Document createDocument (String objectName, String docObject, String docName, List<SObject> objectList) {
		List<German_Mapping__c> mapList = new List<German_Mapping__c>();
		for (German_Mapping__c csMap: mappingMap.get(ObjectName)) {
			if (csMap.Name.contains(docObject)) {
				mapList.add(csMap);
			}
		}
		String docString = '';
		List<String> columnsList = new List<String>();
		List<String> objColumnsList;
		List<String> objRowsList = new List<String>();
		for (German_Mapping__c gm: mapList) {
			if (gm.Name.contains(docObject)) {
				columnsList.add(String.valueOf(gm.get(languageMap.get(language))));
			}
		}
		for (SObject obj: objectList) {
			objColumnsList = new List<String>();
			for (German_Mapping__c gm: mapList) {
				objColumnsList.add(getValue(obj, gm.SF_Field_Name__c, gm.Name));
			}
			objRowsList.add(String.join(objColumnsList, SEPARATOR));
		}
		docString += String.join(columnsList, SEPARATOR) + '\n' + String.join(objRowsList, '\n');
		return insertDoc(docString, docName, 'text/csv', 'csv');
	}
	private Document insertDoc (String docBody, String name, String contType, String type) {
		doc = new Document(
								FolderId = folderId,
								Body = Blob.valueOf(docBody),
								Name = name + '.' + type,
								ContentType = contType,
								Type = type
						  );
		return doc;
	}
	private String getValue (SObject obj, String field, String gmName) {
		String value;
		if (gmName != 'GLABookings7') {
			if (String.isNotBlank(field)) {
				if (field.contains('.')) {
					SObject sobj;
					for (Integer i = 0; i < field.split('\\.').size() - 1; i++) {
						if (i > 0 ? sobj.getSObject(field.split('\\.')[i]) != null : obj.getSObject(field.split('\\.')[i]) != null) {
							sobj = (i > 0 ? sobj.getSObject(field.split('\\.')[i]): obj.getSObject(field.split('\\.')[i]));
						} else {
							sobj = null;
							break;
						}
					}
					value = sobj != null ? String.valueOf(sobj.get(field.split('\\.')[field.split('\\.').size() - 1])) : '';
				} else {
					value = String.valueOf(obj.get(field));
				}
			} else {
				value = '';
			}
		} else {
			if (obj.get(field) != null && (Decimal)obj.get(field) > 0) {
				value = 'false';
			} else {
				value = 'true';
			}
		}

		return value != null ? '"' + value + '"': '""';
	}
	private void getTaxCodeIds () {
		List<AggregateResult> arList =  [
											SELECT  c2g__TaxCode1__c
											FROM    c2g__codaTransactionLineItem__c
											WHERE   c2g__Transaction__r.c2g__Period__c IN :selectedPeriodList
											GROUP BY c2g__TaxCode1__c
											LIMIT   1000
										];
		taxCodeIdSet = new Set<Id>();
		for (AggregateResult ar: arList) {
			taxCodeIdSet.add((Id)ar.get('c2g__TaxCode1__c'));
		}
	}
	private static String getDateString (Date d) {
		return Datetime.newInstance(d.year(), d.month(), d.day()).format('yyyyMMdd');
	}

	private void init(){
		language = 'E';
		docLinkPrepared = false;
		getCompanies();
		if(!companiesList.isEmpty()){
			getPeriods();
		}
	}

	public List<SelectOption> getCompaniesSelectOptions(){
		List<SelectOption> companiesSelectOptions = new List<SelectOption>();
		if(!companiesList.isEmpty()){
			for(c2g__codaCompany__c item : companiesList){
				companiesSelectOptions.add(new SelectOption(item.Id, item.Name));
			}
		}else{
			throw new CustomException('No companies found.');
		}
		return companiesSelectOptions;
	}

	public List<SelectOption> getYearsSelectOptions(){
		periodByYearMap = new Map<String, List<c2g__codaPeriod__c>>();
		List<SelectOption> yearsSelectOptions = new List<SelectOption>();
		System.debug('COMPANY: ' + selectedCompany);
		System.debug('MAP: ' + yearByCompanyMap);
		try{
			if(!yearByCompanyMap.isEmpty() && !yearByCompanyMap.get(selectedCompany).isEmpty()){
				periodByYearMap.putAll(yearByCompanyMap.get(selectedCompany));
				for(String item : periodByYearMap.keySet()){
					yearsSelectOptions.add(new SelectOption(item, item));
				}
			}else{
				throw new CustomException('No years found.');
			}
		}catch(Exception e){
			System.debug('Exception: ' + e.getMessage() + ' ' + e.getStackTraceString());
		}
		return yearsSelectOptions;
	}

	public List<SelectOption> getPeriodsSelectOptions(){
		List<SelectOption> periodsSelectOptions = new List<SelectOption>();
		try{
			if(!periodByYearMap.get(selectedYear).isEmpty()){
				for(c2g__codaPeriod__c item : periodByYearMap.get(selectedYear)){
					periodsSelectOptions.add(new SelectOption(item.Id, item.c2g__StartDate__c.format() + ' - ' + item.c2g__EndDate__c.format()));
				}
			}else{
				throw new CustomException('No periods found.');
			}
		}catch(Exception e){
			System.debug('Exception: ' + e.getMessage() + ' ' + e.getStackTraceString());
		}
		return periodsSelectOptions;
	}

	public List<SelectOption> getLAnguageSelectOptions(){
		List<SelectOption> languageSelectOptions = new List<SelectOption>{
			new SelectOption('E', 'English'),
			new SelectOption('G', 'German')
		};
		return languageSelectOptions;
	}

	private List<c2g__codaCompany__c> getCompanies(){
		companiesList = new List<c2g__codaCompany__c>();
		try{
			companiesList =	[
								SELECT
										Id,
										Name
								FROM	c2g__codaCompany__c
							];
		}catch(Exception e){
			throw new CustomException(e);
		}
		System.debug('LIST OF COMPANIES: ' + companiesList);
		return companiesList;
	}

	private void getPeriods(){
		yearByCompanyMap = new Map<String, Map<String, List<c2g__codaPeriod__c>>>();
		try{
			for(c2g__codaPeriod__c item :	[
												SELECT
														Id,
														c2g__YearName__c,
														c2g__YearName__r.Name,
														c2g__OwnerCompany__c,
														c2g__OwnerCompany__r.Name,
														c2g__StartDate__c,
														c2g__EndDate__c
												FROM	c2g__codaPeriod__c
												WHERE	c2g__Description__c = 'Trading Period'
												AND		c2g__OwnerCompany__c IN:  companiesList
											])
			{
				if(	String.isNotBlank(item.c2g__OwnerCompany__c) &&
					String.isNotBlank(item.c2g__YearName__r.Name)){
					if(yearByCompanyMap.get(item.c2g__OwnerCompany__c) == null){
						yearByCompanyMap.put(item.c2g__OwnerCompany__c, new Map<String, List<c2g__codaPeriod__c>>());
						yearByCompanyMap.get(item.c2g__OwnerCompany__c).put(item.c2g__YearName__r.Name, new List<c2g__codaPeriod__c>{item});
					}else if(yearByCompanyMap.get(item.c2g__OwnerCompany__c) != null){
						if(yearByCompanyMap.get(item.c2g__OwnerCompany__c).get(item.c2g__YearName__r.Name) == null){
							yearByCompanyMap.get(item.c2g__OwnerCompany__c).put(item.c2g__YearName__r.Name, new List<c2g__codaPeriod__c>{item});
						}else{
							yearByCompanyMap.get(item.c2g__OwnerCompany__c).get(item.c2g__YearName__r.Name).add(item);
						}
					}
				}
			}
			selectedCompany = new List<String>(yearByCompanyMap.keySet())[0];
			selectedYear = (new List<String>(yearByCompanyMap.get(selectedCompany).keySet())[0]);
		}catch(Exception e){
			throw new CustomException(e);
		}
		System.debug('LIST OF PERIODS: ' + yearByCompanyMap);
	}

	public class CustomException extends Exception {}
}