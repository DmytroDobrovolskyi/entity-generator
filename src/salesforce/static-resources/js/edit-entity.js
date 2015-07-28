$(function ()
{
    resolveCheckboxes();
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

function resolveChanges(fieldId)
{
    callResolveChanges(fieldId);
}

function resolveCheckboxes()
{
    $(".typeList")
        .children()
        .each(function (index, element)
        {
            var type = $(element);
            var value = element.text;
            if (type.is(':selected'))
            {
                var checkbox = type
                    .closest('td')
                    .next()
                    .next()
                    .children();
                if (value === 'int' || value === 'varchar(255)')
                {
                    checkbox.css('display', 'block');
                }
                else
                {
                    checkbox
                        .css('display', 'none')
                        .prop( "checked", false );
                }
            }
        });

    var checkboxes = $('.pk-checkboxes');

    var allIsUnchecked = true;

    checkboxes.each(function (index, element)
    {
        if ($(element).is(':checked'))
        {
            $(element).removeAttr('disabled');
            allIsUnchecked = false;
        }
        else
        {
            $(element).attr("disabled", true);
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
        name = name
            .replace(/ /g, '_')
            .replace(/\d+/g, '')
            .replace(/\W/g, '')
            .replace(/^[a-z]/, function (firstCharacter)
            {
                return firstCharacter.toUpperCase()
            });

        for(var i=0;i<name.length;i++)
        {
            if (name.charAt(i)=='_')
            {
                name =  name.replace(/_[a-z]/, function(character){ return character.toUpperCase() });
            }
        }
        context.value = name;
    }
}

function fillForReset()
{
    $('.input')
        .filter(function (index, element)
        {
            console.log(element);
            return !element.value;
        })
        .each(function (index, element)
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
