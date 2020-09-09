<?php
class Database
{
    public $servername;
    public $username;
    public $password;
    public $dbname;
    public $conn;

    function __construct($server, $username, $password, $database)
    {
        $this->servername = $server;
        $this->username = $username;
        $this->password = $password;
        $this->dbname = $database;
    }

    public function connect(){
        $this->conn = mysqli_connect($this->servername, $this->username, $this->password,$this->dbname);
        if (!$this->conn) {
            die("Connection failed: " . mysqli_connect_error());
        }
    }

    public function disconnect()
    {
      mysqli_close($this->conn);
    }
}
