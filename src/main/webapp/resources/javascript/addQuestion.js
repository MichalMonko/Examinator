
var nextId = 3;
var counter = 'E'.charCodeAt(0);

function appendAnotherAnswerField()
{
    nextId++;
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

    var button = document.createElement("span");
    button.setAttribute("class", "input-group-addon btn btn-defalut");
    button.setAttribute("id", nextId + "button");
    button.setAttribute("style", "background-color: lightcoral;");
    button.setAttribute("onclick", "switchValues(this)");

    inputGroup.appendChild(answerField);
    inputGroup.appendChild(button);

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
    var currentClass = id.getAttribute("style");
    if ((currentClass.match("green")) != null)
    {
        // id.setAttribute("class", "btn btn-danger");
        id.setAttribute("style","background-color: lightcoral;");
    }
    else
    {
        // id.setAttribute("class", "btn btn-success");
        id.setAttribute("style","background-color: lightgreen;");
    }

    var inputIndex = Number(id.id.toString().charAt(0));
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
