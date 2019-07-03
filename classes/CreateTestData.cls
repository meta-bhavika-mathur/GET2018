@isTest
public class CreateTestData {
  public static List<c2g__codaAccountingCurrency__c> accountCurrencyList;
  public static List<Group> compOwnerQueueList;
  public static final List<String> SOBJECT_TYPES_API_NAMES = new List<String>{
      'c2g__AsOfAging__c',
      'c2g__CancelPayment__c',
      'c2g__ReportingBalance__c',
      'c2g__codaAccountingCurrency__c',
      'c2g__codaBankAccount__c',
      'c2g__codaBankReconciliation__c',
      'c2g__codaBankStatement__c',
      'c2g__codaBudget__c',
      'c2g__codaCashEntry__c',
      'c2g__codaCashMatchingHistory__c',
      'c2g__codaCompany__c',
      'c2g__codaCreditNote__c',
      'c2g__codaCurrencyRevaluationGroup__c',
      'c2g__codaCurrencyRevaluation__c',
      'c2g__codaGroupingReference__c',
      'c2g__codaIntercompanyDefinition__c',
      'c2g__codaIntercompanyTransfer__c',
      'c2g__codaInvoice__c',
      'c2g__codaJournal__c',
      'c2g__codaMatchingReference__c',
      'c2g__codaPaymentMediaControl__c',
      'c2g__codaPaymentMediaSummary__c',
      'c2g__codaPaymentTemplate__c',
      'c2g__codaPayment__c',
      'c2g__codaPurchaseCreditNote__c',
      'c2g__codaPurchaseInvoice__c',
      'c2g__codaTextDefinition__c',
      'c2g__codaTransaction__c',
      'c2g__codaYear__c'
  };
  public static void setupFullTestData(Integer numOfCompanies, String companyName, Integer currentCompany, String taxRegistrationNumber, User someUser, Integer accsPerGLA, Integer numOfInvoicesPerAccount) {
    //c2g__codaCompany__c
    List<c2g__codaCompany__c> companyList = createCompany(numOfCompanies, companyName, currentCompany, taxRegistrationNumber, someUser);
    System.debug('company');

    //c2g__codaGeneralLedgerAccount__c
    List<c2g__codaGeneralLedgerAccount__c> glaList = createGLA(companyList.size());
    System.debug('GLA');

    //c2g__codaTaxCode__c
    List<c2g__codaTaxCode__c> taxCodeList = createTaxCode(glaList);
    System.debug('c2g__codaTaxCode__c');

    //Account
    List<Account> accList = createAccount(glaList, accsPerGLA);
    System.debug('account');

    //Product2
    List<Product2> prodList = createProducts(glaList);
    System.debug('product');

    //Pricebook2
    List<Pricebook2> pricebookList = createPricebook(prodList);
    System.debug('pricebook');

    //PricebookEntry with custom Pricebook2
    List<PricebookEntry> pricebookEntryCustList = createPricebookEntry(prodList, pricebookList);
    System.debug('pricebookentry');

    //c2g__codaInvoice__c + c2g__codaInvoiceLineItem__c
    createInvoice(companyList, compOwnerQueueList, accList, prodList, accountCurrencyList, numOfInvoicesPerAccount);
     
    createAndPostJournals(glaList);

  }
  private static List<c2g__codaCompany__c> createCompany(Integer numOfCompanies, String companyName, Integer currentCompany, String taxRegistrationNumber, User someUser) {
    String recordType = Schema.SObjectType.c2g__codaCompany__c.RecordTypeInfosByName.get('VAT').RecordTypeId;

    Group companyOwnerQueue;
    List<Group> companyOwnerQueueList = new List<Group>();
    GroupMember queueMember;
    QueueSObject queuesObjectType;
    List<QueueSObject> queueSObjectList = new List<QueueSObject>();
    c2g__codaCompany__c company;
    List<c2g__codaCompany__c> companyList = new List<c2g__codaCompany__c>();
    c2g__codaUserCompany__c userCompany;
    List<c2g__codaUserCompany__c> userCompanyList = new List<c2g__codaUserCompany__c>();
    c2g__codaYear__c year;
    List<c2g__codaYear__c> yearList = new List<c2g__codaYear__c>();
    c2g__codaPeriod__c period;
    List<c2g__codaPeriod__c> periodList = new List<c2g__codaPeriod__c>();
    c2g__codaAccountingCurrency__c accCurrency;
    List<c2g__codaAccountingCurrency__c> accCurrencyList = new List<c2g__codaAccountingCurrency__c>();

    if (numOfCompanies == null || numOfCompanies < 0 || numOfCompanies > 10) {
      numOfCompanies = 10;
    }

    //create the company user queue
    for (Integer i = 0; i < numOfCompanies; i++) {
      companyOwnerQueue = new Group();
      companyOwnerQueue.Type = 'Queue';
      companyOwnerQueue.Name = companyName + String.valueOf(i);
      companyOwnerQueueList.add(companyOwnerQueue);


    }

    System.runAs(someUser) {
      insert companyOwnerQueueList;
      compOwnerQueueList = companyOwnerQueueList;

      //add SObjectType to be owned by the company user queue instead of its individual queue members (users)
      for (Group ownerQueue: companyOwnerQueueList) {
        for (String sobjectTypeAPIName : SOBJECT_TYPES_API_NAMES) {
          queuesObjectType = new QueueSObject(
              SobjectType = sobjectTypeAPIName,
              QueueId = ownerQueue.Id
          );
          queueSObjectList.add(queuesObjectType);
        }
      }
      insert queueSObjectList;
      // select company as current
      if (currentCompany != null && currentCompany - 1 >= 0 && companyOwnerQueueList.size() > currentCompany - 1) {
        queueMember = new GroupMember(
            GroupId = companyOwnerQueueList[currentCompany - 1].Id,
            UserOrGroupId = someUser.ID
        );
        insert queueMember;
      }
    }

    //create the company
    for (Integer i = 0; i < numOfCompanies; i++) {
      company = new c2g__codaCompany__c(
          Name = companyName + String.valueOf(i),
          c2g__VATRegistrationNumber__c = taxRegistrationNumber + String.valueOf(i),
          OwnerId = companyOwnerQueueList[i].Id,
          c2g__CashMatchingCurrencyMode__c = 'Account'
      );
      if (recordType != null) {
        company.RecordTypeId = recordType;
      }
      companyList.add(company);
    }

    System.runAs(someUser) {
      insert companyList;
      //give the user access to this company
      for (c2g__codaCompany__c comp: companyList) {
        userCompany = new c2g__codaUserCompany__c(
            c2g__Company__c = comp.Id,
            c2g__User__c = someUser.Id
        );
        userCompanyList.add(userCompany);
      }
      insert userCompanyList;
    }

    //Create financial year
    //TODO implement only for the current company
    //for (Integer i = 0; i < numOfCompanies; i++) {
    if (currentCompany != null && currentCompany - 1 >= 0 && companyOwnerQueueList.size() > currentCompany - 1) {
      year = new c2g__codaYear__c(
          Name = String.valueOf(Date.today().year()),
          c2g__AutomaticPeriodList__c = true,
          c2g__StartDate__c = system.today().addMonths(-1),
          c2g__Status__c = 'Open',
          c2g__UnitOfWork__c = 12,
          c2g__NumberOfPeriods__c = 11,
          c2g__PeriodCalculationBasis__c = '445',
          c2g__OwnerCompany__c = companyList[currentCompany - 1].id,
          OwnerId = companyOwnerQueueList[currentCompany - 1].Id
      );
      yearList.add(year);
    }
    insert yearList;

    //Create financial period
    //TODO implement only for the current company
    //for (Integer i = 0; i < numOfCompanies; i++) {
    for (c2g__codaYear__c yr: yearList) {
      period = new c2g__codaPeriod__c(
          Name = 'Test ' + String.valueOf(Date.today().year()),
          c2g__StartDate__c = System.today().addMonths(-1),
          c2g__EndDate__c = System.today().addMonths(1),
          c2g__OwnerCompany__c = company.id,
          c2g__PeriodNumber__c = '123',
          c2g__Description__c = 'Trading Period',
          c2g__YearName__c = yr.id
      );
      periodList.add(period);
    }
    insert periodList;

    // Create Accounting Currency
    if (UserInfo.isMultiCurrencyOrganization()) {
      Boolean homeCur = true;
      Boolean dualCur = true;
      for (Integer i = 0; i < numOfCompanies; i++) {
        if (i == 0) {
          homeCur = true;
          dualCur = true;
        } else {
          homeCur = false;
          dualCur = false;
        }
        accCurrency = new c2g__codaAccountingCurrency__c(
            Name = 'EUR',
            c2g__OwnerCompany__c = companyList[i].id,
            c2g__DecimalPlaces__c = 2,
            c2g__Home__c = homeCur,
            c2g__Dual__c = dualCur,
            OwnerId = companyOwnerQueueList[i].Id
        );
        accCurrencyList.add(accCurrency);
      }
      insert accCurrencyList;
      accountCurrencyList = accCurrencyList;
    }

    return companyList;
  }
  private static List<c2g__codaGeneralLedgerAccount__c> createGLA(Integer numOfGLA) {
    c2g__codaGeneralLedgerAccount__c gla;
    List<c2g__codaGeneralLedgerAccount__c> glaList = new List<c2g__codaGeneralLedgerAccount__c>();
    if (numOfGLA != null && numOfGLA < 11 || numOfGLA > 0) {
      for (Integer i = 0; i < numOfGLA; i++) {
        gla = new c2g__codaGeneralLedgerAccount__c(
            Name = 'GLA ' + String.valueOf(i) + ' - Cash in bank- EUR',
            c2g__ReportingCode__c = '110' + String.valueOf(i) + '00',
            c2g__Type__c = 'Balance Sheet',
            c2g__AllowRevaluation__c = false,
            c2g__TrialBalance1__c = 'Net Assets',
            c2g__TrialBalance2__c = 'Total assets',
            c2g__TrialBalance3__c = 'Current assets',
            c2g__TrialBalance4__c = 'Cash and cash equivalents',
            c2g__BalanceSheet1__c = 'Balance Sheet',
            c2g__BalanceSheet2__c = 'Current Assets',
            c2g__BalanceSheet3__c = '5131 Banks current accounts'
        );
        glaList.add(gla);
      }
      insert glaList;
      return glaList;
    } else {
      return null;
    }
  }
  private static List<Account> createAccount(List<c2g__codaGeneralLedgerAccount__c> glaList, Integer accsPerGLA) {
    Account acc;
    List<Account> accList = new List<Account>();

    for (c2g__codaGeneralLedgerAccount__c gla: glaList) {
      for (Integer i = 0; i < accsPerGLA; i++) {
        acc = new Account(
            Name = '(' + gla.Name + ') test Account ' + String.valueOf(i),
            BillingStreet = 'BillingStreet',
            BillingCity = 'BillingCity',
            BillingCountry = 'United States',
            CurrencyIsoCode = 'EUR',
            //c2g__CODABankIBANNumber__c = 'NL41INGB000046759' + String.valueOf(i),
            c2g__CODAAccountTradingCurrency__c = 'EUR',
            c2g__CODAAccountsReceivableControl__c = gla.Id,
            c2g__CODAAccountsPayableControl__c = gla.Id
        );
        accList.add(acc);
        //System.debug('IBAN: ' + acc.c2g__CODABankIBANNumber__c);
      }
    }
    insert accList;
    return accList;
  }
  private static List<Product2> createProducts(List<c2g__codaGeneralLedgerAccount__c> glaList) {
    Product2 prod;
    List<Product2> prodList = new List<Product2>();
    for (c2g__codaGeneralLedgerAccount__c gla: glaList) {
      prod = new Product2(
          Name = 'Product Sample (' + gla.Name + ')',
          Family = 'Product',
          IsActive = true,
          c2g__CODASalesRevenueAccount__c = gla.Id
      );
      prodList.add(prod);
    }
    insert prodList;
    return prodList;
  }
  private static List<Pricebook2> createPricebook(List<Product2> prodList) {
    Pricebook2 pricebook;
    List<Pricebook2> pricebookList = new List<Pricebook2>();

    for (Product2 prod: prodList) {
      pricebook = new Pricebook2(
          Name = 'Custom Pricebook (' + prod.Name + ')',
          isActive = true
      );
      pricebookList.add(pricebook);
    }
    insert pricebookList;
    return pricebookList;
  }
  private static List<PricebookEntry> createPricebookEntry(List<Product2> prodList, List<Pricebook2> pricebookList) {
    PricebookEntry pricebookEnt;
    List<PricebookEntry> pricebookEntryList = new List<PricebookEntry>();
    for (Product2 prod: prodList) {
      pricebookEnt =new PricebookEntry(
          Pricebook2Id = Test.getStandardPricebookId(),
          Product2Id = prod.Id,
          UnitPrice = Math.round((Math.random() + 0.1)*1000000000)/100.0,
          IsActive = true
      );
      pricebookEntryList.add(pricebookEnt);
    }
    for (Integer i = 0; i < prodList.size(); i++) {
      pricebookEnt =new PricebookEntry(
          Pricebook2Id = pricebookList[i].Id,
          Product2Id = prodList[i].Id,
          UnitPrice = Math.round((Math.random() + 0.1)*1200000000)/100.0,
          IsActive = true
      );
      pricebookEntryList.add(pricebookEnt);
    }
    insert pricebookEntryList;
    return pricebookEntryList;
  }
  private static List<c2g__codaInvoice__c> createInvoice(List<c2g__codaCompany__c> companyList, List<Group> companyOwnerQueueList, List<Account> accList, List<Product2> prodList, List<c2g__codaAccountingCurrency__c> accountCurrencyList, Integer numOfInvoicesPerAccount) {
    //List<c2g__codaInvoice__c> invoiceList = new List<c2g__codaInvoice__c>();
    Database.executeBatch(new GenerateInvoices(companyList, companyOwnerQueueList, accList, prodList, accountCurrencyList, numOfInvoicesPerAccount), 50);
    return null;
  }
  private static String genIBAN () {
    String num = String.valueOf(Math.random()*10000000000.0).remove('.');
    Integer numLength = num.length();
    if (numLength < 10) {
      for (Integer i = numLength; i < 10; numLength++) {
        num = '0' + num;
      }
    } else if (numLength > 10) {
      num.left(10);
    }
    return num;
  }
    
  // To craete Journals
  public static void createAndPostJournals(List<c2g__codaGeneralLedgerAccount__c> glaList){
     
     c2g__codaJournal__c journal = new c2g__codaJournal__c();
     journal.c2g__JournalDate__c = Date.today();
     insert journal;
      
     List<c2g__codaJournalLineItem__c> listOfJrnlLines = new List<c2g__codaJournalLineItem__c>();
     c2g__codaJournalLineItem__c journalLine1 = new c2g__codaJournalLineItem__c();
     journalLine1.c2g__LineType__c = 'General Ledger Account';
     journalLine1.c2g__Journal__c = journal.Id;
 	 journalLine1.c2g__GeneralLedgerAccount__c = glaList[0].Id;
     journalLine1.c2g__Value__c = 42;
     listOfJrnlLines.add(journalLine1);
      
     c2g__codaJournalLineItem__c journalLine2 = new c2g__codaJournalLineItem__c();
     journalLine2.c2g__LineType__c = 'General Ledger Account'; 
     journalLine2.c2g__Journal__c = journal.Id;
  	 journalLine2.c2g__GeneralLedgerAccount__c = glaList[0].Id;
     journalLine2.c2g__Value__c = -42;
     listOfJrnlLines.add(journalLine2);
      
     insert listOfJrnlLines;
     // Post Journals
     c2g.CODAAPIJournal_12_0.PostJournal(null, c2g.CODAAPICommon.getRef(journal.Id, null));        
  }
    
  public static void postInvoices(Integer lim){
    c2g.CODAAPICommon_10_0.Context cContext = new c2g.CODAAPICommon_10_0.Context();

    if (lim == null || lim < 0) {
      lim = 1;
    }

    List<c2g__codaInvoice__c> invoicesToPost = [
        SELECT  Id
        FROM    c2g__codaInvoice__c
        WHERE   c2g__InvoiceStatus__c = 'In Progress' AND
            c2g__InvoiceTotal__c > 0
        LIMIT   :lim
    ];

    List<c2g.CODAAPICommon.Reference> invoiceRefs = new List<c2g.CODAAPICommon.Reference>();
    for (c2g__codaInvoice__c invoiceRef : invoicesToPost){
      system.debug('posting:'+invoiceRef.id);
      invoiceRefs.add(c2g.CODAAPICommon.getRef(invoiceRef.id, null));
    }
    try{
      c2g.CODAAPISalesInvoice_10_0.BulkPostInvoice(cContext, invoiceRefs);
    }catch(Exception e) {
      system.debug('Fill Sales Revenue Account field on Sales Invoice Line Item product. Error message:' + e);
    }

  }
  public static void postPInvoices(Integer lim){
    c2g.CODAAPICommon_9_0.Context cContext = new c2g.CODAAPICommon_9_0.Context();

    if (lim == null || lim < 0) {
      lim = 1;
    }

    List<c2g__codaPurchaseInvoice__c> invoicesToPost = [
        SELECT  id
        FROM    c2g__codaPurchaseInvoice__c
        WHERE   c2g__InvoiceStatus__c = 'In Progress' AND
        c2g__InvoiceTotal__c > 0
        LIMIT   :lim
    ];

    List<c2g.CODAAPICommon.Reference> invoiceRefs = new List<c2g.CODAAPICommon.Reference>();
    for (c2g__codaPurchaseInvoice__c invoiceRef : invoicesToPost){
      system.debug('posting:'+invoiceRef.id);
      invoiceRefs.add(c2g.CODAAPICommon.getRef(invoiceRef.id, null));
    }
    try{
      c2g.CODAAPIPurchaseInvoice_9_0.BulkPostPurchaseInvoice(cContext, invoiceRefs);
    }catch(Exception e) {
      system.debug('Fill Sales Revenue Account field on Sales Invoice Line Item product. Error message:' + e);
    }
  }
  private static List<c2g__codaTaxCode__c> createTaxCode(List<c2g__codaGeneralLedgerAccount__c> glaList) {
    c2g__codaTaxCode__c taxCode;
    List<c2g__codaTaxCode__c> taxCodeList = new List<c2g__codaTaxCode__c>();
    for (c2g__codaGeneralLedgerAccount__c gla: glaList) {
      taxCode = new c2g__codaTaxCode__c(
          Name = '',
          c2g__GeneralLedgerAccount__c = gla.Id
      );
      taxCodeList.add(taxCode);
    }
    insert taxCodeList;
    return taxCodeList;
  }
}