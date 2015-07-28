public class EntityListController
{
    public String entityId { get; set; }

    public Boolean wereChanges { get; set; }    

    @TestVisible
    private Map<String, Entity__c> newEntityMap { get; set; }

    @TestVisible
    private Map<String, Entity__c> entityMap { get; set; }

    @TestVisible
    private Entity__c newEntity { get; set; }

    private static String newEntityId = 'newEntityId';

    public EntityListController()
    {
        this.entityMap = new Map<String, Entity__c>
        ([
            SELECT Id, Name, TableName__c, IsUpdateNeeded__c
            FROM Entity__c
        ]);
    }

    public List<Entity__c> getEntities()
    {
        return entityMap.values();
    }

    public void deleteEntity()
    {
        Database.delete(entityId, true);
        entityMap.remove(entityId);
    }

    public void addRow()
    {
        newEntity = new Entity__c();
        entityMap.put(newEntityId, newEntity);
        wereChanges = true;
    }

    public void applyChanges()
    {
        newEntityMap = new Map<String, Entity__c>();

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
            for(Entity__c entity : entitiesToUpdate)
            {
                if(entity.IsUpdateNeeded__c)
                {
                    if(Database.update(entity, false).isSuccess())
                    {
                        entity.IsUpdateNeeded__c = false;
                    }
                    else
                    {
                        hasError = true;
                    }
                }
            }

            if (newEntity != null)
            {
                if (isAlreadyExist(entitiesToUpdate, newEntity.TableName__c) ||
                        containsNumbersOrNonAlphabeticCharacters(newEntity.TableName__c))
                {
                    hasError = true;
                }
                else
                {
                    Database.insert(newEntity);
                    newEntityMap.put(newEntity.Id, newEntity);
                   
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
                newEntityMap.put(entity.Id, entity);
            }
            newEntityMap.values().sort();
            entityMap = newEntityMap;
            wereChanges = false;
        }
    }

<<<<<<< HEAD
    private boolean isAlreadyExist(Set<Entity__c> entitiesToUpdate, String tableNameToCheck)
    {
        for (Entity__c entity : entitiesToUpdate)
        {
            if (entity.TableName__c == tableNameToCheck)
            {
                return true;
            }
        }
        return false;
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

    public void resolveChanges()
    {
        Entity__c entityToUpdate = entityMap.get(entityId);
        if (entityToUpdate != null && String.valueOf(entityToUpdate.Id) != newEntityId)
        {
            entityToUpdate.IsUpdateNeeded__c = true;
        }
        wereChanges = true;
          
    }

    private void showError()
    {
        ApexPages.Message error = new ApexPages.Message(ApexPages.Severity.ERROR, 'Table name must be unique and contains only alphabetic characters or underscores');
        ApexPages.addMessage(error);
    }
}