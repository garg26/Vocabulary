<?php

$response = array();

if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password']) 
{  
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
     
	
    require_once __DIR__ . '/db_connection.php';
    $db = new DB_CONNECT();
    $result = mysql_query("Insert into words(name,email,password) values('$name','$email','$password')");

    if ($result)
	{
        $response["success"] = 1;
        $response["message"] = "Registration successfully.";
        echo json_encode($response);
    }
	else
	{
      $response["success"] = 0;
      $response["message"] = "Oops! An error occurred.";
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
