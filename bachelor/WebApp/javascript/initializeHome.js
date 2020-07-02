//GLOBAL VARIABLES
let professorEmail = sessionStorage.getItem("email");
let professorFound = 0;

//CHECK SESSION FOR SELECTED DISCIPLINE

function main() {
    checkUser();
    getProfessorData();
}

function checkSession() {
    if(sessionStorage.getItem('selectedDisciplineID')){
       let element = document.getElementById(sessionStorage.getItem('selectedDisciplineID'));
        element.style.backgroundColor = "#4e73df";
        element.style.color = "white";
    }
}

function checkUser() {
    if (!professorEmail) {
        firebase.auth().onAuthStateChanged(function (user) {
            if (!user) {
                window.location.replace("http://localhost/UbbAppWeb/index.html");
            } else {
                firebase.auth().signOut();
                window.location.replace("http://localhost/UbbAppWeb/index.html");
            }
        });
    }
}

function getProfessorImage() {
    let imageUrl = "professor/" + sessionStorage.getItem("professorImageName") + ".jpg";
    let storageRef = firebase.storage().ref();
    storageRef.child(imageUrl).getDownloadURL().then(function (url) {
        $('#professorImage').attr('src', url);
    }).catch(function (error) {
        console.log(error);
    });
}

function insertAfterElement(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}

let storedValues = [];
function addYearsToAnnouncement(value) {
    if(checkForValues(value)){
        return;
    }
    let select = document.getElementById('years');
    let option = document.createElement('option');
    option.value = value;
    option.innerHTML = value;
    select.appendChild(option);
    storedValues.push(value);
}

function checkForValues(value) {
    for (let i = 0; i < storedValues.length; i += 1){
        if(storedValues[i] == value){
            return true;
        }
    }
    return false;
}

function displayDisciplines(item) {
    let yearOfStudyElement;
    if (item['disciplineYearOfStudy'].charAt(1) == '1') {
        yearOfStudyElement = document.getElementById("firstYearOfStudy");
        addYearsToAnnouncement(item['disciplineYearOfStudy'].substring(0, 3));
    } else if (item['disciplineYearOfStudy'].charAt(1) == '2') {
        yearOfStudyElement = document.getElementById("secondYearOfStudy");
        addYearsToAnnouncement(item['disciplineYearOfStudy'].substring(0, 3));
    } else {
        yearOfStudyElement = document.getElementById("thirdYearOfStudy");
        addYearsToAnnouncement(item['disciplineYearOfStudy'].substring(0, 3));
    }
    createDisciplineAnchor(item, yearOfStudyElement);
}

function createAnchor(discipline) {
    let disciplineInfo = discipline['disciplineName'] + "(" + discipline['disciplineType'] + ")" + discipline['disciplineYearOfStudy'];
        ;
    let anchor = document.createElement("a");
    anchor.className += "collapse-item";
    anchor.setAttribute("id", disciplineInfo);
    anchor.setAttribute("onClick", "anchorAction(this); return false;");
    anchor.textContent = disciplineInfo;
    anchor.something = JSON.stringify(discipline);
    return anchor;
}

function createDisciplineAnchor(discipline, parentNode) {
    insertAfterElement(parentNode, createAnchor(discipline));
}

function deselectElement(id) {
    document.getElementById(id).removeAttribute("style");
}

function anchorAction(element) {
    if (sessionStorage.getItem('selectedDisciplineID')) {
        deselectElement(sessionStorage.getItem('selectedDisciplineID'));
    }
    checkWindow();
    showMainWindow();
    sessionStorage['selectedDiscipline'] = element.something;
    sessionStorage['selectedDisciplineID'] = element.id;
    element.style.backgroundColor = "#4e73df";
    element.style.color = "white";
}

function getProfessorData() {
    let database = firebase.database();
    let ref = database.ref("discipline");
    let table = document.querySelector('#scheduleTable tbody');
    ref.once("value").then(function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
            if (childSnapshot.child("professor").child("email").val() == professorEmail) {
                //professor name
                let professorFirstName = childSnapshot.child("professor").child("firstName").val();
                let professorLastName = childSnapshot.child("professor").child("lastName").val();
                let professorFullName = professorFirstName + " " + professorLastName;
                let professorImageName = professorFirstName + "-" + professorLastName;
                if (professorFound == 0) {
                    let object = {};
                    object['email'] = childSnapshot.child("professor").child("email").val();
                    object['firstName'] = childSnapshot.child("professor").child("firstName").val();
                    object['lastName'] = childSnapshot.child("professor").child("lastName").val();
                    object['phoneNumber'] = childSnapshot.child("professor").child("phoneNumber").val();
                    object['rank'] = childSnapshot.child("professor").child("rank").val();
                    object['web'] = childSnapshot.child("professor").child("web").val();
                    let professorStringData= JSON.stringify(object);
                    sessionStorage.setItem("professorData", professorStringData);
                    sessionStorage.setItem("professorImageName", professorImageName);
                    sessionStorage.setItem("professorFullName", professorFullName);
                    $('#professorFullName').html(professorFullName);
                    professorFound = 1;
                    getProfessorImage();
                }

                //Pentru fiecare materie gasita voi folosi o functie ca sa creez un tabel pentru schedule ca la grades
                createScheduleTable(childSnapshot, table);

                //Aici trebuie gandit daca vrei si cursurile sau nu, daca da atunci iful nu mai are rost
                if (childSnapshot.child("type").val() == "seminary" || childSnapshot.child("type").val() == "laboratory") {
                    let object = {};
                    object['disciplineName'] = childSnapshot.child("name").val();
                    object['disciplineYearOfStudy'] = childSnapshot.child("group").val()
                    object['disciplineType'] = childSnapshot.child("type").val();
                    displayDisciplines(object);
                }
            }
        });
        checkSession();
    });
}

function createScheduleTable(discipline, table){
    let row = table.insertRow(-1);
    let cellName = row.insertCell(-1);
    cellName.innerHTML = discipline.child('name').val();
    cellName.dataset.disciplineInfo = JSON.stringify(discipline);
    let cellFullName = row.insertCell(-1);
    cellFullName.innerHTML = discipline.child('fullName').val();
    let cellType = row.insertCell(-1);
    cellType.innerHTML = discipline.child('type').val();
    let cellDay = row.insertCell(-1);
    cellDay.innerHTML = discipline.child('day').val();
    let cellStartHour = row.insertCell(-1);
    cellStartHour.innerHTML = discipline.child('startHour').val();
    let cellEndHour = row.insertCell(-1);
    cellEndHour.innerHTML = discipline.child('endHour').val();
    let cellWeek = row.insertCell(-1);
    cellWeek.innerHTML = discipline.child('week').val();
    let cellGroup = row.insertCell(-1);
    cellGroup.innerHTML = discipline.child('group').val();

    let checkBox = document.createElement('input');
    checkBox.setAttribute('type', 'radio');
    checkBox.setAttribute('name', 'discipline');
    let cellCheckBox = row.insertCell(-1);
    cellCheckBox.append(checkBox);

    $(function () {
        $('input[name="discipline"]').change('checked', function () {
            let $ele = $('input[name="discipline"]:checked');
            if($ele.length){
                let $tds = $ele.closest('tr').find('td');
                let $discipline = $tds.eq(0);
                fillInDisciplineInputs($discipline.data('disciplineInfo'));
            }
        });
    })
}

main();