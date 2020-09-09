<!DOCTYPE html>
<html lang="en" dir="ltr">
	<head>
		<meta charset="utf-8">
		<title>Jamming</title>
		<link rel="stylesheet" href="css/index.css">
        <link rel="icon" href="https://i.pinimg.com/564x/b1/9d/ba/b19dbaf0bb959d8b00ae0e21a2557167.jpg">
	</head>
	<body>
        <div id="intro">
            <h1>Welcome to our quiz!</h1>
            <h3>Please log in, so that we can test you are our friend!</h3>
        </div>

        <div class="outer">
            <div class="inner">
                <form method="post" class="form">
                    <div class="logInTextBox">
                        <p class="infoText">Username</p>
                        <p id="errorMessage"></p>
                    </div>
                    <input id="username" placeholder="Username" class="textBox" type="text" autocomplete="off">
                    <div class="logInTextBox">
                        <p class="infoText">Password</p>
                    </div>
                    <input id="password" class="textBox" type="password" placeholder="Password" required>
                    <br>
<!--                    <input class="button" type="submit" value="Log In">-->
                    <button type="button" class="button" id="logIn">Log in</button>
                </form>
            </div>
        </div>

        <div class="footer">
            <h5>Careful</h5>
            <h6>Don't trip!</h6>
        </div>

        <script src="javascript/jquery.js"></script>
        <script type="text/javascript">
            $('#logIn').click(function () {
                let username = $('#username').val();
                $('#username').val("");
                let password = $('#password').val();
                $('#password').val("");
                $.ajax({
                    type: 'POST',
                    url: 'login.php',
                    cache: false,
                    data: {username: username, password: password},
                    success: function(response){
                        console.log(response);
                        if(response == 1){
                            window.location.href = "http://localhost:1234/Practica/quiz.php";
                        }
                        else{
                            $('#errorMessage').html("Invalid username or password");
                        }
                    }
                })
            });
        </script>
	</body>
</html>
