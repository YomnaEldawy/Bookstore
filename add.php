<?php
 $con = mysqli_connect("localhost", "id9566954_yomnaeldawy97", "N@ncyY0mn@", "id9566954_bookstore");
    $ISBN = $_POST["ISBN"];
    $title = $_POST["title"];
    $publication_year = $_POST["publication_year"];
    $no_of_copies = $_POST["no_of_copies"];
    $threshold = $_POST["threshold"];
    $pic_url = $_POST["pic_url"];
    $publisher = $_POST["publisher"];
    $category = $_POST["category"];
    $price = $_POST["price"];
    $publisher_id = " ";
    $category_id = " ";
    $count = (int) $_POST["count"];
    $authors = array();
    $i = 0;
    while($i < $count){
        $authors[$i] = (String) $_POST["a".$i.""];
         $i = $i+1;
    }
    $response = array();
    $response["success"] = true;
    $response["failureReason"] = 0;
    

    function addBook($cid, $pid) {
        global $con, $ISBN, $title, $publication_year, $no_of_copies, $threshold, $pic_url, $publisher, $category, $price, $publisher_id, $category_id,$publisher,$category; 

    $statement = mysqli_prepare($con, "INSERT INTO Book (Book_ISBN, title, publication_year, price, no_of_copies, threshold, Category_category_id, Publisher_publisher_id , pic_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssidiiiis", $ISBN, $title, $publication_year, $price, $no_of_copies, $threshold, $cid, $pid, $pic_url);
            echo mysqli_error($con);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement); 
    }
 
     function addAuthor($name)
    {
    global $con;
    $statement = mysqli_prepare($con, "INSERT INTO Author (name) VALUES (?)");
    mysqli_stmt_bind_param($statement, "s", $name);
            echo mysqli_error($con);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement); 

    }
    function addHas($aid)
    {
    global $con, $ISBN;
    $statement = mysqli_prepare($con, "INSERT INTO Book_has_Author (Book_ISBN, Author_author_id) VALUES (?, ?)");
    mysqli_stmt_bind_param($statement, "si", $ISBN, $aid);
            echo mysqli_error($con);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement); 

    }

     function ISBNAvailable(){
       global $con, $ISBN;
       $statement = mysqli_prepare($con, "SELECT * FROM Book WHERE Book_ISBN = ?"); 
        mysqli_stmt_bind_param($statement, "s", $ISBN);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }

    }
    
    function publisherAvailable() {
        global $con, $publisher, $publisher_id;
        $statement = mysqli_prepare($con, "SELECT publisher_id FROM Publisher WHERE publisher_name = ?"); 
        mysqli_stmt_bind_param($statement, "s", $publisher);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $pid);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count > 0){
            return true; 
        }else {
            return false; 
        }
    }
    
    function categoryAvailable() {
        global $con, $category, $category_id;
        $statement = mysqli_prepare($con, "SELECT category_id FROM Category WHERE category_name = ?"); 
        mysqli_stmt_bind_param($statement, "s", $category);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count > 0){
            return true; 
        }else {
            return false; 
        }
    }

   
       if (!ISBNAvailable()) {
        $response["success"] = false;  
        $response["failureReason"] = "Book already exists";
        }

    if (!publisherAvailable()){
        $response["success"] = false;  
        $response["failureReason"] = "publisher doesn't exists";
        }

    if (!categoryAvailable()) {
        $response["success"] = false;  
        $response["failureReason"] = "category doesn't exist";
        }

   if ($response["success"]){
           $statement1 = mysqli_prepare($con, "SELECT publisher_id FROM Publisher WHERE publisher_name = ?"); 
        mysqli_stmt_bind_param($statement1, "s", $publisher);
        mysqli_stmt_execute($statement1);
        mysqli_stmt_store_result($statement1);
        mysqli_stmt_bind_result($statement1, $pid);
         while(mysqli_stmt_fetch($statement1)){
        $response["pid"] = $pid; 
         }
         $statement2 = mysqli_prepare($con, "SELECT category_id FROM Category WHERE category_name = ?"); 
        mysqli_stmt_bind_param($statement2, "s", $category);
        mysqli_stmt_execute($statement2);
        mysqli_stmt_store_result($statement2);
        mysqli_stmt_bind_result($statement2, $cid);
        while(mysqli_stmt_fetch($statement2)){
        $response["cid"] = $cid; 
         }
           addBook($cid, $pid);


           $x = 0;
           while ($x < $count) {
             $response["aname"] = $authors[$x];
               addAuthor($authors[$x]);
            $statement3 = mysqli_prepare($con, "SELECT author_id FROM Author WHERE name = ?"); 
        mysqli_stmt_bind_param($statement3, "s", $authors[$x]);
        mysqli_stmt_execute($statement3);
        mysqli_stmt_store_result($statement3);
        mysqli_stmt_bind_result($statement3, $aid);
        while(mysqli_stmt_fetch($statement3)){
        $response["aid"] = $aid; 
                     $response["aname"] = $authors[$x];


         }
               addHas($aid);
               $x = $x +1;
           }

    }
        echo json_encode($response);

?>