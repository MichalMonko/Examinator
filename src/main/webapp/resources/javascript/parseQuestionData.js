var howManyTimesToAsk = 3;
var jsonData = document.getElementById("jsonData").value;

var questions = JSON.parse(jsonData);
var boardStatus;

var questionsNumber = questions.length;
var pendingQuestions = [];
var answersStatusHolder = [];

var secondsPassed = 0;
var minutesPassed = 0;
var hoursPassed = 0;

$(document).keydown(function (e)
{
    if (e.keyCode === 13)
    {
        var button = $("#nextQuestionButton");
        if (button !== null)
        {
            button.click();
        }
    }
});

saveBoardHtml();
resetQuizState();
drawWinBoard();

function resetQuizState()
{
    restoreBoardInitialStatus();

    for (var i = 0; i < questionsNumber; i++)
    {
        pendingQuestions[i] = {"questionIndex": i, "timesToAskLeft": howManyTimesToAsk};
    }

    loadRandomQuestion();
    updateProgressBar();

    setInterval(function () {updateTimer()}, 1000);
}

function resetVariables()
{
    howManyTimesToAsk = 3;
    jsonData = document.getElementById("jsonData").value;

    questions = JSON.parse(jsonData);

    questionsNumber = questions.length;
    pendingQuestions = [];
    answersStatusHolder = [];

    secondsPassed = 0;
    minutesPassed = 0;
    hoursPassed = 0;

    for (var i = 0; i < questionsNumber; i++)
    {
        pendingQuestions[i] = {"questionIndex": i, "timesToAskLeft": howManyTimesToAsk};
    }
}

function saveBoardHtml()
{
    boardStatus = $("#quizBoard").html();
}

function restoreBoardInitialStatus()
{
    $("#quizBoard").html(boardStatus);
}

function drawWinBoard()
{
    $("#quizQuestion").html("<h2>GRATULACJE!</h2>");
    $("#quizAnswers").html("<h3>Opanowałeś wszystkie pytania! Powodzenia na teście!</h3>" +
        "<p>Kliknij w przycisk poniżej aby ponownie rozwiązać quiz</p>");


    $("#quizPanel").html("<div class='btn btn-default btn-block' id='resetButton'></div>");
    var button = $("#resetButton");

    button.removeAttr("onclick");
    button.attr("onclick",
        'resetQuizState()'
    );
    button.html("Rozwiąż ponownie");


}

function loadRandomQuestion()
{

    answersStatusHolder = [];
    $("#quizAnswers").html("");

    var randomQuestionIndex = Math.floor(Math.random() * pendingQuestions.length);
    var question = questions[pendingQuestions[randomQuestionIndex].questionIndex];
    console.log(question);

    var questionContent = question.content;
    var answers = question.answers;

    $("#quizQuestion").html(questionContent);

    function setIndexValueToListener(v)
    {
        return function ()
        {
            updateAnswerStatus(v);
        }
    }

    for (var i = 0; i < answers.length; i++)
    {
        var answerContent = answers[i].content;
        console.log(answerContent);
        var answer = $("<div class='answer' id='answer" + i + "'><p>" + answerContent + "</p></div>");
        answer.appendTo("#quizAnswers");

        answersStatusHolder.push({"id": i, "isCorrect": answers[i].correct, "isSelected": false});

        answer.on('click', setIndexValueToListener(i));

    }

//panel update
    $("#counter").html("Ilość wystąpień: " + pendingQuestions[randomQuestionIndex].timesToAskLeft);

    $("#nextQuestionButton").removeAttr("onclick").html("Sprawdź").attr("onclick",
        'updateProgress(' + randomQuestionIndex + ')'
    );

}

function updateProgress(questionIndex)
{

    $("#quizAnswers").find("*").off('click');
    var correctMarkedAnswers = 0;
    var correctAnswers = 0;

    for (var i = 0; i < answersStatusHolder.length; i++)
    {
        var answerStatus = answersStatusHolder[i];

        var isCorrect = (answerStatus.isCorrect === true);

        if (isCorrect)
        {
            correctAnswers++;
        }

        if (answerStatus.isSelected && isCorrect)
        {
            correctMarkedAnswers++;
        }


        var answer = $("#answer" + i);

        if (isCorrect)
        {

            if (answerStatus.isSelected)
            {
                answer.attr("class", "answer answer-pending answer-correct");
            }
            else
            {
                answer.attr("class", "answer answer-correct");
            }
        }
        else
        {
            if (answerStatus.isSelected)
            {
                answer.attr("class", "answer answer-pending answer-incorrect");
            }
            else
            {
                answer.attr("class", "answer answer-incorrect");
            }
        }
    }

    {
        if (correctAnswers === correctMarkedAnswers)
        {
            if ((--pendingQuestions[questionIndex].timesToAskLeft) <= 0)
            {
                updateProgressBar();
                pendingQuestions.splice(questionIndex, 1);
                checkForWin();
            }
        }
        else
        {
            ++pendingQuestions[questionIndex].timesToAskLeft;
        }
    }

    var button = $("#nextQuestionButton");
    button.removeAttr("onclick");
    button.attr("onclick",
        'loadRandomQuestion()'
    );
    button.html("Kontynuuj");

}

function checkForWin()
{
    if (pendingQuestions.length <= 0)
    {
        resetVariables();
        restoreBoardInitialStatus();
        drawWinBoard();
    }
}

function updateAnswerStatus(index)
{
    if ($("#nextQuestionButton").html() === "Sprawdź")
    {

        var answer = $("#answer" + index);
        var answerClass = answer.attr("class");
        if (answerClass === "answer")
        {
            answer.attr("class", "answer answer-pending");
            answersStatusHolder[index].isSelected = true;
        }
        else
        {
            answer.attr("class", "answer");
            answersStatusHolder[index].isSelected = false;
        }
    }
}

function updateProgressBar()
{
    var progress = Math.floor(((questions.length - pendingQuestions.length) / questions.length) * 100);

    $("#progressBar")
        .css("width", progress + "%")
        .html((questions.length - pendingQuestions.length) + " / " + questions.length);
}

function updateTimer()
{
    var timer = $("#timer");
    var timerString = "Czas nauki: ";

    if ((++secondsPassed) >= 60)
    {
        secondsPassed = 0;

        if ((++minutesPassed >= 60))
        {
            minutesPassed = 0;
            hoursPassed++;
        }

        if (hoursPassed > 0)
        {
            timerString += "0" + hoursPassed + ":";
        }
    }

    if (minutesPassed >= 10)
    {
        timerString += minutesPassed + ":";
    }
    else
    {
        timerString += "0" + minutesPassed + ":";
    }

    if (secondsPassed >= 10)
    {
        timerString += secondsPassed;
    }
    else
    {
        timerString += "0" + secondsPassed;
    }

    timer.html(timerString);
}
