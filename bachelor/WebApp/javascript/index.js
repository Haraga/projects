firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
    window.location.replace("http://localhost/UbbAppWeb/home.html");
  }
});

function logIn() {

  let userEmail = document.getElementById("email").value;
  let userPassword = document.getElementById("password").value;

  if (userEmail.length < 1) {
    $('#errorMessage').html("Please fill in the email!");
    return;
  }
  if (userPassword.length < 1) {
    $('#errorMessage').html("Please fill in the password!");
    return;
  }

  sessionStorage.setItem("email", userEmail);
  firebase.auth().signInWithEmailAndPassword(userEmail, userPassword).catch(function(error) {
    // Handle Errors here.
    var errorCode = error.code;
    var errorMessage = error.message;

    $('#errorMessage').html("Email or Password is incorect!");
  });
}
