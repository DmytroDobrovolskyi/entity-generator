$(function ()
{
    $('[data-toggle="popover"]').popover();
});


function setWereChanges()
{
    EditEntityController.setWereChanges();
}

function generateColumnName(context)
{
    var name = null;

    $(context)
        .parent()
        .parent()
        .prev()
        .children()
        .children('input')
        .each(function (index, element)
        {
            name = element.value;
        });

    context.value = name
        .replace(/ /g, '_')
        .replace(/\d+/g, '')
        .replace(/\W/g, '')
        .toUpperCase();
}

function fillForReset()
{
    var inputs = $('.requiredInput')
        .children('input')
        .filter(function (index, element)
        {
            return !element.value;
        }).each(function (index, element)
        {
            element.value = 'reset value';
        });
}
