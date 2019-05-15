<?php
 $con = mysqli_connect("localhost", "id9566954_yomnaeldawy97", "N@ncyY0mn@", "id9566954_bookstore");

 $statement = mysqli_prepare($con, "SELECT B.Book_ISBN, B.title, sum(C.quantity) AS total_sales FROM Book AS B INNER JOIN Customer_Order AS C 
 	                           WHERE B.Book_ISBN = C.Book_ISBN and C.checkout = 1 and DATEDIFF(C.date,current_date()) <= 90
 	                           group by C.Book_ISBN"); 
        echo mysqli_error($con);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $ISBN, $title, $total_sales);
          $response = array();
          $response["success"] = true;
	      $response["failureReason"] = 0;
	       $i = 0;
     while(mysqli_stmt_fetch($statement)){
        $response["Book_ISBN"][$i] = $ISBN;
        $response["title"][$i] = $title;
        $response["total_sales"][$i] = $total_sales;
       
        $i = $i+1;
        }

        echo json_encode($response);

 ?>

 