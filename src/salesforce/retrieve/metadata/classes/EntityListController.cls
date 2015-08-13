public class EntityListController
{
    private static final String LONG_RUNNING_SERVICE_URL = getRemoteSettingURL() + 'entity-generator/generate-entities';

    public String entityId { get; set; }

    public Boolean wereChanges { get; set; }
    
    public Set<Entity__c> entitiesToUpdate { get; set; }

    @TestVisible
    private static List<Entity__c> entities =
    [
        SELECT Id, Name, TableName__c, IsUpdateNeeded__c, IsProcessingNeeded__c
        FROM Entity__c
    ];

    @TestVisible
    private Map<String, Entity__c> entityMap { get; set; }

    @TestVisible
    private Entity__c newEntity { get; set; }

    private static String newEntityId = 'newEntityId';

    public EntityListController()
    {
        this.entityMap = new Map<String, Entity__c>(entities);
        Set<Entity__c> entitySet = new Set<Entity__c>(entityMap.values());
    }
        
    public static String getRemoteSettingURL()
    {
        CustomSettingObject__c customSettingObject = CustomSettingObject__c.getInstance('RemoteSiteURL');
    
        return customSettingObject.RemoteSiteURLField__c;
    }

    @RemoteAction
    public static Boolean isGenerateButtonVisible()
    {
        if (getRequestState() == 'IN_PROGRESS')
        {
            return true;
        }

        for (Entity__c entity : entities)
        {
            if (entity.IsProcessingNeeded__c)
            {
                return true;
            }
        }
        return false;
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
        entitiesToUpdate = new Set<Entity__c>(entityMap.values());
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
                entity.IsProcessingNeeded__c = true;
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

    @TestVisible
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

     @RemoteAction
     public static Integer generateEntities()
     {
         HttpRequest req = new HttpRequest();
         req.setMethod('POST');
         req.setEndpoint(LONG_RUNNING_SERVICE_URL);
         req.setTimeout(60000);
         req.setBody(EncodingUtil.urlEncode(UserInfo.getOrganizationId(), 'UTF-8'));

         return Requester.sendRequest(req);
     }

    @RemoteAction
    public static String getRequestState()
    {
        List<RequestState__c> requestState =
        [
                SELECT Status__c
                FROM RequestState__c
        ];

        return requestState.size() > 0 ? requestState[0].Status__c : null;
    }

    @RemoteAction
    public static void deleteRequestState()
    {
        DELETE
        [
                SELECT Id
                FROM RequestState__c
        ];
    }
}