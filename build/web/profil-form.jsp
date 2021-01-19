<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Staj İşlemleri Takip/Bİlgi Sistemi</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous"/>
    <link rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" 
          integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" 
          crossorigin="anonymous"/>
    
    <style type="text/css">
        body{
            background-color: white;
            font-family: 'Roboto', sans-serif;
	}

    	small {
            font-size: 12px;
            color: rgba(0, 0, 0, 0.4);
    	}

    	section {
            height: 100vh;
    	}
    
	p.solid {
            border-style: solid;
            text-align: center;
            font-weight: 1000;
            letter-spacing: -1px;
            color: black;
            font-size: 20px;
            font-style: italic;
	}
	a.left{
            font-weight: 1000;
            color: black;
            font-size: 13px;
	}
        
    	/* NAVIGATION */
  		nav {
  			width: 100%;
  			margin: 0 auto;
  			background: #f1f2f6;
  			padding: 10px 0;
  			box-shadow: 0px 5px 0px #dfe4ea;
  		}
  		nav ul {
  			list-style: none;
			text-align: center;
    	}
  		nav ul li {
  			display: inline-block;
  		}
  		nav ul li a {
  			display: block;
  			padding: 10px;
  			text-decoration: none;
  			color: #aaa;
  			font-weight: 800;
  			text-transform: uppercase;
  			margin: 0 10px;
  		}
  		nav ul li a,
  		nav ul li a:after,
  		nav ul li a:before {
  			transition: all .5s;
  		}
  		nav ul li a:hover {
  			color: #555;
  		}

  		/* stroke */
  		nav.stroke ul li a,
  		nav.fill ul li a {
  			position: relative;
  		}
  		nav.stroke ul li a:after,
  		nav.fill ul li a:after {
  			position: absolute;
  			bottom: 0;
  			left: 0;
  			right: 0;
  			margin: auto;
  			width: 0%;
  			content: '.';
  			color: transparent;
  			background: #333;
  			height: 1px;
  		}
  		nav.stroke ul li a:hover:after {
  			width: 100%;
  		}

  		nav.fill ul li a {
  			transition: all 2s;
  		}
  		nav.fill ul li a:after {
  			text-align: left;
  			content: '.';
  			margin: 0;
  			opacity: 0;
  		}
  		nav.fill ul li a:hover {
  			color: #023f1c;
  			z-index: 1;
  		}
  		nav.fill ul li a:hover:after {
  			z-index: -10;
  			animation: fill 1s forwards;
  			-webkit-animation: fill 1s forwards;
  			-moz-animation: fill 1s forwards;
  			opacity: 1;
  		}

		/* Keyframes */
		@-webkit-keyframes fill {
  			0% {
    			width: 0%;
    			height: 1px;
  			}
  			50% {
    			width: 100%;
    			height: 1px;
  			}
  			100% {
    			width: 100%;
    			height: 100%;
    			background: #2ECC71;
  			}
		}
 
		/* Keyframes */
		@-webkit-keyframes circle {
  			0% {
    			width: 1px;
    			top: 0;
    			left: 0;
    			bottom: 0;
    			right: 0;
    			margin: auto;
    			height: 1px;
    			z-index: -1;
    			background: #eee;
    			border-radius: 100%;
  			}
  			100% {
    			background: #aaa;
    			height: 5000%;
    			width: 5000%;
    			z-index: -1;
    			top: 0;
    			bottom: 0;
    			left: 0;
    			right: 0;
    			margin: auto;
    			border-radius: 0;
  			}
		}
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
        
        <section style="background: #2f3542; color: black;">
        <nav class="stroke">
	<ul>
	  <c:if test="${loggedDanisman != null }">
			<li><a href="<%=request.getContextPath()%>/stajbasvuruListele" class="nav-link">Staj Başvuruları</a></li>
			<li><a href="<%=request.getContextPath()%>/stajdefteriListele" class="nav-link">Staj Defterleri</a></li>
			<li><a href="<%=request.getContextPath()%>/profilSayfam" class="nav-link">Profil Sayfam</a></li>
                        <li><a href="<%=request.getContextPath()%>/cikisYap" class="nav-link">Çıkış Yap <i class="fa fa-sign-out-alt"></i></a></li>
            </c:if>
                        
            <c:if test="${loggedOgrenci != null }">
			<li><a href="<%=request.getContextPath()%>/stajbasvuruListele" class="nav-link">Staj Başvurularım</a></li>
			<li><a href="<%=request.getContextPath()%>/stajdefteriListele" class="nav-link">Staj Defterlerim</a></li>
			<li><a href="<%=request.getContextPath()%>/profilSayfam" class="nav-link">Profil Sayfam</a></li>
			<li><a href="<%=request.getContextPath()%>/cikisYap" class="nav-link">Çıkış Yap <i class="fa fa-sign-out-alt"></i></a></li>
            </c:if>	
	</ul>
        </nav>
        <br>
        
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<caption>
					<h2>
           				Profil Bilgilerim 
           				<c:if test="${loggedOgrenci != null}">
							(Öğrenci)
						</c:if>
						<c:if test="${loggedDanisman != null}">
							(Danışman)
						</c:if>
					</h2>
				</caption>

				<div class="row pt-3">
					<div class="col">
						<label><strong>Ad :</strong> </label>
						<c:if test="${loggedOgrenci != null}">
							<label>${loggedOgrenci.ad}</label>
						</c:if>
						<c:if test="${loggedDanisman != null}">
							<label>${loggedDanisman.ad}</label>
						</c:if>
					</div>
					<div class="col">
						<label><strong>Soyad :</strong> </label>
						<c:if test="${loggedOgrenci != null}">
							<label>${loggedOgrenci.soyad}</label>
						</c:if>
						<c:if test="${loggedDanisman != null}">
							<label>${loggedDanisman.soyad}</label>
						</c:if>
					</div>
				</div>
				<div class="row pt-3">
					<div class="col">
						<label><strong>Email :</strong> </label>
						<c:if test="${loggedOgrenci != null}">
							<label>${loggedOgrenci.email}</label>
						</c:if>
						<c:if test="${loggedDanisman != null}">
							<label>${loggedDanisman.email}</label>
						</c:if>
					</div>
					<div class="col">
						<label><strong>Bölüm :</strong> </label>
						<c:if test="${loggedOgrenci != null}">
							<label>${loggedOgrenci.bolum.ad}</label>
						</c:if>
						<c:if test="${loggedDanisman != null}">
							<label>${loggedDanisman.bolum.ad}</label>
						</c:if>
					</div>
				</div>
				<c:if test="${loggedOgrenci != null}">
					<div class="row pt-3">
						<div class="col">
							<label><strong>Fakülte :</strong> </label>
							<label>${loggedOgrenci.bolum.fakulte.ad}</label>
						</div>
						<div class="col">
						</div>
					</div>
				</c:if>
                            <div class="col">
				<div class="row pt-3">
					<div class="col">
					
					<c:if test="${loggedOgrenci != null}">
						<a class="btn btn-success" style="background-color: gray" href="ogrenciDuzenle?id=<c:out value='${loggedOgrenci.id}' />">Bilgilerimi Düzenle</a>
					</c:if>
                                                
					<c:if test="${loggedDanisman != null}">
						<a class="btn btn-success" style="background-color: gray" href="danismanDuzenle?id=<c:out value='${loggedDanisman.id}' />">Bilgilerimi Düzenle</a>
					</c:if>
					</div>
					
						
					</div>
				</div>
				
			</div>
		</div>
	</div>
            </section>
</body>
<script>
	(function() {
})();
</script>
</html>
