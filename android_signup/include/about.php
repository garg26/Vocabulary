<?php

    $response = array();

    require_once __DIR__ . '/db_connection.php';

    $db = new DB_CONNECT();

    $result = mysql_query("Select * from about");

	

    if (mysql_num_rows($result))

	{

	$row=mysql_fetch_array($result);

	$response["success"]=1;

	$response["about"]=$row['about'];

	echo json_encode($response);

	}

	else

	{

	$response["success"]=0;

	$response["about"]="Data not found";

	echo json_encode($response);

	}

?>
