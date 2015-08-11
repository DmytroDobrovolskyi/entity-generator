public with sharing class Requester
{
    public static Integer sendRequest(HttpRequest req)
    {
        return new Http().send(req).getStatusCode();
    }
}