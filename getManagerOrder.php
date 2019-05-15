<?php
    $con = mysqli_connect("localhost", "id9566954_yomnaeldawy97", "N@ncyY0mn@", "id9566954_bookstore");
        $value = $_POST["value"];

    $statement = mysqli_prepare($con, "SELECT title, quantity, order_id, Book_ISBN FROM Manager_Order AS m INNER JOIN Book AS b  Where confirmed = 0 and m.Book_ISBN = b.Book_ISBN ");
    echo mysqli_error($con);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $title, $quantity, $order_id, $ISBN);

    $count = mysqli_stmt_num_rows($statement);
    $response = array();
    $response["success"] = true;
    $response["failureReason"] = 0;
    if ($count < 1){
        $response["success"] = false;  
        $response["failureReason"] = "No order found";
    }else{
        $i = 0;
     while(mysqli_stmt_fetch($statement)){
        $response["title"][$i] = $title;
        $response["quantity"][$i] = $quantity;
        $response["order_id"][$i] = $order_id;
        $response["Book_ISBN"][$i] = $ISBN;
        $i = $i+1;

    }
    }
    echo json_encode($response);
?>      