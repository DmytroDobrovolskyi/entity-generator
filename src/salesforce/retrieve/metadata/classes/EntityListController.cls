public class EntityListController
{
    public String tableName { get; set; }

    public Boolean wereChanges { get; set; }

    @TestVisible 
    private Map<String, Entity__c> entityMap { get; set; }

    private Entity__c newEntity;

    public EntityListController()
    {
        this.entityMap = new Map<String, Entity__c>();

        for (Entity__c entity :
        [
            SELECT Id, Name, TableName__c
            FROM Entity__c
        ])
        {
            entityMap.put(entity.TableName__c, entity);
        }
    }

    public List<Entity__c> getEntities()
    {
        return entityMap.values();
    }

    public void deleteEntity()
    {
        Database.delete(entityMap.get(tableName));
        entityMap.remove(tableName);
    }

    public void addRow()
    {
        newEntity = new Entity__c();
        entityMap.put(null, newEntity);
        setWereChanges();
    }

    public void applyChanges()
    {
        Map<String, Entity__c> newEntityMap = new Map<String, Entity__c>();

        Set<Entity__c> entitiesToUpdate = new Set<Entity__c>(entityMap.values());
        entitiesToUpdate.remove(newEntity);

        Boolean hasError = false;

        for (Entity__c entity : entitiesToUpdate)
        {
            if(containsNumbersOrNonAlphabeticCharacters(entity.TableName__c))
            {
                 hasError = true;
            }
        }

        if (!hasError)
        {
            Database.SaveResult[] results = Database.update(new List<Entity__c>(entitiesToUpdate), false);

            for(Database.SaveResult result : results)
            {
                if(!result.isSuccess())
                {
                    hasError = true;
                }
            }

            if (newEntity != null)
            {
                if (entityMap.containsKey(newEntity.TableName__c) || containsNumbersOrNonAlphabeticCharacters(newEntity.TableName__c))
                {
                    hasError = true;
                }
                else
                {
                    newEntityMap.put(newEntity.TableName__c, newEntity);

                    Database.insert(newEntity);
                    newEntity = null;
                }
            }
        }

        if (hasError)
        {
            showError();
        }
        else
        {
            for (Entity__c entity : entitiesToUpdate)
            {
                newEntityMap.put(entity.TableName__c, entity);
            }
            entityMap = newEntityMap;
            wereChanges = false;
        }
    }

    @TestVisible 
    private Boolean containsNumbersOrNonAlphabeticCharacters(String str)
    {
        Matcher matcher = Pattern.compile('(\\W)|(\\s)|(\\d)').matcher(str);
        return matcher.find();

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
        ApexPages.Message error = new ApexPages.Message(ApexPages.Severity.ERROR, 'Table name must be unique and contains only alphabetic characters or underscores');
        ApexPages.addMessage(error);
    }
}