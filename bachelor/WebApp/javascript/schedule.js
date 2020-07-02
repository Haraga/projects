//SCHEDULE - DONE
function getSchedule(element) {
    checkMainWindow();
    checkWindow();
    displayWindow("scheduleWindow");
    selectTool(element);
}

function fillInDisciplineInputs(discipline) {
    //ADDRESS
    setStandardText('scheduleLatitude', discipline['address']['latitude']);
    setStandardText('scheduleLongitude', discipline['address']['longitude']);
    setStandardText('addressName', discipline['address']['name']);
    setStandardText('addressRoom', discipline['address']['room']);

    //DISCIPLINE INFO
    setStandardText('disciplineName', discipline['name']);
    setStandardText('disciplineFullName', discipline['fullName']);
    $('#disciplineType').val(discipline['type']);
    $('#disciplineDay').val(discipline['day']);
    setStandardText('disciplineStartHour', discipline['startHour']);
    setStandardText('disciplineEndHour', discipline['endHour']);
    if (discipline['week'] == 0) {
        $('#disciplineWeek').val("Every Week");
    } else if (discipline['week'] == 1) {
        $('#disciplineWeek').val("Uneven");
    } else {
        $('#disciplineWeek').val("Even");
    }
    setStandardText('disciplineGroup', discipline['group']);
}

function getDisciplineKey(){
    if(!checkNotEmptyInput('disciplineName')){
        displayMessage('errorMessageSchedule', "Fill in the class name input!");
        return;
    }
    let professorData = JSON.parse(sessionStorage['professorData']);
    firebase.database().ref().child('discipline').once("value", function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
            if(childSnapshot.child("professor").child("email").val() == professorData['email']
                && childSnapshot.child("name").val() == getTextboxContent('disciplineName') &&
                childSnapshot.child("type").val() == getTextboxContent('disciplineType')){
                console.log(childSnapshot.key);
                updateDiscipline(childSnapshot.key);
            }
        });
    });
}

function updateDiscipline(disciplineKey) {
    let updates = {};
    if(checkNotEmptyInput('scheduleLatitude')){
        updates['address/latitude'] = getTextboxContent('scheduleLatitude');
    }
    if(checkNotEmptyInput('scheduleLongitude')){
        updates['address/longitude'] = getTextboxContent('scheduleLongitude');
    }
    if(checkNotEmptyInput('addressName')){
        updates['address/name'] = getTextboxContent('addressName');
    }
    if(checkNotEmptyInput('addressRoom')){
        updates['address/room'] = getTextboxContent('addressRoom');
    }
    if(checkNotEmptyInput('disciplineDay')){
        updates['day'] = getTextboxContent('disciplineDay');
    }
    if(checkNotEmptyInput('disciplineEndHour')){
        updates['endHour'] = getTextboxContent('disciplineEndHour');
    }
    if(checkNotEmptyInput('disciplineFullName')){
        updates['fullName'] = getTextboxContent('disciplineFullName');
    }
    if(checkNotEmptyInput('disciplineGroup')){
        updates['group'] = getTextboxContent('disciplineGroup');
    }
    if(checkNotEmptyInput('disciplineName')){
        updates['name'] = getTextboxContent('disciplineName');
    }
    if(checkNotEmptyInput('disciplineStartHour')){
        updates['startHour'] = getTextboxContent('disciplineStartHour');
    }
    if(checkNotEmptyInput('disciplineType')){
        updates['type'] = getTextboxContent('disciplineType');
    }
    if(checkNotEmptyInput('disciplineWeek')){
        if (getTextboxContent('disciplineWeek') == "Every Week") {
            updates['startHour'] = 0;
        } else if (getTextboxContent('disciplineWeek') == "Even") {
            updates['startHour'] = 2;
        } else {
            updates['startHour'] = 1;
        }
    }
    console.log(updates);
    firebase.database().ref().child('discipline').child(disciplineKey).update(updates);
}

function addDiscipline() {
    if(checkScheduleInputs()){
        return;
    }


    let reference = firebase.database().ref('discipline').push({
        day: getTextboxContent('disciplineDay'),
        endHour: getTextboxContent('disciplineEndHour'),
        fullName: getTextboxContent('disciplineFullName'),
        group: getTextboxContent('disciplineGroup'),
        name: getTextboxContent('disciplineName'),
        startHour: getTextboxContent('disciplineStartHour'),
        type: getTextboxContent('disciplineType'),
        week: week
    }, function (error) {
        if (error) {
            displayMessage('errorMessageSchedule', "Something went wrong, check console for more details!");
            console.log(error);
        }
    });

    reference.child('address').set({
        latitude: getTextboxContent('scheduleLatitude'),
        longitude: getTextboxContent('scheduleLongitude'),
        name: getTextboxContent('addressName'),
        room: getTextboxContent('addressRoom')
    }, function (error) {
        if (error) {
            displayMessage('errorMessageSchedule', "Something went wrong, check console for more details!");
            console.log(error);
        }
    });

    let professorData = JSON.parse(sessionStorage['professorData']);

    reference.child('professor').set({
        email: professorData['email'],
        firstName: professorData['firstName'],
        lastName: professorData['lastName'],
        phoneNumber: professorData['phoneNumber'],
        rank: professorData['rank'],
        web: professorData['web']
    }, function (error) {
        if (error) {
            displayMessage('errorMessageSchedule', "Something went wrong, check console for more details!");
            console.log(error);
        } else {
            displayMessage('successMessageSchedule', "Discipline added successful!");
            window.location.hash = 'successMessageSchedule';
            clearDisciplineInputs();
        }
    });
}

function checkScheduleInputs(){
    if(!checkNotEmptyInput('scheduleLatitude')){
        displayMessage('errorMessageSchedule', "Fill in the latitude input!");
        return true;
    }
    if(!checkNotEmptyInput('scheduleLongitude')){
        displayMessage('errorMessageSchedule', "Fill in the longitude input!");
        return true;
    }
    if(!checkNotEmptyInput('addressName')){
        displayMessage('errorMessageSchedule', "Fill in the address name input!");
        return true;
    }
    if(!checkNotEmptyInput('addressRoom')){
        displayMessage('errorMessageSchedule', "Fill in the address room input!");
        return true;
    }
    if(!checkNotEmptyInput('disciplineName')){
        displayMessage('errorMessageSchedule', "Fill in the class name input!");
        return true;
    }
    if(!checkNotEmptyInput('disciplineFullName')){
        displayMessage('errorMessageSchedule', "Fill in the class full name input!");
        return true;
    }
    if(!checkNotEmptyInput('disciplineStartHour')){
        displayMessage('errorMessageSchedule', "Fill in the class start hour input!");
        return true;
    }
    if(!checkNotEmptyInput('disciplineEndHour')){
        displayMessage('errorMessageSchedule', "Fill in the class end hour input!");
        return true;
    }
    if(!checkNotEmptyInput('disciplineGroup')){
        displayMessage('errorMessageSchedule', "Fill in the class group input!");
        return true;
    }
    return false;
}

function clearDisciplineInputs() {
    clearTextbox('scheduleLatitude');
    clearTextbox('scheduleLongitude');
    clearTextbox('addressName');
    clearTextbox('addressRoom');
    clearTextbox('disciplineName');
    clearTextbox('disciplineFullName');
    clearTextbox('disciplineStartHour');
    clearTextbox('disciplineEndHour');
    clearTextbox('disciplineGroup');
}