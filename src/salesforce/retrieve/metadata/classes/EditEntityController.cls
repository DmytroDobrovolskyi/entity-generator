public class EditEntityController
{
    public Entity__c entity { get; set; }

    public String columnName { get; set; }

    public Boolean wereChanges { get; set; }

    public String selectedType { get; set; }

    public Boolean isPrimaryKey { get; set; }

    private Map<String, Field__c> fieldMap;

    private Field__c newField;

    public EditEntityController(ApexPages.StandardController controller)
    {
        this.entity = (Entity__c) controller.getRecord();
        this.fieldMap = new Map<String, Field__c>();

        for (Field__c field :
        [
            SELECT Id, Name, ColumnName__c, Type__c, isPrimaryKey__c
            FROM Field__c
            WHERE Entity__c = :entity.Id
        ])
        {
            fieldMap.put(field.ColumnName__c, field);
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
        options.add(new SelectOption('varchar(255)','varchar(255)'));
        options.add(new SelectOption('int','int'));
        options.add(new SelectOption('date','date'));
        return options;
    }

    public List<SelectOption> getRadioButtonOption()
    {
        List<SelectOption> options = new List<SelectOption>();
        options.add(new SelectOption('',''));
         options.add(new SelectOption('',''));
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

    public  Boolean containSpaces(String str)
    {
        return str.containsAny(' ');
    }

    public void applyChanges()
    {
        Boolean hasError = false;

        List<String> columnNames = new List<String>();

        for (Field__c filed : fieldMap.values())
        {
            columnNames.add(filed.ColumnName__c);
        }

        Set<String> uniqueColumnNames =  new Set<String>(columnNames);

        if (columnNames.size() != uniqueColumnNames.size())
        {
            showError();
            return;
        }
        else
        {
            Set<Field__c> filedsToUpdate = new Set<Field__c>(fieldMap.values());
            filedsToUpdate.remove(newField);

             for (Field__c field : filedsToUpdate)
              {
                 if (containSpaces(field.ColumnName__c))
                     {
                        hasError = true;
                     }
              }

              if (!hasError)
              {
                  Database.SaveResult[] results = Database.update(new List<Field__c> (filedsToUpdate), false);
              }
        }

        if (newField != null)
        {
            if (fieldMap.containsKey(newField.ColumnName__c) || containSpaces(newField.ColumnName__c))
            {
                hasError = true;
            }
            else
            {
                fieldMap.remove(null);
                fieldMap.put(newField.ColumnName__c, newField);

                Database.insert(newField);
                newField = null;
            }
        }
        if (hasError)
        {
            showError();
        }
        else
        {
            wereChanges = false;
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

    private void showError()
    {
        ApexPages.Message error = new ApexPages.Message(ApexPages.Severity.ERROR, 'Column name must be unique and not contain spaces');
        ApexPages.addMessage(error);
    }
}