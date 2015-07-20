package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.salesforce.util.Splitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SplitterTest
{
    private static final int EXPECTED_SIZE_OF_OBJECTS_LIST = 2;

    @Test
    public void testSplitSObjects()
    {
        assertEquals
                (
                        Splitter.splitSObjects(this.getJson()).size(),
                        EXPECTED_SIZE_OF_OBJECTS_LIST
                );

        Splitter splitter = mock(Splitter.class);
        Splitter.splitSObjects(this.getJson());
        verify(splitter).splitSObjects(this.getJson());
    }
    private  String getJson()
    {
        return "{\n" +
                "  \"totalSize\" : 2,\n" +
                "  \"done\" : true,\n" +
                "  \"records\" : [ {\n" +
                "    \"attributes\" : {\n" +
                "      \"type\" : \"Entity__c\",\n" +
                "      \"url\" : \"/services/data/v33.0/sobjects/Entity__c/a002000000fMPSKAA4\"\n" +
                "    },\n" +
                "    \"Name\" : \"hththt\",\n" +
                "    \"EntityId__c\" : \"hththt\",\n" +
                "    \"TableName__c\" : \"HTHTHT\",\n" +
                "    \"Fields__r\" : {\n" +
                "      \"totalSize\" : 7,\n" +
                "      \"done\" : true,\n" +
                "      \"records\" : [ {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCzbAAE\"\n" +
                "        },\n" +
                "        \"Name\" : \"hththt\",\n" +
                "        \"FieldId__c\" : \"hththt.hththt\",\n" +
                "        \"ColumnName__c\" : \"Hththt\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : true\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogDlBAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"adcad\",\n" +
                "        \"FieldId__c\" : \"hththt.adcaddas\",\n" +
                "        \"ColumnName__c\" : \"Adcaddas\",\n" +
                "        \"Type__c\" : \"date\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogDhTAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"grgr\",\n" +
                "        \"FieldId__c\" : \"hththt.grgr\",\n" +
                "        \"ColumnName__c\" : \"Grgr\",\n" +
                "        \"Type__c\" : \"date\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCvZAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"htyjutykjityki\",\n" +
                "        \"FieldId__c\" : \"hththt.htyjutykjityki\",\n" +
                "        \"ColumnName__c\" : \"Htyjutykjityki\",\n" +
                "        \"Type__c\" : \"varchar(255)\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCvPAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"rthrthrt\",\n" +
                "        \"FieldId__c\" : \"hththt.rthrthrt\",\n" +
                "        \"ColumnName__c\" : \"Rthrthrt\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCveAAE\"\n" +
                "        },\n" +
                "        \"Name\" : \"r3ewrwrrw\",\n" +
                "        \"FieldId__c\" : \"hththt.rewrwrrw\",\n" +
                "        \"ColumnName__c\" : \"Rewrwrrw\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCvUAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"rthrt\",\n" +
                "        \"FieldId__c\" : \"hththt.rthrt\",\n" +
                "        \"ColumnName__c\" : \"Rthrt\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      } ]\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"attributes\" : {\n" +
                "      \"type\" : \"Entity__c\",\n" +
                "      \"url\" : \"/services/data/v33.0/sobjects/Entity__c/a002000000fMOebAAG\"\n" +
                "    },\n" +
                "    \"Name\" : \"grgrrg\",\n" +
                "    \"EntityId__c\" : \"grgrrg\",\n" +
                "    \"TableName__c\" : \"GRGRRG\",\n" +
                "    \"Fields__r\" : {\n" +
                "      \"totalSize\" : 7,\n" +
                "      \"done\" : true,\n" +
                "      \"records\" : [ {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCzqAAE\"\n" +
                "        },\n" +
                "        \"Name\" : \"hello wolrdf\",\n" +
                "        \"FieldId__c\" : \"grgrrg.hellowolrdf\",\n" +
                "        \"ColumnName__c\" : \"Hello_Wolrdf\",\n" +
                "        \"Type__c\" : \"varchar(255)\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCvFAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"hello wolrd\",\n" +
                "        \"FieldId__c\" : \"grgrrg.hellowolrd\",\n" +
                "        \"ColumnName__c\" : \"Hello_Wolrd\",\n" +
                "        \"Type__c\" : \"varchar(255)\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCsQAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"rgrgr\",\n" +
                "        \"FieldId__c\" : \"grgrrg.rgrgr\",\n" +
                "        \"ColumnName__c\" : \"Rgrgr\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : true\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCv5AAE\"\n" +
                "        },\n" +
                "        \"Name\" : \"kjyukiyukl\",\n" +
                "        \"FieldId__c\" : \"grgrrg.kjyukiyukl\",\n" +
                "        \"ColumnName__c\" : \"Kjyukiyukl\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCvKAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"gerege\",\n" +
                "        \"FieldId__c\" : \"grgrrg.gerege\",\n" +
                "        \"ColumnName__c\" : \"Gerege\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCp7AAE\"\n" +
                "        },\n" +
                "        \"Name\" : \"jyujyujyu\",\n" +
                "        \"FieldId__c\" : \"grgrrg.jyujyujyu\",\n" +
                "        \"ColumnName__c\" : \"Jyujyujyu\",\n" +
                "        \"Type__c\" : \"int\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCaKAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"htht\",\n" +
                "        \"FieldId__c\" : \"grgrrg.jjhtht\",\n" +
                "        \"ColumnName__c\" : \"jjHtht\",\n" +
                "        \"Type__c\" : \"varchar(255)\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      } ]\n" +
                "    }\n" +
                "  } ]\n" +
                "}\n";
    }
}
