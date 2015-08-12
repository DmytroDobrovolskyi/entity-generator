var refreshIntervalId;

var RequestStatus =
{
    IN_PROGRESS: 'IN_PROGRESS',
    COMPLETED: 'COMPLETED',
    FAILED: 'FAILED'
};

$(function ()
{
    trackRequestState();

    resolveGenerateButtonVisibility();
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

function trackRequestState()
{
    EntityListController.getRequestState
    (
        function (result, event)
        {
            switch (result)
            {
                case RequestStatus.IN_PROGRESS :
                    showSpinner();
                    break;
                case RequestStatus.COMPLETED :
                    showSuccessModal();

                    hideSpinner();
                    setTimeout(function()
                    {
                        resolveGenerateButtonVisibility();
                    }, 2000);
                    EntityListController.deleteRequestState(function(){});
                    clearInterval(refreshIntervalId);
                    break;
                case RequestStatus.FAILED:
                    showFailModal();

                    hideSpinner();
                    EntityListController.deleteRequestState(function(){});
                    clearInterval(refreshIntervalId);
                    break;
            }
        }
    );
}

function generateEntities()
{
    showSpinner();
    clearInterval(refreshIntervalId);
    refreshIntervalId = setInterval(trackRequestState, 3000);

    EntityListController.generateEntities(function(){});
}

function showSpinner()
{
    var btnBlock = $('.generate-btn');
    var spinner = $('.fa-spinner');

    btnBlock.html('');
    btnBlock.append(spinner);
    spinner.show();
}

function hideSpinner()
{
    var btnBlock = $('.generate-btn');
    var spinner = $('.fa-spinner');

    btnBlock.parent().append(spinner);
    spinner.hide();
    btnBlock.html('Generate entities');
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
            width: 360,
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

function showFailModal()
{
    $("#fail-dialog").dialog(
        {
            open: function() {
                $('.ui-widget-overlay').addClass('fail-overlay');
            },
            close: function() {
                $('.ui-widget-overlay').removeClass('fail-overlay');
            },
            resizable: false,
            width: 520,
            height: 180,
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
