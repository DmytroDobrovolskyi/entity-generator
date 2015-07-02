$(function ()
{
    init();
    var inputs = $('.input');
    inputs.blur(function (index, element)
    {
        console.log("blur");
    });
});

function test()
{
    console.log("test()");
}

function init()
{
    console.log("init()");
    $('[data-toggle="popover"]').popover();
}


function setWereChanges()
{
    console.log("setWereChanges()");
    EntityListController.setWereChanges();
}

function generateTableName(context)
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

    if (!context.value)
    {
        context.value = name
            .replace(/ /g, '_')
            .replace(/\d+/g, '')
            .replace(/\W/g, '')
            .toUpperCase();
    }
}

function fillForReset()
{
    $('.input')
        .filter(function (index, element)
        {
            return !element.value;
        })
        .each(function (index, element)
        {
            element.value = 'resetting...';
        });
}
