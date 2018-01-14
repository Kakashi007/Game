
<?php
$file='algo_file.xml';

if(isset($_POST['index']))
{
	$doc=new DOMDocument();
	$doc->load($file);
	
	$xpath=new DOMXpath($doc);

	echo $xpath->evaluate('count(//content/*)');

}

if(isset($_POST['name'])&&isset($_POST['num']))
{
	$r=$_POST['name'];
	$n=$_POST['num'];
	$s='ques'.$n;
	
	$doc = new DOMDocument();
	$doc->preserveWhiteSpace = false;
	$doc->formatOutput = true;
	
	$doc->load($file);

	$content= $doc->getElementsByTagName('content')->item(0);

	$node=$doc->createElement($s);
	$content->appendChild($node);
	$node->appendChild($doc->createTextNode($r));
	echo $doc->save($file);

		
}

if(isset($_POST['name1'])&&isset($_POST['serial']))
{
	$a=$_POST['name1'];
	$n=$_POST['serial'];

	$doc = new DOMDocument();
	$doc->preserveWhiteSpace = false;
	$doc->formatOutput = true;
	
	$doc->load($file);

	$ques= $doc->getElementsByTagName('ques'.$n)->item(0);
	$node=$doc->createElement('ans');
	$ques->appendChild($node);
	$node->appendChild($doc->createTextNode($a));
	echo $doc->save($file);


	
}

?>

