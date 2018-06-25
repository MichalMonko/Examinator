var counter = 'E'.charCodeAt(0);

function appendAnotherAnswerField() {

    var formGroup = document.createElement('div');
    formGroup.setAttribute("class", "form-group");

    var label = document.createElement('label');
    label.setAttribute("class", "controlLabel");
    var labelNode = document.createTextNode("Odpowiedz " + String.fromCharCode(counter));
    label.appendChild(labelNode);

    var answerField = document.createElement('textarea');
    answerField.setAttribute("form", "question_add_form");
    // answerField.setAttribute("id", "answer");
    answerField.setAttribute("name", "answer");
    answerField.setAttribute("rows", "5");
    answerField.setAttribute("class", "form-control");

    var break_line = document.createElement('br');

    formGroup.appendChild(label);
    formGroup.appendChild(break_line);
    formGroup.appendChild(answerField);

    var form = document.getElementById("question_add_form");

    form.insertBefore(formGroup, document.getElementById("submit_section"));
    counter++;
}
