firebase.auth().onAuthStateChanged(function (user) {
    if(!user){
        window.location.replace("http://localhost/UbbAppWeb/index.html");
    }
});
