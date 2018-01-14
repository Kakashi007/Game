<?php

echo<<<_END

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	
	<link href="algo_style.css" rel="stylesheet" type="text/css"/>
		 
	<div id="algo">
		<span>
			<button id='post_ques'>Post a question!</button>
			<button id='load_ques'>Answer questions</button>
		</span>
		
			
		
		<br></br>
		<div id="wg_div"></div>
			<textarea id='type_ques' name='type_ques'></textarea>
			<input type='submit' id='submit_ques'  value='Submit' ></input>
			<ul id="ques_list"></ul>
			<ul id="load_prev_ques_list"></ul>
		
	</div>

	<script type="text/javascript">
 		$(document).ready(function() { 
 			var user,to_show=1;

 			$.ajax({
				         url: 'logout.php', 
				         type: "POST",
				         data: ({u_name:1}),
				         async:false,
				         success: function(data){
				            		//alert('hello_u_name');
				            		user='<br><br>  -- '+data;
				         }	
					});

			$('#post_ques').click(function(){
				to_show=1;
				 $('#type_ques').css('display', 'block');
				$('#submit_ques').css('display', 'block');
			});
				var p=0,p_for_load=0;
				$.ajax({
				         url: 'algo_ajax_handler.php', 
				         type: "POST",
				         data: ({index:p}),
				         async:false,
				         success: function(data){
				            		p_for_load=p=parseInt(data)+1;
				         }	
					});

					

			  $('#submit_ques').click(function(){
			  	$('#type_ques').css('display', 'none');
				$('#submit_ques').css('display', 'none');

				var st=$('#type_ques').val();

				var el=$("<button class='but'>Answer</button>").attr('alias',p);
				var el1=$("<textarea ></textarea>");
				
				//var newIduser+
				
				//alert(newId);
				
				var el8=$('<li class="ques_li"></li>' ).css('list-style', 'none');
				var el9=$('<span class="tab" >'+st+user+'</span>');//.attr('alias',newId);
				
				//alert(el9.attr('alias'));
				$( "#ques_list" ).after(el8).append(el9).append(el);
				

				$.ajax({
				            url: 'algo_ajax_handler.php', 
				            type: "POST",
				            data: ({name:st+user ,num:p}),
				            success: function(data){
				            		//alert(data);
				            }	
						});

				p++;
				
				el9.css({'display':'block','border':'solid 2px white','marginLeft':'70px','marginRight':'70px','marginTop':'10px','minWidth':'600px','fontSize':'20px','fontFamily':'Arial','backgroundColor':'black' ,'color':'white'});
				
				el.css({'border':'1px solid white','backgroundColor':'#05214f','color':'#2db3ee','cursor':'pointer','marginLeft':'770px','marginTop':'20px','height':'40px','width':'60px','display':'block'});

				el1.css({'fontSize':'25px','fontStyle':'bold','fontFamily':'Arial','backgroundColor':'black','color':'white','marginRight':'40px','width':'800px','resize': 'none','display':'block'});

				var el2=$("<button >Post my Answer</button>").css({'border':'1px solid white','backgroundColor':'#05214f','color':'#2db3ee','cursor':'pointer','marginLeft':'770px','marginTop':'20px','height':'40px','width':'100px','display':'block'});

				var num;

				el.on("click",function(){
					
					/*if($(this).attr('alias')==-1)
					{
						num=$(this).parent().find("span:last").attr('alias').replace(/\D/g,"");
						$(this).attr('alias',num);
					}
					else
					{*/
						num=$(this).attr('alias');
					//}
					//alert(num);
					el1.css('display','block');
					el2.css('display','block');
					$(this).after(el1);
					el1.after(el2);
					
				})

				var el3=$("<ul></ul>");

				el2.on("click",function(){
					el1.css('display','none');
					el2.css('display','none');
					var st2=el1.val();
					var el4=$('<span >'+st2+user+'</span>').css({'display':'block','border':'solid 2px white','marginLeft':'30px','marginRight':'70px','marginTop':'10px','minWidth':'600px','fontSize':'20px','fontFamily':'Arial','backgroundColor':'white' ,'color':'black'});
					
					$(this).after(el3);
					el3.after('<li ></li>').css('list-style','none').append(el4);
					$.ajax({
				            url: 'algo_ajax_handler.php', 
				            type: "POST",
				            data: ({name1:st2+user , serial:num}),
				            success: function(data){
				            		//alert(data);
				            }	
						});
					

				});
		
			});

				$('#load_ques').click(function(){
					if(to_show==1){
						to_show=0;
						var c=1;
						
						//$("#hello_div").css('border','2px solid red');
						$.ajax({

							type:"GET",
							url:"algo_file.xml",
							dataType:"xml",
							success:function(xml){
								for(;c<p_for_load;c++)
								{
									var v="";
									//alert('ques'+c);
									var ele=$('<li ></li>' ).css('list-style', 'none');
									var v1=$(xml).find('ques'+c).text();

									//the ques

									$(xml).find('ques'+c).find('ans').each(function(){
										v+=$(this).text();
									});

									v1=v1.replace(v,"");
									v="";

									$(xml).find('ques'+c).find('ans').each(function(){
											v+=$(this).text()+'.'+"<br><br>";
										});

									//the prev answers to this question

									var ele2=$('<p>'+v+'</p>').css({'display':'block','border':'solid 2px white','marginLeft':'30px','marginRight':'70px','marginTop':'10px','minWidth':'600px','fontSize':'20px','fontFamily':'Arial','backgroundColor':'white' ,'color':'black'});

									var ele1=$('<span class="tab" >'+v1+'</span>').css({'display':'block','border':'solid 2px white','marginLeft':'70px','marginRight':'70px','marginTop':'10px','minWidth':'600px','fontSize':'20px','fontFamily':'Arial','backgroundColor':'black' ,'color':'white'});;
									
									//the show previous answer button
									
									var ele3=$("<button class='but'>Show Previous Answer</button>").attr('alias',c).css({'border':'1px solid white','backgroundColor':'#05214f','color':'#2db3ee','cursor':'pointer','marginLeft':'40px','marginTop':'15px','height':'40px','width':'130px','display':'inline'});


									
									//the answer button
									
									var el=$("<button class='but'>Answer</button>").attr('alias',c).css({'border':'1px solid white','backgroundColor':'#05214f','color':'#2db3ee','cursor':'pointer','marginLeft':'600px','marginTop':'15px','height':'40px','width':'60px','display':'inline'});

									
								
									$('#load_prev_ques_list').after(ele).append(ele1).append(el).append(ele3);


									//textarea  to show to write ans on click of 'Answer' button

									var el1=$("<textarea ></textarea>").css({'fontSize':'25px','fontStyle':'bold','fontFamily':'Arial','backgroundColor':'black','color':'white','marginRight':'40px','width':'800px','resize': 'none','display':'none'});
									
									//the post my answer button

									var el2=$("<button >Post my Answer</button>").css({'border':'1px solid white','backgroundColor':'#05214f','color':'#2db3ee','cursor':'pointer','marginLeft':'770px','marginTop':'20px','height':'40px','width':'100px','display':'none'});

									


									var num;

									el.on("click",function(){
										
										/*if($(this).attr('alias')==-1)
										{
											num=$(this).parent().find("span:last").attr('alias').replace(/\D/g,"");
											$(this).attr('alias',num);
											//alert('uou');
										}
										else
										{*/
											num=$(this).attr('alias');
											//alert(num);
										//}
										//alert(num);
										el1.css('display','block');
										el2.css('display','block');
										$(this).after(el1);
										el1.after(el2);
										
									})

									

									el2.on("click",function(){
										el1.css('display','none');
										el2.css('display','none');
										var st2=el1.val();
										var el4=$('<span >'+st2+user+'</span>').css({'display':'block','border':'solid 2px white','marginLeft':'30px','marginRight':'70px','marginTop':'10px','minWidth':'600px','fontSize':'20px','fontFamily':'Arial','backgroundColor':'white' ,'color':'black'});
										var el3=$("<ul></ul>");
										$(this).after(el3);
										el3.after('<li ></li>').css('list-style','none').append(el4);
										$.ajax({
									            url: 'algo_ajax_handler.php', 
									            type: "POST",
									            data: ({name1:st2+user , serial:num}),
									            success: function(data){
									            		//alert(data);
									            }	
											});
										

									});

									ele3.on("click",function(){
										$(this).after(ele2);
									});
									
								}
								//alert("hi1");
								
							}

						});
					}
			});
			
			
		});
			  
	</script>
	

_END;


