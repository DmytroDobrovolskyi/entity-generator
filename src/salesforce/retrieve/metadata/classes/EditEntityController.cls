public class EditEntityController
{
    public Entity__c entity { get; set; }

    public String fieldId { get; set; }

    public Boolean wereChanges { get; set; }

    public String selectedType { get; set; }

    public Boolean isPrimaryKey { get; set; }

    private static String newFieldId = 'newFieldId';

    @TestVisible
    private Map<String, Field__c> fieldMap { get; set; }

    private Field__c newField;

    public EditEntityController(ApexPages.StandardController controller)
    {
        this.entity = (Entity__c) controller.getRecord();
        this.fieldMap = new Map<String, Field__c>
        ([
            SELECT Id, Name, ColumnName__c, Type__c, IsPrimaryKey__c, IsUpdateNeeded__c
            FROM Field__c
            WHERE Entity__c = :entity.Id
        ]);

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


    public void deleteField()
    {
        Database.delete(fieldId, true);
        fieldMap.remove(fieldId);
        setIsProcessingNeeded();
    }

    public void addRow()
    {
        newField = new Field__c();
        newField.Entity__c = entity.Id;
        newField.Type__c = 'varchar(255)';
        fieldMap.put(newFieldId, newField);

        wereChanges = true;
    }

    public void applyChanges()
    {
        List<String> columnNames = new List<String>();

        for (Field__c field : fieldMap.values())
        {
            columnNames.add(field.ColumnName__c);
        }

        Set<String> uniqueColumnNames =  new Set<String>(columnNames);

        if (columnNames.size() != uniqueColumnNames.size())
        {
            showError();
            return;
        }

        Set<Field__c> fieldsToUpdate = new Set<Field__c>(fieldMap.values());
        fieldsToUpdate.remove(newField);

        for (Field__c field : fieldsToUpdate)
        {
            if (containsNumbersOrNonAlphabeticCharacters(field.ColumnName__c))
            {
                showError();
                return;
            }
        }

        Boolean isProcessingNeeded = false;
        for (Field__c field : fieldsToUpdate)
        {
            if(field.IsUpdateNeeded__c)
            {
                resolveIsPrimaryKey(field);
                if(Database.update(field, false).isSuccess())
                {
                    isProcessingNeeded = true;
                    field.IsUpdateNeeded__c = false;
                }
                else
                {
                    showError();
                    return;
                }
            }
        }
        if (isProcessingNeeded)
        {
            setIsProcessingNeeded();
        }

        Map<String, Field__c>  newFieldMap = new Map<String, Field__c>();

        if (newField != null)
        {
            if (isAlreadyExist(fieldsToUpdate, newField.ColumnName__c) ||
                    containsNumbersOrNonAlphabeticCharacters(newField.ColumnName__c))
            {
                showError();
                return;
            }
            else
            {
                resolveIsPrimaryKey(newField);
                Database.insert(newField, true);
                newFieldMap.put(newField.Id, newField);
                setIsProcessingNeeded();
                newField = null;
            }
        }

        for (Field__c field : fieldsToUpdate)
        {
            newFieldMap.put(field.Id, field);
        }
        newFieldMap.values().sort();
        fieldMap = newFieldMap;
        wereChanges = false;
    }

    public PageReference discardChanges()
    {
        return new PageReference(ApexPages.currentPage().getHeaders().get('referer'));
    }

    private boolean isAlreadyExist(Set<Field__c> fieldsToUpdate, String columnNameToCheck)
    {
        for (Field__c field : fieldsToUpdate)
        {
            if (field.ColumnName__c == columnNameToCheck)
            {
                return true;
            }
        }
        return false;
    }

    private void resolveIsPrimaryKey(Field__c field)
    {
        if (field.Type__c != 'int' && field.Type__c != 'varchar(255)')
        {
            field.IsPrimaryKey__c = false;
        }
    }

    @TestVisible
    private Boolean containsNumbersOrNonAlphabeticCharacters(String str)
    {
        Matcher matcher = Pattern.compile('(\\W)|(\\d)|(\\s)').matcher(str);

        return matcher.find();
    }

    public void resolveChanges()
    {
        Field__c fieldToUpdate = fieldMap.get(fieldId);
        if (fieldToUpdate != null && String.valueOf(fieldToUpdate.Id) != newFieldId)
        {
            fieldToUpdate.IsUpdateNeeded__c = true;
        }
        wereChanges = true;
    }

    private void setIsProcessingNeeded()
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