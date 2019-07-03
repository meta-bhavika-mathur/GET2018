/**
 * Created by vitek on 09.10.2018.
 */

public with sharing class CreateGermanAuditPostinstall {
	public void insertCS () {
		List<German_Mapping__c> mappingList = new List<German_Mapping__c>();
		List<String> mappingFieldsList;
		German_Mapping__c mapping;
		List<German_Mapping__c> gmList = [
												SELECT  Id
												FROM    German_Mapping__c
										 ];
		if (gmList.size() > 0) {
			delete gmList;
		}
		List<StaticResource> srList = [
											SELECT	Body,
													Id,
													Name
											FROM    StaticResource
											WHERE   Name = 'GermanMapping'
											LIMIT 1
									  ];
		if (srList.size() > 0) {
			String s = srList[0].Body.toString();
			for (String str: s.split('\r\n')) {
				mappingFieldsList = str.split(',');
				mapping = new German_Mapping__c(
						name = (String.isNotBlank(mappingFieldsList[0]) ? mappingFieldsList[0].removeEnd(' ').removeStart(' ') : mappingFieldsList[0].replaceAll(' ', '')),
						German_Column_Name__c = (String.isNotBlank(mappingFieldsList[1]) ? mappingFieldsList[1].removeEnd(' ').removeStart(' ') : mappingFieldsList[1].replaceAll(' ', '')),
						English_Column_Name__c = (String.isNotBlank(mappingFieldsList[2]) ? mappingFieldsList[2].removeEnd(' ').removeStart(' ') : mappingFieldsList[2].replaceAll(' ', '')),
						SF_Field_Name__c = (String.isNotBlank(mappingFieldsList[3]) ? mappingFieldsList[3].removeEnd(' ').removeStart(' ') : mappingFieldsList[3].replaceAll(' ', '')),
						SF_Object_Name__c = (String.isNotBlank(mappingFieldsList[4]) ? mappingFieldsList[4].removeEnd(' ').removeStart(' ') : mappingFieldsList[4].replaceAll(' ', '')),
						Field_Type__c = (String.isNotBlank(mappingFieldsList[5]) ? mappingFieldsList[5].removeEnd(' ').removeStart(' ') : mappingFieldsList[5].replaceAll(' ', ''))
				);
				mappingList.add(mapping);
			}
			insert mappingList;
		}
	}
}