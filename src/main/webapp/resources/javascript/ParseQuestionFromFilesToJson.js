var questions = [];
var counter;

var submitButton = document.getElementById("addFilesButton");
submitButton.addEventListener("click", function () {
    submitForm()
});

function appendJsonToForm() {
    var jsonData = JSON.stringify(questions);
    var jsonFormInput = document.getElementById("jsonData");
    var encoded = encodeURI(jsonData);
    console.log(encoded);
    var decoded = decodeURI(encoded);
    console.log(decoded);
    jsonFormInput.value = encoded;
}

function submitForm() {
    appendJsonToForm();
    var form = document.getElementById("jsonAddForm");
    form.submit();
}

function readFiles() {
    var filestatusDiv = document.getElementById("file_status");
    filestatusDiv.innerHTML = '';

    var filelist = document.getElementById("file_input").files;
    var numOfFiles = filelist.length;
    counter = numOfFiles;

    for (var i = 0; i < numOfFiles; i++) {
        readFile(filelist[i]);
    }
}

function readFile(file) {
    var reader = new FileReader();
    reader.onload = function () {

        var filestatusDiv = document.getElementById("file_status");
        var fileStatusAlert = document.createElement("div");
        fileStatusAlert.setAttribute("role", "alert");

        try {
            var question = convertToObjects(reader.result, file);
            questions.push(question);

            fileStatusAlert.setAttribute("class", "alert alert-success");
            fileStatusAlert.innerHTML = "<strong>" + file.name + "<strong>" + "" +
                "<span class='glyphicon glyphicon-ok float-right'></span>";

        } catch (err) {
            fileStatusAlert.setAttribute("class", "alert alert-danger");
            fileStatusAlert.innerHTML = "<strong>Błąd!\t<strong>" +
                err +
                "<span class='glyphicon glyphicon-remove float-right'></span>";
        }
        finally {

            filestatusDiv.appendChild(fileStatusAlert);
            counter--;
        }
    };
    reader.readAsText(file, "cp1250");
}

function convertToObjects(text, file) {
    var lines = text.toString().split("\n");
    var answers = [];

    var answerLine = lines[0];
    if (!checkFirstLineFormat(answerLine)) {
        throw ("Plik " + file.name + " ma niewłaściwy format!");
    }

    var correctAnswers = [];

    for (var j = 1; j < answerLine.length; j++) {
        if (answerLine.charAt(j) === '1') {
            correctAnswers.push(j + 1);
        }
    }

    var questionLine = lines[1];

    for (var i = 2; i < lines.length - 1; ++i) {
        var answerString = lines[i].trim();
        if (answerString === "") {
            continue;
        }
        var correct = ($.inArray(i, correctAnswers) !== -1);
        var answer = {"content": answerString, "correct": correct};
        answers.push(answer);
    }

    return {"content": questionLine, "answers": answers};
}

function checkFirstLineFormat(line) {
    var characterNumber = line.toString().length - 1;

    if (characterNumber === 0) {
        return false;
    }
    if (line.charAt(0).toLowerCase() !== 'x') {
        return false;
    }

    for (var i = 1; i < characterNumber; i++) {
        var digit = parseInt(line.toString().charAt(i), 10);
        if (isNaN(digit)) {
            return false;
        }
    }

    return true;
}






