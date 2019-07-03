public class GenerateLatvianAuditFileController{
    
    private List<Latvian_Audit_File_Mappings__mdt> mappingList;
	private Map<String, List<Latvian_Audit_File_Mappings__mdt>> mappingRecordByLabelMap;
	private Map<String, Latvian_Audit_File_Mappings__mdt> mappingRecordByDeveloperNameMap;    
    private String xmlFileString;
	private String folderId;

    public String docLink{get; set;}    
    public Date startDate{get; set;}
	public Date endDate{get; set;}

    final String CUSTOM_EXCEPTION_MESSAGE = 'Choose Year and Month';
    final String VAT_REPORT_NAME = 'Latvian Audit File';
    
    public String periodYear{get; set;}
	public String periodMonth{get; set;}
    public List<String> yearList{get; set;}
	public List<String> monthList{get; set;}
	public List<SelectOption> yearOptions{get; set;}
	public List<SelectOption> monthOptions{get; set;}
	
	private List<c2g__codaTransaction__c> listOfTransactions;
    private Set<transactionPurInvWrapper> listOfTrnsctnPurchaseInv = new Set<transactionPurInvWrapper>();
    @TestVisible
    private Set<transactionSalesInvWrapper> listOfTrnsctnSalesInv = new Set<transactionSalesInvWrapper>();
    
    public GenerateLatvianAuditFileController(){
        try{
            getPeriods();
            getFieldMapping();
            sortFieldMapping(mappingList);      
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
        }
	}
   
    public void createXml(){
        try{	  
        		if(String.isBlank(periodYear) || String.isBlank(periodMonth)){
					throw new CustomException(CUSTOM_EXCEPTION_MESSAGE);
				}else{  	   
	            	getTransactions(); 
	            	String valueOfDokPVNv5 = getDokPVNv5TagBody();
					xmlFileString = '';
					xmlFileString += '<?xml version="1.0" encoding="windows-1257" ?>';
	            	xmlFileString += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('DokPVNv50').Tag_Name__c,
	                                  valueOfDokPVNv5, 
	                                  getMapOfAttributesAndValByLabel(mappingRecordByDeveloperNameMap.get('DokPVNv50').MasterLabel, null));            	
	                docLink = ReportsGenerationUtility.generateDocAndAddToFolder(VAT_REPORT_NAME, xmlFileString); 
        		}          
        }catch(Exception e){
            	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
        }
    }
    
    private String getDokPVNv5TagBody(){
    	String str = '';
    	
    	try{
    		String precizejumsStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv52').Tag_Name__c, 
                                        mappingRecordByDeveloperNameMap.get('DokPVNv52').Default_Value__c);
	    	String parskGadsStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv53').Tag_Name__c, periodYear);
	    	String parskMenStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv54').Tag_Name__c, periodMonth);
	    	String mmrKodsStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv55').Tag_Name__c, 
	                                        mappingRecordByDeveloperNameMap.get('DokPVNv55').Default_Value__c);
	    	String talrunisStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv56').Tag_Name__c, 
	                                        mappingRecordByDeveloperNameMap.get('DokPVNv56').Default_Value__c);
	    	str = precizejumsStr + parskGadsStr + parskMenStr + mmrKodsStr + talrunisStr;
			str += getPVN();
			str += getPVN1_First();
			str += getPVN1_Second();		
			str += getPVN1_Third();	
			str += getPVN7I();    		
    	}catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
        }  		
        return str;
    }
    
    private String getPVN(){
    	String pvnStr = '';
    	String pvnVal = '';
    	String devName = '';
    	
    	try{
    		for(Integer i=1; i<=32; i++){
	    		devName = 'PVN' + i;
	    		pvnVal += addNode(mappingRecordByDeveloperNameMap.get(devName).Tag_Name__c, '0.0');
    		}
    		pvnStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv57').Tag_Name__c, pvnVal);
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}  	
    	return pvnStr;
    }
    
    private String getPVN1_First(){
    	String pvn1IStr = '';
    	
    	try{
    		String pvn1IStrBody = getpvn1IStrBody();
    		pvn1IStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv58').Tag_Name__c, pvn1IStrBody); 
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}   	  	
    	return pvn1IStr;
    }
    
    // To get information of purchases where account country is Latvia(LV)-(domestic purchases)
    private String getpvn1IStrBody(){
    	String pvnIStrBody = '';
    	String pvnIStrBodyR = '';
    	String attribute = '';
    	String fieldName = '';
    	String fieldVal = '';
        final String ACC_COUNTRY = 'LV';// To be set as Latvia
        try{
        	if(listOfTrnsctnPurchaseInv != null){
	            for(transactionPurInvWrapper trnPurchase : listOfTrnsctnPurchaseInv){
                    if(trnPurchase.accRegCountry != '' && trnPurchase.accRegCountry == ACC_COUNTRY){
                       pvnIStrBodyR = addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R1').Tag_Name__c, trnPurchase.accRegCountry) +
	                    			  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R2').Tag_Name__c, trnPurchase.accVATRegNo) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R3').Tag_Name__c, trnPurchase.accName) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R4').Tag_Name__c, trnPurchase.transType) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R5').Tag_Name__c, trnPurchase.invTotal) +
                        			  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R6').Tag_Name__c, trnPurchase.taxTotal) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R7').Tag_Name__c, trnPurchase.transDocType) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R8').Tag_Name__c, trnPurchase.transDocNum) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1I_R9').Tag_Name__c, trnPurchase.transDate); 	                                           
	        			pvnIStrBody += addNode(mappingRecordByDeveloperNameMap.get('PVN1I_1').Tag_Name__c, pvnIStrBodyR); 
                    }	            	
	            }
        	}
        }catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}      
    	return pvnIStrBody;
    }
   
    private String getPVN1_Second(){   	
    	String pvn1IIStr = '';
    	String pvn1IIStrBody = '';
    	
    	try{
    		pvn1IIStrBody = getpvn1IIStrBody();
    		pvn1IIStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv59').Tag_Name__c, pvn1IIStrBody); 
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}   	  	
    	return pvn1IIStr;
    }
   
    //Payable Invoice/Credit Notes with account country is not Latvia
    private String getpvn1IIStrBody(){   	
    	String pvn1IIStr = '';
    	String pvn1IIStrBody = '';
    	final String ACC_COUNTRY = 'LV';// To be set as Latvia
        final String darVeidsTagVal = 'P';
     	try{
        	if(listOfTrnsctnPurchaseInv != null){
	             for(transactionPurInvWrapper trnPurchase : listOfTrnsctnPurchaseInv){
                    if(trnPurchase.accRegCountry != '' && trnPurchase.accRegCountry != ACC_COUNTRY){
                       pvn1IIStr = addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R1').Tag_Name__c, trnPurchase.accRegCountry) +
	                    			  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R2').Tag_Name__c, trnPurchase.accVATRegNo) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R3').Tag_Name__c, trnPurchase.accName) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R4').Tag_Name__c, darVeidsTagVal) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R5').Tag_Name__c, trnPurchase.invTotal) +
                        			  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R6').Tag_Name__c, trnPurchase.taxTotal) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R7').Tag_Name__c, '') +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R8').Tag_Name__c, trnPurchase.invTotal) +
                           			  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R9').Tag_Name__c, trnPurchase.transDocNum) +
	                                  addNode(mappingRecordByDeveloperNameMap.get('PVN1II_R10').Tag_Name__c, trnPurchase.transDate); 	                                           
	        			pvn1IIStrBody += addNode(mappingRecordByDeveloperNameMap.get('PVN1II_1').Tag_Name__c, pvn1IIStr); 
                    }	            	
	            }
        	}
        }catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}  	 
    	return pvn1IIStrBody;
    }
     
    private String getPVN1_Third(){
    	String pvn1IIIStr = '';   	
    	try{
    		String pvn1IIIStrBody = getpvn1IIIStrBody();   	
    		pvn1IIIStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv510').Tag_Name__c, pvn1IIIStrBody);  
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}   	
    	return pvn1IIIStr;
    }
    
    // To set information of sales to Latvian customers
    private String getpvn1IIIStrBody(){
    	String pvnIIIStrBody = '';
    	String pvnIIIStrBodyR = '';
    	String attribute = '';
    	String fieldName = '';
    	String fieldVal = '';
    	
    	try{
    		if(listOfTrnsctnSalesInv != null){
	            for(transactionSalesInvWrapper trnsctNSales : listOfTrnsctnSalesInv){
	            	pvnIIIStrBodyR = addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R1').Tag_Name__c, trnsctNSales.accRegCountry) +
	                    			 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R2').Tag_Name__c, trnsctNSales.accVATRegNo) +
	                                 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R3').Tag_Name__c, trnsctNSales.accName) +
	                                 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R4').Tag_Name__c, '') +
	                                 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R5').Tag_Name__c, trnsctNSales.invTotal) +
	                                 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R6').Tag_Name__c, trnsctNSales.taxTotal) +
	                                 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R7').Tag_Name__c, '') +
	                                 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R8').Tag_Name__c, trnsctNSales.transDocNum) +
	                                 addNode(mappingRecordByDeveloperNameMap.get('PVN1III_R9').Tag_Name__c, trnsctNSales.transDate); 
	                                           
	        		pvnIIIStrBody += addNode(mappingRecordByDeveloperNameMap.get('PVN1III_1').Tag_Name__c, pvnIIIStrBodyR);
	            }
        	}
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}       
        return pvnIIIStrBody;
    }
    
    private String getPVN7I(){
    	String pvn7IStr = '';   	
    	try{
    		pvn7IStr = addNode(mappingRecordByDeveloperNameMap.get('DokPVNv511').Tag_Name__c, ' '); 
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	} 
    	return pvn7IStr;
    }
      
    private Map<String, String> getDokPVNv5TAgAttributes(){
    	Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
        
        try{
        	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('DokPVNv51').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('DokPVNv51').Attribute_Value__c);
	        mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('DokPVNv50').Attribute_Name__c,
	    								 mappingRecordByDeveloperNameMap.get('DokPVNv50').Attribute_Value__c);
        }catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}    
    	return mapOfAttributesAndValues;
    }  
     
    private void getTransactions(){	    	
    	Boolean hasSubquery = true;
        String whereCondition = 'c2g__Period__r.c2g__YearName__r.Name = :periodYear';
        String objectName = '';
        Set<String> fieldNameSet = new Set<String>();
        Set<String> setOfInnerQueryFields = new Set<String>();
        Map<String, Set<String>> innerQueryObjAndFieldsMap = new Map<String, Set<String>>();
        listOfTransactions = new List<c2g__codaTransaction__c>();
        
        try{
            objectName = mappingRecordByDeveloperNameMap.get('PVN1I_1').Object_Name__c;
        	fieldNameSet = getFieldNameSet(mappingRecordByDeveloperNameMap.get('PVN1I_R1').MasterLabel);
        	fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('PVN1II_R1').MasterLabel));
        	fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('PVN1III_R1').MasterLabel));
           	fieldNameSet.add('Name');  
           	
           	setOfInnerQueryFields.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('salesInvoiceQuery1').MasterLabel));
           	innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('salesInvoiceQuery1').Object_Name__c, 
           									setOfInnerQueryFields);
           	
           	setOfInnerQueryFields = new Set<String>();
           	setOfInnerQueryFields.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('purchseInvoiceQuery1').MasterLabel));
           	innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('purchseInvoiceQuery1').Object_Name__c, 
           									setOfInnerQueryFields);
            
            setOfInnerQueryFields = new Set<String>();
           	setOfInnerQueryFields.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('purCreditNoteQuery1').MasterLabel));
           	innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('purCreditNoteQuery1').Object_Name__c, 
           									setOfInnerQueryFields);
           									
            setOfInnerQueryFields = new Set<String>();
           	setOfInnerQueryFields.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('creditNotesQuery1').MasterLabel));
           	innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('creditNotesQuery1').Object_Name__c, 
           									setOfInnerQueryFields);
           									
          
           	ReportsGenerationUtility.periodYear = periodYear;
            ReportsGenerationUtility.periodMonth = periodMonth;
 			listOfTransactions = (List<c2g__codaTransaction__c>)ReportsGenerationUtility.getSFDCRecordsWithSubQuery(objectName, 
                                     fieldNameSet, whereCondition, hasSubquery, innerQueryObjAndFieldsMap);
    		setInvoicesInfo();
    	//	setCreditNotesInfo();
    		setPurInvoicesInfo();
			setPurchaseCreditNotesinfo();
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
        }       
    }
    
    private void setInvoicesInfo(){   	
	    String accRegCountry, accVATRegNo, accName, invTotal, taxTotal, transType, transDocNum, transDate, transDocType; 
	    final String ACC_COUNTRY = 'LV'; //Latvia
	    try{
	    	if(listOfTransactions != null){
                for(c2g__codaTransaction__c trnsctn : listOfTransactions){
                    accRegCountry = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R1').SFDC_Field_Name__c);
                    if(trnsctn.c2g__Invoices__r != null && accRegCountry != null && accRegCountry == ACC_COUNTRY ){
                            
                            accVATRegNo = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R2').SFDC_Field_Name__c);
                            accName = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R3').SFDC_Field_Name__c);
                            transType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R4').SFDC_Field_Name__c);
                            transDocType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R7').SFDC_Field_Name__c);
                            transDocNum = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R8').SFDC_Field_Name__c);
                            transDate = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R9').SFDC_Field_Name__c);
                            
                            for(c2g__codaInvoice__c saleInv : trnsctn.c2g__Invoices__r){
                                invTotal = ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('salesInvoiceQuery1').SFDC_Field_Name__c);
                                taxTotal = ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('salesInvoiceQuery2').SFDC_Field_Name__c);
                                listOfTrnsctnSalesInv.add(new transactionSalesInvWrapper(
                                                                 accRegCountry != null ? accRegCountry : '', 
                                                                 accVATRegNo != null ? accVATRegNo : '',  
                                                                 accName != null ? accName : '',  
                                                                 transType != null ? transType : '', 
                                                                 invTotal != null ? invTotal : '',  
                                                                 taxTotal != null ? taxTotal : '',  
                                                                 transDocType != null ? transDocType : '',  
                                                                 transDocNum != null ? transDocNum : '',  
                                                                 transDate != null ? transDate : '' ));	
                            }
                        } 			
                    }
                }
	    }catch(Exception e){
	     	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
	    }  
    }
    
    private void setCreditNotesInfo(){
	    String accRegCountry, accVATRegNo, accName, invTotal, taxTotal, transType, transDocNum, transDate, transDocType; 
	    final String ACC_COUNTRY = 'LV'; //Latvia
	    try{
	    	if(listOfTransactions != null){
                for(c2g__codaTransaction__c trnsctn : listOfTransactions){
                    accRegCountry = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R1').SFDC_Field_Name__c);
                    if(trnsctn.c2g__CreditNotes__r != null && accRegCountry != null && accRegCountry == ACC_COUNTRY ){
                            
                            accVATRegNo = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R2').SFDC_Field_Name__c);
                            accName = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R3').SFDC_Field_Name__c);
                            transType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R4').SFDC_Field_Name__c);
                            transDocType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R7').SFDC_Field_Name__c);
                            transDocNum = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R8').SFDC_Field_Name__c);
                            transDate = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R9').SFDC_Field_Name__c);
                            
                            for(c2g__codaCreditNote__c credNote : trnsctn.c2g__CreditNotes__r){
                                invTotal = ReportsGenerationUtility.getValue(credNote, mappingRecordByDeveloperNameMap.get('creditNotesQuery1').SFDC_Field_Name__c);
                                taxTotal = ReportsGenerationUtility.getValue(credNote, mappingRecordByDeveloperNameMap.get('creditNotesQuery2').SFDC_Field_Name__c);
                                listOfTrnsctnSalesInv.add(new transactionSalesInvWrapper(
                                                                 accRegCountry != null ? accRegCountry : '', 
                                                                 accVATRegNo != null ? accVATRegNo : '',  
                                                                 accName != null ? accName : '',  
                                                                 transType != null ? transType : '', 
                                                                 invTotal != null ? invTotal : '',  
                                                                 taxTotal != null ? taxTotal : '',  
                                                                 transDocType != null ? transDocType : '',  
                                                                 transDocNum != null ? transDocNum : '',  
                                                                 transDate != null ? transDate : '' ));	
                            }
                        } 			
                    }
	    	}
	    }catch(Exception e){
	     	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
	    }  
    }
    
    private void setPurInvoicesInfo(){
	    String accRegCountry, accVATRegNo, accName, invTotal, taxTotal, transType, transDocNum, transDate, transDocType; 
	    
	    try{
	    	if(listOfTransactions != null){
                for(c2g__codaTransaction__c trnsctn : listOfTransactions){
                    if(trnsctn.c2g__PurchaseInvoices__r != null){
                            accRegCountry = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R1').SFDC_Field_Name__c);
                            accVATRegNo = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R2').SFDC_Field_Name__c);
                            accName = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R3').SFDC_Field_Name__c);
                            transType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R4').SFDC_Field_Name__c);
                            transDocType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R7').SFDC_Field_Name__c);
                            transDocNum = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R8').SFDC_Field_Name__c);
                            transDate = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R9').SFDC_Field_Name__c);
                            
                            for(c2g__codaPurchaseInvoice__c purInv : trnsctn.c2g__PurchaseInvoices__r){
                                invTotal = ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('purchseInvoiceQuery1').SFDC_Field_Name__c);
                                taxTotal = ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('purchseInvoiceQuery2').SFDC_Field_Name__c);
                                listOfTrnsctnPurchaseInv.add(new transactionPurInvWrapper(
                                                                 accRegCountry != null ? accRegCountry : '', 
                                                                 accVATRegNo != null ? accVATRegNo : '',  
                                                                 accName != null ? accName : '',  
                                                                 transType != null ? transType : '', 
                                                                 invTotal != null ? invTotal : '',  
                                                                 taxTotal != null ? taxTotal : '',  
                                                                 transDocType != null ? transDocType : '',  
                                                                 transDocNum != null ? transDocNum : '',  
                                                                 transDate != null ? transDate : '' ));	
                            }
                        } 			
                    }
	    	}
	    }catch(Exception e){
	     	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
	    }  
    }
    
    private void setPurchaseCreditNotesinfo(){
	    String accRegCountry, accVATRegNo, accName, invTotal, taxTotal, transType, transDocNum, transDate, transDocType; 
	    
	    try{
	    	if(listOfTransactions != null){
                for(c2g__codaTransaction__c trnsctn : listOfTransactions){
                    if(trnsctn.c2g__PurchaseCreditNotes__r != null){
                            accRegCountry = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R1').SFDC_Field_Name__c);
                            accVATRegNo = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R2').SFDC_Field_Name__c);
                            accName = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R3').SFDC_Field_Name__c);
                            transType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R4').SFDC_Field_Name__c);
                            transDocType = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R7').SFDC_Field_Name__c);
                            transDocNum = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R8').SFDC_Field_Name__c);
                            transDate = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get('PVN1I_R9').SFDC_Field_Name__c);
                            
                            for(c2g__codaPurchaseCreditNote__c purCredNote : trnsctn.c2g__PurchaseCreditNotes__r){
                                invTotal = ReportsGenerationUtility.getValue(purCredNote, mappingRecordByDeveloperNameMap.get('purCreditNoteQuery1').SFDC_Field_Name__c);
                                taxTotal = ReportsGenerationUtility.getValue(purCredNote, mappingRecordByDeveloperNameMap.get('purCreditNoteQuery2').SFDC_Field_Name__c);
                                listOfTrnsctnPurchaseInv.add(new transactionPurInvWrapper(
                                                                 accRegCountry != null ? accRegCountry : '', 
                                                                 accVATRegNo != null ? accVATRegNo : '',  
                                                                 accName != null ? accName : '',  
                                                                 transType != null ? transType : '', 
                                                                 invTotal != null ? invTotal : '',  
                                                                 taxTotal != null ? taxTotal : '',  
                                                                 transDocType != null ? transDocType : '',  
                                                                 transDocNum != null ? transDocNum : '',  
                                                                 transDate != null ? transDate : '' ));	
                            }
                        } 			
                    }
	    	}
	    }catch(Exception e){
	     	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
	    }  
    }
    
    private Set<String> getFieldNameSet(String parentTagName){
		Set<String> fieldNameSet = new Set<String>();
		List<Latvian_Audit_File_Mappings__mdt> mappingRecordList = mappingRecordByLabelMap.get(parentTagName);
		try{
			for(Latvian_Audit_File_Mappings__mdt item : mappingRecordList){
				if(String.isNotBlank(item.SFDC_Field_Name__c)){
					fieldNameSet.add(item.SFDC_Field_Name__c);
				}
			}
		}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}  		
		return fieldNameSet;
	}
	
    private String addNode(String tagName, String value){
		return '<' + tagName + '>' + value + '</' + tagName + '>';
	}
	
    private String addNodeWithAttributes(String tagName, String value, Map<String, String> mapOfAttributesAndValue){  	
    	String str = '<' +  tagName;
        
        for(String key : mapOfAttributesAndValue.keySet()){
    		str += ' ' + key + '="' + mapOfAttributesAndValue.get(key) + '"';
    	}  
    	str += '>' + value + '</' + tagName + '>'; 
        return str;
    }
    
    private Map<String, String> getMapOfAttributesAndValByLabel(String label, sObject obj){    
        Map<String, String> mapOfAttributesAndLabel = new Map<String, String>();
        try{
        	if(label == 'DokPVNv5'){
            	mapOfAttributesAndLabel = getDokPVNv5TAgAttributes();
        	}
        }catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
    	}      
        return mapOfAttributesAndLabel;
    }
    
    public void getFieldMapping(){
		mappingList = new List<Latvian_Audit_File_Mappings__mdt>();          
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
						Attribute_Value__c
				FROM	Latvian_Audit_File_Mappings__mdt
				LIMIT	1000
			];
		}
	}

	private void sortFieldMapping(List<Latvian_Audit_File_Mappings__mdt> unsortedList){
		mappingRecordByLabelMap = new Map<String, List<Latvian_Audit_File_Mappings__mdt>>();
		mappingRecordByDeveloperNameMap = new Map<String, Latvian_Audit_File_Mappings__mdt>();
		if(unsortedList != null && !unsortedList.isEmpty()){
			for(Latvian_Audit_File_Mappings__mdt item : unsortedList){
				if(mappingRecordByLabelMap.get(item.MasterLabel) == null){
					mappingRecordByLabelMap.put(item.MasterLabel, new List<Latvian_Audit_File_Mappings__mdt>{item});
				}else{
					mappingRecordByLabelMap.get(item.MasterLabel).add(item);
				}
				mappingRecordByDeveloperNameMap.put(item.DeveloperName, item);
			}
        }else{
            throw new MissingCustomMDTMappingsException('Error: Cannot find mappings for Custom MDT Latvian_Audit_File_Mappings__mdt!');
        }
	}
	
    public void getPeriods(){
        ReportsGenerationUtility.getPeriods();
        yearOptions = ReportsGenerationUtility.yearOptions;
        monthOptions = ReportsGenerationUtility.monthOptions;
	}
    
	@TestVisible
    public class transactionSalesInvWrapper {
        public String accRegCountry;
        public String accVATRegNo;
        public String accName;
        public String invTotal;
        public String taxTotal;
        public String transType;
        public String transDocType;
        public String transDocNum;
        public String transDate;

        public transactionSalesInvWrapper(String accRegCountry, String accVATRegNo, String accName, String transType, 
                                          String invTotal, String taxTotal, String transDocType, String transDocNum, String transDate) {
            this.accRegCountry = accRegCountry;
            this.accVATRegNo = accVATRegNo;
            this.accName = accName;
            this.invTotal = invTotal;
            this.taxTotal = taxTotal;
            this.transType = transType;
            this.transDocType = transDocType;
            this.transDocNum = transDocNum;
            this.transDate = transDate;
        }
    }
    
    @TestVisible
    public class transactionPurInvWrapper {
        public String accRegCountry;
        public String accVATRegNo;
        public String accName;
        public String invTotal;
        public String taxTotal;
        public String transType;
        public String transDocType;
        public String transDocNum;
        public String transDate;

        public transactionPurInvWrapper(String accRegCountry, String accVATRegNo, String accName, String transType, 
                                        String invTotal, String taxTotal, String transDocType, String transDocNum, String transDate) {
            this.accRegCountry = accRegCountry;
            this.accVATRegNo = accVATRegNo;
            this.accName = accName;
            this.invTotal = invTotal;
            this.taxTotal = taxTotal;
            this.transType = transType;
            this.transDocType = transDocType;
            this.transDocNum = transDocNum;
            this.transDate = transDate;
        }
    }
    
    public class MissingCustomMDTMappingsException extends Exception{}
    public class CustomException extends Exception{}

}