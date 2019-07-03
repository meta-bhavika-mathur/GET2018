global class TransactionsInsertBatch implements Database.Batchable<sObject>, Database.Stateful {

    private String query;
	private String docId;
	private Map<String, XAFXML_Mapping__c> allXAFCustSetMap;

    public TransactionsInsertBatch(String q, String d, Map<String, XAFXML_Mapping__c> csMap) {
        query = q;
        docId = d;
        allXAFCustSetMap = csMap;
    }

    global Database.QueryLocator start(Database.BatchableContext BC) {
        return Database.getQueryLocator(query);
    }

    global void execute(Database.BatchableContext BC, List<c2g__codaTransaction__c> transactionList) {
        TransactionAuditFile__c taf;
		List<TransactionAuditFile__c> tafList = new List<TransactionAuditFile__c>();
		
		for (c2g__codaTransaction__c tr: transactionList) {
			taf = new TransactionAuditFile__c(
														Audit_File_Transaction_Text__c = generatingTrString(tr),
														Transaction__c = tr.Id,
                										DocumentId__c = docId
													);
			tafList.add(taf);
        }
		insert tafList;
    }   

    global void finish(Database.BatchableContext BC) {
    }
	
	private String generatingTrString (c2g__codaTransaction__c tr) {
        String xafString = '';
        xafString = addNode(getValue('c2g__codaTransaction__c9', 'XAF_Tag__c'),
                            addNode(getValue('c2g__codaTransaction__c10', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c10', 'SFDC_Field__c'), tr)) +	
                            addNode(getValue('c2g__codaTransaction__c11', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c11', 'SFDC_Field__c'), tr)) +	
                            addNode(getValue('c2g__codaTransaction__c12', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c12', 'SFDC_Field__c'), tr)) +	
                            addNode(getValue('c2g__codaTransaction__c13', 'XAF_Tag__c'), getTagValue(getValue('c2g__codaTransaction__c13', 'SFDC_Field__c'), tr).split(' ')[0]) +	
                            generateTrLinesString(tr.c2g__TransactionLineItems__r)
                         );
        return xafString;
    }
    private String generateTrLinesString (List<c2g__codaTransactionLineItem__c> trLineList) {
        String xafString = '';
        for (c2g__codaTransactionLineItem__c trLine: trLineList) {
                //trLine
                    //nr
                    //accID
                    //docRef
                    //effDate
                    //desc
                    //amnt
                    //amntTp
            xafString += addNode(getValue('c2g__TransactionLineItems__r1', 'XAF_Tag__c'),
                            addNode(getValue('c2g__TransactionLineItems__r2', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r2', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r3', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r3', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r4', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r4', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r5', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r5', 'SFDC_Field__c'), trLine).split(' ')[0]) +
                            addNode(getValue('c2g__TransactionLineItems__r6', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r6', 'SFDC_Field__c'), trLine)) +	
                            addNode(getValue('c2g__TransactionLineItems__r7', 'XAF_Tag__c'), getTagValue(getValue('c2g__TransactionLineItems__r7', 'SFDC_Field__c'), trLine)) +
                            addNode(getValue('c2g__TransactionLineItems__r8', 'XAF_Tag__c'), debitOrCredit(getTagValue(getValue('c2g__TransactionLineItems__r7', 'SFDC_Field__c'), trLine)))
                         );
        }
        return xafString;
    }
	@TestVisible
	private String debitOrCredit (String homeValue) {
		String value = 'D';
		if (Double.valueOf(homeValue) < 0) {
			value = 'C';
		}
		return value;
	}
	@TestVisible
	private static String getTagValue (String sfdcField, SObject obj) {
		String value = '';
		List<String> strList;
		if (sfdcField != null && sfdcField.trim() != ''){// && obj.getPopulatedFieldsAsMap().containsKey(sfdcField)) {
			for (String field: sfdcField.split(',')) {
				strList = field.split('\\.');
				if (strList.size() > 1) {
					if (String.isNotBlank(String.valueOf(obj.getSObject(strList[0]).get(strList[1])))) {
						value = String.valueOf(obj.getSObject(strList[0]).get(strList[1]));
						break;
					}
				} else if (String.isNotBlank(String.valueOf(obj.get(field)))) {
					value = String.valueOf(obj.get(field));
					break;
				}
			}
		}
		return value;
	}
	@TestVisible
	private static String addNode(String name, String value) {
		return '<'+name+'>'+value+'</'+name+'>';
	}
	@TestVisible
	private String getValue(String key, String fieldName) {
		if(allXAFCustSetMap.containsKey(key)) {
			return (String) allXAFCustSetMap.get(key).get(fieldName);
		}
		return '';
	}
}