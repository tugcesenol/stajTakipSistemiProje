<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Staj İşlemleri Takip/Bilgi Sistemi</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" 
          integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" 
          crossorigin="anonymous" />
    <style>
	body{
		background-color: #f1f2f6;
		font-family: 'Roboto', sans-serif;
        }
	p{
		text-align: center;
  		font-weight: 1000;
  		letter-spacing: -1px;
  		color: black;
  		font-size: 20px;
  		font-style: italic;
	}
	p.solid {border-style: solid;}
    </style>    
</head>
<body>
    <header>    
        
        <div>
            <img src="https://www.cumhuriyet.edu.tr/kurumsal_logo.png" width=140px; height=140px;"/>
        </div> 
        
        <p class="solid">SİVAS CUMHURİYET ÜNİVERSİTESİ STAJ İŞLEMLERİ TAKİP/BİLGİ SİSTEMİ</p>
        <div>
            <a href="<%=request.getContextPath()%>/kullaniciListele" class="navbar-brand"></a>
	</div>
	<ul class="navbar-nav"> </ul>	
    </header>
                 
    <br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<div class="row" id="userTypeSelection" style="display:block">
					<div class="col" style="background-color: gray; border: 1px solid lightgray; border-radius: 10px; padding: 10px; text-align: center;cursor:pointer" onclick="setUserType(1)">
						<div class="row">
							<div class="col">
                                                            <i class="fa fa-chalkboard-teacher fa-10x"></i>
							</div>
						</div>
						<div class="row pt-2">
							<div class="col">
								<p style="font-wight:bold">DANIŞMAN</p>
							</div>
						</div>
					</div>
                                    
					<div class="col" style="background-color: gray; border: 1px solid lightgray; border-radius: 10px; padding: 10px; text-align: center;cursor:pointer" onclick="setUserType(2)">
						<div class="row">
							<div class="col">
								<i class="fa fa-user fa-10x"></i>
							</div>
						</div>
						<div class="row pt-2">
							<div class="col">
								<p style="font-wight:bold">ÖĞRENCİ</p>
							</div>
						</div>
					</div>
                                    
				</div>
				<form action="loginUser" method="post" id="loginForm" style="display:none">
					<caption>
						<h2><span id="accountTypeName"></span> Giriş</h2>
					</caption>
					<input type="number" id="userType" name="userType" hidden/>
					<fieldset class="form-group">
						<label>E-Posta</label> <input type="text" class="form-control"
							name="email" required="required">
					</fieldset>
					
					<fieldset class="form-group">
						<label>Şifre</label> <input type="password" class="form-control"
							name="sifre" required="required">
					</fieldset>
	
					<button type="submit" class="btn btn-success"  style="background-color: gray;" >GİRİŞ</button>
                                        
					<c:if test="${error != null}">
						<label class="text-danger">${error}</label>
					</c:if>
				</form>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	function setUserType(type){
		document.getElementById("userType").value=type;
		document.getElementById("userTypeSelection").style.display="none";
		document.getElementById("loginForm").style.display="block";
		if(type==1)
			document.getElementById("accountTypeName").innerHTML="Danışman";
		else
			document.getElementById("accountTypeName").innerHTML="Öğrenci";
		
	}
	
	function changeLoginType(){
		document.getElementById("userTypeSelection").style.display="block";
		document.getElementById("loginForm").style.display="none";
		document.getElementsByName("email")[0].value="";
		document.getElementsByName("sifre")[0].value="";
	}
</script>
</html>
