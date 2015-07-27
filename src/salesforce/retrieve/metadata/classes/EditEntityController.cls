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
            SELECT Id, Name, ColumnName__c, Type__c, IsPrimaryKey__c
            FROM Field__c
            WHERE Entity__c = :entity.Id
        ])
        {
            fieldMap.put(field.ColumnName__c, field);
        }

        this.entity =
        [
            SELECT Name, TableName__c, IsProcessingNeeded__c
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
        options.add(new SelectOption('int', 'int'));
        options.add(new SelectOption('date', 'date'));
        options.add(new SelectOption('money', 'money'));
        options.add(new SelectOption('bit', 'bit'));
        options.add(new SelectOption('decimal', 'decimal'));
        return options;
    }

    public List<SelectOption> getRadioButtonOption()
    {
        List<SelectOption> options = new List<SelectOption>();
        options.add(new SelectOption('', ''));
        options.add(new SelectOption('', ''));
        return options;
    }

    public void deleteField()
    {
        Database.delete(fieldMap.get(columnName));
        fieldMap.remove(columnName);
        setIsProcessingNeeded();
    }

    public void addRow()
    {
        if(fieldMap.values().size() != 0 )
        {
            setIsProcessingNeeded();
        }

        newField = new Field__c();
        newField.Entity__c = entity.Id;
        newField.Type__c = 'varchar(255)';
        fieldMap.put(null, newField);

        setWereChanges();
    }

    public void applyChanges()
    {
        Map<String, Field__c> newFieldMap = new Map<String, Field__c>();

        Boolean hasError = false;

        List<String> columnNames = new List<String>();

        for (Field__c field : fieldMap.values())
        {
            columnNames.add(field.ColumnName__c);
        }

        Set<String> uniqueColumnNames =  new Set<String>(columnNames);

        Set<Field__c> fieldsToUpdate = null;

        if (columnNames.size() != uniqueColumnNames.size())
        {
            showError();
            return;
        }
        else
        {
            fieldsToUpdate = new Set<Field__c>(fieldMap.values());
            fieldsToUpdate.remove(newField);

             for (Field__c field : fieldsToUpdate)
             {
                 if (containsNumbersOrNonAlphabeticCharacters(field.ColumnName__c))
                 {
                     hasError = true;
                 }
              }

              if (!hasError)
              {
                  Database.SaveResult[] results = Database.update(new List<Field__c> (fieldsToUpdate), false);
                  for (Database.SaveResult saveResult : results)
                  {
                      if(saveResult.isSuccess())
                      {
                          setIsProcessingNeeded();
                          break;
                      }
                  }
              }
        }

        if (newField != null)
        {
            if (fieldMap.containsKey(newField.ColumnName__c) ||
                    containsNumbersOrNonAlphabeticCharacters(newField.ColumnName__c) || newField.Type__c == ' ')
            {
                hasError = true;
            }
            else
            {
                newFieldMap.put(newField.ColumnName__c, newField);
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
            for (Field__c field : fieldsToUpdate)
            {
                newFieldMap.put(field.ColumnName__c, field);
            }
            fieldMap = newFieldMap;
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

    public void resetIsPrimaryKey()
    {
        Field__c field = fieldMap.get(null);

        if(field != null)
        {
            if (field.Type__c != 'int' || field.Type__c != 'varchar(255)')
            {
                field.IsPrimaryKey__c = false;
            }
        }
    }

    private Boolean containsNumbersOrNonAlphabeticCharacters(String str)
    {
        Matcher matcher = Pattern.compile('(\\W)|(\\d)|(\\s)').matcher(str);

        return matcher.find();
    }

    public void setIsProcessingNeeded()
    {
        entity.IsProcessingNeeded__c = true;
        Database.update(entity);
    }

    private void showError()
    {
        ApexPages.Message error = new ApexPages.Message(ApexPages.Severity.ERROR, 'Column name must be unique and contains only alphabetic characters or underscores');
        ApexPages.addMessage(error);
    }
}