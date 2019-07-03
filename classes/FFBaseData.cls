public with sharing class FFBaseData {
    public static final String TEST_COMPANY_NAME1 = 'TestCTCompany1';
    public static final String TEST_COMPANY_GROUP1 = 'FF '+'TestCTCompany1';
    public static final String TEST_COMPANY_NAME2 = 'TestCTCompany2';
    public static final String TEST_COMPANY_GROUP2 = 'FF '+'TestCTCompany2';
    public static String NAME_PRICEBOOK = 'ApexCM CTPrice Book';
    public static final Integer TEST_CURRENT_YEAR = Date.Today().Year();
    public static final String TEST_PRODUCT = 'Test CTProduct';
    public static final String TEST_ACCOUNT_USD = 'Test CTAccount 001';
    public static final String TEST_ACCOUNT_GBP = 'Test CTAccount 002';
    public static final String TEST_BANK_ACCOUNT1 = 'Test CTBank Account 001';
    public FFBaseData() {

    }

    public static void createTestData() 
	{
		try
		{
            //TODO the follwing method cannot be called in same context we have to call each method in 
            //different context
			createCompanies();
            createQueues();
            activateCompanies();
            createDataUserCompanies();
			createDataSelectCompany();
			createNewYearsPart();
			calculatePeriod();
			createData();
		}
		catch(Exception ex)
		{
			throw ex;
		}
				
	}
	
    /*
     * Destroy the data
     */
    public static void destroyData()
    {
        //Todo Disable the trigger for bypassing validation

        // Clean the org
        for(c2g__codaGeneralLedgerAccount__c[] stuff : [select Id from c2g__codaGeneralLedgerAccount__c])
            delete stuff;
        for(Opportunity[] stuff : [select Id from Opportunity])
            delete stuff;
        for(Case[] stuff : [select Id from Case])
            delete stuff;
        for(c2g__codaAccountInUse__c[] stuff : [select Id from c2g__codaAccountInUse__c])
            delete stuff;
        for(Account[] stuff : [select Id from Account])
            delete stuff;
        for(c2g__codaPeriod__c[] stuff : [select Id from c2g__codaPeriod__c])
            delete stuff;
        for(c2g__codaYear__c[] stuff : [select Id from c2g__codaYear__c])
            delete stuff;
        for(c2g__codaExchangeRate__c[] stuff : [select Id from c2g__codaExchangeRate__c])
            delete stuff;
        for(c2g__codaBankAccount__c[] stuff : [select Id from c2g__codaBankAccount__c])
            delete stuff;
        for(c2g__codaTaxRate__c[] stuff : [select Id from c2g__codaTaxRate__c])
            delete stuff;
        for(c2g__codaTaxCode__c[] stuff : [select Id from c2g__codaTaxCode__c])
            delete stuff;
        for(c2g__codaDimension1__c[] stuff : [select Id from c2g__codaDimension1__c])
            delete stuff;
        for(c2g__codaDimension2__c[] stuff : [select Id from c2g__codaDimension2__c])
            delete stuff;
        for(c2g__codaDimension3__c[] stuff : [select Id from c2g__codaDimension3__c])
            delete stuff;
        for(c2g__codaDimension4__c[] stuff : [select Id from c2g__codaDimension4__c])
            delete stuff;
        for(c2g__codaAccountingCurrency__c[] stuff : [select Id from c2g__codaAccountingCurrency__c])
            delete stuff;
        for(c2g__codaIncomeScheduleDefinition__c[] stuff : [select Id from c2g__codaIncomeScheduleDefinition__c])
            delete stuff;
        for(c2g__codaProductInUse__c[] stuff : [select Id from c2g__codaProductInUse__c])
            delete stuff;
        for(Product2 [] stuff : [select Id from Product2])
            delete stuff;
        for(PricebookEntry[] stuff : [SELECT Id FROM PricebookEntry c])
            delete stuff;
        for(Pricebook2[] stuff : [SELECT Id FROM Pricebook2 c WHERE c.Name = :NAME_PRICEBOOK])
            delete stuff;

        // Delete company rows
        for(c2g__codaUserCompany__c[] stuff : [select Id from c2g__codaUserCompany__c])
            delete stuff;
        for(c2g__codaCompany__c[] companies : [select Id from c2g__codaCompany__c])
            delete companies;
        //Todo Enable the trigger again
    }
    public static void createCompanies()
    {
        try
        {
           RecordType recType = [select Id, DeveloperName From RecordType where SobjectType = 'c2g__codaCompany__c' and DeveloperName='SUT'];
           c2g__codaCompany__c testCompany = new c2g__codaCompany__c(Name=TEST_COMPANY_NAME1, RecordTypeId=recType.id);
           insert testCompany;
           
        /*   RecordType recType1 = [select Id, DeveloperName From RecordType where SobjectType = 'c2g__codaCompany__c' and DeveloperName='VAT'];
           c2g__codaCompany__c testCompany1 = new c2g__codaCompany__c(Name=TEST_COMPANY_NAME2, RecordTypeId=recType1.id);
           insert testCompany1;*/
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public static void createQueues()
    {
        try
        {
            c2g__codaCompany__c testCompany = [Select id From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME1];
            c2g.CODACompanyWebService.createQueue(testCompany.id , 'EUR', TEST_COMPANY_NAME1 );
            
          /*  c2g__codaCompany__c testCompany1 = [Select id From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME2];
            c2g.CODACompanyWebService.createQueue2(testCompany1.id , 'EUR' );*/
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public static void activateCompanies()
    {
        try
        {
            c2g__codaCompany__c testCompany = [Select id From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME1];
            c2g.CODACompanyWebService.activateCompany(testCompany.id, 'EUR', TEST_COMPANY_NAME1);
            
           /* c2g__codaCompany__c testCompany1 = [Select id From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME2];
            c2g.CODACompanyWebService.activateCompany2(testCompany1.id , 'EUR' );*/
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public static void createDataUserCompanies()
    {
        try
        {
            c2g__codaCompany__c testCompany = [Select id From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME1];
           // c2g__codaCompany__c testCompany1 = [Select id From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME2];
            //assign company to user
            c2g__codaUserCompany__c testUserCompany1 = new c2g__codaUserCompany__c(c2g__User__c=UserInfo.getUserId(),c2g__Company__c=testCompany.id);
            insert testUserCompany1;
            
           // c2g__codaUserCompany__c testUserCompany2 = new c2g__codaUserCompany__c(c2g__User__c=UserInfo.getUserId(),c2g__Company__c=testCompany1.id);
           // insert testUserCompany2;
        }
        catch(Exception e)
        {
            throw e;
        }
     }
    public static void createDataSelectCompany()
    {
        try
        {
            list<Group> lstGroup =  [Select id from Group where Name = :TEST_COMPANY_GROUP1];
            Group testGroup = lstGroup.get(0);
            GroupMember testGroupMember = new GroupMember(UserOrGroupId=UserInfo.getUserId(),GroupId=testGroup.id);
            insert testGroupMember;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public static void createNewYearsPart()
    {
        try
        {
            c2g__codaCompany__c testCompany = [Select id, ownerid From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME1];
			
        	c2g__codaYear__c testYear = new c2g__codaYear__c(Name=String.valueOf(TEST_CURRENT_YEAR-1), c2g__StartDate__c=Date.newInstance(TEST_CURRENT_YEAR-1,01,01), 
        	c2g__NumberOfPeriods__c=12, c2g__OwnerCompany__c=testCompany.Id, ownerid=testCompany.ownerid, c2g__PeriodCalculationBasis__c='Month End',
        	c2g__EndDate__c=Date.newInstance(TEST_CURRENT_YEAR-1,12,31));
        	insert new List<c2g__codaYear__c>{testYear};

        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    public static void calculatePeriod()
    {
        try
        {
            String yname = String.valueOf(TEST_CURRENT_YEAR-1);
			c2g__codaYear__c testYear = [Select id from c2g__codaYear__c where name = :yname][0];
        	String returnedString = c2g.CODAYearWebService.calculatePeriods(testYear.id);
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public static void createData()
    {
        try
        {
            if(UserInfo.isMultiCurrencyOrganization())
            {
                List<c2g__codaAccountingCurrency__c> accCurrList = new List<c2g__codaAccountingCurrency__c>();
                accCurrList.add(createCurr('EUR', true, false));
                accCurrList.add(createCurr('GBP', false, true));
                insert accCurrList;

                List<Decimal> rateList = new List<Decimal>();
                rateList.add(0.5);
                createExcRate(accCurrList, rateList);
            }
			
			//insert GLA
			c2g__codaGeneralLedgerAccount__c gla = new c2g__codaGeneralLedgerAccount__c(Name='Bank Account GLA', c2g__ReportingCode__c='RC-0001', c2g__Type__c='Balance Sheet', c2g__TrialBalance1__c='Balance Sheet',
                                                         c2g__TrialBalance2__c='Current Assets', c2g__TrialBalance3__c='Bank and Cash', c2g__BalanceSheet1__c='Balance Sheet', 
                                                         c2g__BalanceSheet2__c='Current Assets', c2g__BalanceSheet3__c='Bank and Cash');
        	
        	insert gla;

			c2g__codaCompany__c testCompany = [Select id From c2g__codaCompany__c Where Name = :TEST_COMPANY_NAME1];
			c2g__codaAccountingCurrency__c curr = [Select id from c2g__codaAccountingCurrency__c where Name='EUR' and c2g__OwnerCompany__c=:testCompany.id];
			//insert bank account
			c2g__codaBankAccount__c bankAccount = new c2g__codaBankAccount__c(Name=TEST_BANK_ACCOUNT1, c2g__ReportingCode__c='RC-001', c2g__BankName__c=TEST_BANK_ACCOUNT1, c2g__AccountName__c=TEST_BANK_ACCOUNT1, 
                            c2g__AccountNumber__c=TEST_BANK_ACCOUNT1, c2g__BankAccountCurrency__c=curr.Id, c2g__GeneralLedgerAccount__c=gla.Id, 
                            c2g__BankChargesGLA__c=gla.Id, c2g__InterestPaidGLA__c=gla.Id);
        	
        	insert bankAccount;
        	
        	List<Product2> prodList = new List<Product2>();
        	prodList.add(createProd(TEST_PRODUCT, gla.id, gla.id, null, null, ''));
        	
        	insert prodList;
        	
        	List<Account> accList = new List<Account>();
        	accList.add(createAcc(TEST_ACCOUNT_USD, 'EUR', gla.id, gla.id, 'EUR', 'Standard', 'Invoice Date', 5, 0.00, gla.id));
        	accList.add(createAcc(TEST_ACCOUNT_GBP, 'GBP', gla.id, gla.id, 'GBP', 'Standard', 'Invoice Date', 5, 0.00, gla.id));
        	
        	insert accList;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    private static boolean isUserInGroup(Id userid, Id groupid)
    {
        try
        {
            GroupMember groupmember = [Select id From GroupMember g where g.UserOrGroupId = :userid and g.GroupId = :groupid];
        }
        catch (System.QueryException sqe)
        {
            return false; 
        }
        
        return true;    
    }
    
    private static c2g__codaAccountingCurrency__c createCurr(String currencyCode, boolean isHome, boolean isDual)
    {
        c2g__codaAccountingCurrency__c retCurr = new c2g__codaAccountingCurrency__c(Name=currencyCode, c2g__Home__c=isHome, c2g__Dual__c=isDual, c2g__DecimalPlaces__c=2);
        if (UserInfo.isMultiCurrencyOrganization())
        {
            retCurr.put('CurrencyISOCode', currencyCode);
        }
        
        return retCurr;
    }
    
    private static void createExcRate(c2g__codaAccountingCurrency__c[] accCurrList, List<Decimal> rateList)
    {
        List<c2g__codaExchangeRate__c> currRateList = new List<c2g__codaExchangeRate__c>();
        integer i = 0;
        for (c2g__codaAccountingCurrency__c accCurr : accCurrList)
        {
            if (!accCurr.c2g__Home__c)
            {
                c2g__codaExchangeRate__c currRate = new c2g__codaExchangeRate__c(c2g__ExchangeRateCurrency__c=accCurr.id, c2g__StartDate__c=date.newinstance(TEST_CURRENT_YEAR-1,01,01), c2g__Rate__c=rateList[i]);
                i++;
                currRateList.add(currRate);
            }
        }
        insert currRateList;
    }
    
    private static Product2 createProd(string prodName, id purchAnalysisAcc, id salesRevAcc, id inputTaxCode, id outputTaxCode, string salesTaxStatus)
    {
        Product2 retProduct = new Product2(Name=prodName, ProductCode = prodName, c2g__CODAInputTaxCode__c = inputTaxCode, c2g__CODATaxCode__c = outputTaxCode, 
            c2g__CODAPurchaseAnalysisAccount__c = purchAnalysisAcc, c2g__CODASalesRevenueAccount__c = salesRevAcc, c2g__CODASalesTaxStatus__c = salesTaxStatus, IsActive = true);
        return retProduct;
    }
    
    private static Account createAcc(string accountName, string tradecurr, id accRecGla, id accPayGla, string curr,
                	string description1, string baseDate1, integer daysOffset1, decimal discount1, id glaDefaultExpense)
    {
        Account retAcc = new Account(Name= accountName, c2g__CODAAccountsPayableControl__c = accPayGla, 
            c2g__CODAAccountsReceivableControl__c = accRecGla,c2g__CODADescription1__c = description1, c2g__CODABaseDate1__c = baseDate1, 
            c2g__CODADaysOffset1__c = daysOffset1, c2g__CODADiscount1__c = discount1, c2g__CODADefaultExpenseAccount__c=glaDefaultExpense);
             
        if (UserInfo.isMultiCurrencyOrganization())
        {
            retAcc.put('CurrencyISOCode',  curr);
        }
        
        if(getFieldMap('Account').containsKey('c2g__CODAAccountTradingCurrency__c'.toLowerCase()))
		{
			retAcc.put('c2g__CODAAccountTradingCurrency__c', tradecurr);
		}
		return retAcc;
    }
    
    private static Map<String, SObjectField> getFieldMap(String objectName)
    {
	     Map<String, SObjectField> fieldMap = new Map<String, SObjectField>();
	     for(SObjectField field : Schema.getGlobalDescribe().get(objectName).getDescribe().fields.getMap().Values())
	     {
	      	fieldMap.put(field.getDescribe().getName().toLowerCase(), field);
	     }
	     return fieldMap;
    }
}