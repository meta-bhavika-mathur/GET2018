public with sharing class SpanishAuditFileController  {
	/*private String xmlString { get; set; }
	private List<CTCM__SpanishAuditFileMapping__mdt> mappingList;
	private Map<String, String> fieldsBySobjectMap; 
	Map<Integer, CTCM__SpanishAuditFileMapping__mdt> spanishAuditFileMappingMap;

	public SpanishAuditFile(){
		doInit();
	}

	private String addNode(String tagName, String parameters, String value){
		return '<' + tagName + ' ' + parameters + '>' + value + '</' + tagName '>';
	}

	private String addNode(String tagName, String value){
		return '<' + tagName + '>' + value + '</' + tagName '>';
	}

	private void generateInvoiceIssued(){
		
		xmlString += addNode(mappingList('InvoiceIssued1').CTCM__XMLTag__c, mappingList('InvoiceIssued1').CTCM__XMLTag__c, 
			mappingList('InvoiceIssued1').CTCM__XMLTag__c, );
	}

	private void generateInvoiceReceived(){
		
	}

	private void sortFieldsBySobjectMap(CTCM__SpanishAuditFileMapping__mdt mapping){
		fieldsBySobjectMap = new Map<String, String>();
		for(CTCM__SpanishAuditFileMapping__mdt item : mapping){
			fieldsBySobjectMap.put(item.CTCM__SFDCObject__c, item.CTCM__SFDCField__c);
		}
	}

	private void prepareMapping(){
		mappingList = [
			SELECT 	
					Id,
					MasterLabel,
					DeveloperName,
					CTCM__DefaultValue__c,
					CTCM__Parameters__c,
					CTCM__SFDCField__c,
					CTCM__SFDCObject__c,
					CTCM__XMLTag__c
			FROM	CTCM__SpanishAuditFileMapping__mdt
		];

		sortFieldsBySobjectMap(mappingList);

		spanishAuditFileMappingMap = new Map<Integer, CTCM__SpanishAuditFileMapping__mdt>();
		for(CTCM__SpanishAuditFileMapping__mdt item : mappingList){
			spanishAuditFileMappingMap.put(MasterLabel, item);
		}

	}

	private List<CTCM__SpanishAuditFileMapping__mdt> sortValuesInMapping(List<CTCM__SpanishAuditFileMapping__mdt> mappingUnsortedList){
		List<CTCM__SpanishAuditFileMapping__mdt> mappingSortedList = new List<CTCM__SpanishAuditFileMapping__mdt>();
		for(CTCM__SpanishAuditFileMapping__mdt item : mappingUnsortedList){
			
		}

		return mappingSortedList;
	}

	private void doInit(){
		prepareMapping();
	}

	private String getValue (SObject obj, String field) {
		String value;
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

		return value != null ? '"' + value + '"': '""';
	}*/
}