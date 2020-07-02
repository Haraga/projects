//HOMEWORK - DONE
function makeHomework(element) {
    if (!checkIfDisciplineSelected()) {
        return;
    }
    checkMainWindow();
    checkWindow();
    displayWindow("homeworkWindow");
    selectTool(element);
}

function addHomework() {
    clearMessage();
    if (!checkNotEmptyInput('deadline')) {
        displayMessage('errorMessageHomework', "No deadline given, please add one!");
        return;
    }
    if (!checkNotEmptyInput('startWeek')) {
        displayMessage('errorMessageHomework', "No start week given, please add one!");
        return;
    }
    if (!checkNotEmptyInput('title')) {
        displayMessage('errorMessageHomework', "No title given, please add one!");
        return;
    }
    let discipline = getDiscipline();

    let seminary, laboratory
    if (discipline['disciplineType'] == 'seminary') {
        seminary = true;
        laboratory = false;
    } else {
        seminary = false;
        laboratory = true;
    }

    setStandardText('homeworkText', "There are no further information needed!");

    firebase.database().ref().child('homework').push({
        deadline: getTextboxContent('deadline'),
        discipline: discipline['disciplineName'],
        downloadLink: getTextboxContent('extraHomework'),
        seminary: seminary,
        laboratory: laboratory,
        start: getTextboxContent('startWeek'),
        text: getTextboxContent('homeworkText'),
        title: getTextboxContent('title'),
        yearOfStudy: discipline['disciplineYearOfStudy'].substring(0, 2)
    }, function (error) {
        if (error) {
            displayMessage('errorMessageHomework', "Something went wrong, check console for more details!");
            console.log(error);
        } else {
            displayMessage('successMessageHomework', "Homework added successful!");
            window.location.hash = 'successMessageHomework';
            //clear inputs
            clearTextbox('deadline');
            clearTextbox('startWeek');
            clearTextbox('title');
            clearTextbox('extraHomework');
            clearTextbox('homeworkText');
        }
    })
}