var dragwindow = {};

dragwindow.offsetX = 0;
dragwindow.offsetY = 0;
dragwindow.elem = null;
dragwindow.fog = null;

dragwindow.dragMe = function (id) {
	function stopProp (e) {
		if (e && e.preventDefault) {
			e.preventDefault();
		}
		if (e && e.stopPropagation) {
			e.stopPropagation();
		} else {
			if (window.event && window.event.cancelBubble) {
				window.event.cancelBubble = true;
			}
		}
	};
	
	dragwindow.elem = document.getElementById(id);
	if (dragwindow.elem!=null) {
		var rect = dragwindow.elem.getBoundingClientRect();
		dragwindow.offsetX = event.x - rect.left;
		dragwindow.offsetY = event.y - rect.top;
		
		dragwindow.fog.style.top = rect.top + 32 + "px";
		dragwindow.fog.style.left = rect.left + "px";
		dragwindow.fog.style.width = rect.width + "px";
		dragwindow.fog.style.height = rect.height -32 + "px";
		
		stopProp(event);
	}	
}

dragwindow.letMe = function () {
	dragwindow.elem = null;
	dragwindow.fog.style.width = "0px";
	dragwindow.fog.style.height = "0px";
	dragwindow.fog.style.top = "0px";
	dragwindow.fog.style.left = "0px";
}

dragwindow.start = function () {
	$(document).ready(
		function() {
			if (dragwindow.fog == null) {			
				dragwindow.fog = document.createElement("div");
				dragwindow.fog.style.position = "fixed";
				dragwindow.fog.style.top = "0px";
				dragwindow.fog.style.left = "0px";
				dragwindow.fog.style.zIndex = "99999";
				document.body.appendChild(dragwindow.fog);
			}
			
			var bd = $("body");
			bd[0].onmousemove = function() {
				if (dragwindow.elem != null) {	
					var t = (event.y - dragwindow.offsetY);
					var l = (event.x - dragwindow.offsetX);
					dragwindow.fog.style.top =  t + 32 +"px";
					dragwindow.fog.style.left = l + "px";
					dragwindow.elem.style.top = t + "px";
					dragwindow.elem.style.left = l + "px";
				}
			}
			bd = null;
		}	
	);
}

if ("$" in window) {
	dragwindow.start();
} else {
	setTimeout(dragwindow.start,5000);
}
