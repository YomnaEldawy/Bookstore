<?php
    $con = mysqli_connect("localhost", "id6948666_id6948666_yomnaeldawy", "N@ncyY0mn@", "id6948666_bookstore");
        $value = $_POST["value"];

    $statement = mysqli_prepare($con, "SELECT b.Book_ISBN, title, publication_year, price, no_of_copies, threshold, Category_category_id, Publisher_publisher_id, pic_url, author_id, name FROM (Book AS b INNER JOIN Book_has_Author AS a ) INNER JOIN Author  WHERE b.Book_ISBN = a.Book_ISBN and Author_author_id = author_id and b.title = ?");
    mysqli_stmt_bind_param($statement, "s", $value);

echo mysqli_error($con);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $Book_ISBN, $title, $publication_year, $price, $no_of_copies, $threshold, $Category_category_id, $Publisher_publisher_id, $pic_url, $author_id, $author_name);

  $count = mysqli_stmt_num_rows($statement);
    $response = array();
    $response["success"] = true;
    $response["failureReason"] = 0;
    if ($count < 1){
        $response["success"] = false;  
        $response["failureReason"] = "No Books found";
    }else{
    $i = 0;
     while(mysqli_stmt_fetch($statement)){
        $response["Book_ISBN"][$i] = $Book_ISBN;
        $response["title"][$i] = $title;
        $response["publication_year"][$i] = $publication_year;
        $response["price"][$i] = $price;
        $response["no_of_copies"][$i] = $no_of_copies;
        $response["threshold"][$i] = $threshold;
        $response["Category_category_id"][$i] = $Category_category_id;
        $response["Publisher_publisher_id"][$i] = $Publisher_publisher_id; 
        $response["pic_url"][$i] = $pic_url;
        $response["author_id"][$i] = $author_id;
        $response["author_name"][$i] = $author_name;
        $i = $i+1;
    }
    }
    echo json_encode($response);
?>