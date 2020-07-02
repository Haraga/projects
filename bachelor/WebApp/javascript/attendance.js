//attendance global variable
let studentsReference;
let presentStudents = [];
let attendanceNumber;

//ATTENDANCE
function getLocation() {
    if (document.getElementById('withLocation').checked) {
        navigator.geolocation.getCurrentPosition(function (position) {
            let latitude = position.coords.latitude;
            let longitude = position.coords.longitude;
            createAttendanceSession(latitude, longitude);
        });
    } else {
        createAttendanceSession(-200, -200);
    }
}

function makeAttendance(element) {
    clearMessage();
    if (!checkIfDisciplineSelected()) {
        return;
    }
    checkMainWindow();
    checkWindow();
    displayWindow("attendanceWindow");
    selectTool(element);
}

function createAttendanceSession(latitude, longitude) {
    clearMessage();
    let discipline = getDiscipline();
    let date = new Date();
    let time = date.getTime();

    let reference = firebase.database().ref('forAttendance').push({
        code: getTextboxContent('attendanceCode'),
        discipline: discipline['disciplineName'],
        latitude: latitude,
        longitude: longitude,
        professor: sessionStorage.getItem('email'),
        timeStamp: time,
        type: discipline['disciplineType'],
        typeNumber: getTextboxContent('attendanceNumber')
    }, function (error) {
        if (error) {
            displayMessage('errorMessageAttendance', "Something went wrong, check console for more details!");
            console.log(error);
        } else {
            displayMessage('successMessageAttendance', "Attendance session created successful!");
            window.location.hash = 'successMessageAttendance';
            attendanceNumber = parseInt(getTextboxContent('attendanceNumber'));
            clearTextbox('attendanceCode');
            clearTextbox('attendanceNumber');
        }
    });
    document.getElementById('attendanceCode').value = "";
    studentsReference = reference.key;
}


function getList() {
    clearMessage();
    if (!studentsReference) {
        displayMessage('errorMessageAttendance', "Please create attendance session to see list!");
        return;
    }
    const database = firebase.database().ref().child('forAttendance').child(studentsReference).child('students');
    let table = document.querySelector('#attendanceTable tbody');
    presentStudents = [];
    database.on('value', snap => {
        while (table.hasChildNodes()) {
            table.removeChild(table.firstChild);
        }
        let count = 1;
        let students = snap.val();
        for (let student in students) {
            let row = table.insertRow(-1);
            let cellCount = row.insertCell(-1);
            cellCount.innerHTML = count;
            let cellName = row.insertCell(-1);
            cellName.innerHTML = students[student]['name'];
            let cellEmail = row.insertCell(-1);
            cellEmail.innerHTML = students[student]['email'];
            presentStudents.push(students[student]['email']);
            count += 1;
        }
    });
}

function writeStudentsToAttendance() {
    clearMessage();
    if (!attendanceNumber) {
        displayMessage('errorMessageAttendance', "Please type in a class number!");
        return;
    }
    let discipline = getDiscipline();
    if (presentStudents) {
        presentStudents.forEach(function (student) {
            findStudentAttendanceKey(student, discipline['disciplineName'], discipline['disciplineType']);
        })
    }
}

function findStudentAttendanceKey(studentEmail, disciplineName, type) {
    const database = firebase.database().ref().child('attendance');
    let key = false;
    database.once("value", snap => {
            snap.forEach(function (data) {
                if (data.val()['user'] == studentEmail) {
                    key = data.key;
                }
            });
            if(key != false){
                findStudentAttendanceNumber(key, disciplineName, type);
            }
        }
    );
}

function findStudentAttendanceNumber(studentKey, disciplineName, type) {
    const database = firebase.database().ref().child('attendance').child(studentKey)
        .child(disciplineName).child(type);
    let key = false;
    database.limitToFirst(attendanceNumber).once("value", snapshot => {
        snapshot.forEach(function (childSnapshot) {
            key = childSnapshot.key;
        });
        if(key){
            insertStudentAttendance(studentKey, disciplineName, type, key);
        }
    });
}

function insertStudentAttendance(studentKey, disciplineName, type, attendanceKey){
    let updates = {};
    updates[attendanceKey] = 1;
    firebase.database().ref().child('attendance').child(studentKey).child(disciplineName)
        .child(type).update(updates);
}