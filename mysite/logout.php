<?php
//echo "hi";
			//echo session_name();
			//echo $_SESSION['_user'];
	if (isset($_POST['lg']))
	{
		session_start();

		if(isset($_SESSION['_user']))
		{

			$username = $_SESSION['_user'];
			$_SESSION = array();
			if ( isset($_COOKIE[session_name()]))
				setcookie(session_name(), '', time() - 2592000, '/');
			
			session_destroy();
	
			//echo "You have been logged out. Please " .
			//"<a href='homepage.php'>click here</a> to refresh the screen.";

			
		}
		//header('homepage.php');
		echo $username;
		//echo session_destroy() ? "Successfully Logged Out" : "An error Occurred";
	}
			
	if (isset($_POST['u_name']))
	{
		session_start();
		$username = $_SESSION['_user'];
		echo $username;

	}
	if (isset($_POST['logged_in']))
	{
		session_start();
		$username = $_SESSION['_user'];
		echo $username;

	}

?>