public with sharing class GenerateLTReportXMLController {
	
    private List<Lithuania_File_Mapping__mdt> mappingList;
	private Map<String, List<Lithuania_File_Mapping__mdt>> mappingRecordByLabelMap;
	private Map<String, Lithuania_File_Mapping__mdt> mappingRecordByDeveloperNameMap;
	private List<c2g__codaPurchaseInvoice__c> listOfPurchaseInvoices;
	private List<c2g__codaInvoice__c> listOfSalesInvoices;
	private Map<String, c2g__codaPurchaseInvoice__c> mapOfPurchaseInvoices;
	private Map<String, c2g__codaInvoice__c> mapOfSalesInvoices; 
	private List<c2g__codaGeneralLedgerAccount__c> listOfGlAs;
	private List<c2g__codaTransactionLineItem__c> listOfTransactionLines;
	
	private Map<String, wrapCustomerAccount> mapOfSAFCustomers;
	private Map<String, List<c2g__codaInvoice__c>> mapOfCustomerAndOpenInv;
	
	private Map<String, wrapSupplierAccount> mapOfSAFSuppliers;
	private Map<String, List<c2g__codaPurchaseInvoice__c>> mapOfSupplierAndOpenPurInv;
	List<c2g__codaTransaction__c> listOfTransactions;
	
	List<c2g__codaTaxCode__c> listOfTaxCodes;
	Map<String, taxWrapper> mapOfTaxCodeInfo;
	
	private Map<String, glaWrapper> mapOfGlaInfo;
    Map<String, String> mapOfReportingCodeOpeningDebitBal;
    Map<String, String> mapOfReportingCodeOpeningCreditBal;
    Map<String, String> mapOfReportingCodeClosingDebitBal;
    Map<String, String> mapOfReportingCodeClosingCreditBal;
    
    
    @TestVisible
    private Map<String, wrapCustomerAccount> mapOfCustomerAccount;
    @TestVisible
    private Map<String, wrapSupplierAccount> mapOfSupplierAccount;	
	private String xmlFileString;
	private String folderId;
    
   	private final String ISAF_REPORT_NAME = 'iSAF Report';
    private final String SAFT_REPORT_NAME = 'SAF-T Report';
    private final String CUSTOM_EXCEPTION_MESSAGE = 'Please select a report type!';
    
	public String docLink{get; set;}    
    public Date startDate{get; set;}
	public Date endDate{get; set;}
    public String fileType{get; set;}
	public List<SelectOption> fileTypeOptions{get; set;}
  
    public GenerateLTReportXMLController(){
        try{
            startDate = endDate = Date.today();
            initFileTypes();
            getFieldMapping();
            sortFieldMapping(mappingList);            
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
        }
	}
    
    public void initFileTypes(){
		fileTypeOptions = new List<SelectOption>();
		fileTypeOptions.add(new SelectOption('report1', ISAF_REPORT_NAME));
		fileTypeOptions.add(new SelectOption('report2', SAFT_REPORT_NAME));
	}
 
    public void createXml(){
        try{	 
            	if(fileType == 'report1'){
                    createISAF_Report();
                }else if(fileType == 'report2'){                	
                    createSAFT_Report();
                }else{
                    throw new CustomException(CUSTOM_EXCEPTION_MESSAGE);
                }
            
        }catch(Exception e){
            	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage()));
        }
    }
    
    private void createISAF_Report(){
        getSalesInvoices();
        getPurchaseInvoices();        
        String iSAFFileTagValue = getiSAFFileTagBody();
        xmlFileString = '';
        xmlFileString += '<?xml version="1.0" encoding="UTF-8"?>';
        xmlFileString += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('iSAFFile0').Tag_Name__c,
                         iSAFFileTagValue, getMapOfAttributesAndValByLabel(mappingRecordByDeveloperNameMap.get('iSAFFile0').MasterLabel, null));                    
        String fileName = 'Lithuania iSAF File';
        docLink = ReportsGenerationUtility.generateDocAndAddToFolder(fileName, xmlFileString);     
    } 
    
    private String getiSAFFileTagBody(){		
		String str = '';
		str += getHeader();
		str += getMasterFiles();
		str += getSourceDocuments();				
        return str;
	}
    
    private String getHeader(){	
		String headerTagBody = '';
        String fileDescriptionBody = '';
        try{
                fileDescriptionBody = addNode(mappingRecordByDeveloperNameMap.get('Header0').Tag_Name__c,
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription1').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription1').Default_Value__c)  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription2').Tag_Name__c, 
                                               String.valueOf(System.now()))  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription3').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription3').Default_Value__c)  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription4').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription4').Default_Value__c)  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription5').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription5').Default_Value__c)  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription6').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription6').Default_Value__c)  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription7').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription7').Default_Value__c)  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription8').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription8').Default_Value__c)  +		
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription9').Tag_Name__c, 
                                                mappingRecordByDeveloperNameMap.get('FileDescription9').Default_Value__c)  +
                                        addNode(mappingRecordByDeveloperNameMap.get('FileDescription10').Tag_Name__c, 
                                            addNode(mappingRecordByDeveloperNameMap.get('SelectionCriteria1').Tag_Name__c, String.valueOf(startDate)) 
                                            + addNode(mappingRecordByDeveloperNameMap.get('SelectionCriteria2').Tag_Name__c, String.valueOf(endDate))) 		 		
                                     );	
            	headerTagBody = addNode(mappingRecordByDeveloperNameMap.get('iSAFFile2').Tag_Name__c, fileDescriptionBody); 
        }catch(Exception e){ 	
            throw new MissingCustomMDTMappingsException('Error while getting mappings for File Description!');
        }
        return headerTagBody;
	}
    
    private String getSourceDocuments(){	
        String sourceDocumentBody = '';	
        try{	
                String purchaseInvoicesBody = getPurchaseInvoicesBody();
                String salesInvoicesBody = getSalesInvoicesBody();
            	sourceDocumentBody = addNode(mappingRecordByDeveloperNameMap.get('iSAFFile4').Tag_Name__c, purchaseInvoicesBody + salesInvoicesBody);
        }catch(Exception e){
        	throw new MissingCustomMDTMappingsException('Error while getting mappings for Source Documents!');
        }	      
        return sourceDocumentBody;
	}
    
    private String getPurchaseInvoicesBody(){   	
    	String purchaseInvoices = '';
        String listOfPurchaseInv = '';

        try{	
                if(mapOfPurchaseInvoices != null && !mapOfPurchaseInvoices.isEmpty()){
                    for(String purInvId : mapOfPurchaseInvoices.keyset()){
                        purchaseInvoices += addNode(mappingRecordByDeveloperNameMap.get('PurchaseInvoices0').Tag_Name__c, 
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice0').Tag_Name__c, 
                                                ReportsGenerationUtility.getValue(mapOfPurchaseInvoices.get(purInvId), mappingRecordByDeveloperNameMap.get('Invoice0').SFDC_Field_Name__c )) +
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice1').Tag_Name__c, addSupplierOnPurchaseInvoice(purInvId)) + 
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice2').Tag_Name__c, 
                                                ReportsGenerationUtility.getValue(mapOfPurchaseInvoices.get(purInvId), mappingRecordByDeveloperNameMap.get('Invoice2').SFDC_Field_Name__c )) +
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice3').Tag_Name__c, mappingRecordByDeveloperNameMap.get('Invoice3').Default_Value__c ) +
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice4').Tag_Name__c, ' ' ) +
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice5').Tag_Name__c, ' ' ) +
                                                addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('Invoice6').Tag_Name__c,
                                                '', getMapOfAttributesAndValByLabel(mappingRecordByDeveloperNameMap.get('Invoice6').MasterLabel, null))	+
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice7').Tag_Name__c, ' ') +
                                                addNode(mappingRecordByDeveloperNameMap.get('Invoice8').Tag_Name__c, getAllDocumentTotal(mapOfPurchaseInvoices.get(purInvId)))						 
                                         );
                        }                          
                } 
            	listOfPurchaseInv = addNode(mappingRecordByDeveloperNameMap.get('SourceDocuments1').Tag_Name__c, purchaseInvoices); 
        }catch(Exception e){
         		throw new MissingCustomMDTMappingsException('Error while getting mappings for Purchase Invoices!');
        }	
    	return listOfPurchaseInv;   	
    }
    
    private String addSupplierOnPurchaseInvoice(String purchaseInvoiceId){
        String supplierInfo = '';
        c2g__codaPurchaseInvoice__c purchaseInv;
        String supplierId;
        wrapSupplierAccount acc;
        
        try{
            if(purchaseInvoiceId != null && purchaseInvoiceId != ''){
                if(mapOfPurchaseInvoices != null)
                    purchaseInv = mapOfPurchaseInvoices.get(purchaseInvoiceId);  
                if(purchaseInv != null){
                    supplierId = ReportsGenerationUtility.getValue(purchaseInv, mappingRecordByDeveloperNameMap.get('Supplier1').SFDC_Field_Name__c );	
                }
        	}        
            if(supplierId != null && supplierId != ''){
                if(mapOfSupplierAccount != null)
                    acc = mapOfSupplierAccount.get(supplierId); 
                if(acc != null)
                    supplierInfo = addSupplier(acc); 
            }           
        }catch(Exception e){
           throw new MissingCustomMDTMappingsException('Error while getting mappings for Supplier ID!'); 
        }                                     
        return supplierInfo;
    }
    
    private String addSupplier(wrapSupplierAccount acc){
        String accInfo = '';
        try{
                if(acc != null){
                    accInfo = addNode(mappingRecordByDeveloperNameMap.get('Supplier1').Tag_Name__c, acc.accountId) + 
                              addNode(mappingRecordByDeveloperNameMap.get('Supplier2').Tag_Name__c, acc.regNo) +
                              addNode(mappingRecordByDeveloperNameMap.get('Supplier3').Tag_Name__c, acc.VATRegNo) + 
                              addNode(mappingRecordByDeveloperNameMap.get('Supplier4').Tag_Name__c, acc.country) + 
                              addNode(mappingRecordByDeveloperNameMap.get('Supplier5').Tag_Name__c, acc.accName);      
                }     
        }catch(Exception e){
        	   throw new MissingCustomMDTMappingsException('Error while getting mappings for Supplier!');    
        }     
        return accInfo;     
    }
    
    private String addCustomerOnSalesInvoice(String saleInvoiceId){
        String customerInfo = '';
        String customerId;
        wrapCustomerAccount acc;
        c2g__codaInvoice__c salesInv; 
        
        try{
                if(saleInvoiceId != null && saleInvoiceId != ''){
                    if(mapOfSalesInvoices != null)
                        salesInv = mapOfSalesInvoices.get(saleInvoiceId);  
                    if(salesInv != null){
                        customerId = ReportsGenerationUtility.getValue(salesInv, mappingRecordByDeveloperNameMap.get('Customer1').SFDC_Field_Name__c );	
                    }
                }
                if(customerId != null && customerId != ''){
                    if(mapOfCustomerAccount != null)
                        acc = mapOfCustomerAccount.get(customerId); 
                    if(acc != null)
                        customerInfo = addCustomer(acc);
                }              
        }catch(Exception e){
             throw new MissingCustomMDTMappingsException('Error while getting mappings for customer id!');   
        }                                           
        return customerInfo;
    }
    
    private String addCustomer(wrapCustomerAccount acc){
        String accInfo = '';
        try{
            if(acc != null){
                accInfo = addNode(mappingRecordByDeveloperNameMap.get('Customer1').Tag_Name__c, acc.accountId) + 
                         addNode(mappingRecordByDeveloperNameMap.get('Customer2').Tag_Name__c, acc.VATRegNo) +
                         addNode(mappingRecordByDeveloperNameMap.get('Customer3').Tag_Name__c, acc.regNo) + 
                         addNode(mappingRecordByDeveloperNameMap.get('Customer4').Tag_Name__c, acc.country) + 
                         addNode(mappingRecordByDeveloperNameMap.get('Customer5').Tag_Name__c, acc.accName); 
            }                 
        }catch(Exception e){
            throw new MissingCustomMDTMappingsException('Error while getting mappings for Customer!');  
        }       
        return accInfo;     
    }
    
    private String getSalesInvoicesBody(){  	
    	String salesInvoices = '';
        String listOfSalesInv = '';
        try{
                if(mapOfSalesInvoices != null && !mapOfSalesInvoices.isEmpty()){
                    for(String salesInvId : mapOfSalesInvoices.keyset()){
                    	salesInvoices += addNode(mappingRecordByDeveloperNameMap.get('SalesInvoices0').Tag_Name__c, 
                                            addNode(mappingRecordByDeveloperNameMap.get('Invoice0').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(mapOfSalesInvoices.get(salesInvId), mappingRecordByDeveloperNameMap.get('Invoice0').SFDC_Field_Name__c )) +
                                            addNode(mappingRecordByDeveloperNameMap.get('salesInvoice2').Tag_Name__c, addCustomerOnSalesInvoice(salesInvId)) + 
                                            addNode(mappingRecordByDeveloperNameMap.get('Invoice2').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(mapOfSalesInvoices.get(salesInvId), mappingRecordByDeveloperNameMap.get('Invoice2').SFDC_Field_Name__c )) +
                                            addNode(mappingRecordByDeveloperNameMap.get('Invoice3').Tag_Name__c, mappingRecordByDeveloperNameMap.get('Invoice3').Default_Value__c ) +
                                            addNode(mappingRecordByDeveloperNameMap.get('Invoice4').Tag_Name__c, ' ' ) +
                                            addNode(mappingRecordByDeveloperNameMap.get('Invoice5').Tag_Name__c, ' ' ) +
                                            addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('Invoice6').Tag_Name__c,
                                            '', getMapOfAttributesAndValByLabel(mappingRecordByDeveloperNameMap.get('Invoice6').MasterLabel, null))	+
                                            addNode(mappingRecordByDeveloperNameMap.get('Invoice7').Tag_Name__c, ' ') +
                                            addNode(mappingRecordByDeveloperNameMap.get('Invoice8').Tag_Name__c, getAllDocumentTotal(mapOfSalesInvoices.get(salesInvId)))						 
                                     ); 
                    }                          
            }	
            listOfSalesInv = addNode(mappingRecordByDeveloperNameMap.get('SourceDocuments2').Tag_Name__c, salesInvoices);
        }catch(Exception e){
         	throw new MissingCustomMDTMappingsException('Error while getting mappings for Customer!');   
        }        
    	return listOfSalesInv;   	  	
    }
    
    private String getAllDocumentTotal(sObject obj){		
		String documentTotal = '';
		c2g__codaInvoice__c salesInvObj = null; 
		c2g__codaPurchaseInvoice__c purchaseInvobj = null;
        try{	
            if(obj != null){
                if(obj.getsObjectType() == c2g__codaInvoice__c.sObjectType){
                    salesInvObj = (c2g__codaInvoice__c)obj;
                    for(c2g__codaInvoiceLineItem__c lineItem : salesInvObj.c2g__InvoiceLineItems__r){  
                        documentTotal += addNode(mappingRecordByDeveloperNameMap.get('DocumentTotals0').Tag_Name__c, 
                                            addNode(mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal0').Tag_Name__c,  	
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal0').SFDC_Field_Name__c )) +
                                            addNode(mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal1').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal1').SFDC_Field_Name__c )) +
                                            addNode(mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal2').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal2').SFDC_Field_Name__c )) +
                                            addNode(mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal3').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal3').SFDC_Field_Name__c )) 
                                        ); 
                    }       		
                }          		 
                else if(obj.getsObjectType() == c2g__codaPurchaseInvoice__c.sObjectType){
                    purchaseInvobj = (c2g__codaPurchaseInvoice__c)obj;
                    for(c2g__codaPurchaseInvoiceLineItem__c lineItem : purchaseInvobj.c2g__PurchaseInvoiceLineItems__r){
                        documentTotal += addNode(mappingRecordByDeveloperNameMap.get('DocumentTotals0').Tag_Name__c, 
                                            addNode(mappingRecordByDeveloperNameMap.get('purLineItemDocTotal1').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purLineItemDocTotal1').SFDC_Field_Name__c )) +    
                                            addNode(mappingRecordByDeveloperNameMap.get('purLineItemDocTotal2').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purLineItemDocTotal2').SFDC_Field_Name__c )) +       								
                                            addNode(mappingRecordByDeveloperNameMap.get('purLineItemDocTotal3').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purLineItemDocTotal3').SFDC_Field_Name__c )) +       
                                            addNode(mappingRecordByDeveloperNameMap.get('purLineItemDocTotal4').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purLineItemDocTotal4').SFDC_Field_Name__c )) 
                                        ); 
                    } 
                    for(c2g__codaPurchaseInvoiceExpenseLineItem__c lineItem : purchaseInvobj.c2g__PurchaseInvoiceExpenseLineItems__r){
                        documentTotal += addNode(mappingRecordByDeveloperNameMap.get('DocumentTotals0').Tag_Name__c, 
                                            addNode(mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal1').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal1').SFDC_Field_Name__c )) +    
                                            addNode(mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal2').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal2').SFDC_Field_Name__c )) +       								
                                            addNode(mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal3').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal3').SFDC_Field_Name__c )) +       
                                            addNode(mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal4').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(lineItem, mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal4').SFDC_Field_Name__c )) 
                                        ); 
                    } 
                }    
        	}            
        }catch(Exception e){
            throw new MissingCustomMDTMappingsException('Error while getting mappings for Document Totals!');  
        }         
        return documentTotal;      
    }
    
    private String getMasterFiles(){	
		String customersInfo = getCustomers();
		String suppliersInfo = getSuppliers();
        String masterFilesBody = '';
        
        try{
            masterFilesBody = addNode(mappingRecordByDeveloperNameMap.get('iSAFFile3').Tag_Name__c, 
									customersInfo + suppliersInfo);   
        }catch(Exception e){
            throw new MissingCustomMDTMappingsException('Error while getting mappings for Master Files!');
        }	
        return masterFilesBody;
	}
    
    private String getCustomers(){
		String listOfCustomers = '';
        String customerInfo = '';
        
        try{
            if(mapOfCustomerAccount != null && !mapOfCustomerAccount.isEmpty()){
                for(String accId : mapOfCustomerAccount.keySet()){
                    listOfCustomers += addNode(mappingRecordByDeveloperNameMap.get('Customers0').Tag_Name__c, 
                                               addCustomer(mapOfCustomerAccount.get(accId)));
                }         
            }   
            customerInfo = addNode(mappingRecordByDeveloperNameMap.get('MasterFiles1').Tag_Name__c, listOfCustomers);
        }catch(Exception e){
             throw new MissingCustomMDTMappingsException('Mappings missing for Customers!');
        }                       
        return customerInfo;
	}
	
	private String getSuppliers(){	    
        String listOfSuppliers = '';
        String suppliersInfo = '';
        
        try{
            if(mapOfSupplierAccount != null && !mapOfSupplierAccount.isEmpty()){
                for(String accId : mapOfSupplierAccount.keySet()){
                    listOfSuppliers += addNode(mappingRecordByDeveloperNameMap.get('Suppliers0').Tag_Name__c, 
                                               addSupplier(mapOfSupplierAccount.get(accId)));
                }     
            }
            suppliersInfo = addNode(mappingRecordByDeveloperNameMap.get('MasterFiles2').Tag_Name__c, listOfSuppliers); 
        }catch(Exception e){
            throw new MissingCustomMDTMappingsException('Mappings missing for Suppliers!');
        }                      
        return suppliersInfo;
	}
 
    private void getPurchaseInvoices(){
        try{ 
           		Set<String> fieldNameSet = new Set<String>();  
            	String whereCondition = 'c2g__InvoiceDate__c >= :startDate and c2g__InvoiceDate__c <= :endDate';
           		listOfPurchaseInvoices = (List<c2g__codaPurchaseInvoice__c>)queryInvoices('c2g__codaPurchaseInvoice__c', 'purcahseInvoiceQuery', fieldNameSet, whereCondition);   	
           
           		if(listOfPurchaseInvoices != null && !listOfPurchaseInvoices.isEmpty()){
           			  mapOfPurchaseInvoices = new Map<String, c2g__codaPurchaseInvoice__c>(listOfPurchaseInvoices);
           			  mapOfSupplierAccount = new Map<String, wrapSupplierAccount>();    
           			  String accId, accName, accRegNo, accVATRegNo, accCountry;
           			  String accNameVal, accRegNoVal, accVATRegNumVal, accCountryVal;
           			
           			  for(c2g__codaPurchaseInvoice__c purchaseInv : listOfPurchaseInvoices){ 
           			  	accId = ReportsGenerationUtility.getValue(purchaseInv, mappingRecordByDeveloperNameMap.get('purcahseInvoiceQuery3').SFDC_Field_Name__c); 
                	  	if(accId != null){
                	  	   accNameVal = ReportsGenerationUtility.getValue(purchaseInv, mappingRecordByDeveloperNameMap.get('purcahseInvoiceQuery4').SFDC_Field_Name__c);
		                   accName = (accNameVal != null) ? accNameVal : '';
		                   accRegNoVal = ReportsGenerationUtility.getValue(purchaseInv, mappingRecordByDeveloperNameMap.get('purcahseInvoiceQuery5').SFDC_Field_Name__c);
		                   accRegNo = (accRegNoVal != null) ? accRegNoVal : '';
		                   accVATRegNumVal = ReportsGenerationUtility.getValue(purchaseInv, mappingRecordByDeveloperNameMap.get('purcahseInvoiceQuery6').SFDC_Field_Name__c);
		                   accVATRegNo = (accVATRegNumVal != null) ? accVATRegNumVal : '';
		                   accCountryVal = ReportsGenerationUtility.getValue(purchaseInv, mappingRecordByDeveloperNameMap.get('purcahseInvoiceQuery7').SFDC_Field_Name__c);
		                   accCountry = (accCountryVal != null) ? accCountryVal : '';	

		                    if(mapOfSupplierAccount.get(accId) == null){
		                        mapOfSupplierAccount.put(accId, new wrapSupplierAccount(accId, accRegNo, accVATRegNo, accCountry, accName));
                    		}        
                	}          
                 }
			}         
        }catch(Exception e){
         	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 'Error while fetching Purchase Invoice Records: ' + 
                                                       e.getMessage() + ' ' + e.getStackTraceString()));   
        }
    }

    private void getSalesInvoices(){    
        try{
            	Set<String> fieldNameSet = new Set<String>();            	
            	String whereCondition = 'c2g__InvoiceDate__c >= :startDate and c2g__InvoiceDate__c <= :endDate';
            	listOfSalesInvoices = (List<c2g__codaInvoice__c>)queryInvoices('c2g__codaInvoice__c', 'salesInvoiceQuery', fieldNameSet, whereCondition);   	
            	
            	if(listOfSalesInvoices != null && !listOfSalesInvoices.isEmpty()){
            		mapOfSalesInvoices = new Map<String, c2g__codaInvoice__c>(listOfSalesInvoices);  
            		mapOfCustomerAccount = new Map<String, wrapCustomerAccount>();    
            		String accId, accName, accRegNo, accVATRegNo, accCountry; 
            		String accNameVal, accRegNoVal, accVATRegNumVal, accCountryVal;
		
            		for(c2g__codaInvoice__c salesInv : listOfSalesInvoices){
            			accId = ReportsGenerationUtility.getValue(salesInv, mappingRecordByDeveloperNameMap.get('salesInvoiceQuery3').SFDC_Field_Name__c);
                		if(accId != null){
		                   accNameVal = ReportsGenerationUtility.getValue(salesInv, mappingRecordByDeveloperNameMap.get('salesInvoiceQuery4').SFDC_Field_Name__c);
		                   accName = (accNameVal != null) ? accNameVal : '';
		                   accRegNoVal = ReportsGenerationUtility.getValue(salesInv, mappingRecordByDeveloperNameMap.get('salesInvoiceQuery5').SFDC_Field_Name__c);
		                   accRegNo = (accRegNoVal != null) ? accRegNoVal : '';
		                   accVATRegNumVal = ReportsGenerationUtility.getValue(salesInv, mappingRecordByDeveloperNameMap.get('salesInvoiceQuery6').SFDC_Field_Name__c);
		                   accVATRegNo = (accVATRegNumVal != null) ? accVATRegNumVal : '';
		                   accCountryVal = ReportsGenerationUtility.getValue(salesInv, mappingRecordByDeveloperNameMap.get('salesInvoiceQuery7').SFDC_Field_Name__c);
		                   accCountry = (accCountryVal != null) ? accCountryVal : '';		                    
				           
		                    if(mapOfCustomerAccount.get(accId) == null){
		                        mapOfCustomerAccount.put(accId, new wrapCustomerAccount(accId, accRegNo, accVATRegNo, accCountry, accName));
                    		}   
                		}          
           		 	}    
            	}
        }catch(Exception e){
         	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 'Error while fetching Sales Invoice Records: ' + 
                                                       e.getMessage() + ' ' + e.getStackTraceString()));     
        }   	
    }
    
    private void createSAFT_Report(){
    	setSAFTReportData();
        String auditFileTagValue = getAuditFileTagBody();
        xmlFileString = '';
        xmlFileString += '<?xml version="1.0" encoding="UTF-8"?>';
        xmlFileString += addNodeWithAttributes(mappingRecordByDeveloperNameMap.get('SAFT_AuditFile0').Tag_Name__c,
                         auditFileTagValue, getMapOfAttributesAndValByLabel(mappingRecordByDeveloperNameMap.get('SAFT_AuditFile0').MasterLabel, 
                         													null));                    
        String fileName = 'Lithuania SAF-T File';
        docLink = ReportsGenerationUtility.generateDocAndAddToFolder(fileName, xmlFileString); 
    }
    
    private String getAuditFileTagBody(){
    	String auditFileTagBody = '';
    	auditFileTagBody = getHeaderBody() + getMasterFilesBody() + getGLAEntriesBody() + getSourceDocumentsBody();
    	return auditFileTagBody;
    }
    
    private String getHeaderBody(){
    	String headerBodyStr = '';  
    	String headerContent = '';
    	try{
    		headerContent = addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header1').Tag_Name__c, 
    							mappingRecordByDeveloperNameMap.get('SAFT_Header1').Default_Value__c ) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header2').Tag_Name__c, 
    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header2').Default_Value__c ) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header3').Tag_Name__c, String.valueOf(System.now())) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header4').Tag_Name__c, 
    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header4').Default_Value__c ) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header5').Tag_Name__c, 
                                mappingRecordByDeveloperNameMap.get('SAFT_Header5').Default_Value__c ) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header6').Tag_Name__c, 
    						    mappingRecordByDeveloperNameMap.get('SAFT_Header6').Default_Value__c ) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header7').Tag_Name__c, String.valueOf(startDate)) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header8').Tag_Name__c, String.valueOf(endDate)) +
    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header9').Tag_Name__c, 
	    							addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo1').Tag_Name__c, 
	    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo1').Default_Value__c) + 
	    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo2').Tag_Name__c,
	    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo2').Default_Value__c) +
	    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo3').Tag_Name__c,
	    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo3').Default_Value__c) +
	    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo4').Tag_Name__c, 
	    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo4').Default_Value__c) +
	    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo5').Tag_Name__c, 
	    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo5').Default_Value__c) +
	    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo6').Tag_Name__c, 
	    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo6').Default_Value__c) +
	    						   	addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo7').Tag_Name__c, 
	    						   	mappingRecordByDeveloperNameMap.get('SAFT_Header_CmpnyAddInfo7').Default_Value__c)) +
    						   addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header10').Tag_Name__c,
    						   mappingRecordByDeveloperNameMap.get('SAFT_Header10').Default_Value__c ) +
                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header11').Tag_Name__c,
                               mappingRecordByDeveloperNameMap.get('SAFT_Header11').Default_Value__c ) +
                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_Header12').Tag_Name__c,
                               mappingRecordByDeveloperNameMap.get('SAFT_Header12').Default_Value__c ) ;
    						      	
    		headerBodyStr = addNode(mappingRecordByDeveloperNameMap.get('SAFT_AuditFile4').Tag_Name__c, headerContent);   		
    	}catch(Exception e){
    		throw new MissingCustomMDTMappingsException('Error while getting mappings for Header! ' + e.getMessage());
    	}    	
    	return headerBodyStr;
    }
    
    // To get master file data for SAF-T 
    private String getMasterFilesBody(){
    	String masterFilesBodyStr = '';
    	String masterFiles = '';
    	masterFiles = getMasterFilesGLA() + getMasterFilesCustomers() + getMasterFilesSuppliers();
    	masterFilesBodyStr = addNode(mappingRecordByDeveloperNameMap.get('SAFT_AuditFile5').Tag_Name__c, masterFiles);
    	return masterFilesBodyStr;
    }
    
    private String getMasterFilesGLA(){
    	String mstrFilesGLA = '';
        String mstrFilesGLABody = '';   	
    	try{
    		if(mapOfGlaInfo != null){
	    		for(String repCode: mapOfGlaInfo.keySet()){
	    			glaWrapper gla = mapOfGlaInfo.get(repCode);
	    			mstrFilesGLA += addNode(mappingRecordByDeveloperNameMap.get('SAFT_MasterFileGLAs1').Tag_Name__c, 
	                                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc1').Tag_Name__c, gla.accId != null ? gla.accId : '') + 
	                                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc2').Tag_Name__c, gla.accDescription != null ? gla.accDescription : '') +
	                                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc5').Tag_Name__c, gla.accType != null ? gla.accType : '') + 
	                                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc6').Tag_Name__c, gla.openingDb != null ? gla.openingDb : '') +
	                                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc7').Tag_Name__c, gla.openingCr != null ? gla.openingCr : '') + 
	                                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc8').Tag_Name__c, gla.closingDb != null ? gla.closingDb : '') +
	                                               addNode(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc9').Tag_Name__c, gla.closingCr != null ? gla.closingCr : ''));
	    		}
    		}
        	mstrFilesGLABody = addNode(mappingRecordByDeveloperNameMap.get('SAFT_MasterFiles1').Tag_Name__c, mstrFilesGLA);   		
    	}catch(Exception e){
    		throw new MissingCustomMDTMappingsException('Error while getting mappings for Master Files! ' + e.getMessage());
    	}   	
    	return mstrFilesGLABody;
    }
    
    // To add customer information within Master Files
    private String getMasterFilesCustomers(){
    	String mstrFileCustomers = '';
    	String mstrFileCustomersBody = '';
    	try{
    		if(mapOfSAFCustomers != null){
	    		for(String customerId : mapOfSAFCustomers.keyset()){
	    			mstrFileCustomersBody += addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer0').Tag_Name__c,
	    										 addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer1').Tag_Name__c,
		    									 mapOfSAFCustomers.get(customerId).customerRegNo) +
		    									 addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer2').Tag_Name__c,
		    									 mapOfSAFCustomers.get(customerId).customerName) +
		    									 addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer3').Tag_Name__c, addCustomerAddress(customerId)) + 
		    									 getOpenSalesInvoices(customerId));   									
	    		}
    		}
    		mstrFileCustomers = addNode(mappingRecordByDeveloperNameMap.get('SAFT_MasterFile2').Tag_Name__c, mstrFileCustomersBody); 		
    	}catch(Exception e){
    		throw new MissingCustomMDTMappingsException('Error while getting mappings for Master Files\'s Customers! ' + e.getMessage());
    	}
    	return mstrFileCustomers;
    }
    
    // To add address information of customer
    private String addCustomerAddress(String id){
    	String address = '';
    	String mappingName = '';   	
    	try{
    		address = addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add1').Tag_Name__c, mapOfSAFCustomers.get(id).custStreet != null ? mapOfSAFCustomers.get(id).custStreet : '') +
			    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add2').Tag_Name__c, mapOfSAFCustomers.get(id).custHouseNo != null ? mapOfSAFCustomers.get(id).custHouseNo : '') +
			    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add3').Tag_Name__c, mapOfSAFCustomers.get(id).custCity != null ? mapOfSAFCustomers.get(id).custCity : '') +
			    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add4').Tag_Name__c, mapOfSAFCustomers.get(id).custPostalCode != null ? mapOfSAFCustomers.get(id).custPostalCode : '') +
			    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add5').Tag_Name__c, mapOfSAFCustomers.get(id).custCountry != null ? mapOfSAFCustomers.get(id).custCountry : '');		
    	}catch(Exception e){
    		throw new MissingCustomMDTMappingsException('Error while getting mappings for Master Files\'s Customer Address! ' + e.getMessage());
    	}
    	return address;
    }
    
    // To set open sales invoices on customer
    private String getOpenSalesInvoices(String customerId){
    	String opnSalesInvBody = '';
    	String mappingName = '';
    	String opnsalesInv = '';
    	List<c2g__codaInvoice__c> listOfCustomerSalesInv = new List<c2g__codaInvoice__c>();
    	
    	try{
    		if(mapOfCustomerAndOpenInv != null){
    			listOfCustomerSalesInv = mapOfCustomerAndOpenInv.get(customerId);
    			for(c2g__codaInvoice__c openInv : listOfCustomerSalesInv){
    				opnsalesInv = '';
    				for(Integer i=1; i<=9; i++){
    					mappingName = 'SAFT_Cust_OpenSalesInv' + i;
    					opnsalesInv += addNode(mappingRecordByDeveloperNameMap.get(mappingName).Tag_Name__c,
    								  ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get(mappingName).SFDC_Field_Name__c ));
    				}
    				opnSalesInvBody += addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer14').Tag_Name__c, opnsalesInv);
    			}
    		}  	    		
    	}catch(Exception e){
    		throw new MissingCustomMDTMappingsException('Error while getting mappings for Customer\'s open sales invoices! ' + e.getMessage());
    	}	
    	return opnSalesInvBody;
    }
    
    // To add address information of supplier
    private String addSupplierAddress(String id){
    	String address = '';
    	String mappingName = '';   	
    	try{
    		address = addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add1').Tag_Name__c, mapOfSAFSuppliers.get(id).supplierName != null ? mapOfSAFSuppliers.get(id).supplierName : '') +
    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add2').Tag_Name__c, mapOfSAFSuppliers.get(id).supplierHouseNo != null ? mapOfSAFSuppliers.get(id).supplierHouseNo : '') +
    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add3').Tag_Name__c, mapOfSAFSuppliers.get(id).supplierCity != null ? mapOfSAFSuppliers.get(id).supplierCity : '') +
    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add4').Tag_Name__c, mapOfSAFSuppliers.get(id).supplierPostalCode != null ? mapOfSAFSuppliers.get(id).supplierPostalCode : '') +
    		addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add5').Tag_Name__c, mapOfSAFSuppliers.get(id).supplierCountry != null ? mapOfSAFSuppliers.get(id).supplierCountry : ''); 		
    	}catch(Exception e){
    		throw new MissingCustomMDTMappingsException('Error while getting mappings for Supplier\'s Address! ' + e.getMessage());
    	}
    	return address;
    }
    
    // To set supplier's information in Master Files
    private String getMasterFilesSuppliers(){
    	String mstrFileSuppliers = '';
    	String mstrFileSuppliersBody = '';
        try{
        	if(mapOfSAFSuppliers != null){
	    		for(String supplierId : mapOfSAFSuppliers.keyset()){
	    			mstrFileSuppliersBody += addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier0').Tag_Name__c,
	    										 addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier1').Tag_Name__c,
		    									 mapOfSAFSuppliers.get(supplierId).supplierRegNo) +
		    									 addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier2').Tag_Name__c,
		    									 mapOfSAFSuppliers.get(supplierId).supplierName) +
		    									 addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier3').Tag_Name__c, addSupplierAddress(supplierId)) +	    
		    									 getOpenPurchaseInvoices(supplierId));   									
	    		}
    		}
    		mstrFileSuppliers = addNode(mappingRecordByDeveloperNameMap.get('SAFT_MasterFile3').Tag_Name__c, mstrFileSuppliersBody);     	
        }catch(Exception e){
        	throw new MissingCustomMDTMappingsException('Error while getting mappings for Master Files-Supplier! ' + e.getMessage());
        } 	
    	return mstrFileSuppliers;
    }
    
    // To set open purchase invoices on supplier
    private String getOpenPurchaseInvoices(String supplierId){
    	String opnPurchaseInvBody = '';
    	String opnPurchaseInv = '';
    	String mappingName = '';
    	List<c2g__codaPurchaseInvoice__c> listOfSupplierPurchaseInv = new List<c2g__codaPurchaseInvoice__c>();
    	try{
    		if(mapOfSupplierAndOpenPurInv != null){
    			listOfSupplierPurchaseInv = mapOfSupplierAndOpenPurInv.get(supplierId);
    			for(c2g__codaPurchaseInvoice__c openPurInv : listOfSupplierPurchaseInv){
    				opnPurchaseInv = '';
    				for(Integer i=1; i<=10; i++){
    					mappingName = 'SAFT_Supp_OpenPurInv' + i;
    					opnPurchaseInv += addNode(mappingRecordByDeveloperNameMap.get(mappingName).Tag_Name__c,
    								      ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get(mappingName).SFDC_Field_Name__c ));
    				}
    				opnPurchaseInvBody += addNode(mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier14').Tag_Name__c, opnPurchaseInv);
    			}
    		}  		
    	}catch(Exception e){
        	throw new MissingCustomMDTMappingsException('Error while getting mappings for Supplier\'s Open Purchase Invoices! ' + e.getMessage());
        }
    	return opnPurchaseInvBody;
    }
    
    private String getGLAEntriesBody(){
    	String glaEntriesStr = '';
    	String glaEntriesStrBody = '';
    	Integer numberofRecords = 0;
    	Decimal totalDebit = 0.0;
    	Decimal totalCredit = 0.0;   	
    	String journalBody = '';
    	String mappingName = '';
    	
    	try{
    		if(listOfTransactions != null){
	    		for(c2g__codaTransaction__c trnsctn : listOfTransactions){ 		
		    		if(trnsctn.c2g__TransactionType__c == 'Journal' && trnsctn.c2g__Journals__r != null){
		    			numberofRecords++;
		    			totalDebit += Integer.valueOf(ReportsGenerationUtility.getValue(trnsctn, 'c2g__Debits__c'));
		    			totalCredit += Integer.valueOf(ReportsGenerationUtility.getValue(trnsctn, 'c2g__Credits__c'));
		    			
		    			for(c2g__codaJournal__c journl : trnsctn.c2g__Journals__r){
			    			journalBody = addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal1').Tag_Name__c, 
			    						  ReportsGenerationUtility.getValue(journl, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal1').SFDC_Field_Name__c) != null ?
	                                      ReportsGenerationUtility.getValue(journl, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal1').SFDC_Field_Name__c) : '') + 
						    			  addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal2').Tag_Name__c, 
						    			  ReportsGenerationUtility.getValue(journl, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal2').SFDC_Field_Name__c) != null ?
	                                      ReportsGenerationUtility.getValue(journl, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal2').SFDC_Field_Name__c) : '') + 
						    			  addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal3').Tag_Name__c, 
						    			  ReportsGenerationUtility.getValue(journl, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal3').SFDC_Field_Name__c) != null ?   
	                                      ReportsGenerationUtility.getValue(journl, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal3').SFDC_Field_Name__c) : '') + 
						    			  addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal4').Tag_Name__c, setTransactionOnJournal(trnsctn)); 
		    			}
		    		}   		
	    		}
    		}   	   	
	    	glaEntriesStrBody = addNode(mappingRecordByDeveloperNameMap.get('SAFT_GeneralLedgerEntries1').Tag_Name__c, String.valueOf(numberofRecords)) + 
						    	addNode(mappingRecordByDeveloperNameMap.get('SAFT_GeneralLedgerEntries2').Tag_Name__c, String.valueOf(totalDebit)) +
						    	addNode(mappingRecordByDeveloperNameMap.get('SAFT_GeneralLedgerEntries3').Tag_Name__c, String.valueOf(totalCredit)) + 
	    						addNode(mappingRecordByDeveloperNameMap.get('SAFT_GeneralLedgerEntries4').Tag_Name__c, journalBody); 
	
	    	glaEntriesStr = addNode(mappingRecordByDeveloperNameMap.get('SAFT_AuditFile6').Tag_Name__c, glaEntriesStrBody);   		
    	}catch(Exception e){
        	throw new MissingCustomMDTMappingsException('Error while getting mappings for General Ledger Entries! ' + e.getMessage());
        }	
    	return glaEntriesStr;
    }
    
    private String setTransactionOnJournal(c2g__codaTransaction__c trnsctn){
    	String journalTransaction = '';
    	String mappingName = '';
    	String fieldValue = '';
    	try{
    		for(Integer i=1; i <=10; i++){
	    		mappingName = 'SAFT_GLE_Jrnl_Transaction' + i;
	    		fieldValue = ReportsGenerationUtility.getValue(trnsctn, mappingRecordByDeveloperNameMap.get(mappingName).SFDC_Field_Name__c);
	    		journalTransaction += addNode(mappingRecordByDeveloperNameMap.get(mappingName).Tag_Name__c, fieldValue != null ? fieldValue : '');
    		}
    		journalTransaction += addTransactionLines(trnsctn);		
    	}catch(Exception e){
        	throw new MissingCustomMDTMappingsException('Error while getting mappings for GLE Journal Transaction! ' + e.getMessage());
        } 	
    	return journalTransaction;
    }
    
    // To add Transaction Line Info on GLE Transaction Entries
    private String addTransactionLines(c2g__codaTransaction__c trnsctn){
    	String trnLi = '';
   		try{
   			if(trnsctn.c2g__TransactionLineItems__r != null){
	    		for(c2g__codaTransactionLineItem__c tli : trnsctn.c2g__TransactionLineItems__r){
	    			trnLi += addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Transaction11').Tag_Name__c,
	    						addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line1').Tag_Name__c,
	    				   		ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line1').SFDC_Field_Name__c ) != null ?
	                            ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line1').SFDC_Field_Name__c ) : '') + 
				    			addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line2').Tag_Name__c, 
				    			ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line2').SFDC_Field_Name__c ) != null ?
	                            ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line2').SFDC_Field_Name__c ) : '') +  
				    			addAnalysisDimensions(tli) + 
				    			addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line4').Tag_Name__c, 
				    			ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line4').SFDC_Field_Name__c ) != null ?
	                            ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line4').SFDC_Field_Name__c ) : '') +  
				    			addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line5').Tag_Name__c, 
				    			ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line5').SFDC_Field_Name__c )) +  
				    			addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line6').Tag_Name__c, 
				    			ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line6').SFDC_Field_Name__c ) != null ?
	                            ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line6').SFDC_Field_Name__c) : '') +  
				    			addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line7').Tag_Name__c,
				    			ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line7').SFDC_Field_Name__c )) + 
				    			addTransactionLineTaxInfo(tli) + 
				    			addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line9').Tag_Name__c, 
				    			ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line9').SFDC_Field_Name__c ) != null ?
	                            ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line9').SFDC_Field_Name__c ) : ''));
	    		}
    		}     			
   		}catch(Exception e){
        	throw new MissingCustomMDTMappingsException('Error while getting mappings for GLE Journal Transaction Lines! ' + e.getMessage());
        }  		
    	return trnLi;   	
    }
    
    private String addTransactionLineTaxInfo(c2g__codaTransactionLineItem__c tli){
    	String taxDetails = '';
    	String mappingName = '';
    	String fieldVal = '';
    	String taxCode1, taxCode2, taxCode3;
    	List<String> listofTaxCodes = new List<String>();
    	taxCode1 = ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_TaxCode1').SFDC_Field_Name__c );
    	taxCode2 = ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_TaxCode2').SFDC_Field_Name__c );
    	taxCode3 = ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_TaxCode3').SFDC_Field_Name__c );
        
        if(taxCode1 != null && taxCode1 != ''){
        	listofTaxCodes.add(taxCode1);
        }
        if(taxCode2 != null && taxCode2 != ''){
        	listofTaxCodes.add(taxCode2);
        }
        if(taxCode3 != null && taxCode3 != ''){
        	listofTaxCodes.add(taxCode3);
        }
               
        if(listofTaxCodes.size() > 0){
        	for(String taxCode: listofTaxCodes){
        		taxDetails = addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line8').Tag_Name__c,
	    					 addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_Tax1').Tag_Name__c, 
						     mapOfTaxCodeInfo.get(taxCode).taxType) +
						     addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_Tax2').Tag_Name__c, 
						     mapOfTaxCodeInfo.get(taxCode).taxCode) +
						     addNode(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_Tax3').Tag_Name__c, 
						     mapOfTaxCodeInfo.get(taxCode).taxPercentage));  	
        	}    			
	    }
    	return taxDetails;
    }
   
    private String addAnalysisDimensions(c2g__codaTransactionLineItem__c tli){
    	String mappingName1 = '';
        String mappingName2 = '';
        String analysisId = '';
        String analysisDesc = '';
        String analysisDim = '';
        String tagName = mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line3').Tag_Name__c;
    	String analysisIdTag = mappingRecordByDeveloperNameMap.get('SAFT_GLE_TrnLine_AnlysisDim1').Tag_Name__c;
    	String analysisDescTag = mappingRecordByDeveloperNameMap.get('SAFT_GLE_TrnLine_AnlysisDim1_II').Tag_Name__c;     
        
        for(Integer i=1; i<=4; i++){
            mappingName1 = 'SAFT_GLE_TrnLine_AnlysisDim' + i;
            mappingName2 = 'SAFT_GLE_TrnLine_AnlysisDim' + i + '_II';
            analysisId = ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get(mappingName1).SFDC_Field_Name__c) != null ?
							ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get(mappingName1).SFDC_Field_Name__c) : '';
            analysisDesc = ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get(mappingName2).SFDC_Field_Name__c) != null ? 
							ReportsGenerationUtility.getValue(tli, mappingRecordByDeveloperNameMap.get(mappingName2).SFDC_Field_Name__c) : '';
            if(analysisId != '' || analysisDesc != ''){
                analysisDim += addNode(tagName, addNode(analysisIdTag, analysisId) + addNode(analysisDescTag, analysisDesc));   
            }            
        }
    	return analysisDim;    	
    }
    
    private String getSourceDocumentsBody(){
    	String srcDocsBodyStr = '';
    	String srcDocBody = getSalesInvSourceDocBody() + getPurchaseInvSourceDocBody(); 
    	
    	srcDocsBodyStr = addNode(mappingRecordByDeveloperNameMap.get('SAFT_AuditFile7').Tag_Name__c, srcDocBody);
    	return srcDocsBodyStr;
    }
    
    // To get SalesInvoices within SourceDocuments for SAF-T report
    private String getSalesInvSourceDocBody(){
    	String salesInv = '';
    	String salesInvBody = '';
    	String saleInvTot = '';
    	Integer totalInvoices = 0;
    	Decimal totalCredit = 0.0;
    	Decimal totalDebit = 0.0;
    	
    	if(listOfSalesInvoices != null){			
    		for(c2g__codaInvoice__c saleInv : listOfSalesInvoices){  			
    				totalCredit += Decimal.valueOf(ReportsGenerationUtility.getValue(saleInv, 'c2g__Transaction__r.c2g__Credits__c'));
    				totalDebit += Decimal.valueOf(ReportsGenerationUtility.getValue(saleInv, 'c2g__Transaction__r.c2g__Debits__c'));
    				salesInvBody += addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv4').Tag_Name__c, 
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').Tag_Name__c,
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').SFDC_Field_Name__c ) != null ?
                                           ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv2').Tag_Name__c, 
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv2').SFDC_Field_Name__c ) != null ?
                                           ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv2').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv3').Tag_Name__c, 
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv3').SFDC_Field_Name__c ) != null ?
                                           ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv3').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv4').Tag_Name__c, 
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv4').SFDC_Field_Name__c ) != null ?
                                           ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv4').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv5').Tag_Name__c,
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv5').SFDC_Field_Name__c ) != null ?
                                            ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv5').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv6').Tag_Name__c, 
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv6').SFDC_Field_Name__c ) != null ?
                                            ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv6').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv10').Tag_Name__c, 
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv10').SFDC_Field_Name__c ) != null ?
                                            ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv10').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv11').Tag_Name__c, 
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv11').SFDC_Field_Name__c ) != null ?
                                            ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv11').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv12').Tag_Name__c, 
									ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv12').SFDC_Field_Name__c ) != null ?
                                            ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv12').SFDC_Field_Name__c ) : '') +
									addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv16').Tag_Name__c, 
										addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').Tag_Name__c, 
										ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').SFDC_Field_Name__c )) +
										addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot2').Tag_Name__c, 
										ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').SFDC_Field_Name__c )) +
										addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot3').Tag_Name__c, 
										ReportsGenerationUtility.getValue(saleInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot3').SFDC_Field_Name__c ))
									));
    		}   		
    		totalInvoices = listOfSalesInvoices.size();
    		saleInvTot = addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv1').Tag_Name__c, String.valueOf(totalInvoices)) +
    					 addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv2').Tag_Name__c, String.valueOf(totalDebit)) + 
    					 addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv3').Tag_Name__c, String.valueOf(totalCredit)); 
    	}    	
    	salesInv = addNode(mappingRecordByDeveloperNameMap.get('SAFT_SourceDocuments1').Tag_Name__c, saleInvTot + salesInvBody);
    	return salesInv;
    }
    
    // To get PurchaseInvoices within SourceDocuments for SAF-T report
    private String getPurchaseInvSourceDocBody(){
    	String purchaseInv = '';
    	String purchaseInvBody = '';
    	String purInvTot = '';
    	Integer totalInvoices = 0;
    	Decimal totalCredit = 0.0;
    	Decimal totalDebit = 0.0;
    	
        try{
                if(listOfPurchaseInvoices != null){				
                    for(c2g__codaPurchaseInvoice__c purInv : listOfPurchaseInvoices){
                            totalCredit += Decimal.valueOf(ReportsGenerationUtility.getValue(purInv, 'c2g__Transaction__r.c2g__Credits__c'));
                            totalDebit += Decimal.valueOf(ReportsGenerationUtility.getValue(purInv, 'c2g__Transaction__r.c2g__Debits__c'));
                            purchaseInvBody += addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv4').Tag_Name__c, 
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').Tag_Name__c,
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').SFDC_Field_Name__c ) != null ?
                                            		ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').SFDC_Field_Name__c ) : '') +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv2').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv2').SFDC_Field_Name__c ) != null ?
                                                	ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv2').SFDC_Field_Name__c ) : '') +     
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv3').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv3').SFDC_Field_Name__c ) != null ?
                                                    ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv3').SFDC_Field_Name__c ) : '') +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv4').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv4').SFDC_Field_Name__c ) != null ?
                                                    ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv4').SFDC_Field_Name__c ) : '') +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv5').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv5').SFDC_Field_Name__c ) != null ?
                                                    ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv5').SFDC_Field_Name__c ) : '') +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv6').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv6').SFDC_Field_Name__c ) != null ?
                                                    ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv6').SFDC_Field_Name__c ) : '') +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv10').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv10').SFDC_Field_Name__c ) != null ?
                                                    ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv10').SFDC_Field_Name__c ) : '') +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv11').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv11').SFDC_Field_Name__c ) != null ?
                                                    ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv11').SFDC_Field_Name__c ) : '') +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv12').Tag_Name__c, 
                                            ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv12').SFDC_Field_Name__c )) +
                                            addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv16').Tag_Name__c, 
                                                addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').Tag_Name__c, 
                                                ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').SFDC_Field_Name__c )) +
                                                addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot2').Tag_Name__c, 
                                                ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').SFDC_Field_Name__c )) +
                                                addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot3').Tag_Name__c, 
                                                ReportsGenerationUtility.getValue(purInv, mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot3').SFDC_Field_Name__c ))
                                           ));
                    }
                    totalInvoices = listOfPurchaseInvoices.size();
                    purInvTot = addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv1').Tag_Name__c, String.valueOf(totalInvoices)) +
                                addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv2').Tag_Name__c, String.valueOf(totalDebit)) + 
                                addNode(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv3').Tag_Name__c, String.valueOf(totalCredit)); 
            	}   	
            	purchaseInv = addNode(mappingRecordByDeveloperNameMap.get('SAFT_SourceDocuments2').Tag_Name__c, purInvTot + purchaseInvBody);
        }catch(Exception e){
            throw new MissingCustomMDTMappingsException('Error while getting mappings for Purchase Invoices! ' + e.getMessage());
        }    	
    	return purchaseInv;
   }
   
   private void setSAFTReportData(){
    	Map<String, Set<String>> innerQueryObjAndFieldsMap = new Map<String, Set<String>>(); 	
    	Set<String> setOfInnerQueryFields = new Set<String>();
   		Set<String> fieldNameSet = new Set<String>();
   		Boolean hasSubquery = true;
   		String objName, whereCondition;
   		
   		try{
   			objName = mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Transaction1').Object_Name__c;
	   //	whereCondition = 'c2g__Transaction__r.c2g__TransactionDate__c >= :startDate and c2g__Transaction__r.c2g__TransactionDate__c <= :endDate';
	   		whereCondition = '';
	   		fieldNameSet = getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Transaction1').MasterLabel);  	 		
	   		fieldNameSet.add('c2g__Period__r.c2g__YearName__r.c2g__StartDate__c');
	   		fieldNameSet.add('c2g__Period__r.c2g__YearName__r.c2g__EndDate__c');
	   		fieldNameSet.add('c2g__TransactionType__c');
	   		fieldNameSet.add('c2g__Period__c');
	   		fieldNameSet.add('c2g__Debits__c');
	   		fieldNameSet.add('c2g__Credits__c');
				
			// get fields on journal
			setOfInnerQueryFields = getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal1').MasterLabel);    	
			innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Journal1').Object_Name__c, setOfInnerQueryFields);   
			// set fields on transction lines
			setOfInnerQueryFields = getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc0').MasterLabel);
			setOfInnerQueryFields.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Trn_Line1').MasterLabel)); 
	        setOfInnerQueryFields.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_GLE_TrnLine_AnlysisDim1').MasterLabel)); 
	        setOfInnerQueryFields.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_TaxCode1').MasterLabel));            
	        setOfInnerQueryFields.add('c2g__HomeValue__c');
	    	setOfInnerQueryFields.add('c2g__Transaction__r.c2g__Period__r.c2g__PeriodNumber__c');	    	
			innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('SAFT_glaAcc0').Object_Name__c, setOfInnerQueryFields);   
			
	    	ReportsGenerationUtility.startDate = startDate;
	        ReportsGenerationUtility.endDate = endDate;
			listOfTransactions = (List<c2g__codaTransaction__c>)ReportsGenerationUtility.getSFDCRecordsWithSubQuery(objName, 
	                                  fieldNameSet, whereCondition, hasSubquery, innerQueryObjAndFieldsMap); 
	 
	    	if(listOfTransactions != null){
	    		setGLABalancesInfo();
	    		setGLAInfo();
	    	}
	    	getTaxCodeData();
	    	getSalesInvoicesSAFT();
	    	getPurchaseInvoicesSAFT();	   			
   		}catch(Exception e){
   			throw new CustomException('Error while querying SAF-T report data! ' + e.getMessage());
   		}
   		
   }
 
   private void getTaxCodeData(){
	   	Map<String, Set<String>> innerQueryObjAndFieldsMap = new Map<String, Set<String>>(); 	
	    Set<String> setOfInnerQueryFields = new Set<String>();
	   	Set<String> fieldNameSet = new Set<String>();
	   	Boolean hasSubquery = true;
	   	String objName, innerQueryObjName;
	   	String whereCondition = '';   	
	   	try{
	   		objName = mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_Tax1').Object_Name__c;
		    fieldNameSet = getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_Tax1').MasterLabel);
		    setOfInnerQueryFields.add('c2g__Rate__c');
		    innerQueryObjName = 'c2g__TaxRates__r';
		    innerQueryObjAndFieldsMap.put(innerQueryObjName, setOfInnerQueryFields);
		    listOfTaxCodes = (List<c2g__codaTaxCode__c>)ReportsGenerationUtility.getSFDCRecordsWithSubQuery(objName, 
	                                  fieldNameSet, whereCondition, hasSubquery, innerQueryObjAndFieldsMap); 	                          
	        if(listOfTaxCodes != null){
	        	setUpTaxInfo();
	        }   		
	   	}catch(Exception e){
        	throw new CustomException('Error while getting Tax Code information! ' + e.getMessage());
        }    
   }
   
   // To set tax information on transaction line items
   private void setUpTaxInfo(){
   		mapOfTaxCodeInfo = new Map<String, taxWrapper>();
   		String taxCodeName = '';
   	    String taxType = '';
   	    Decimal taxRate;
   		
   		for(c2g__codaTaxCode__c taxCodeInfo : listOfTaxCodes){
   			taxCodeName = ReportsGenerationUtility.getValue(taxCodeInfo, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_Tax2').SFDC_Field_Name__c);
   			
   			if(mapOfTaxCodeInfo.get(taxCodeName) == null){
   				taxType = ReportsGenerationUtility.getValue(taxCodeInfo, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_TrnLine_Tax1').SFDC_Field_Name__c);
				taxRate = 0.0;	
   				if(taxCodeInfo.c2g__TaxRates__r.size() > 0){
   					taxRate = taxCodeInfo.c2g__TaxRates__r[0].c2g__Rate__c;
   				}
				mapOfTaxCodeInfo.put(taxCodeName, new taxWrapper(taxType != null ? taxType : '', taxCodeName, String.valueOf(taxRate)));
			}				
   		}
   }
   
   private void setGLABalancesInfo(){
	   	mapOfReportingCodeOpeningDebitBal = new Map<String, String>();
	    mapOfReportingCodeOpeningCreditBal = new Map<String, String>();
	    mapOfReportingCodeClosingDebitBal = new Map<String, String>();
	    mapOfReportingCodeClosingCreditBal = new Map<String, String>();
	    
	    if(listOfTransactions != null){
	    	for(c2g__codaTransaction__c trn : listOfTransactions){	    	
		    	if(trn.c2g__TransactionLineItems__r != null){
                    
                    Date trnStartDate = Date.valueOf(ReportsGenerationUtility.getValue(trn, 'c2g__Period__r.c2g__YearName__r.c2g__StartDate__c'));//Date.valueOf(startDate);
				    Date trnEndDate = Date.valueOf(ReportsGenerationUtility.getValue(trn, 'c2g__Period__r.c2g__YearName__r.c2g__EndDate__c'));//Date.valueOf(endDate);
                    Date dateOfTransaction = Date.valueOf(ReportsGenerationUtility.getValue(trn, mappingRecordByDeveloperNameMap.get('SAFT_GLE_Jrnl_Transaction4').SFDC_Field_Name__c));
		    		for(c2g__codaTransactionLineItem__c trnLine : trn.c2g__TransactionLineItems__r){
		    			String repCode = ReportsGenerationUtility.getValue(trnLine, mappingRecordByDeveloperNameMap.get('SAFT_glaAcc1').SFDC_Field_Name__c);
				        Decimal lineItemHomeValue = Decimal.valueOf(ReportsGenerationUtility.getValue(trnLine, 'c2g__HomeValue__c'));
   
				        //startDate is start date from UI
				        if(dateOfTransaction < startDate && dateOfTransaction >= trnStartDate){
				        	if(lineItemHomeValue >= 0){
				        		Decimal updatedOpeningDb = (mapOfReportingCodeOpeningDebitBal.get(repCode) != null) ? 
	                                       Decimal.valueOf(mapOfReportingCodeOpeningDebitBal.get(repCode)) + lineItemHomeValue : 
	                                       lineItemHomeValue;
	           					 mapOfReportingCodeOpeningDebitBal.put(repCode, String.valueOf(updatedOpeningDb));
				        	}else{
				        		Decimal updatedOpeningCr = (mapOfReportingCodeOpeningCreditBal.get(repCode) != null) ? 
	                            		   Decimal.valueOf(mapOfReportingCodeOpeningCreditBal.get(repCode)) + lineItemHomeValue : 
	                                       lineItemHomeValue;
	                            mapOfReportingCodeOpeningCreditBal.put(repCode, String.valueOf(updatedOpeningCr));
				        	}
				        }else if(dateOfTransaction <= endDate && dateOfTransaction >= trnEndDate){
				        	if(lineItemHomeValue >= 0){
				        		Decimal updatedClosingDb = (mapOfReportingCodeClosingDebitBal.get(repCode) != null) ? 
	                                       Decimal.valueOf(mapOfReportingCodeClosingDebitBal.get(repCode)) + lineItemHomeValue : 
	                                       lineItemHomeValue;
	           					 mapOfReportingCodeClosingDebitBal.put(repCode, String.valueOf(updatedClosingDb));
				        	}else{
				        		Decimal updatedClosingCr = (mapOfReportingCodeClosingCreditBal.get(repCode) != null) ? 
	                            		   Decimal.valueOf(mapOfReportingCodeClosingCreditBal.get(repCode)) + lineItemHomeValue : 
	                                       lineItemHomeValue;
	                            mapOfReportingCodeClosingCreditBal.put(repCode, String.valueOf(updatedClosingCr));
				        	}
				        }
		    		}
		    	}	    	
	    	}
	    }  
   }
     
   private void setGLAInfo(){
    	mapOfGlaInfo = new Map<String, glaWrapper>();
    	String glaReportingCode, accDescription, accType, openingDb, openingCr, closingDb, closingCr;
    	
    	// Gla reporting code on transaction line
    	String fieldName = (mappingRecordByDeveloperNameMap.get('SAFT_glaAcc1').SFDC_Field_Name__c != null) ?
							mappingRecordByDeveloperNameMap.get('SAFT_glaAcc1').SFDC_Field_Name__c : '';
    	
    	 if(listOfTransactions != null){
	    	for(c2g__codaTransaction__c trn : listOfTransactions){	    	
		    	if(trn.c2g__TransactionLineItems__r != null){
		    		for(c2g__codaTransactionLineItem__c trnLine : trn.c2g__TransactionLineItems__r){
		    			glaReportingCode = ReportsGenerationUtility.getValue(trnLine, fieldName);	
		    			if(glaReportingCode != null){  
		    				accDescription = ReportsGenerationUtility.getValue(trnLine, mappingRecordByDeveloperNameMap.get('SAFT_glaAcc2').SFDC_Field_Name__c);
				    		accType = ReportsGenerationUtility.getValue(trnLine, mappingRecordByDeveloperNameMap.get('SAFT_glaAcc5').SFDC_Field_Name__c);
				    		openingDb = mapOfReportingCodeOpeningDebitBal.get(glaReportingCode);
				    		openingCr = mapOfReportingCodeOpeningCreditBal.get(glaReportingCode);
				    		closingDb = mapOfReportingCodeClosingDebitBal.get(glaReportingCode);
				    		closingCr = mapOfReportingCodeClosingCreditBal.get(glaReportingCode);					    		
				    		
			    			if(mapOfGlaInfo.get(glaReportingCode) == null){
			    				mapOfGlaInfo.put(glaReportingCode, new glaWrapper(glaReportingCode, accDescription, accType, openingDb, 
			    																	openingCr, closingDb, closingCr));
			    			}		
		    			}		    				
		    		}
		    	}
		    }
	    }
    }
    
    // Get sale invoices for SAF-T
    private void getSalesInvoicesSAFT(){
    	Map<String, Set<String>> innerQueryObjAndFieldsMap = new Map<String, Set<String>>();
	    Set<String> fieldNameSet = getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer0').MasterLabel);
	    String objName = mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer0').Object_Name__c;
	    Boolean isNested = false;
	    String whereCondition = 'c2g__Transaction__c != null and c2g__InvoiceDate__c >= :startDate and c2g__InvoiceDate__c <= :endDate'; 
    		
    	try{  	
	    	fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_Cust_OpenSalesInv1').MasterLabel));
	    	fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').MasterLabel));
	    	fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add1').MasterLabel));
            fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').MasterLabel));
	    	fieldNameSet.add('c2g__Transaction__r.c2g__Debits__c');
	    	fieldNameSet.add('c2g__Transaction__r.c2g__Credits__c');
	    		
	    	ReportsGenerationUtility.startDate = startDate;
			ReportsGenerationUtility.endDate = endDate;
	    	listOfSalesInvoices = (List<c2g__codaInvoice__c>)ReportsGenerationUtility.getSFDCRecordsWithSubQuery(objName, fieldNameSet,
	    							whereCondition, isNested, innerQueryObjAndFieldsMap);
	    			
	    	if(listOfSalesInvoices != null){
	    		processSalesInvoices(listOfSalesInvoices);
	    	}		    		
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 'Error while Sales Invoice records: ' + 
                                 e.getMessage() + ' ' + e.getStackTraceString()));   
    	}    									 
    }
    
    private void getPurchaseInvoicesSAFT(){
    	Map<String, Set<String>> innerQueryObjAndFieldsMap = new Map<String, Set<String>>();
    	Set<String> fieldNameSet = getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier0').MasterLabel);
    	String objName = mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier0').Object_Name__c;
    	Boolean isNested = false;
    	String whereCondition = 'c2g__Transaction__c != null and c2g__InvoiceDate__c >= :startDate and c2g__InvoiceDate__c <= :endDate';
    	
    	try{
    		fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_Supp_OpenPurInv1').MasterLabel));
	    	fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_SalesInv_Inv1').MasterLabel));
            fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add1').MasterLabel));
	    	fieldNameSet.addAll(getFieldNameSet(mappingRecordByDeveloperNameMap.get('SAFT_SrcDoc_Inv_DocTot1').MasterLabel));
	    	fieldNameSet.add('c2g__Transaction__r.c2g__Debits__c');
	    	fieldNameSet.add('c2g__Transaction__r.c2g__Credits__c');
	    	
	    	ReportsGenerationUtility.startDate = startDate;
			ReportsGenerationUtility.endDate = endDate;
	    	listOfPurchaseInvoices = (List<c2g__codaPurchaseInvoice__c>)ReportsGenerationUtility.getSFDCRecordsWithSubQuery(objName, fieldNameSet,
	    													 whereCondition, isNested, innerQueryObjAndFieldsMap);
	    			
	    	if(listOfPurchaseInvoices != null){
	    		processPurchaseInvoices(listOfPurchaseInvoices);
	    	}	    		
    	}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 'Error while querying Purchase Invoice Records: ' + 
                                 e.getMessage() + ' ' + e.getStackTraceString()));   
    	}    	
    }
    
    private void processPurchaseInvoices(List<c2g__codaPurchaseInvoice__c> listOfPurchaseInvoices){
    	String supplierUniqueId;
    	String supplierRegNo, supplierName, supplierStreet, supplierHouseNo, supplierCity, supplierPostalCode, supplierCountry;
    	mapOfSAFSuppliers = new Map<String, wrapSupplierAccount>();
		mapOfSupplierAndOpenPurInv = new Map<String, List<c2g__codaPurchaseInvoice__c>>();	
		
		try{
			String uniqueFieldName = (mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier1').SFDC_Field_Name__c != null) ?
										mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier1').SFDC_Field_Name__c : '';	 
	
			for(c2g__codaPurchaseInvoice__c openPurInv : listOfPurchaseInvoices){
				supplierUniqueId = ReportsGenerationUtility.getValue(openPurInv, uniqueFieldName);	
				supplierRegNo = ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier1').SFDC_Field_Name__c);
				supplierName = ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Supplier2').SFDC_Field_Name__c);
				supplierStreet = ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add1').SFDC_Field_Name__c);
                supplierHouseNo = ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add2').SFDC_Field_Name__c);
                supplierCity = ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add3').SFDC_Field_Name__c);
                supplierPostalCode = ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add4').SFDC_Field_Name__c);
                supplierCountry = ReportsGenerationUtility.getValue(openPurInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add5').SFDC_Field_Name__c);
				
				if(supplierUniqueId != null){	
					if(mapOfSupplierAndOpenPurInv.get(supplierUniqueId) == null){
						mapOfSupplierAndOpenPurInv.put(supplierUniqueId, new List<c2g__codaPurchaseInvoice__c>{openPurInv});
					}else{
						mapOfSupplierAndOpenPurInv.get(supplierUniqueId).add(openPurInv);
					}				
					mapOfSAFSuppliers.put(supplierUniqueId, new wrapSupplierAccount(supplierRegNo, supplierName, supplierStreet, supplierHouseNo, supplierCity,
                                                                                   	supplierPostalCode, supplierCountry));			
				}				
			}				
		}catch(Exception e){
    		ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 'Error while updating supplier information: ' + 
                                 e.getMessage() + ' ' + e.getStackTraceString()));   
    	} 			
    }
    
    private void processSalesInvoices(List<c2g__codaInvoice__c> listOfSalesInvoices){
    	String customerUniqueId;
    	String customerRegNo, customerName, custStreet, custHouseNo, custCity, custPostalCode, custCountry;
    	mapOfSAFCustomers = new Map<String, wrapCustomerAccount>();
		mapOfCustomerAndOpenInv =  new Map<String, List<c2g__codaInvoice__c>>();	
		try{
			String uniqueFieldName = (mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer1').SFDC_Field_Name__c != null) ?
										mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer1').SFDC_Field_Name__c : '';	 
	
			for(c2g__codaInvoice__c openInv : listOfSalesInvoices){
				customerUniqueId = ReportsGenerationUtility.getValue(openInv, uniqueFieldName);	
				customerRegNo = ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer1').SFDC_Field_Name__c);
				customerName = ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer2').SFDC_Field_Name__c);
				custStreet = ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add1').SFDC_Field_Name__c);
				custHouseNo = ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add2').SFDC_Field_Name__c);
				custCity = ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add3').SFDC_Field_Name__c);
				custPostalCode = ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add4').SFDC_Field_Name__c);
				custCountry = ReportsGenerationUtility.getValue(openInv, mappingRecordByDeveloperNameMap.get('SAFT_MF_Customer_Add5').SFDC_Field_Name__c);
						
				if(customerUniqueId != null){	
					if(mapOfCustomerAndOpenInv.get(customerUniqueId) == null){
						mapOfCustomerAndOpenInv.put(customerUniqueId, new List<c2g__codaInvoice__c>{openInv});
					}else{
						mapOfCustomerAndOpenInv.get(customerUniqueId).add(openInv);
					}
					
					mapOfSAFCustomers.put(customerUniqueId, new wrapCustomerAccount(customerRegNo, customerName, custStreet, custHouseNo,
	        						  						custCity, custPostalCode, custCountry));		
				}				
			}				
		}catch(Exception e){
			throw new MissingCustomMDTMappingsException('Error while getting mappings for Customer on Sales Invoices! ' + e.getMessage());
		}				
    }
    
    private List<sObject> queryInvoices(String objName, String fieldNameSetSrt, Set<String> additionalFields, String whereCondition){
        Map<String, Set<String>> innerQueryObjAndFieldsMap = new Map<String, Set<String>>();
        Set<String> fieldNameSet = new Set<String>();
        Set<String> setOfFields = new Set<String>();
        List<sObject> sObjectList = new List<sObject>();
        Boolean isNested = true; 
        
        try{         
                fieldNameSet = getFieldNameSet(fieldNameSetSrt);
                if(additionalFields != null && !additionalFields.isEmpty()){
                    fieldNameSet.addAll(additionalFields);
                }
   
                if(objName == 'c2g__codaInvoice__c'){
                    setOfFields = getFieldNameSet(mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal0').MasterLabel);
                    innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('salesLineItemDocTotal0').Object_Name__c, setOfFields);                                   
                }else if(objName == 'c2g__codaPurchaseInvoice__c'){         
                    setOfFields = getFieldNameSet(mappingRecordByDeveloperNameMap.get('purLineItemDocTotal1').MasterLabel);
                    innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('purLineItemDocTotal1').Object_Name__c, setOfFields);  
                    setOfFields = getFieldNameSet(mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal1').MasterLabel);
                    innerQueryObjAndFieldsMap.put(mappingRecordByDeveloperNameMap.get('purExpLineItemDocTotal1').Object_Name__c, setOfFields);             	
                }         
                ReportsGenerationUtility.startDate = startDate;
                ReportsGenerationUtility.endDate = endDate;
                sObjectList = ReportsGenerationUtility.getSFDCRecordsWithSubQuery(objName, fieldNameSet, whereCondition, isNested, innerQueryObjAndFieldsMap);
        }catch(Exception e){
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, e.getMessage() + ' ' + e.getStackTraceString()));
        }       	
        return sObjectList;
	}

	private Set<String> getFieldNameSet(String parentTagName){
		Set<String> fieldNameSet = new Set<String>();
		List<Lithuania_File_Mapping__mdt> mappingRecordList = mappingRecordByLabelMap.get(parentTagName);
		for(Lithuania_File_Mapping__mdt item : mappingRecordList){
			if(String.isNotBlank(item.SFDC_Field_Name__c)){
				fieldNameSet.add(item.SFDC_Field_Name__c);
			}
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
        
        if(label == 'iSAFFile'){
            mapOfAttributesAndLabel = getiSAFFileAttributes();
        }else if(label == 'Invoice'){
        	mapOfAttributesAndLabel.put('xsi:nil', 'true');
        }else if(label == 'SAFT_AuditFile'){
        	mapOfAttributesAndLabel = getSAFTFileAttributes();
        }
        return mapOfAttributesAndLabel;
    }
    
    private Map<String, String> getiSAFFileAttributes(){       
        Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
        
        try{ 
        	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('iSAFFile1').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('iSAFFile1').Attribute_Value__c);
        	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get('iSAFFile0').Attribute_Name__c,
    								 mappingRecordByDeveloperNameMap.get('iSAFFile0').Attribute_Value__c);       	
        }catch(Exception e){
        	ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 'Error while fetching iSAFFileAttributes: ' + 
                                                       e.getMessage() + ' ' + e.getStackTraceString()));   
        }
       
    	return mapOfAttributesAndValues;
    }
     
    private Map<String, String> getSAFTFileAttributes(){
    	Map<String, String> mapOfAttributesAndValues = new Map<String, String>();
    	String devName = '';     
        for(Integer i=3; i>=0; i--){
        	devName = 'SAFT_AuditFile' + i;
        	mapOfAttributesAndValues.put(mappingRecordByDeveloperNameMap.get(devName).Attribute_Name__c,
    								     mappingRecordByDeveloperNameMap.get(devName).Attribute_Value__c);
        }							 
    	return mapOfAttributesAndValues;
    }
    
    public void getFieldMapping(){
		mappingList = new List<Lithuania_File_Mapping__mdt>();          
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
				FROM	Lithuania_File_Mapping__mdt
				LIMIT	1000
			];
		}
	}

	private void sortFieldMapping(List<Lithuania_File_Mapping__mdt> unsortedList){
		mappingRecordByLabelMap = new Map<String, List<Lithuania_File_Mapping__mdt>>();
		mappingRecordByDeveloperNameMap = new Map<String, Lithuania_File_Mapping__mdt>();
		if(unsortedList != null && !unsortedList.isEmpty()){
			for(Lithuania_File_Mapping__mdt item : unsortedList){
				if(mappingRecordByLabelMap.get(item.MasterLabel) == null){
					mappingRecordByLabelMap.put(item.MasterLabel, new List<Lithuania_File_Mapping__mdt>{item});
				}else{
					mappingRecordByLabelMap.get(item.MasterLabel).add(item);
				}
				mappingRecordByDeveloperNameMap.put(item.DeveloperName, item);
			}
        }else{
            throw new MissingCustomMDTMappingsException('Error: Cannot find mappings for Custom MDT Lithuania_File_Mapping__mdt!');
        }
	}
    
    public class glaWrapper{
    	public String accId;
        public String accDescription;
        public String accType;        
        public String openingDb;
        public String openingCr;
        public String closingDb;
        public String closingCr;
        
        public glaWrapper(String accId, String accDescription, String accType, String openingDb, String openingCr,
        					String closingDb, String closingCr) {
            this.accId = accId;
            this.accDescription = accDescription;
            this.accType = accType;
            this.openingDb = openingDb;
            this.openingCr = openingCr;
            this.closingDb = closingDb;
            this.closingCr = closingCr;
        }        
    }
    
    @TestVisible
    public class wrapCustomerAccount {
        public String accountId;
        public String regNo;
        public String VATRegNo;
        public String country;
        public String accName;
        
        public String customerRegNo;
        public String customerName;
        public String custStreet;
        public String custHouseNo;
        public String custCity;
        public String custPostalCode;
        public String custCountry;
        
        public wrapCustomerAccount(String accountId, String regNo, String VATRegNo, String country, String accName) {
            this.accountId = accountId;
            this.regNo = regNo;
            this.VATRegNo = VATRegNo;
            this.country = country;
            this.accName = accName;
        }
             
        public wrapCustomerAccount(String customerRegNo, String customerName, String custStreet, String custHouseNo,
        						  String custCity, String custPostalCode, String custCountry) {
            this.customerRegNo = customerRegNo;
            this.customerName = customerName;
            this.custStreet = custStreet;
            this.custHouseNo = custHouseNo;
            this.custCity = custCity;
            this.custPostalCode = custPostalCode;
            this.custCountry = custCountry;
        }
    }
    
    @TestVisible
    public class wrapSupplierAccount {
        public String accountId;
        public String regNo;
        public String VATRegNo;
        public String country;
        public String accName;
        
        public String supplierRegNo;
        public String supplierName;
        public String supplierStreet;
        public String supplierHouseNo;
        public String supplierCity;
        public String supplierPostalCode;
        public String supplierCountry;

        public wrapSupplierAccount(String accountId, String regNo, String VATRegNo, String country, String accName) {
            this.accountId = accountId;
            this.regNo = regNo;
            this.VATRegNo = VATRegNo;
            this.country = country;
            this.accName = accName;
        }
        
        public wrapSupplierAccount(String supplierRegNo, String supplierName, String supplierStreet, String supplierHouseNo,
        						  String supplierCity, String supplierPostalCode, String supplierCountry) {
            this.supplierRegNo = supplierRegNo;
            this.supplierName = supplierName;
            this.supplierStreet = supplierStreet;
            this.supplierHouseNo = supplierHouseNo;
            this.supplierCity = supplierCity;
            this.supplierPostalCode = supplierPostalCode;
            this.supplierCountry = supplierCountry;
        }
    }
    
    public class taxWrapper{
    	public String taxType;
    	public String taxCode;
    	public String taxPercentage; 
    	
    	public taxWrapper(String taxType, String taxCode, String taxPercentage) {
            this.taxType = taxType;
            this.taxCode = taxCode;
            this.taxPercentage = taxPercentage;
        } 	
    }
    public class MissingCustomMDTMappingsException extends Exception{}
    public class CustomException extends Exception{}
}