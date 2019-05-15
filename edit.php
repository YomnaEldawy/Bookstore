<?php
 $con = mysqli_connect("localhost", "id9566954_yomnaeldawy97", "N@ncyY0mn@", "id9566954_bookstore");
     $firstname = $_POST["first_name"];
    $lastname = $_POST["last_name"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $email = $_POST["email"];
    $country = $_POST["country"];
    $city = $_POST["city"];
    $streetnumber = $_POST["street_number"];
    $streetname = $_POST["street_name"];
    $phonenumber = $_POST["phone_number"];
    $old_username = $_POST["old_username"];
    $old_email = $_POST["old_email"];
    $is_manager = $_POST["is_manager"];
    $response = array();

  function usernameAvailable() {
        global $con, $username, $old_username;
        $statement = mysqli_prepare($con, "SELECT * FROM User WHERE username = ?"); 
        mysqli_stmt_bind_param($statement, "s", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        
        if ($old_username == $username){
      return true;
    }
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }
    }
    
    function emailAvailable() {
    global $con, $email, $username, $old_email;
        $statement = mysqli_prepare($con, "SELECT * FROM User WHERE email = ?"); 
        mysqli_stmt_bind_param($statement, "s", $email);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        
        if ($old_email == $email){
            return true;
        }
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }
  }
     
    $response["success"] = true;
  $response["failureReason"] = 0;
  if (!usernameAvailable()){
    $response["success"] = false;  
    $response["failureReason"] = "Username already exists";
  }
  if (!emailAvailable()) {
    $response["success"] = false;  
    $response["failureReason"] = "E-mail already exists";
  }
  if (strlen($password) < 6){
    $response["success"] = false;  
    $response["failureReason"] = "Password length must be 6 characters at least";
  }
  if (strlen($username) < 2){
    $response["success"] = false;  
    $response["failureReason"] = "Username must be at least 2 characters long";
  }
if (strlen($firstname) < 2){
        $response["success"] = false;  
        $response["failureReason"] = "Name must be at least 2 characters long";
    }   
    
    if (strlen($lastname) < 2){
        $response["success"] = false;  
        $response["failureReason"] = "Name must be at least 2 characters long";
    }

    if (!filter_var($email, FILTER_VALIDATE_EMAIL)){
    $response["success"] = false;  
    $response["failureReason"] = "Please enter a valid email address";
  }
  
    if ($response["success"]){

         $sql = "UPDATE User SET first_name = '".$firstname."' , last_name ='".$lastname."' , username = '".$username."' , email = '".$email."'
                              , password = '".$password."' , country = '".$country."' ,  city = '".$city."' , street_name = '".$streetname."'
                              , street_number = '".$streetnumber."' , phone_number = '".$phonenumber."' , is_manager = '".$is_manager."' WHERE username = '".$old_username."' ";
      if(mysqli_query($con, $sql)){
                $response["username"] = $username;  
                $response["password"] = $password;  
                $response["email"] = $email;  
                $response["firstname"] = $firstname;  
                $response["lastname"] = $lastname;  
                $response["phonenumber"] = $phonenumber;  
                $response["country"] = $country;  
                $response["city"] = $city;  
                $response["streetname"] = $streetname;  
                $response["streetnumber"] = $streetnumber; 
                $response["ismanager"] = $is_manager; 

      } else {
          mysqli_error($con);
      }
            mysqli_close($con);

    }
    
    echo json_encode($response);
?>
