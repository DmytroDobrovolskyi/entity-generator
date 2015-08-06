public class EntityListController
{
    public String entityId { get; set; }

    public Boolean wereChanges { get; set; }

    public String requestLabel;

    @TestVisible
    private Map<String, Entity__c> entityMap { get; set; }

    @TestVisible
    private Entity__c newEntity { get; set; }

    private static String newEntityId = 'newEntityId';

    public String result {get;set;}

    private static final String LONG_RUNNING_SERVICE_URL = 'http://23.251.129.95:8080/entity-generator/generate-tables';

    public boolean isVisible {get; set;}

    public EntityListController()
    {
        this.entityMap = new Map<String, Entity__c>
        ([
            SELECT Id, Name, TableName__c, IsUpdateNeeded__c, IsProcessingNeeded__c
            FROM Entity__c
        ]);
        Set<Entity__c> entitySet = new Set<Entity__c>(entityMap.values());

        for (Entity__c entity : entitySet)
        {
           if (entity.IsProcessingNeeded__c)
           {
               isVisible = true;
           }
        }
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
        isVisible = true;

        Set<Entity__c> entitiesToUpdate = new Set<Entity__c>(entityMap.values());
        entitiesToUpdate.remove(newEntity);

        for (Entity__c entity : entitiesToUpdate)
        {
            if(containsNumbersOrNonAlphabeticCharacters(entity.TableName__c))
            {
                showError();
                return;
            }
        }

        for(Entity__c entity : entitiesToUpdate)
        {
            if (entity.IsUpdateNeeded__c)
            {
                if (Database.update(entity, false).isSuccess())
                {
                    entity.IsUpdateNeeded__c = false;
                }
                else
                {
                    showError();
                    return;
                }
            }
        }

        if (newEntity != null)
        {
            if (isAlreadyExist(entitiesToUpdate, newEntity.TableName__c) ||
                    containsNumbersOrNonAlphabeticCharacters(newEntity.TableName__c))
            {
                showError();
                return;
            }
            else
            {
                Database.insert(newEntity);

                entityMap.remove(newEntityId);
                entityMap.put(newEntity.Id, newEntity);

                newEntity = null;
            }
        }
        wereChanges = false;
    }

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

    public Object startRequest()
    {
        isVisible = false;
        Continuation con = new Continuation(60);
        con.continuationMethod='processResponse';
        HttpRequest req = new HttpRequest();
        req.setMethod('POST');
        req.setEndpoint(LONG_RUNNING_SERVICE_URL);
        this.requestLabel = con.addHttpRequest(req);
        return con;
    }

    public Object processResponse()
    {
        HttpResponse response = Continuation.getResponse(this.requestLabel);
        this.result = response.getBody();
        return null;
    }
}
