function goBack() {
    window.history.back()
}
  	
  	function abrirBot() {
		document.querySelector(".bot-area").innerHTML = ` <button onclick="fecharBot()" id="bot-nome" style="color:#ffffff; margin-bottom: 5px">Suporte do TutoriTech</button>
    	<iframe id="bot" src="https://getacquainted.co/widget/c682c457-3206-4784-b93f-f26a0c0499c9"
    	style="border:0px #ffffff none;" name="myiFrame" scrolling="yes" frameborder="0"
    	marginheight="0px" marginwidth="0px" height="350px" width="300px" allowfullscreen></iframe>`;
	}
	
	function fecharBot() {
		document.querySelector(".bot-area").innerHTML = ` <button onclick="abrirBot()" id="bot-nome" style="color:#ffffff; margin-bottom: 22px">Suporte do TutoriTech</button>`;
	}

