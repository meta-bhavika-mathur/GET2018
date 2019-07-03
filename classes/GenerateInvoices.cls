global class GenerateInvoices implements Database.Batchable<sObject>{
  global final List<c2g__codaCompany__c> companyList;
  global final List<Group> companyOwnerQueueList;
  global final List<Account> accList;
  global final List<Product2> prodList;
  global final List<c2g__codaAccountingCurrency__c> accountCurrencyList;
  global final Integer numOfInvoicesPerAccount;

  global GenerateInvoices(List<c2g__codaCompany__c> companyList, List<Group> companyOwnerQueueList, List<Account> accList, List<Product2> prodList, List<c2g__codaAccountingCurrency__c> accountCurrencyList, Integer numOfInvoicesPerAccount){
    this.companyList = companyList;
    this.companyOwnerQueueList = companyOwnerQueueList;
    this.accList = accList;
    this.prodList = prodList;
    this.accountCurrencyList = accountCurrencyList;
    this.numOfInvoicesPerAccount = numOfInvoicesPerAccount;
  }

  global Database.QueryLocator start(Database.BatchableContext BC){
    Set<Id> companyIdSet = new Set<Id>();
    for (c2g__codaCompany__c company: companyList) {
      companyIdSet.add(company.Id);
    }
    String query  = 'SELECT Id ' +
        'FROM   c2g__codaCompany__c ' +
        'WHERE  Id IN :companyIdSet';
    return Database.getQueryLocator(query);
  }

  global void execute(Database.BatchableContext BC, List<sObject> scope){
    c2g__codaInvoice__c invoice;
    List<c2g__codaInvoice__c> invoiceList = new List<c2g__codaInvoice__c>();
    c2g__codaPurchaseInvoice__c pInvoice;
    List<c2g__codaPurchaseInvoice__c> pInvoiceList = new List<c2g__codaPurchaseInvoice__c>();
    c2g__codaInvoiceLineItem__c invoiceLineItem;
    List<c2g__codaInvoiceLineItem__c> invoiceLineItemList = new List<c2g__codaInvoiceLineItem__c>();
    for (Integer i = 0; i < companyList.size(); i++) {
      for (Integer j = 0; j < accList.size(); j++) {
        for (Integer k = 0; k < numOfInvoicesPerAccount; k++) {
          invoice = new c2g__codaInvoice__c(
              c2g__OwnerCompany__c = companyList[i].Id,
              CurrencyIsoCode = 'EUR',
              OwnerId = companyOwnerQueueList[i].Id,
              c2g__InvoiceDate__c = Date.today().addDays(-i - j - k),
              c2g__DueDate__c = Date.today().addDays(i + j + k),
              c2g__Account__c = accList[j].Id,
              c2g__InvoiceStatus__c = 'In Progress',
              c2g__InvoiceCurrency__c = accountCurrencyList[i].Id
          );
          invoiceList.add(invoice);

          pInvoice = new c2g__codaPurchaseInvoice__c(
              c2g__OwnerCompany__c = companyList[i].Id,
              CurrencyIsoCode = 'EUR',
              OwnerId = companyOwnerQueueList[i].Id,
              c2g__InvoiceDate__c = Date.today().addDays(-i - j - k),
              c2g__DueDate__c = Date.today().addDays(i + j + k),
              c2g__Account__c = accList[j].Id,
              c2g__InvoiceStatus__c = 'In Progress',
              c2g__InvoiceCurrency__c = accountCurrencyList[i].Id,
              c2g__AccountInvoiceNumber__c = '2897' + String.valueOf(i)
          );
          pInvoiceList.add(pInvoice);
        }
      }
    }
    insert invoiceList;
    insert pInvoiceList;

    for (Product2 prod: prodList) {
      for (c2g__codaInvoice__c inv: invoiceList) {
        invoiceLineItem = new c2g__codaInvoiceLineItem__c(
            c2g__Invoice__c = inv.id,
            c2g__UnitPrice__c = Math.round((Math.random() + 0.1)*10000)/100.0,
            c2g__Quantity__c = Integer.valueOf((Math.random() + 0.1)*10),
            c2g__Product__c = prod.Id
        );
        invoiceLineItemList.add(invoiceLineItem);
      }
    }
    insert invoiceLineItemList;
  }

  global void finish(Database.BatchableContext BC){

  }

}