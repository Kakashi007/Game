<!DOCTYPE html>
<?php

?>
<html>
	<head>
		<title> Ask Your Questions</title>
		<link href="homepage_style.css" rel="stylesheet" type="text/css"/>
		<!--<script type="text/javascript" src="no.js"></script>-->
		<script type="text/javascript" src="/js/jquery-3.1.1.min.js"></script>
		<script type="text/javascript">
			$(document).ready (function() {
				
				$("#LogIn").click(function(e){
					$("#LogInForm").slideDown('slow');
					$("#SignUpForm").hide();
					$("#Welcome").css('display','none');
					e.preventDefault();
				});
				
				$("#SignUp").click(function(e){
					$("#SignUpForm").slideDown('slow');
					$("#LogInForm").hide();
					$("#Welcome").css('display','none');
					e.preventDefault();
				});

				var num=1;
				var el=$("<button>Click me to refresh page </button>").css({'border':'1px solid white','backgroundColor':'#05214f','color':'#2db3ee','cursor':'pointer','marginLeft':'770px','marginTop':'20px','height':'40px','width':'60px','display':'block'});

				$('#LogOut').click(function(){
					$('#LogIn').css('display','inline');
					$('#SignUp').css('display','inline');
					$('#LogOut').css('display','none');

					$('#Welcome').append(el);

					$.ajax({
						url:'logout.php',
						type:"POST",
						data:({lg:num}),
						success:function(data){
							
							$("#main").css('display','block');
							$("#file").css('display','none');
							$("#Welcome").text(" Bye "+data +". Please do visit again! ;-)");
							el.css('display','block');
						},
						error:function(data){
							alert("noooo!");
						}
					});

					

					el.on('click',function(){
						header('homepage.php');
					});

				});

				
				
			});

		</script>
	</head>

	<body>
		
		<div id="page">
			<div id="header">
				<span>
				Ask & Answer
				</span>
				<ul>
					<li><button id="LogOut">LogOut</button></li>
					<li ><button id="LogIn">LogIn</butto></li>
					<li><button id="SignUp">SignUp</button></li>
				</ul>
				<!--<button>Click Me!</button>-->
			</div>
			<div id="left">
			</div>
			<div id ="LogInForm">
				<form action="homepage.php" method="post"><pre>
					<span>Username*</span>
					<input type="text" name="username" autocomplete='on' required='required'></input>
					<span>Password*</span>
					<input type="password" name="user_pw" required='required'></input>
					<br>
					<input id="Submit_LogInForm" type="submit" name="logged_in" value="LogIn"></input></pre>
				</form>
			</div>
			<div id ="SignUpForm">
				<form  action="homepage.php" method="post"><pre>
					<span>Name*</span>
					<input type="text" name="name" autocomplete='on' required='required'></input>
					<span>Username*</span>
					<input type="text" name="new_username" autocomplete='on' required='required'></input>
					<span>Email</span>
					<input type="text" name="mail_id" required='required'>
					<span>Password*</span>
					<input type="password" name="pw" required='required'></input>
					<span>Confirm Password*</span>
					<input type="password" name="cnf_pw" required='required'></input>
					<br>
					<input id="Submit_SignUpForm" type="submit" name="submit2" value="SignUp"></input></pre>
				</form>
			</div>
			<div id="main">
			<p id="Welcome"><span>Welcome to Ask & Answer,A hub of all the questions you wanna ask and all the answers that you know!! 
			To begin, please SignUp and become a member(It only takes a minute ;-p ).If already a user please LogIn :-)</span></p>
			</div>
		</div>
		<?php
			require_once 'login.php';
			$salt1="p@ol^";
			$salt2="*ty!";
			
			$connection=new mysqli($db_hostname,$db_username,$db_password,$db_database);
			
			if($connection->connect_error) die($connecttion->error);
			
			//LogIn Verification and validation
			if(isset($_POST['username'])&&isset($_POST['user_pw']))
			{
				$u=sanitizeMySQL($connection,$_POST['username']);
				$p=sanitizeMySQL($connection,$_POST['user_pw']);
				
				$query="SELECT * FROM user_details WHERE username='$u'";
				
				$result=$connection->query($query);
				
				if(!$result) 
					die($connection->error);
				
				elseif($result->num_rows==1)
				{
					$row=$result->fetch_array(MYSQLI_ASSOC);
					$result->close();
					
					$token=hash('ripemd128',"$salt1$p$salt2");
					
					if($token==$row['password'])
					{	
						session_start();
						$_SESSION['_user']=$u;
						$_SESSION['_pw']=$p;
						
						echo<<<_END

						<script type="text/javascript">
		
							$('#LogOut').css('display','inline');
							$('#LogIn').css('display','none');
							$('#SignUp').css('display','none');

							$.ajax({
								url:'logout.php',
								type:"POST",
								data:({logged_in:1}),
								 async:false,
								success:function(data){
									var msg="Hi "+data+"!";
									//alert(data);
									$('#LogOut').css('display','inline');
									$('#LogIn').css('display','none');
									$('#SignUp').css('display','none');
									$("#Welcome").text(msg);
									$("#main").css('display','none');
								},
								error:function(data){
									alert("noooo!");
								}
							});
				
						</script>
_END;

						echo"<div id='file'>";
							include("Algo.php");
						echo"</div>";
					
					}
					else 
					{
						echo<<<_END2

						<script>
							$('#Welcome').text("Wrong username/password combination.Please login again");
						</script>

_END2;
					}
				}
				else
				{
					echo<<<_END2

						<script>
							$('#Welcome').text("You're not a registered member .Please SignUp first to continue");
						</script>

_END2;

				}
			/*show logout instead of login/signup*/
		
			}
			if(isset($_POST['name'])&&isset($_POST['new_username'])&&isset($_POST['pw'])&&isset($_POST['mail_id'])&&isset($_POST["cnf_pw"]))
			{
				$n=sanitizeMySQL($connection,$_POST['name']);
				$u=sanitizeMySQL($connection,$_POST['new_username']);
				$p=sanitizeMySQL($connection,$_POST['pw']);
				$m=sanitizeMySQL($connection,$_POST['mail_id']);
				$c=sanitizeMySQL($connection,$_POST["cnf_pw"]);

				if($p!=$c)
				{
					echo<<<_END2
						<script>
							$('#Welcome').text("Password fields did not match.Please SignUp again");
						</script>
_END2;
				}
				else
				{
					$token = hash('ripemd128', "$salt1$p$salt2");
					
					$query="SELECT * FROM user_details WHERE username='$u'";
					$result=$connection->query($query);
					if(!$result) die($connection->error);
					if($result->num_rows) 
					{
						echo<<<_END2
						<script>
							$('#Welcome').text("Username already exists.Please SignUp  again");
						</script>
_END2;
			
					}
					else
					{
						$query="INSERT into user_details(name,username,password,email_id) VALUES"."('$n','$u','$token','$m')";
						$result=$connection->query($query);
						if(!$result) echo "INSERT failed: $query<br>" .$connection->error . "<br><br>";
						echo<<<_END2
						<script>
							$('#Welcome').text("Signed Up successfully.Please login to continue.");
						</script>
_END2;
						
					}
				}
			}

		?>
		
		
		
	<?php
		function sanitizeString($var)
		{
			$var = stripslashes($var);
			$var = htmlentities($var);
			$var = strip_tags($var);
			return $var;
		}
		function sanitizeMySQL($connection, $var)
		{
			$var = $connection->real_escape_string($var);
			$var = sanitizeString($var);
			return $var;
		}
	?>


	</body>

</html>