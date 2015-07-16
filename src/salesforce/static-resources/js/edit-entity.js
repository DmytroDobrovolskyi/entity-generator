$(function ()
{
    init();
    var input = $('.input').first();
    var times = 0;
    input.focus(function ()
    {
        input.blur();
        times++;
        if (times === 2) //don't ask why
        {
            input.off('focus');
        }
    });
});

function init()
{
    $('[data-toggle="popover"]').popover();
}

function setWereChanges()
{
    EditEntityController.setWereChanges();
}

function resolveCheckboxes()
{
    var allIsUnchecked = true;

    var checkboxes = $('.pk-checkboxes');

    checkboxes.each(function (index, element)
    {
        if ($(this).is(':checked'))
        {
            $(this).removeAttr('disabled');
            allIsUnchecked = false;
        }
        else
        {
            $(this).attr("disabled", true);
        }
    });

    if (allIsUnchecked)
    {
        checkboxes.removeAttr('disabled');
    }
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

    if (!context.value)
    {
        context.value = name
            .replace(/ /g, '_')
            .replace(/\d+/g, '')
            .replace(/\W/g, '')
            .replace(/^[a-z]/, function (firstCharacter)
            {
                return firstCharacter.toUpperCase()
            })
            .replace(/_[a-z]/, function (character)
            {
                return character.toUpperCase()
            });
    }
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
            element.value = 'resetting...';
        });
}

function initAndDeleteErrors()
{
    resolveCheckboxes();
    init();
    $('.errorMsg').remove();
}

function deleteField(columnName)
{
    $("#dialog-confirm").dialog(
        {
            resizable: false,
            height: 'auto',
            width: 500,
            modal: true,
            dialogClass: 'confirmation-dialog',
            buttons: {
                "Delete": function ()
                {
                    callDeleteField(columnName);
                    $(this).dialog("close");
                },
                Cancel: function ()
                {
                    $(this).dialog("close");
                }
            }
        });
}
