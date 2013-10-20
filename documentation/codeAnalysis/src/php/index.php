<?php
include("function.php");

if(isset($_POST['cmdupload']) && $_POST['cmdupload'])
{
	if ($_FILES["txtfile"]["error"] > 0)
	{
		echo "Error: " . $_FILES["txtfile"]["error"] . "<br>";
	}
	else
	{
		//$tmp = "Upload: " . $_FILES["txtfile"]["name"] . "<br>";
		//$tmpfile = $_FILES["txtfile"]["name"]." | ".$_FILES["txtfile"]["type"]." | Size : ".formatbytes($_FILES["txtfile"]["tmp_name"],"KB");
		$tmpfile = getFileInfo($_FILES["txtfile"]["name"],$_FILES["txtfile"]["type"],$_FILES["txtfile"]["tmp_name"],"KB");
		move_uploaded_file($_FILES["txtfile"]["tmp_name"],"upload/" . $_FILES["txtfile"]["name"]);
		//$readedFile = readCommentInFile("upload/".$_FILES["txtfile"]["name"]);
		$readedFile = readUploadFile2("upload/".$_FILES["txtfile"]["name"]);

		
		
		
		/*if (file_exists("upload/" . $_FILES["txtfile"]["name"]))
		{
			echo $_FILES["txtfile"]["name"] . " already exists. ";
		}
		else
		{
			move_uploaded_file($_FILES["txtfile"]["tmp_name"],"upload/" . $_FILES["txtfile"]["name"]);
			//$readedFile=readUploadFile("upload/".$_FILES["txtfile"]["name"]);
			$readedFile = readCommentInFile("upload/".$_FILES["txtfile"]["name"]);
		//}*/
	}
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Get Comment</title>
	<!-- Framework CSS -->
    <link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="blueprint/print.css" type="text/css" media="print">
    <link rel="stylesheet" href="blueprint/src/grid.css" type="text/css" media="print">
    <!--[if lt IE 8]><link rel="stylesheet" href="../../blueprint/ie.css" type="text/css" media="screen, projection"><![endif]-->

    <!-- Import fancy-type plugin for the sample page. -->
    <link rel="stylesheet" href="blueprint/plugins/fancy-type/screen.css" type="text/css" media="screen, projection">
</head>
<style>
.readedFile
{
	font-family:"Courier New", Courier, monospace;
	font-size:11px;
}
.infoFile
{
	font-family:Arial, Helvetica, sans-serif;
	font-size:12px;
}
</style>
<body>
<div class="container">
	<hr class="space" />
    <h1 align="center">Retrieve Comment from File</h1>
    <hr>
	<div>
    <p><h2>Please upload plain text file only!</h2></p>
    <hr>
	<form action="index.php" method="post" name= "frmupload" id="frmupload" enctype="multipart/form-data">
    <label for="txt">Please select any file</label>&nbsp;<input type="file" name="txtfile" id="txtfile" style="border:2px #333333;" />
    <input type="submit" name="cmdupload" id="cmdupload" value="Upload"/>
    </form>
    </div>
    <hr class="space">
    <hr />
    <?php 
	if(!empty($tmpfile)) 
		echo "<h3>$tmpfile</h5><hr>";
		
	if(!empty($readedFile))
		echo "<div class='readedFile'>".$readedFile."</div>"; 
	?>
</div>
</body>
</html>
