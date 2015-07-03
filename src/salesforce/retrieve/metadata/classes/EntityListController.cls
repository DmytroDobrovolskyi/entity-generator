public class EntityListController
{
    public String tableName { get; set; }

    public Boolean wereChanges { get; set; }

    private Map<String, Entity__c> entityMap;

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
        wereChanges = true;
    }

    public void applyChanges()
    {
        Set<Entity__c> entitiesToUpdate = new Set<Entity__c>(entityMap.values());
        entitiesToUpdate.remove(newEntity);

        Database.SaveResult[] results = Database.update(new List<Entity__c>(entitiesToUpdate), false);
        Boolean hasError = false;

        for(Database.SaveResult result : results)
        {
            if(!result.isSuccess())
            {
                hasError = true;
            }
        }

        if (newEntity != null)
        {
            if (entityMap.containsKey(newEntity.TableName__c))
            {
                hasError = true;
            } else
            {
                entityMap.remove(null);
                entityMap.put(newEntity.TableName__c, newEntity);

                Database.insert(newEntity);
                newEntity = null;
            }
        }
        if(hasError)
        {
            ApexPages.Message error = new ApexPages.Message(ApexPages.Severity.ERROR, 'Table name must be unique');
            ApexPages.addMessage(error);
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
}