public with sharing class MexicanAuditFile  {
	public String documentLink = '';
	public String xmlFileBody = '';

	/*private static List<CTCM__Mexican_GLA_Mapping__mdt> fieldMappingValuesList {
		get{
			return	[
				SELECT	Id, 
						MasterLabel,
						CTCM__Default_Value__c,
						CTCM__Level__c,
						CTCM__Parameter__c,
						CTCM__sObject_Field__c,
						CTCM__sObject_Name__c,
						CTCM__XML_Tag_Name__c,
						CTCM__Sequence__c,
						CTCM__Parent_Tag_Name__c
				FROM	CTCM__Mexican_GLA_Mapping__mdt
				WHERE	MasterLabel = "Mexican"
				LIMIT	1000	
			];	
		}
		set;
	}

	private Map<String, String> parametersByTagMap;
	private Map<Integer, String> tagByLevelMap;
	private Map<String, String> childTagByParentTagMap;

	private Integer levelsCount;

	public void doInit(){
		tagByLevelMap = new Map<Integer, String>();
		parametersByTagMap = new Map<String, String>();
		childTagByParentTagMap = new Map<String, String>();
		for(CTCM__Mexican_GLA_Mapping__mdt item : fieldMappingValuesList){
			tagByLevelMap.put(item.CTCM__Level__c, item.CTCM__XML_Tag_Name__c);
			parametersByTagMap.put(item.CTCM__XML_Tag_Name__c, item.CTCM__Parameter__c);
			childTagByParentTagMap.put(item.CTCM__Parent_Tag_Name__c, item.CTCM__XML_Tag_Name__c);
		}
		levelsCount = tagByLevelMap.keySet().size();
	}

	public void generateXML(){
		String childNodes = '';
		for(Integer i = 0; i < 5; i++){
			childNodes += addChildNode(childTagByParentTagMap.get());
		}
		xmlFileBody += 
	}

	private String getValue (SObject obj, String field, String gmName) {
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
	}

	private void createDocument(String docBody, String docName, String docCreationDate){
		String folderId =	[
			SELECT	
					Id
			FROM	Folder
			WHERE	Name = 'FinancialForceDocuments'
			LIMIT	1
		].Id;

		Document doc = new Document();
		doc.FolderId = folderId;
		doc.Body = Blob.valueOf(docBody);
		doc.Name = docName + ' ' + docCreationDate;
		doc.ContentType = 'text/xml';
		doc.Type = 'xml';
		insert doc;
		documentLink = '/' + doc.Id;
	}

	private String addParentNode(String tagName, Map<String, String> attributeValueByName, String value){
		try{
			String attributes = '';
			for(String item : attributeValueByName.keySet()){
				attributes += ' ' + item + '="' + attributeValueByName.get(item) + '"';
			}
		}catch(Exception e){
			throw new CustomException('Exception Handled: ' + e.getMessage() + ' Stack Trace: ' + e.getStackTraceString());	
		}
		return '<' + tagName + '' + attributes + '>' + value + '</' + tagName + '>';
	}

	private String addChildNode(String tagName, Map<String, String> attributeValueByName){
		try{
			String attributes = '';
			for(String item : attributeValueByName.keySet()){
				attributes += ' ' + item + '="' + attributeValueByName.get(item) + '"';
			}
		}catch(Exception e){
			throw new CustomException('Exception Handled: ' + e.getMessage() + ' Stack Trace: ' + e.getStackTraceString());	
		}
		return '<' + tagName + '' + attributes + '/>';
	}*/

	public class CustomException extends Exception{}

}