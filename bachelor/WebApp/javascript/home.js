let selectedToolID, selectedWindowID, lastMessageID;

//RANDOM
function checkIfDisciplineSelected() {
    if (sessionStorage['selectedDiscipline']) {
        return true;
    }
    alert("Please select a Course!");
    return false;
}

function checkWindow() {
    if (selectedWindowID) {
        let window = document.getElementById(selectedWindowID);
        window.className = "beforeClick";
    }
}

function displayWindow(id) {
    let window = document.getElementById(id);
    selectedWindowID = id;
    window.className = "afterClick";
}

function selectTool(element) {
    if (selectedToolID) {
        deselectElement(selectedToolID);
    }
    selectedToolID = element.id;
    element.style.backgroundColor = "#4e73df";
    element.style.color = "white";
}

function setStandardText(id, content) {
    document.getElementById(id).value = content;
}

function displayMessage(id, message) {
    lastMessageID = id;
    document.getElementById(id).textContent = message;
}

function clearMessage() {
    if (lastMessageID) {
        displayMessage(lastMessageID, "");
    }
}

function clearTextbox(id) {
    document.getElementById(id).value = "";
}

function getTextboxContent(id) {
    return document.getElementById(id).value;
}

function logOut() {
    firebase.auth().signOut();
    sessionStorage.clear();
    window.location.replace("http://localhost/UbbAppWeb/index.html");
}

function checkNotEmptyInput(id) {
    return document.getElementById(id).value;
}

function getDiscipline() {
    let disciplineString = sessionStorage.getItem('selectedDiscipline');
    let discipline;
    try {
        discipline = JSON.parse(disciplineString);
    } catch (e) {
        console.error(e);
    }
    return discipline;
}

function showMainWindow() {
    document.getElementById('mainWindow').className = "afterClick";
}

function checkMainWindow() {
    let className = $("#mainWindow").attr("class")
    if (className == "afterClick") {
        document.getElementById('mainWindow').className = "beforeClick";
    }
}

//JQUERY
(function ($) {
    "use strict"; // Start of use strict

    // Toggle the side navigation
    $("#sidebarToggle, #sidebarToggleTop").on('click', function (e) {
        $("body").toggleClass("sidebar-toggled");
        $(".sidebar").toggleClass("toggled");
        if ($(".sidebar").hasClass("toggled")) {
            $('.sidebar .collapse').collapse('hide');
        }
    });

    // Close any open menu accordions when window is resized below 768px
    $(window).resize(function () {
        if ($(window).width() < 768) {
            $('.sidebar .collapse').collapse('hide');
        }
        ;
    });

    // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
    $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function (e) {
        if ($(window).width() > 768) {
            var e0 = e.originalEvent,
                delta = e0.wheelDelta || -e0.detail;
            this.scrollTop += (delta < 0 ? 1 : -1) * 30;
            e.preventDefault();
        }
    });

    // Scroll to top button appear
    $(document).on('scroll', function () {
        var scrollDistance = $(this).scrollTop();
        if (scrollDistance > 100) {
            $('.scroll-to-top').fadeIn();
        } else {
            $('.scroll-to-top').fadeOut();
        }
    });

    // Smooth scrolling using jQuery easing
    $(document).on('click', 'a.scroll-to-top', function (e) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: ($($anchor.attr('href')).offset().top)
        }, 1000, 'easeInOutExpo');
        e.preventDefault();
    });

})(jQuery); // End of use strict