<?php
    $con = mysqli_connect("localhost", "id9566954_yomnaeldawy97", "N@ncyY0mn@", "id9566954_bookstore");
        $ISBN = $_POST["Book_ISBN"];
        $id = $_POST["order_id"];

      $sql = "UPDATE Customer_Order SET quantity = quantity + 1  WHERE Book_ISBN = '".$ISBN."' and User_username = '".$id."' and checkout = 0 ";
      if(mysqli_query($con, $sql)){
              echo "Records were updated successfully.";

      } else {
        echo "ERROR: Could not able to execute $sql. " . mysqli_error($con);
      }
      mysqli_close($con);

    
?>      