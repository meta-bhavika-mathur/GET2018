public with sharing class CreateMexicanFileXMLController  {
	
	public String docLink{get; set;}
    public String fileType{get; set;}
    public Boolean fileTypeIsSelected{get; set;}
    public Boolean enableSelectYear{get; set;}
    public Boolean enableSelectMonth{get; set;}
    public List<SelectOption> fileTypeOptions{get; set;}
    public String periodYear{get; set;}
	public String periodMonth{get; set;}
    public List<String> yearList{get; set;}
	public List<String> monthList{get; set;}
	public List<SelectOption> yearOptions{get; set;}
	public List<SelectOption> monthOptions{get; set;}
    
    private Date startDate;
	private Date endDate;	
    private String fileName;
    
    @TestVisible
    private String xmlFileString;
	private String folderId;	
	private List<c2g__codaGeneralLedgerAccount__c> listOfGlAs; 
	private List<c2g__codaTransaction__c> listOfTransactions;
	private List<Mexican_File_Mapping__mdt> mexicanFileMappingList; 
	private Map<String, List<Mexican_File_Mapping__mdt>> mappingRecordByLabelMap; 
	private Map<String, Mexican_File_Mapping__mdt> mappingRecordByDeveloperNameMap; 
    
    private Map<String, List<c2g__codaPeriod__c>> periodByYearMap;
	
	public CreateMexicanFileXMLController(){
        try{
            initFileTypes();
            getPeriods();
            getFieldMapping();	
            sortFieldMapping(); 
            setGlaRecords(); 
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getLineNumber()));
        }
	}
    
    public void initFileTypes(){
		fileTypeOptions = new List<SelectOption>();
		fileTypeOptions.add(new SelectOption('report1', 'CoA'));
		fileTypeOptions.add(new SelectOption('report2', 'TB'));
	}
	
	public void createXML(){
        try{
            	if(fileTypeIsSelected){
					if(fileType == 'report1'){
						createCoAReport();
					}else if(fileType =='report2'){
						createTBReport();
					}
				}          
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getLineNumber()));
        }      
	}
	
	private void createTBReport(){
		try{		
            	if(String.isBlank(periodYear)){
					throw new CustomException('Choose Year!');
				}else{ 
                    
					String balanzaCtasStr = getbalanzaCtasString(periodYear);
                    xmlFileString = '';
                    xmlFileString += '<?xml version="1.0" encoding="UTF-8"?>';
                    xmlFileString += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('balanza0').Tag_Name__c,
                                           balanzaCtasStr, getMapOfAttributesAndValByLabel(mappingRecordByDeveloperNameMap.get('balanza0').MasterLabel, null));                        
                    
                    fileName = 'Mexican Audit TB Report XML';
                	docLink = ReportsGenerationUtility.generateDocAndAddToFolder(fileName, xmlFileString);
                }
		}catch(Exception e){
			ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
		}
	}
    
    private String getbalanzaCtasString(String periodYear){    
    	String str = '';   

        try{
            String objectName = mappingRecordByDeveloperNameMap.get('balanzaCtas1').Object_Name__c;
            Set<String> fieldNameSet = getFieldNameSet(mappingRecordByDeveloperNameMap.get('balanzaCtas1').MasterLabel);
            String whereCondition = 'c2g__LineType__c = \'Account\' AND c2g__Transaction__r.c2g__Period__r.c2g__YearName__r.Name = :periodYear';
            ReportsGenerationUtility.periodYear = periodYear;            
            List<c2g__codaTransactionLineItem__c> recordsList;          
            recordsList = (List<c2g__codaTransactionLineItem__c>)ReportsGenerationUtility.getSFDCRecordsWithSubQuery(objectName,
                                                                 fieldNameSet, whereCondition, false, null);		    
   
            Map<String, String> mapOfReportingCodeSaldoIni = new Map<String, String>();
            Map<String, String> mapOfReportingCodeDebe = new Map<String, String>();
            Map<String, String> mapOfReportingCodeHaber = new Map<String, String>();
            Map<String, String> mapOfReportingCodeSaldoFin = new Map<String, String>();
            Set<String> setOfGLAReportingCodes = new Set<String>();
    
            if(recordsList != null && !recordsList.isEmpty()){
                for(c2g__codaTransactionLineItem__c transLineItem : recordsList){
                    
                    String repCode = ReportsGenerationUtility.getValue(transLineItem, mappingRecordByDeveloperNameMap.get('balanzaCtas1').SFDC_Field_Name__c);
                    String transactionPeriodNumber = ReportsGenerationUtility.getValue(transLineItem, mappingRecordByDeveloperNameMap.get('balanzaCtas4').SFDC_Field_Name__c);
                    Decimal lineItemHomeValue = Decimal.valueOf(ReportsGenerationUtility.getValue(transLineItem, mappingRecordByDeveloperNameMap.get('balanzaCtas2').SFDC_Field_Name__c));
                        
                    if(repCode != null && repCode != '')
                        setOfGLAReportingCodes.add(repCode);
                        
                   if(transactionPeriodNumber == '000'){  	                      
                        Decimal updatedSaldoIni = (mapOfReportingCodeSaldoIni.get(repCode) != null) ? 
                                                   Decimal.valueOf(mapOfReportingCodeSaldoIni.get(repCode)) + lineItemHomeValue : 
                                                   lineItemHomeValue;
                        mapOfReportingCodeSaldoIni.put(repCode, String.valueOf(updatedSaldoIni));
                   }else if(transactionPeriodNumber == '101'){
                        Decimal updatedSaldoFin = (mapOfReportingCodeSaldoFin.get(repCode) != null) ? 
                                                   Decimal.valueOf(mapOfReportingCodeSaldoFin.get(repCode)) + lineItemHomeValue : 
                                                   lineItemHomeValue;
                        mapOfReportingCodeSaldoFin.put(repCode, String.valueOf(updatedSaldoFin));   
                   }else{                      
                            if(transLineItem.c2g__HomeValue__c >= 0){	
                                Decimal updatedDebe = (mapOfReportingCodeDebe.get(repCode) != null) ? 
                                                        Decimal.valueOf(mapOfReportingCodeDebe.get(repCode)) + lineItemHomeValue : 
                                                        lineItemHomeValue;
                                mapOfReportingCodeDebe.put(repCode, String.valueOf(updatedDebe));                 		
                            }else{
                                        Decimal updatedHaber = (mapOfReportingCodeHaber.get(repCode) != null) ? 
                                                                Decimal.valueOf(mapOfReportingCodeHaber.get(repCode)) + lineItemHomeValue : 
                                                                lineItemHomeValue;
                                        mapOfReportingCodeHaber.put(repCode, String.valueOf(updatedHaber));     		
                                }
                        }                	
                    }				
                }
            
               String valueOfSaldoIni, valueOfDebe, valueOfHaber, valueOfSaldoFin;
               Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
      
               for(String repCode : setOfGLAReportingCodes){		
        
                        valueOfSaldoIni = mapOfReportingCodeSaldoIni.get(repCode) != null ? mapOfReportingCodeSaldoIni.get(repCode) : '0.0';
                        valueOfDebe = mapOfReportingCodeDebe.get(repCode) != null ? mapOfReportingCodeDebe.get(repCode) : '0.0';
                        valueOfHaber = mapOfReportingCodeHaber.get(repCode) != null ? mapOfReportingCodeHaber.get(repCode) : '0.0';
                        valueOfSaldoFin = mapOfReportingCodeSaldoFin.get(repCode) != null ? mapOfReportingCodeSaldoFin.get(repCode) : '0.0';	       	
        
                        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanzaCtas5').Attribute_Name__c, valueOfSaldoFin);
                        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanzaCtas4').Attribute_Name__c, valueOfHaber);
                        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanzaCtas3').Attribute_Name__c, valueOfDebe);
                        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanzaCtas2').Attribute_Name__c, valueOfSaldoIni);
                        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanzaCtas1').Attribute_Name__c, repCode);
                        
                        str += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('balanzaCtas0').Tag_Name__c, 
                               '', mapOfAttributesAndValues);	     	    			
               } 
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 'Error while craeting getbalanzaCtasString ' + 
                                                       e.getMessage() + ' ' + e.getStackTraceString()));   
        }        
        return str;
    }
        
    @TestVisible
	private Map<String, String> getMapOfAttributesAndValByLabel(String label, sObject obj){		
		Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
		c2g__codaGeneralLedgerAccount__c glaObj = null; 
		c2g__codaTransaction__c transactionObj = null;
		                          
        if(obj != null){
        	if(obj.getsObjectType() == c2g__codaGeneralLedgerAccount__c.sObjectType)
           		glaObj = (c2g__codaGeneralLedgerAccount__c)obj;  
           	else if(obj.getsObjectType() == c2g__codaTransaction__c.sObjectType){
           		transactionObj = (c2g__codaTransaction__c)obj;
           	}     
        }
        
        if(label == 'Ctas'){
        	mapOfAttributesAndValues = getCatasAttributes(glaObj);
        }else if(label == 'Catalogo'){
        	mapOfAttributesAndValues = getCatalogoAttributes();
        }else if(label == 'balanza'){
        	mapOfAttributesAndValues = getBalanzaAttributes();
        }     	
        return mapOfAttributesAndValues;
	}
    
    private Map<String, String> getBalanzaAttributes(){
    	
    	Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
        
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza8').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza8').Attribute_Value__c);
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza7').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza7').Attribute_Value__c);
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza6').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza6').Attribute_Value__c);
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza5').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza5').Attribute_Value__c);
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza4').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza4').Attribute_Value__c);
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza3').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza3').Attribute_Value__c);
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza2').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza2').Attribute_Value__c);
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('balanza1').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('balanza1').Attribute_Value__c);
  								 
     	return mapOfAttributesAndValues;					 						 					   
    }
	private void createCoAReport(){
		try{		
            	if(String.isBlank(periodYear) || String.isBlank(periodMonth)){
					throw new CustomException('Choose Year and Month');
				}else{ 
                	String catalogocuentasCtasStr = getcatalogocuentasCtasString();

                    xmlFileString = '';
                    xmlFileString += '<?xml version="1.0" encoding="UTF-8"?>';
                    xmlFileString += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo0').Tag_Name__c,
                                         catalogocuentasCtasStr, getMapOfAttributesAndValByLabel('Catalogo', null));                    
                    
                    fileName = 'Mexican Audit CoA Report XML';
                	docLink = ReportsGenerationUtility.generateDocAndAddToFolder(fileName, xmlFileString);
                }
		}catch(Exception e){
			ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
		}
	}
	  
    private Map<String, String> getCatasAttributes(c2g__codaGeneralLedgerAccount__c glaObj){
    	
        String subCta, reportingCode, reportName;
    	Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
    	String valueOfNatur = 'D';
    	 
    	reportingCode = ReportsGenerationUtility.getValue(glaObj, mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas1').SFDC_Field_Name__c);
    	reportName = ReportsGenerationUtility.getValue(glaObj, mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas3').SFDC_Field_Name__c);
    	List<String> reportingCodeLevels = reportingCode.split('\\.');
      
    	subCta = (reportingCodeLevels.size() > 1) ? reportingCodeLevels[0] : reportingCode;

		List<String> trialBalanceOneOptions = new List<String>{'Equity', 'Liabilities', 'Income'};
    	List<String> trialBalanceTwoOptions = new List<String>{'Property, Plant And Equipment', 'Intangible Assets'};
    	
        List<String> trialBalanceThreeOptions = new List<String>{'Accumulated depreciation: Land And Buildings',
    	'Accumulated depreciation: Machinery And Equipment', 'Accumulated depreciation: IT equipment', 
    	'Accumulated depreciation: Vehicles', 'Accumulated depreciation: Furniture and office equipment', 
    	'Accumulated depreciation: Construction in progress and capitalized FA expenditures', 'Accumulated amortisation: Website',
    	'Accumulated amortisation: Software and licenses', 'Accumulated amortisation: Capitalized expenditures - accreditation programs', 
    	'Accumulated amortisation: Capitalized expenditures - software and other'};
    	
    	String glaTrialBalance1 = glaObj.c2g__TrialBalance1__c;
    	String glaTrialBalance2 = glaObj.c2g__TrialBalance2__c;
    	String glaTrialBalance3 = glaObj.c2g__TrialBalance3__c;
    	
    	if(trialBalanceOneOptions.contains(glaTrialBalance1)){
    		valueOfNatur = 'A';
    	}else if(glaTrialBalance1 == 'Assets'){
    		if(trialBalanceTwoOptions.contains(glaTrialBalance2) && trialBalanceThreeOptions.contains(glaTrialBalance3)){
    			valueOfNatur = 'A';
    		}
    	}
    	 	
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas1').Attribute_Name__c,
    								 reportingCode);
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas2').Attribute_Name__c,
    								 reportingCode);		
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas3').Attribute_Name__c,
    								(reportName.split('\\-').size() > 1) ? reportName.split('\\-')[1] : reportName.split('\\-')[0]);	
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas4').Attribute_Name__c,
    								 subCta);	
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas5').Attribute_Name__c,
    								(reportingCodeLevels.size() > 1) ? String.valueOf(reportingCodeLevels.size()) : '1');	
        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas6').Attribute_Name__c,
   								 	 valueOfNatur);	    								 
    	return mapOfAttributesAndValues;					 						 					   
    }
    
     private Map<String, String> getCatalogoAttributes(){
    	
    	Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
         
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo1').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo1').Attribute_Value__c);
		mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo2').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo2').Attribute_Value__c);
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo3').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo3').Attribute_Value__c);
		mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo4').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo4').Attribute_Value__c);
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo5').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo5').Attribute_Value__c);
    	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo6').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo6').Attribute_Value__c);
		mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo7').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('catalogocuentas_Catalogo7').Attribute_Value__c);
    								 
     	return mapOfAttributesAndValues;					 						 					     
    }

     private String getcatalogocuentasCtasString(){
        String str = '';
        
        if(listOfGlAs != null && !listOfGlAs.isEmpty()){
            for(c2g__codaGeneralLedgerAccount__c gla : listOfGlAs){
                str += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas0').Tag_Name__c, '',
                                    		 getMapOfAttributesAndValByLabel('Ctas', gla));
            }
        }
        return str;
    }
   
    private String addNodeWithAttributes(String tagName, String value, Map<String, String> mapOfAttributesAndValue){ 	
    	String str = '<' +  tagName;       
        
        for(String key : mapOfAttributesAndValue.keySet()){
    		str += ' ' + key + '="' + mapOfAttributesAndValue.get(key) + '"';
    	}  
    	str += '>' + value + '</' + tagName + '>'; 
        return str;
   }
	
	private void setGlaRecords(){		        
        Set<String> additionalFields = new Set<String>();
        additionalFields.add('c2g__TrialBalance1__c');
        additionalFields.add('c2g__TrialBalance2__c');
        additionalFields.add('c2g__TrialBalance3__c');
		
		listOfGlAs = (List<c2g__codaGeneralLedgerAccount__c>)getRecords('c2g__codaGeneralLedgerAccount__c', 
                                             mappingRecordByDeveloperNameMap.get('catalogocuentas_Ctas0').MasterLabel, additionalFields, '');
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

	private List<sObject> getRecords(String objName, String fieldNameSetSrt, Set<String> additionalFields, String whereCondition){
        Set<String> fieldNameSet = new Set<String>();
		fieldNameSet = getFieldNameSet(fieldNameSetSrt);
		if(additionalFields != null && !additionalFields.isEmpty()){
			fieldNameSet.addAll(additionalFields);
		}
		List<sObject> sObjectList = ReportsGenerationUtility.getSFDCRecords(objName, fieldNameSet, whereCondition);     
		return sObjectList;
	}
    
    public void getPeriods(){
        ReportsGenerationUtility.getPeriods();
        yearOptions = ReportsGenerationUtility.yearOptions;
        monthOptions = ReportsGenerationUtility.monthOptions;
	}

    public void selectFileType(){
		fileTypeIsSelected = true;
		enableSelectYear = true;

		if(fileType == 'report1'){
			enableSelectMonth = true;
		}else if(fileType =='report2'){
			enableSelectMonth = false;
		}  
	}

	public class CustomException extends Exception{}	
}