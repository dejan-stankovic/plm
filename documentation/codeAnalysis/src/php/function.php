<?php
/******************************************
author	: tandhy
date	: 10.07.13
purpose	: convert bytes to KB, MB or GB
feature	: none
******************************************/
function formatbytes($file, $type)
{
	if(file_exists($file))
	{
		switch($type){
		case "KB":
			$filesize = filesize($file) * .0009765625; // bytes to KB
			break;
		case "MB":
			$filesize = (filesize($file) * .0009765625) * .0009765625; // bytes to MB
			break;
		case "GB":
			$filesize = ((filesize($file) * .0009765625) * .0009765625) * .0009765625; // bytes to GB
			break;
		}
		if($filesize <= 0)
		{
			return $filesize = 'unknown file size';
		} else {
			return round($filesize, 2).' '.$type;
		}
	}
}

/******************************************
author	: tandhy
date	: 10.07.13
purpose	: return file information
feature	: none
******************************************/
function getFileInfo($srcFileName,$srcFileType,$srcFileTmpName,$destSize)
{
	//$tmpfile = $_FILES["txtfile"]["name"]." | ".$_FILES["txtfile"]["type"]." | Size : ".formatbytes($_FILES["txtfile"]["tmp_name"],"KB");
	return $srcFileName." | ".$srcFileType." | Size : ".formatbytes($srcFileTmpName,$destSize)."<br>";
}


/******************************************
author	: tandhy
date	: 10.07.13
purpose	: read line by line uploaded file
feature	: none
******************************************/
function readUploadFile($tmpfile)
{
	$readFile="";
	$tmpWord = "";
	$openedfile = fopen($tmpfile, "r") or exit("Unable to open file!");
	//Output a line of the file until the end is reached
	while(!feof($openedfile))
	{
		$tmpWord = fgets($openedfile);
		$readFile.= $tmpWord. "<br>";
		if( strpos( $tmpWord , "/*" ) !== false )
		{
			echo "/* in col ".strpos($tmpWord,"/*")."<br>";
		}
	}
	fclose($openedfile);
	return $readFile;
}

/******************************************
function name : readUploadFile2
author	: tandhy
date	: 10.07.13
purpose	: read line by line uploaded file
feature	: none
******************************************/
function readUploadFile2($tmpfile)
{
	$readFile = "";
	$tmpChar = "";
	$needle1 = "/*";
	$needle2 = "*/";
	$funcName = "function name";
	$saveToReadFile = 0;
	$endComment = 0;
	$totalLine = 0;
	$totalLineInFile = 0;
	$totalLineOfComment = 0;
	$totalFunction = 0;
	$totalEmptyLine = 0;
	$openedfile = fopen( $tmpfile, "r" ) or exit( "Unable to open file!" );
	//Output a line of the file until the end is reached
	while( !feof( $openedfile ))
	{
		$tmpChar = fgets( $openedfile )."<br>"; // save each line from file in tmpChar
		// check where is $needle1 exist
		if( strpos( $tmpChar,  $needle1 ) !== false ) // check whether needle1 found in $tmp or not
		{
			// check whether tmpChar is a one line comment. if so, do not save the line
			if( strpos( $tmpChar, $needle2 ) !== false ) // check whether needle2 found in $tmp or not
			{
				// this is a one line comment, do not need to save
				$saveToReadFile = 0;
				$endComment = 0;
			}
			else // this is not a one line comment, proceed to set status to saveToReadFile to 1
			{
				$saveToReadFile = 1;
				$endComment = 0;
			}
		}
		else // check whether the saveToReadFile is 1. if 1, the line is still one sets with the previous
		{
			// check whether needle2 is IN the line or NOT
			if( strpos( $tmpChar, $needle2 ) !== false )
			{
				// if needle2 is IN the line, this is mean that this is the end of the comment.
				$saveToReadFile = 1;
				$endComment = 1;
			}
			else // if needle2 is NOT IN the line
			{
				if( $saveToReadFile == 1 ) // if the line is continuous comment, save the line
				{
					// save the line
					$saveToReadFile = 1;
				}
				else // do not save the line
				{
					$saveToReadFile = 0;
				}
			}
		}

		if( $saveToReadFile == 1 ) // check whether line will be saved or not
		{
			$readFile .= $tmpChar; // save the line
			$totalLineOfComment++; // counting total line of comment
			if( $endComment == 1 ) // check whether the line is the end of comment or NOT. 
			{
				// the line IS the end of comment, assign endComment to false and savetoReadFile to false
				$endComment = 0;
				$saveToReadFile = 0;
			}
		}
		
		// here is command for checking total number of function
		// check whether the line contain "function name" or not. If so, increase the number of function
		if( strpos( strtolower( $tmpChar ) , $needle2 ) !== false )
		{
			// if "function name" is in the line
			$totalFunction++;
		}
		
		//check for empty line
		if ( strlen(trim( $tmpChar )) == 4 )
		{
			$totalEmptyLine++; // count empty Line
		}
		
		$totalLineInFile++; // count total number of line in file
		
	}

	fclose( $openedfile );
	$msg = "<div class='infoFile'>";
	$msg.= "Total Function : ".($totalFunction-1)."<br>";
	//$msg.= "Total Line : ".($totalLineInFile)."<br>";
	//$msg.= "Total Empty Line : ".($totalEmptyLine)."<br>";
	//$msg.= "Total Line of Comment : ".($totalLineOfComment)."<br>";
	$msg.= "Total Line of Code : ".($totalLineInFile-$totalEmptyLine-$totalLineOfComment)."<br>";
	$msg.= "</div><br><hr />";
	return $msg.$readFile;
}

/******************************************
author	: tandhy
date	: 10.07.13
purpose	: read uploaded file : char by char
feature	: none
******************************************/
function readCommentInFile( $srcfile )
{
	$tmpchr = "";
	$readFile = fopen( $srcfile, "r" ) or exit("Unable to open file!");
	while (!feof($readFile))
	{
		$chr = fgetc( $readFile );
		if($chr == "\n" || $chr == "\r")
		{
			$tmpchr.="<br>";
		}
		else
		{
			$tmpchr.= $chr;
		}
	}

	fclose($readFile);
	
	return $tmpchr;
}
?>
