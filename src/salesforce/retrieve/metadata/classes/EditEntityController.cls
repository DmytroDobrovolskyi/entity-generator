public class EditEntityController
{
    public Entity__c entity { get; set; }

    public String columnName { get; set; }

    public Boolean wereChanges { get; set; }

    public String selectedType { get; set; }

    private Map<String, Field__c> fieldMap;

    private Field__c newField;

    public EditEntityController(ApexPages.StandardController controller)
    {
        this.entity = (Entity__c) controller.getRecord();
        this.fieldMap = new Map<String, Field__c>();

        for (Field__c filed :
        [
            SELECT Id, Name, ColumnName__c, Type__c
            FROM Field__c
            WHERE Entity__c = :entity.Id
        ])
        {
            fieldMap.put(filed.ColumnName__c, filed);
        }
        
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

    public List<SelectOption> getTypes()
    {
        List<SelectOption> options = new List<SelectOption>();
        options.add(new SelectOption('varchar','varchar'));
        options.add(new SelectOption('int','int'));
        options.add(new SelectOption('date','date'));
        return options;
    }

    public void deleteField()
    {
        Database.delete(fieldMap.get(columnName));
        fieldMap.remove(columnName);
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
        Database.update(fieldMap.values(), false);
        wereChanges = false;
        if(newField != null)
        {
            if (fieldMap.containsKey(newField.ColumnName__c))
            {
                ApexPages.Message error = new ApexPages.Message(ApexPages.Severity.ERROR, 'Column name must be unique');
                ApexPages.addMessage(error);
                wereChanges = true;
            } else
            {
                fieldMap.remove(null);
                fieldMap.put(newField.ColumnName__c, newField);

                Database.insert(newField);
                newField = null;
            }
        }
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
