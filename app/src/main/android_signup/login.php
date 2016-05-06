<?php

$response = array();
if (isset($_POST['email']) && isset($_POST['password']) ) 
  { 
    $email  = $_POST['email'];
    $password = $_POST['password'];
    require_once __DIR__ . '/db_connection.php';
    $db = new DB_CONNECT();
	
    $result = mysql_query("Select * from words where email ='$email' and password='$password'");
	
    if (mysql_num_rows($result))
	{
        $response["success"] = 1;
        $response["message"] = "Login successfully.";
        echo json_encode($response);
    } 
	else 
	{
        $response["success"] = 0;
        $response["message"] = "Please Enter correct mobile number and password.";
        echo json_encode($response);
    }
 
 }
 else 
 {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
} 
?>
