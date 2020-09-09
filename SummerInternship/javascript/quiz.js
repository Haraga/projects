loadAge();
displayImage();

standardSizes = ["XS", "S", "M", "L", "XL", "XXL"];

//Function loads the select tag with option from 18 to 100
function loadAge(){
    let select = document.getElementById('options');
    for(let i = 18; i <= 100; i++) {
        let option = document.createElement('option');
        option.value = i;
        option.text = i;
        select.appendChild(option);
    }
}

//Function displays the name of the selected file
function displayImage() {
    $('#image').change(function(){
        $('#selected-file').text($('#image')[0].files[0].name);
    });
}

//Function that happens when user clicks the Submit button(AJAX)
$('#submit').click(function () {
    //Takes input from user and validates it
    let ok = true;

    let firstname = $('#firstname').val();
    ok = ok && validateInputs('#firstname');
    if(!validName(firstname)){
        printMessage("This is not your firstname!");
        return;
    }

    let lastname = $('#lastname').val();
    ok = ok && validateInputs('#lastname');
    if(!validName(lastname)){
        printMessage("This is not your lastname!");
        return;
    }

    let email = $('#email').val();
    ok = ok && validateInputs('#email');

    let number = $('#number').val();
    ok = ok && validateInputs('#number');
    if(!validNumber(number)){
        printMessage("This is not your number!");
        return;
    }

    let age = $("select[name='age']").val();

    let address = $('#address').val();
    ok = ok && validateInputs('#address');

    let sex = $("input[type='radio'].radio:checked").val();

    let size = $('#size').val();
    ok = ok && validateInputs('#size');
    if(!validSize(size)){
        printMessage("This is not a standard size!");
        return;
    }

    let image = $('#image')[0].files[0].name;
    let type = image.slice((image.lastIndexOf(".") - 1 >>> 0) + 2);
    if (type == "jpeg" || type == "png"){
        console.log(type);
    }
    else{
        printMessage("Invalid file, please select an image!");
        return;
    }

    //If the input is all in order, send data to save.php to handle the query
    if(ok){
        $.ajax({
            type: 'POST',
            url: 'save.php',
            cache: false,
            data: {
                firstname: firstname,
                lastname: lastname,
                email: email,
                number: number,
                age: age,
                address: address,
                sex: sex,
                size: size,
                image: image
            },
            success: function (response) {
                if(response == 1){
                    clearInputs();
                }
                else{
                    printMessage("Ups, something went wrong, try again!");
                }
                return;
            }
        });
    }
    else{
        printMessage("Ups, you missed something!");
        return;
    }
    printMessage("Well done, the form is done!");
});

//Function that clears the input that the user typed
function clearInputs(){
    //Clears the user inputs
    clearTextBox('#firstname');
    clearTextBox('#lastname');
    clearTextBox('#email');
    clearTextBox('#number');
    $('#options').selectedIndex = 0;
    clearTextBox('#address');
    clearTextBox('#size');
    $('#selected-file').text("");
}

//Function that clears the content of a given element
function clearTextBox(element){
    $(element).val("");
}

//Function that tests if the input given is not empty
function validateInputs(element){
    if($(element).val() == ""){
        return false;
    }
    return true;
}

//Function that prints a given message
function printMessage(message){
    let text = $('#message');
    text.css({"margin:-bottom": "2%"});
    text.text(message);
}

//Function that tests if a given input contains only numbers
function validNumber(number){
    if(/^\d+$/.test(number)){
        return true;
    }
    return false;
}

//Function that test if a given input contains only letters
function validName(name){
    if (/^[a-zA-Z]+$/.test(name)){
        return true;
    }
    return false;
}

//Function that test if a given input is a standard size
function validSize(size){
    if ($.inArray(size, standardSizes) !== -1){
        return true;
    }
    return false;
}