/**
 * Created by jbUser on 01-Oct-18.
 */
public with sharing class CreateXAFXMLpostinstall {
	public void insertCS () {
		List<XAFXML_Mapping__c> mappingList = new List<XAFXML_Mapping__c>();
		List<String> mappingFieldsList;
		XAFXML_Mapping__c mapping;
		List<StaticResource> srList = [
											SELECT	Body,
													Id,
													Name
											FROM    StaticResource
											WHERE   Name = 'XAFXMLmapping'
											LIMIT 1
									  ];
		if (srList.size() > 0) {
			String s = srList[0].Body.toString();
			for (String str: s.split('\r\n')) {
				mappingFieldsList = str.split(',');
				mapping = new XAFXML_Mapping__c(
													name = mappingFieldsList[0],
													Object_Name__c = mappingFieldsList[2],
													SFDC_Field__c = mappingFieldsList.size() > 3 ? mappingFieldsList[3] : null,
													XAF_Tag__c = mappingFieldsList[1]
												);
				mappingList.add(mapping);
			}
			insert mappingList;
		}
	}
}