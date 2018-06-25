var questions = [];
var counter;

function sendFiles() {
    console.log(JSON.stringify(questions));
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/api/questions/4", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(questions));
}

function readFiles() {
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
        try {
            var question = convertToObjects(reader.result, file);
            questions.push(question);
        } catch (err) {

        }
        finally {
            counter--;
            if (counter <= 0) {
                sendFiles();
            }
        }
    };
    reader.readAsText(file, "cp1250");
}

function convertToObjects(text, file) {
    var lines = text.toString().split("\n");
    var answers = [];

    var answerLine = lines[0];
    if (answerLine.charAt(0).toLowerCase() !== 'x') {
        throw "Plik " + file.name + " ma niewłaściwy format!";
    }
    var correctAnswer = answerLine.indexOf('1') - 1;

    var questionLine = lines[1];

    for (var i = 2; i < lines.length; ++i) {
        var answerString = lines[i];
        var isCorrect = (i === correctAnswer);
        var answer = {"content": answerString, "isCorrect": isCorrect};
        answers.push(answer);
    }

    return {"content": questionLine, "answers": answers};
}

