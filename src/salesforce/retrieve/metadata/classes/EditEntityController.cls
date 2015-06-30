public class EditEntityController
{
    public Entity__c entity { get; set; }

    public String fieldId { get; set; }

    public Boolean wereChanges { get; set; }

    private Map<String, Field__c> fieldMap;

    private Field__c newField;

    public EditEntityController(ApexPages.StandardController controller)
    {
        this.entity = (Entity__c) controller.getRecord();
        this.fieldMap = new Map<String, Field__c>
        ([
            SELECT Id, Name, ColumnName__c
            FROM Field__c
            WHERE Entity__c = :entity.Id
        ]);
        
        this.entity = 
        [
            SELECT Name, TableName__c 
            FROM Entity__c 
            WHERE Entity__c.id = :entity.Id
        ];
    }

    public List<Field__c> getFields()
    {
        return fieldMap.values();
    }


    public void deleteField()
    {
        Database.delete(fieldId);
        fieldMap.remove(fieldId);
    }

    public void addRow()
    {
        newField = new Field__c();
        newField.Entity__c = entity.Id;
        fieldMap.put(null, newField);
        wereChanges = true;
    }

    public void applyChanges()
    {
        if(newField != null)
        {
            Database.insert(newField);
            fieldMap.remove(null);
            fieldMap.put(newField.Id, newField);
            newField = null;
        }
        Database.update(fieldMap.values(), false);
        wereChanges = false;
    }
    
    public PageReference discardChanges() 
    {
        return new PageReference(ApexPages.currentPage().getHeaders().get('referer'));
    }

    public void setWereChanges()
    {
        wereChanges = true;
    }
}