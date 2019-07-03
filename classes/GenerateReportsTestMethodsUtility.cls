public class GenerateReportsTestMethodsUtility {
	
    public static List<c2g__codaPurchaseInvoice__c> listOfPurchaseInvoices;
  //  public static c2g__codaTaxCode__c taxcode = [select id, Name from c2g__codaTaxCode__c limit 1];  
    public static Account supplierAcc;
    
    // Create common test data for all test methods for reports generation
    public static void testSetup() {
		Integer numOfCompanies = 1;
		String companyName = 'TestCompany';
		Integer currentCompany = 1;
		String taxRegistrationNumber = 'TAX11234';
		User someUser = new User(Id = UserInfo.getUserId());
		Integer accsPerGLA = 1;
		Integer numOfInvoicesPerAccount = 1;
		
		CreateTestData.setupFullTestData(numOfCompanies, companyName, currentCompany, taxRegistrationNumber, someUser, accsPerGLA, numOfInvoicesPerAccount);
        
		c2g__codaTaxCode__c taxcode = [select id, Name from c2g__codaTaxCode__c limit 1];   
        List<c2g__codaTaxRate__c> listOfTaxRate = new List<c2g__codaTaxRate__c>();
        for(Integer i=0; i<5; i++){
            c2g__codaTaxRate__c taxRate = new c2g__codaTaxRate__c(
                													c2g__TaxCode__c = taxcode.Id,
                                                                 	c2g__Rate__c = 2.5
                                                                 );                                                                             
            listOfTaxRate.add(taxRate);    
        }
        insert listOfTaxRate;
        
        Account acc = [select Id, Name, c2g__CODAVATRegistrationNumber__c, c2g__CODAECCountryCode__c from Account limit 1]; 
        acc.c2g__CODAECCountryCode__c = 'LV';
        acc.c2g__CODAVATRegistrationNumber__c = 'LT23123';
        acc.AccountNumber = '2423422';
        update acc;
        
		acc = [
                   SELECT
                        Id, c2g__CODAVATRegistrationNumber__c, c2g__CODAECCountryCode__c
                   FROM Account 
                   WHERE c2g__CODAVATRegistrationNumber__c = 'LT23123' limit 1
       		  ];                
        createAccount();
		     
        // To set c2g__Account__r.c2g__CODAVATRegistrationNumber__c 
        List<c2g__codaInvoice__c> codaInvList = [select 
                                                   	   Id, 
                                                       c2g__Account__c, 
                                                   	   c2g__invoiceTotal__c, 
                                                   	   c2g__Period__r.c2g__StartDate__c 
                                                  from c2g__codaInvoice__c 
                                                  where c2g__invoiceTotal__c > 45 AND 
                                                   		c2g__Period__r.c2g__StartDate__c >= 2017-01-01 limit 2];
       
        List<c2g__codaInvoice__c> codaInvlistToupdate = new List<c2g__codaInvoice__c>();       
        for(c2g__codaInvoice__c inv : codaInvList){
            inv.c2g__Account__c = acc.Id;           
            codaInvlistToupdate.add(inv);          
        }
        update codaInvlistToupdate; 
        
        //To set c2g__Period__c on c2g__codaPurchaseInvoice__c 
        c2g__codaPeriod__c period = [select Id, c2g__StartDate__c from c2g__codaPeriod__c where c2g__StartDate__c != null limit 1];
        supplierAcc = [
            			SELECT 
                       		Id, Name, c2g__CODAVATRegistrationNumber__c, AccountNumber, c2g__CODAECCountryCode__c
					   FROM Account 
                       WHERE Name = 'Test Account 0' 
                       LIMIT 1
        			];    
        
        supplierAcc.c2g__CODAECCountryCode__c = 'LV';
        supplierAcc.c2g__CODAVATRegistrationNumber__c = 'LT2312345';
        supplierAcc.AccountNumber = '2423401';
        update supplierAcc;
        
        List<String> listOfCountryCodes = new List<String>{'LV', 'EU'};
        // Create list of purchase invoices
        listOfPurchaseInvoices = new List<c2g__codaPurchaseInvoice__c>();        
        for(Integer i = 0; i < 2; i++){
        	c2g__codaPurchaseInvoice__c pInvoice = new c2g__codaPurchaseInvoice__c(    
                                                                                        c2g__TaxCode1__c = taxcode.Id,
                                                                                        c2g__Period__c = period.Id,
                                                                                        CurrencyIsoCode = 'EUR',
                                                                                        c2g__InvoiceDate__c = Date.today().addDays(-5),
                                                                                        c2g__DueDate__c = Date.today().addDays(5),
                                                                                        c2g__Account__c = supplierAcc.Id,
                                                                                        c2g__InvoiceStatus__c = 'In Progress',                                                                                  
                                                                                        c2g__AccountInvoiceNumber__c = '2897' + String.valueOf(i)
                   																 	);
            listOfPurchaseInvoices.add(pInvoice);         
        }        
        insert listOfPurchaseInvoices;   
        createPurchaseInvoiceLineItems();
	} 
    
    public static void createPurchaseInvoiceLineItems(){
        
        // Create list of purchase expense line items and purchase line items
        c2g__codaPurchaseInvoiceExpenseLineItem__c purInvoiceExpenseLineItem;
        c2g__codaPurchaseInvoiceLineItem__c purInvLine; 
        List<c2g__codaPurchaseInvoiceExpenseLineItem__c> listOfPurExpenseLineItem = new List<c2g__codaPurchaseInvoiceExpenseLineItem__c>();
        List<c2g__codaPurchaseInvoiceLineItem__c> listOfPurInvoiceLineItem = new List<c2g__codaPurchaseInvoiceLineItem__c>();
        c2g__codaGeneralLedgerAccount__c gla = [select Id from c2g__codaGeneralLedgerAccount__c limit 1];
        Product2 prod = [select Id from Product2 limit 1];
        prod.c2g__CODAPurchaseAnalysisAccount__c = gla.Id;
        update prod;
        
        listOfPurchaseInvoices = [select Id from c2g__codaPurchaseInvoice__c];
        
        for(c2g__codaPurchaseInvoice__c purInv: listOfPurchaseInvoices) {
                purInvoiceExpenseLineItem = new c2g__codaPurchaseInvoiceExpenseLineItem__c(
                        					c2g__PurchaseInvoice__c = purInv.Id,
											c2g__GeneralLedgerAccount__c = gla.Id,
                    						c2g__NetValue__c = 50,
                    						c2g__UnitOfWork__c = 2
                );
                listOfPurExpenseLineItem.add(purInvoiceExpenseLineItem);
            
            	purInvLine = new c2g__codaPurchaseInvoiceLineItem__c(
                        					c2g__PurchaseInvoice__c = purInv.Id,
                    						c2g__UnitOfWork__c = 2,
                    						c2g__Product__c = prod.Id
                );
                listOfPurInvoiceLineItem.add(purInvLine);
        }
        insert listOfPurExpenseLineItem;
        insert listOfPurInvoiceLineItem;       
    }
    
    public static void createAccount(){
        Account acc;
        List<Account> accList = new List<Account>();
		List<c2g__codaGeneralLedgerAccount__c> glaList = [SELECT Id, Name FROM c2g__codaGeneralLedgerAccount__c];
        
        for (c2g__codaGeneralLedgerAccount__c gla: glaList) {
            for (Integer i = 0; i < 1; i++) {
                acc = new Account(
                        Name = 'Test Account ' + String.valueOf(i),
                        BillingStreet = 'BillingStreet',
                        BillingCity = 'BillingCity',
                        BillingCountry = 'United States',
                        c2g__CODAAccountTradingCurrency__c = 'EUR',
                        c2g__CODAAccountsReceivableControl__c = gla.Id,
                        c2g__CODAAccountsPayableControl__c = gla.Id
                );
                accList.add(acc);
            }
        }
        insert accList;
    }
    
}