public with sharing class MexicanFileClass  {
	
	public String docLink{get; set;}
	private String xmlFileString;
	private String folderId;
	
	private List<c2g__codaGeneralLedgerAccount__c> listOfGlAs; 
	private List<Mexican_File_Mapping__mdt> mexicanFileMappingList; 
	private Map<String, List<Mexican_File_Mapping__mdt>> mappingRecordByLabelMap; 
	private Map<String, Mexican_File_Mapping__mdt> mappingRecordByDeveloperNameMap; 
	
	public MexicanFileClass(){
		getFieldMapping();	
        sortFieldMapping(); 
        setGlaRecords(); 
	}
	
	public void createXML(){
        try{
            createMexFile(); 
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getLineNumber()));
        }      
	}
	
	private void createMexFile(){
		try{		
                String catalogocuentasCtasStr = getcatalogocuentasCtasString();

				xmlFileString = '';
				xmlFileString += '<?xml version="1.0" encoding="UTF-8"?>';
                xmlFileString += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo0').Tag_Name__c,
                                     catalogocuentasCtasStr, mappingRecordByLabelMap.get('Catalogo'), null);
				
				folderId = [
						SELECT	Id
						FROM	Folder
						WHERE	Name = 'FinancialForceDocuments'
						LIMIT	1
				].Id;
            
				String docId = createDoc('Mexican Audit XML File ' + String.valueOf(System.now()), xmlFileString, folderId);
				docLink = '/servlet/servlet.FileDownload?file=' + docId;			 
		}catch(Exception e){
			ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
		}
	}
    
    private String getcatalogocuentasCtasString(){
        String str = '' ;
        
        if(listOfGlAs != null && !listOfGlAs.isEmpty()){
            for(c2g__codaGeneralLedgerAccount__c gla : listOfGlAs){
                str += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas0').Tag_Name__c, '',
                                    		 mappingRecordByLabelMap.get('Ctas'), gla);
            }
        }
        return str;
    }
    
    private String addNodeWithAttributes(String tagName, String value, List<Mexican_File_Mapping__mdt> listOfMDTMappings,
                                         sObject obj){
        String str = '<' +  tagName;
        
        c2g__codaGeneralLedgerAccount__c objname = null;                           
        if(obj != null){
            objname = (c2g__codaGeneralLedgerAccount__c)obj; 
        }
    
        for(Mexican_File_Mapping__mdt mdt : listOfMDTMappings){
            if(mdt.Attribute_Name__c != null){
                str += ' ' + mdt.Attribute_Name__c + '="';
                if(mdt.Attribute_Value__c != null){
                   str += mdt.Attribute_Value__c + '"';                 
                }else if(mdt.SFDC_Field_Name__c != null){
                  str += getValue(objname, mdt.SFDC_Field_Name__c) + '"';                  
                }else{
                   str += '"';                 
                }
            } 	            
        }
        str += '>' + value + '</' + tagName + '>';                               
        return str;
    }
	
	private void setGlaRecords(){		
        
        Set<String> addFields = new Set<String>();
		listOfGlAs = (List<c2g__codaGeneralLedgerAccount__c>)getGlaccts('c2g__codaGeneralLedgerAccount__c', 
                                             mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas0').MasterLabel, addFields, '');
    }
	
	private static String createDoc (String docName, String docBody, String folderId) {
		Document doc = new Document();
		doc.FolderId = folderId;
		doc.Body = Blob.valueOf(docBody);
		doc.Name = docName;
		doc.ContentType = 'text/xml';
		doc.Type = 'xml';
		insert doc;     
        return doc.Id;
	}
	
	public void getFieldMapping(){

		mexicanFileMappingList = new List<Mexican_File_Mapping__mdt>();
		if(mexicanFileMappingList.isEmpty()){
			mexicanFileMappingList = [
				SELECT	
						Id,
						DeveloperName,
						MasterLabel,
						Object_Name__c,
						SFDC_Field_Name__c,
						Tag_Name__c,
						Attribute_Name__c ,
						Attribute_Value__c 
				FROM	Mexican_File_Mapping__mdt
				LIMIT	1000
			];
		}
	}
    
    private void sortFieldMapping(){
		mappingRecordByLabelMap = new Map<String, List<Mexican_File_Mapping__mdt>>();
		mappingRecordByDeveloperNameMap = new Map<String, Mexican_File_Mapping__mdt>();
		
        if(!mexicanFileMappingList.isEmpty()){
			for(Mexican_File_Mapping__mdt item : mexicanFileMappingList){
				if(mappingRecordByLabelMap.get(item.MasterLabel) == null){
					mappingRecordByLabelMap.put(item.MasterLabel, new List<Mexican_File_Mapping__mdt>{item});
				}else{
					mappingRecordByLabelMap.get(item.MasterLabel).add(item);
				}               
				mappingRecordByDeveloperNameMap.put(item.DeveloperName, item);
			}
		}
	}

	private Set<String> getFieldNameSet(String parentTagName){
		Set<String> fieldNameSet = new Set<String>();
		List<Mexican_File_Mapping__mdt> mappingRecordList = mappingRecordByLabelMap.get(parentTagName);
 
        for(Mexican_File_Mapping__mdt item : mappingRecordList){
			if(String.isNotBlank(item.SFDC_Field_Name__c)){
				fieldNameSet.add(item.SFDC_Field_Name__c);
			}
		}
		return fieldNameSet;
	}
	
	private List<SObject> getSFDCRecords(String objName, Set<String> fieldNameSet){
        if(!fieldNameSet.isEmpty()){
			String query =	'SELECT ' + String.join(new List<String>(fieldNameSet), ',') + 
							' FROM ' + objName + ' LIMIT 5'; 
			List<SObject> recordsList = new List<SObject>();
			recordsList = Database.query(query);

			return recordsList;
		}else{
			return null;
		}
	}

	private List<sObject> getGlaccts(String objName, String fieldNameSetSrt, Set<String> additionalFields, String whereCondition){

        Set<String> fieldNameSet = new Set<String>();
		fieldNameSet = getFieldNameSet(fieldNameSetSrt);
		if(additionalFields != null && !additionalFields.isEmpty()){
			fieldNameSet.addAll(additionalFields);
		}
		List<sObject> sObjectList = getSFDCRecords(objName, fieldNameSet);
        
		return sObjectList;
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
		if(value != null && value.contains('&')){
			value = value.replaceAll('&', '&amp;');
		}
		return value;
	}

	public class CustomException extends Exception{}

		
}