function initWebsocket() {
	var Chat = {};

	Chat.socket = null;

	Chat.connect = (function(host) {
		if ('WebSocket' in window) {
			Chat.socket = new WebSocket(host);
		} else if ('MozWebSocket' in window) {
			Chat.socket = new MozWebSocket(host);
		} else {
			Console.log('Error: WebSocket is not supported by this browser.');
			return;
		}

		Chat.socket.onopen = function() {
			Console.log('Info: WebSocket connection opened.');
			document.getElementById('chat').onkeydown = function(event) {
				if (event.keyCode == 13) {
					Chat.sendMessage();
				}
			};
		};

		Chat.socket.onclose = function() {
			document.getElementById('chat').onkeydown = null;
			Console.log('Info: WebSocket closed.');
		};

		Chat.socket.onmessage = function(message) {
			// Console.log("onmessge");
			// Console.log(message.data);
			
             var msg = eval('('+message.data+')');
             var title=msg['title'];
             var content = msg['content'];
             var author=msg['author'];

            alert("管理员"+author+"发布了新公告"+"\n\n"+"标题："+title+"\n"+"内容："+content);
			
			
		};
	});

	Chat.initialize = function() {
		if (window.location.protocol == 'http:') {
			Chat.connect('ws://' + window.location.host + '/MyBatis/message');
		} else {
			Chat.connect('wss://' + window.location.host
					+ '/examples/websocket/chat');
		}
	};

	Chat.sendMessage = (function() {
		var message = document.getElementById('chat').value;
		if (message != '') {
			Chat.socket.send(message);
			document.getElementById('chat').value = '';
		}
	});

	var Console = {};

	Console.log = (function(message) {
		var console = document.getElementById('console');
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.innerHTML = message;
		console.appendChild(p);
		while (console.childNodes.length > 25) {
			console.removeChild(console.firstChild);
		}
		console.scrollTop = console.scrollHeight;
	});

	Chat.initialize();

	document.addEventListener("DOMContentLoaded", function() {
		// Remove elements with "noscript" class - <noscript> is not allowed in
		// XHTML
		var noscripts = document.getElementsByClassName("noscript");
		for (var i = 0; i < noscripts.length; i++) {
			noscripts[i].parentNode.removeChild(noscripts[i]);
		}
	}, false);

}