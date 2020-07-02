//ANNOUNCEMENTS - DONE
function makeAnnouncement(element) {
    checkMainWindow();
    clearMessage();
    checkWindow();
    displayWindow("announcementWindow");
    selectTool(element);
}

function addAnnouncement() {
    clearMessage();
    if (!checkNotEmptyInput('announcementTitle')) {
        displayMessage('errorMessageAnnouncement', "No title given, please add one!");
        return;
    }
    if (!checkNotEmptyInput('announcementDate')) {
        displayMessage('errorMessageAnnouncement', "No date given, please select one!");
        return;
    }
    if (!checkNotEmptyInput('text')) {
        displayMessage('errorMessageAnnouncement', "No text given, please add one!");
        return;
    }

    let all = document.getElementById('announceAll').checked;
    let yearOfStudy = $('#years').find(":selected").text();

    firebase.database().ref().child('news').push({
        all: all,
        author: sessionStorage["professorFullName"],
        content: document.getElementById('text').value,
        date: document.getElementById('announcementDate').value,
        title: document.getElementById('announcementTitle').value,
        yearOfStudy: yearOfStudy
    }, function (error) {
        if (error) {
            displayMessage('errorMessageAnnouncement', "Something went wrong, check console for mroe details!");
            console.log(error);
        } else {
            displayMessage('successMessageAnnouncement', "Announcement made with succes!");
            window.location.hash = 'successMessageAnnouncement';
            //clear inputs
            clearTextbox('text');
            clearTextbox('announcementTitle');
        }
    });
}