package com.softserve.entity.generator;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.service.salesforce.util.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ParserTest
{

    @Test
    public void testParseSObjectJson()
    {
        assertEquals
                (
                        Parser.parseSObjectJson(this.getJson()).trim(),
                        this.parsedString().trim()
                );

        Parser parser = mock(Parser.class);
        Parser.parseSObjectJson(this.getJson());
        verify(parser).parseSObjectJson(this.getJson());
    }

    private  String getJson()
    {
        return  "    \"attributes\" : {\n" +
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
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCaKAAU\"\n" +
                "        },\n" +
                "        \"Name\" : \"htht\",\n" +
                "        \"FieldId__c\" : \"grgrrg.jjhtht\",\n" +
                "        \"ColumnName__c\" : \"jjHtht\",\n" +
                "        \"Type__c\" : \"varchar(255)\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      }, {\n" +
                "        \"attributes\" : {\n" +
                "          \"type\" : \"Field__c\",\n" +
                "          \"url\" : \"/services/data/v33.0/sobjects/Field__c/a012000001ogCzqAAE\"\n" +
                "        },\n" +
                "        \"Name\" : \"hello wolrdf\",\n" +
                "        \"FieldId__c\" : \"grgrrg.hellowolrdf\",\n" +
                "        \"ColumnName__c\" : \"Hello_Wolrdf\",\n" +
                "        \"Type__c\" : \"varchar(255)\",\n" +
                "        \"IsPrimaryKey__c\" : false\n" +
                "      } ]\n" +
                "    }\n" +
                "  } ]\n" +
                "}";
    }

    private String parsedString()
    {
        return "{     \n" +
                "    \"name\" : \"grgrrg\",\n" +
                "    \"entityId\" : \"grgrrg\",\n" +
                "    \"tableName\" : \"GRGRRG\",\n" +
                "    \"fields\" : [ { \n" +
                "      \n" +
                "        \"name\" : \"jyujyujyu\",\n" +
                "        \"fieldId\" : \"grgrrg.jyujyujyu\",\n" +
                "        \"columnName\" : \"Jyujyujyu\",\n" +
                "        \"type\" : \"int\",\n" +
                "        \"isPrimaryKey\" : false\n" +
                "      }, {\n" +
                "        \n" +
                "        \"name\" : \"gerege\",\n" +
                "        \"fieldId\" : \"grgrrg.gerege\",\n" +
                "        \"columnName\" : \"Gerege\",\n" +
                "        \"type\" : \"int\",\n" +
                "        \"isPrimaryKey\" : false\n" +
                "      }, {\n" +
                "        \n" +
                "        \"name\" : \"kjyukiyukl\",\n" +
                "        \"fieldId\" : \"grgrrg.kjyukiyukl\",\n" +
                "        \"columnName\" : \"Kjyukiyukl\",\n" +
                "        \"type\" : \"int\",\n" +
                "        \"isPrimaryKey\" : false\n" +
                "      }, {\n" +
                "        \n" +
                "        \"name\" : \"rgrgr\",\n" +
                "        \"fieldId\" : \"grgrrg.rgrgr\",\n" +
                "        \"columnName\" : \"Rgrgr\",\n" +
                "        \"type\" : \"int\",\n" +
                "        \"isPrimaryKey\" : true\n" +
                "      }, {\n" +
                "        \n" +
                "        \"name\" : \"hello wolrd\",\n" +
                "        \"fieldId\" : \"grgrrg.hellowolrd\",\n" +
                "        \"columnName\" : \"Hello_Wolrd\",\n" +
                "        \"type\" : \"varchar(255)\",\n" +
                "        \"isPrimaryKey\" : false\n" +
                "      }, {\n" +
                "        \n" +
                "        \"name\" : \"htht\",\n" +
                "        \"fieldId\" : \"grgrrg.jjhtht\",\n" +
                "        \"columnName\" : \"jjHtht\",\n" +
                "        \"type\" : \"varchar(255)\",\n" +
                "        \"isPrimaryKey\" : false\n" +
                "      }, {\n" +
                "        \n" +
                "        \"name\" : \"hello wolrdf\",\n" +
                "        \"fieldId\" : \"grgrrg.hellowolrdf\",\n" +
                "        \"columnName\" : \"Hello_Wolrdf\",\n" +
                "        \"type\" : \"varchar(255)\",\n" +
                "        \"isPrimaryKey\" : false\n" +
                "      } ] }";
    }
}
