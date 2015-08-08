$(function ()
{
    $.material.init();
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
    resolveGenerateButtonVisibility();
});

function init()
{
    $('[data-toggle="popover"]').popover();
}

function resolveChanges(entityId)
{
    callResolveChanges(entityId);
}

function resolveGenerateButtonVisibility()
{
    EntityListController.isGenerateButtonVisible
    (
        function (result, event)
        {
            var buttonBlock  =  $('.generate-block');
            if (result)
            {
                buttonBlock.show();
            }
            else
            {
                buttonBlock.hide();
            }
        }
    );
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

function initAndDeleteErrors()
{
    init();
    $('.errorMsg').remove();
}

function deleteEntity(tableName)
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
                    callDeleteEntity(tableName);
                    $(this).dialog("close");
                },
                Cancel: function ()
                {
                    $(this).dialog("close");
                }
            }
        });
}

function generateEntities()
{
    var btnBlock = $('.generate-btn');
    var spinner = $('.fa-spinner');

    btnBlock.html('');
    btnBlock.append(spinner);
    spinner.show();

    EntityListController.generateEntities
    (
        function (result, event)
        {
            if (result === 200)
            {
              showSuccessModal();
            }
            else
            {

            }
            btnBlock.parent().append(spinner);
            spinner.hide();
            btnBlock.html('Generate entities');
        }
    );
}


function showSuccessModal()
{
    $("#success-dialog").dialog(
        {
            open: function() {
                $('.ui-widget-overlay').addClass('success-overlay');
            },
            close: function() {
                $('.ui-widget-overlay').removeClass('success-overlay');
            },
            resizable: false,
            width: 350,
            height: 150,
            modal: true,
            dialogClass: 'msg-dialog',
            buttons: {
                "Ok": function ()
                {
                    $(this).dialog("close");
                }
            }
        });
    resolveGenerateButtonVisibility(false);
}
