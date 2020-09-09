<?php

    saveData();

    function saveData(){
        //Create database instance and connects to database
        require_once('Database.php');
        $database = new Database("localhost", "root", "", "practica");
        $database->connect();

        //Take input and test it for special characters
        $firstname = test_input($_POST['firstname']);
        $lastname = test_input($_POST['lastname']);
        $email = test_input($_POST['email']);
        $number = test_input($_POST['number']);

        $age = test_input($_POST['age']);
        $address = test_input($_POST['address']);
        $sex = test_input($_POST['sex']);
        $sizes = test_input($_POST['size']);

        $image = test_input($_POST['image']);

        //Prepare and execute statement, returning 1 for success and 0 for failure
        if($stmt = $database->conn->prepare("INSERT INTO form (firstname, lastname, email, phoneNumber, age, address, gender, sizes, image)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")){
            $stmt->bind_param('sssiissss', $firstname, $lastname, $email,
                $number, $age, $address, $sex, $sizes, $image);

            $stmt->execute();

            $stmt->close();
            echo 1;
        }
        else{
            echo 0;
        }

        $database->disconnect();
    }

    //Input testing function
    function test_input($data){
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }