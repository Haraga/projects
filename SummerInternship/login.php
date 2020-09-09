<?php

    logIn();

    function logIn(){
        //Create database instance and connects to database
        require_once('Database.php');
        $database = new Database("localhost", "root", "", "practica");
        $database->connect();

        //Validates the user input
        $username = test_input($_POST['username']);
        $password = test_input($_POST['password']);

        //Prepares the sql statement and retrieves the returned values
        if($stmt = $database->conn->prepare("SELECT username, password FROM users WHERE username=? AND password=?")){
            $stmt->bind_param('ss', $username, $password);

            $stmt->execute();

            $result = $stmt->get_result();
            $stmt->close();
        }
        $ok = 0;
        //Tests if the values given are correct
        while($myrow = $result->fetch_assoc()){
            if($myrow['username'] == $username && $myrow['password'] == $password){
                $ok = 1;
                echo 1;
            }
        }
        $database->disconnect();

        if($ok == 0){
            echo 0;
        }
    }

    //Function that tests the user input
    function test_input($data){
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }