<?php
 $con = mysqli_connect("localhost", "id9566954_yomnaeldawy97", "N@ncyY0mn@", "id9566954_bookstore");

 $statement = mysqli_prepare($con, "SELECT S.username, S.last_name, S.first_name, sum(C.quantity*B.price) AS total_sales 
                             FROM ((User AS S INNER JOIN Customer_Order AS C ) INNER JOIN Book AS B)
 	                           WHERE B.Book_ISBN = C.Book_ISBN and C.checkout = 1 and C.User_username = S.username and DATEDIFF(C.date,current_date()) <= 90
 	                           group by C.User_username
 	                           order by total_sales DESC LIMIT 5"); 
        echo mysqli_error($con);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $username, $last_name, $first_name, $total_sales);
          $response = array();
          $response["success"] = true;
	      $response["failureReason"] = 0;
	       $i = 0;
     while(mysqli_stmt_fetch($statement)){
        $response["username"][$i] = $username;
        $response["last_name"][$i] = $last_name;
        $response["first_name"][$i] = $first_name;
        $response["total_sales"][$i] = $total_sales;
        $i = $i+1;
        }

        echo json_encode($response);

 ?>