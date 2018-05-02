<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--shortcut start-->
<jsp:include page="shortcut.jsp" />
<!--shortcut end-->
<div id="header">
	<div class="header_inner">
		<div class="logo">

			<div class="index_topad" id="playLogo" style="">
				<a href="/html/activity/1472179566.html" target="_blank"> <img
					src="/images/html/20160829181637762.gif">
				</a>
			</div>
			<a name="sfbest_hp_hp_head_logo" href="http://www.e3mall.cn"
				class="trackref logoleft"> </a>
			<div class="logo-text">
				<img src="/images/html/logo_word.jpg">
			</div>
		</div>
		<div class="index_promo"></div>
		<div class="search">
			<form action="http://localhost:8085/search.html" id="searchForm"
				name="query" method="GET">
				<input type="text" class="text keyword ac_input" name="keyword"
					id="keyword" value="" style="color: rgb(153, 153, 153);"
					onkeydown="javascript:if(event.keyCode==13) search_keys('searchForm');"
					autocomplete="off"> <input type="button" value=""
					class="submit" onclick="search_keys('searchForm')">
			</form>
			<div class="search_hot">
				<a target="_blank"
					href="http://localhost:8085/search.html?keyword=华为">华为</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=石榴">石榴</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=松茸">松茸</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=牛排">牛排</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=大闸蟹">大闸蟹</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=全脂牛奶">全脂牛奶</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=茅台">茅台</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=笔记本">笔记本</a><a
					target="_blank"
					href="http://localhost:8085/search.html?keyword=oppo">oppo</a>
			</div>
		</div>
		<div class="shopingcar" id="topCart">
			<s class="setCart"></s><a href="http://localhost:8090/cart/cart.html"
				class="t" rel="nofollow">我的购物车</a><b id="cartNum">0</b> <span
				class="outline"></span> <span class="blank"></span>
			<div id="cart_lists">
				<!--cartContent-->
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>