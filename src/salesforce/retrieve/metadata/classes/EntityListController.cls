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
        Database.update(entityMap.values(), false);
        wereChanges = false;
        if (newEntity != null)
        {
            if (entityMap.containsKey(newEntity.TableName__c))
            {
                ApexPages.Message error = new ApexPages.Message(ApexPages.Severity.ERROR, 'Table name must be unique');
                ApexPages.addMessage(error);
                wereChanges = true;
            } else
            {
                entityMap.remove(null);
                entityMap.put(newEntity.TableName__c, newEntity );

                Database.insert(newEntity);
                newEntity = null;
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