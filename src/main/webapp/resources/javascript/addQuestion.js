
var nextName = 3;
var counter = 'E'.charCodeAt(0);

function appendAnotherAnswerField()
{
    nextName++;
    counter++;

    var formGroup = document.createElement('div');
    formGroup.setAttribute("class", "form-group");

    var label = document.createElement('label');
    label.setAttribute("class", "controlLabel");
    var labelNode = document.createTextNode("Odpowiedz " + String.fromCharCode(counter));
    label.appendChild(labelNode);

    var answerField = document.createElement('textarea');
    answerField.setAttribute("form", "question_add_form");
    answerField.setAttribute("name", "answer");
    answerField.setAttribute("rows", "5");
    answerField.setAttribute("class", "form-control");

    var inputGroup = document.createElement("div");
    inputGroup.setAttribute("class", "input-group");

    var prependInputGroup = document.createElement("div");
    prependInputGroup.setAttribute("class", "input-group-prepend");

    var button = document.createElement("input");
    button.setAttribute("class", "btn btn-danger");
    button.setAttribute("name", nextName);
    button.setAttribute("type", "button");
    button.setAttribute("onclick", "switchValues(this)");

    prependInputGroup.appendChild(button);

    inputGroup.appendChild(answerField);
    inputGroup.appendChild(prependInputGroup);

    var hiddenValues = document.getElementById("correctValues");
    var value = document.createElement("input");
    value.setAttribute("type", "hidden");
    value.setAttribute("name", "correct");
    value.setAttribute("value", "0");

    hiddenValues.appendChild(value);

    var break_line = document.createElement('br');

    formGroup.appendChild(label);
    formGroup.appendChild(break_line);
    formGroup.appendChild(inputGroup);

    var form = document.getElementById("question_add_form");

    form.insertBefore(formGroup, document.getElementById("submit_section"));
}

function switchValues(id)
{
    var currentClass = id.getAttribute("class");
    if ((currentClass.match("success")) != null)
    {
        id.setAttribute("class", "btn btn-danger");
    }
    else
    {
        id.setAttribute("class", "btn btn-success");
    }

    var inputIndex = Number(id.name);
    var hiddenDiv = document.getElementById("correctValues");
    var hiddenInputs = hiddenDiv.getElementsByTagName("input");

    var correspondingItem = hiddenInputs[ inputIndex ];

    var currentValue = correspondingItem.value;
    if (currentValue === '0')
    {
        correspondingItem.value = 1;
    }
    else
    {
        correspondingItem.value = 0;
    }
}
