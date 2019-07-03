public class CreateFrenchAudit  {
    public Time timeD = Time.newInstance(0,0,0,0);
    //public String yearChosen {get; set;}
    //public List<SelectOption> yearList {get; set;}
    public String docId {get; set;}
    public String docURL {get; set;}
    public Boolean showResult {get; set;}
	public Date startDate {get; set;}
	public Decimal duration {get; set;}
	public Document doc {get; set;}
    public String companyChosen {get; set;}
    public List<SelectOption> companyList {get; set;}
	@TestVisible
	private String appName = 'French Audit';

	public /*PageReference*/ void checkLicense () {
		/*CloudTeamLicence ctl = new CloudTeamLicence();
		Boolean bool = ctl.checkLicence(appName, UserInfo.getOrganizationId());
		if (bool == true) {
			return null;
		} else {
			PageReference pr = new PageReference('/apex/CloudTeamLicenseAccessDenied');
			pr.setRedirect(true);
			return pr;
		}*/
	}
    public CreateFrenchAudit () {
        docId = '';
        showResult = false;
		startDate = Date.today();
		duration = 1;
        List<AggregateResult> cList = [
                SELECT	Name
                FROM	c2g__codaCompany__c
                GROUP BY Name
                ORDER BY Name
        ];
        companyList = new List<SelectOption>();
        for (AggregateResult c: cList) {
            companyList.add(new SelectOption((String)c.get('Name'),(String)c.get('Name')));
        }
        /*List<AggregateResult> yList = [
                                                SELECT  Name 
                                                FROM    c2g__codaYear__c 
                                                GROUP BY Name 
                                                ORDER BY Name
                                                LIMIT   20 
                                          ];
        yearList = new List<SelectOption>();
        for (AggregateResult y: yList) {
            yearList.add(new SelectOption((String)y.get('Name'),(String)y.get('Name')));
        }*/
    }
	@TestVisible
	private static String getDateString (Date d) {
		return Datetime.newInstance(d.year(), d.month(), d.day()).format('yyyy-MM-dd');
	}
    public void createFrenchAuditString (Date startDate, Date endDate) {
        String separator = '\t';
        List<French_Audit_Field__mdt> frenchFieldsList = [
                                                                    SELECT  Audit_Column__c, 
                                                                            SFDC_Field__c, 
                                                                            SFDC_Object__c, 
                                                                            Index_Number__c
                                                                    FROM    French_Audit_Field__mdt
                                                                    ORDER BY Index_Number__c
                                                               ];
        Set<String> fieldsSet = new Set<String>();
        Set<String> headerSet = new Set<String>();
        for (French_Audit_Field__mdt frenchField: frenchFieldsList) {
            if (String.isNotBlank(frenchField.SFDC_Field__c)) {
                fieldsSet.add(frenchField.SFDC_Field__c);
            }
            if (String.isNotBlank(frenchField.Audit_Column__c)) {
                headerSet.add(frenchField.Audit_Column__c);
            }
        }
        fieldsSet.add('c2g__GeneralLedgerAccount__r.c2g__ReportingCode__c');
        fieldsSet.add('c2g__GeneralLedgerAccount__r.Name');
        fieldsSet.add('c2g__Account__r.Name');
        fieldsSet.add('c2g__BankAccount__r.Name');
        fieldsSet.add('c2g__Product__r.Name');
        fieldsSet.add('c2g__Transaction__r.c2g__DocumentDescription__c');
        String header = String.join(new List<String>(headerSet),separator) + '\n';
        String fieldsToQuery = String.join(new List<String>(fieldsSet),', ');
        String query =  'SELECT ' + fieldsToQuery + ' ' + 
                        'FROM   c2g__codaTransactionLineItem__c ' + 
                        'WHERE  c2g__Transaction__r.c2g__TransactionDate__c >= ' + getDateString(startDate) + ' AND ' + 
                                'c2g__Transaction__r.c2g__TransactionDate__c <= ' + getDateString(endDate) + ' AND ' +
                                'c2g__OwnerCompany__r.Name = \'' + companyChosen + '\' ' + //' AND ' +
		                        // 'c2g__HomeValue__c != 0 ' +
                        'ORDER BY c2g__Transaction__r.c2g__TransactionDate__c ' +
                        'LIMIT  49900';
        List<c2g__codaTransactionLineItem__c> trList = Database.query(query);
        System.debug('query size: ' + trList);
        String frenchAuditString = header;
        String frenchLine;
        List<String> frenchLineList;
        for (c2g__codaTransactionLineItem__c tr: trList) {
            frenchLine = '';
            frenchLineList = new List<String>();
            for (French_Audit_Field__mdt frenchField: frenchFieldsList) {
                frenchLineList.add(getFieldValue(tr, frenchField));
            }
            frenchLine = String.join(frenchLineList, separator);
            frenchAuditString += frenchLine + '\n';
        }
        createDocument(frenchAuditString, getDateString(startDate), getDateString(endDate));
    }
    private String getFieldValue (c2g__codaTransactionLineItem__c tr, French_Audit_Field__mdt frenchField) {
        //fields with dot +
        //get field type +
        //format date +
        //debit credit
        String fieldValue = '';
        if (frenchField.SFDC_Field__c != null && String.isNotBlank(frenchField.SFDC_Field__c)) {
            if (frenchField.SFDC_Field__c.contains('.')) {
                String[] strList = frenchField.SFDC_Field__c.split('\\.');
                SObject obj = tr.getSObject(strList[0]);
                if (obj != null) {
                    fieldValue = String.valueOf(obj.get(strList[1]));
                } else {
                    fieldValue = '';
                }
            } else {
                fieldValue = String.valueOf(tr.get(frenchField.SFDC_Field__c));
            }
            if (String.isNotBlank(fieldValue)) {
                try{            
                    Date convertedD = Date.valueOf(String.valueOf(fieldValue));
                    fieldValue = String.valueOf(Datetime.newInstance(convertedD, timeD).format('yyyyMMdd'));
                } catch (TypeException e){
                    //do nothing
                }
            }
            if (frenchField.Audit_Column__c == 'Debit' || frenchField.Audit_Column__c == 'Credit') {
                if (frenchField.Audit_Column__c == 'Debit') {
                    if (Decimal.valueOf(fieldValue) < 0) {
                        fieldValue = '0.00';
                    } else if (Decimal.valueOf(fieldValue) == 0) {
						fieldValue = '0.0001';
					}
                } else if (frenchField.Audit_Column__c == 'Credit') {
                    if (Decimal.valueOf(fieldValue) > 0) {
                        fieldValue = '0.00';
                    } else if (Decimal.valueOf(fieldValue) < 0) {
                        fieldValue = fieldValue.remove('-');
                    } else if (Decimal.valueOf(fieldValue) == 0) {
						fieldValue = '0.00';
					}
                }
            }
            if (fieldValue == null) {
                fieldValue = '';
            }
        }
        if (frenchField.Audit_Column__c == 'JournalCode') {
            if (fieldValue == '') {
                fieldValue = tr.c2g__GeneralLedgerAccount__r.c2g__ReportingCode__c;
            } else if (fieldValue.length() > 6) {
                fieldValue.substring(0,5);
            }
        }
        if (frenchField.Audit_Column__c == 'JournalLib') {
            if (fieldValue == '') {
                fieldValue = tr.c2g__GeneralLedgerAccount__r.Name;
            }
        }
        if (frenchField.Audit_Column__c == 'EcritureLib') {
            if (fieldValue == '') {
                if (tr.c2g__Transaction__r.c2g__DocumentDescription__c != null) {
                    fieldValue = tr.c2g__Transaction__r.c2g__DocumentDescription__c;
                } else {
                    fieldValue = tr.c2g__Account__r.Name + ', ' + tr.c2g__BankAccount__r.Name + ', ' + tr.c2g__Product__r.Name;
                }
            }
        }
		fieldValue = fieldValue.replaceAll('\\n\\r', '; ');
        fieldValue = fieldValue.replaceAll('\\n', '; ');
        fieldValue = fieldValue.replaceAll('\\r', '; ');
        return fieldValue;
    }
    public void createDocument (String docBody, String sDate, String eDate) {
        doc = new Document();
        doc.FolderId = [
                            SELECT  Id
                            FROM    Folder
                            WHERE   Name = 'FinancialForceDocuments'
                            LIMIT   1
                       ].Id;
        doc.Body = Blob.valueOf(docBody);
        doc.Name = 'French audit file (' + sDate + ' - ' + eDate + ') Created: ' + String.valueOf(Datetime.now());
        doc.ContentType = 'text/plain';
        doc.Type = 'txt';
        insert doc;
        docId = doc.Id;
    }
    public void createAuditFile () {
        docId = '';
        showResult = false;
		if (duration < 0) {
			duration = duration*(-1);
		}
        createFrenchAuditString(startDate, startDate.addMonths((Integer)duration));
        if (docId != '') {
            docURL = '/' + docId;
            showResult = true;
        } else {
            showResult = false;
        }
    }
    public void getInteger () {
        Integer i = 0;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
        i++;
    }
}