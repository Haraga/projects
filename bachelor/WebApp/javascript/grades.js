//GRADES - DONE
function getGrades(element) {
    if (!checkIfDisciplineSelected()) {
        return;
    }
    checkMainWindow();
    checkWindow();
    displayWindow("gradesWindow");
    selectTool(element);
    getStudents();
}

function getStudents() {
    clearMessage();
    const database = firebase.database().ref().child('student');
    let table = document.querySelector('#gradesTable tbody');
    let discipline = getDiscipline();
    database.orderByChild("group").startAt(discipline['disciplineYearOfStudy'])
        .on('value', function (snap) {
            while (table.hasChildNodes()) {
                table.removeChild(table.firstChild);
            }
            let students = snap.val();
            for (let i in students) {
                let row = table.insertRow(-1);
                let cellName = row.insertCell(-1);
                cellName.innerHTML = students[i]['firstName'] + " " + students[i]['lastName'];
                let cellEmail = row.insertCell(-1);
                cellEmail.innerHTML = students[i]['email'];
                let checkBox = document.createElement('input');
                checkBox.setAttribute('type', 'radio');
                checkBox.setAttribute('name', 'student');
                let cellCheckBox = row.insertCell(-1);
                cellCheckBox.append(checkBox);
            }
            addCheck();
        });
}

$(function () {
    $('input[name="optionGrades"]').change('checked', function () {
        if ($(this).val() == "markStudent") {
            $('#markStudentContent').css("display", "contents");
        } else {
            $('#markStudentContent').css("display", "none");
        }
        if ($(this).val() == "markStudents") {
            $('#markStudentsContent').css("display", "contents");
        } else {
            $('#markStudentsContent').css("display", "none");
        }
    });
});

function addCheck() {
    $('input[name="student"]').change('checked', function () {
            let $ele = $('input[name="student"]:checked');
            if ($ele.length) {
                let $tds = $ele.closest('tr').find('td')
                document.getElementById('studentEmailMark').value = $tds.eq(1).text();
            }
        }
    );
}

function addMark() {
    if (!checkNotEmptyInput('studentEmailMark')) {
        displayMessage('errorMessageGrades', "No email given, please add one!");
        return;
    }
    if (!checkNotEmptyInput('studentMark')) {
        displayMessage('errorMessageGrades', "No mark given, please add one!");
        return;
    }
    if (!checkNotEmptyInput('studentCourseNumber')) {
        displayMessage('errorMessageGrades', "No course number given, please add one!");
        return;
    }
    getStudentMarkKey(document.getElementById('studentEmailMark').value);
}

function getStudentMarkKey(studentEmail) {
    const database = firebase.database().ref().child('marks');
    let key = false;
    database.once("value", snap => {
            snap.forEach(function (data) {
                if (data.val()['user'] == studentEmail) {
                    key = data.key;
                }
            });
            insertMark(key);
        }
    );
}

function insertMark(key) {
    let discipline = getDiscipline();
    setStandardText('studentRemark', "All good, keep it up!");
    firebase.database().ref().child('marks')
        .child(key)
        .child(discipline['disciplineName']).push({
        mark: getTextboxContent("studentMark"),
        number: getTextboxContent("studentCourseNumber"),
        remark: getTextboxContent('studentRemark')
    }, function (error) {
        if (error) {
            displayMessage('errorMessageGrades', "Something went wrong, check console for more details!");
            console.log(error);
        } else {
            displayMessage('successMessageGrades', "Mark inserted successful!");
            window.location.hash = 'successMessageGrades';
            //clear inputs
            clearTextbox('studentEmailMark');
            clearTextbox('studentMark');
            clearTextbox('studentCourseNumber');
            clearTextbox('studentRemark');
        }
    });

}