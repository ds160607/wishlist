var clickedImage = null;

document.addEventListener("mousedown", function(event) {
	// right click
	if ((event.button == 2) && (event.target.tagName == "IMG")) {
		clickedImage = event.target;
	} else {
		clickedImage = null;
	}
}, true);

chrome.extension.onRequest
		.addListener(function(request, sender, sendResponse) {
			if (request == "grabImage") {
				if (clickedImage) {

					if (clickedImage.src.substr(0, 5) == "data:") {
						sendResponse(clickedImage.src);
					} else {
						var xhr = new XMLHttpRequest(), blob, fileReader = new FileReader();
						xhr.open("GET", clickedImage.src, true);

						xhr.responseType = "arraybuffer";

						xhr.addEventListener("load", function() {
							if (xhr.status === 200) {
								var dt64 = _arrayBufferToBase64(xhr.response);
								var m_type = xhr
										.getResponseHeader('content-type');
								var data_url = "data:" + m_type + ";base64,"
										+ dt64;
								sendResponse(data_url);
							}
						}, false);
						// Send XHR
						xhr.send();
					}
					var t = new animation(clickedImage);
					t.init(showPopup);
					t.doStep();
					clickedImage = null;
				}
				// sendResponse(true);
			}
		});

function animation(element) {

	this.doStep = function() {

		if (this.start_time == 0) {
			this.start_time = (new Date()).getTime();
		}

		var now = (new Date()).getTime();
		var delta = now - this.start_time;
		var proc = delta / this.duration;

		if (proc < 1) {
			var _x = this.x + this.delta_x * proc * proc; // proc*proc -
															// function of time
			var _y = this.y + this.delta_y * proc * proc;
			var _width = this.width + this.delta_width * proc * proc;
			var _height = this.height + this.delta_height * proc * proc;

			this.rect.style.top = _y + "px";
			this.rect.style.left = _x + "px";
			this.rect.style.width = _width + "px";
			this.rect.style.height = _height + "px";

			(function(s) {
				var slf = s;
				setTimeout(function() {
					slf.doStep()
				}, slf.speed);
			})(this)
		} else {
			this.rect.remove()
			this.callback();
		}
	}

	this.init = function(clbck) {
		this.callback = clbck;
		this.el_rect = this.el.getClientRects();
		if (this.el_rect.length > 0) {
			this.el_rect = this.el_rect[0]
		} else {
			this.el_rect = null
		}
		var body = document.getElementsByTagName("body");
		if (body.length > 0) {
			body = body[0];
		} else {
			body = null;
		}

		if ((body) && (this.el_rect)) {
			// this.rect = document.createElement("DIV");
			this.rect = document.createElement("IMG");
			this.rect.src = this.el.src;
			// this.rect.style.border = "1px solid yellow";
			// this.rect.style.backgroundColor = "yellow"
			this.rect.style.opacity = "0.8"
			this.rect.style.borderRadius = "10px";
			this.rect.style.position = "fixed";
			this.rect.style.zIndex = "99999";
			this.rect.style.width = this.el_rect.width + "px";
			this.rect.style.height = this.el_rect.height + "px";
			this.rect.style.top = this.el_rect.top + "px";
			this.rect.style.left = this.el_rect.left + "px";

			this.x = this.el_rect.left;
			this.y = this.el_rect.top;
			this.delta_x = this.aim_x - this.x;
			this.delta_y = this.aim_y - this.y;

			this.width = this.el_rect.width;
			this.height = this.el_rect.height;
			this.delta_width = this.aim_width - this.width;
			this.delta_height = this.aim_height - this.height;

			body.appendChild(this.rect);
			this.interval = this.duration * 1000 / this.speed;
		}
	}
	this.callback = function() {
	};
	this.el = element;
	this.el_rect = null;
	this.aim_x = window.innerWidth - 50;
	this.aim_y = 0;
	this.aim_width = 0;
	this.aim_height = 0;

	this.x = 0;
	this.y = 0;
	this.delta_x = 0;
	this.delta_y = 0;

	this.width = 0;
	this.height = 0;
	this.delta_width = 0;
	this.delta_height = 0;

	this.start_time = 0;
	this.duration = 750;
	this.speed = 1000 / 25;

	this.interval = 0;
	this.rect = null;

}

function _arrayBufferToBase64(buffer) {
	var binary = ''
	var bytes = new Uint8Array(buffer)
	var len = bytes.byteLength;
	for (var i = 0; i < len; i++) {
		binary += String.fromCharCode(bytes[i])
	}
	return window.btoa(binary);
}

function showPopup() {
	if (document.getElementById("wishlist_frame_cntnr") == null) {
		
		var src = chrome.extension.getURL("wishlist.html") + "?edit=yes";
		
		var div_cntnr = document.createElement("DIV");
		div_cntnr.id = "wishlist_frame_cntnr";
		div_cntnr.style.position = "fixed";
		div_cntnr.style.top = "10px";
		div_cntnr.style.left = document.body.clientWidth - 570 + "px";
		div_cntnr.style.zIndex = "99998";
		div_cntnr.style.backgroundColor = "white";
		div_cntnr.style.boxShadow = "0 0 8px black";
		
		var div_header = document.createElement("DIV");		
		div_header.onmousedown = function() {
			dragwindow.dragMe("wishlist_frame_cntnr")
		}
		div_header.onmouseup = function() {
			dragwindow.letMe()
		};
		div_header.style.position = "relative";
		div_header.style.height = "32px";
		div_header.style.backgroundColor = "#444444";

		var span_close = document.createElement("span");
		span_close.style.cursor = "pointer";
		span_close.style.width = "16px";
		span_close.style.height = "16px";
		span_close.style.background ="0 0 no-repeat url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAKBmlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4KPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS4wLWMwNjAgNjEuMTM0Nzc3LCAyMDEwLzAyLzEyLTE3OjMyOjAwICAgICAgICAiPgogPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KICA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIgogICAgeG1sbnM6eG1wUmlnaHRzPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvcmlnaHRzLyIKICAgIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIKICAgIHhtbG5zOklwdGM0eG1wQ29yZT0iaHR0cDovL2lwdGMub3JnL3N0ZC9JcHRjNHhtcENvcmUvMS4wL3htbG5zLyIKICAgIHhtbG5zOnBsdXNfMV89Imh0dHA6Ly9ucy51c2VwbHVzLm9yZy9sZGYveG1wLzEuMC8iCiAgICB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iCiAgICB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIKICAgIHhtbG5zOnN0RXZ0PSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VFdmVudCMiCiAgIHhtcFJpZ2h0czpNYXJrZWQ9IlRydWUiCiAgIHhtcDpNZXRhZGF0YURhdGU9IjIwMTEtMDEtMjVUMTM6NTU6MzcrMDE6MDAiCiAgIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NkNCRjEwNjg4MjI4RTAxMTk4OUNDMEExQUQwMkI1QzIiCiAgIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NkNCRjEwNjg4MjI4RTAxMTk4OUNDMEExQUQwMkI1QzIiCiAgIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo2Q0JGMTA2ODgyMjhFMDExOTg5Q0MwQTFBRDAyQjVDMiI+CiAgIDx4bXBSaWdodHM6VXNhZ2VUZXJtcz4KICAgIDxyZGY6QWx0PgogICAgIDxyZGY6bGkgeG1sOmxhbmc9IngtZGVmYXVsdCI+Q3JlYXRpdmUgQ29tbW9ucyBBdHRyaWJ1dGlvbi1Ob25Db21tZXJjaWFsIGxpY2Vuc2U8L3JkZjpsaT4KICAgIDwvcmRmOkFsdD4KICAgPC94bXBSaWdodHM6VXNhZ2VUZXJtcz4KICAgPGRjOmNyZWF0b3I+CiAgICA8cmRmOlNlcT4KICAgICA8cmRmOmxpPkdlbnRsZWZhY2UgY3VzdG9tIHRvb2xiYXIgaWNvbnMgZGVzaWduPC9yZGY6bGk+CiAgICA8L3JkZjpTZXE+CiAgIDwvZGM6Y3JlYXRvcj4KICAgPGRjOmRlc2NyaXB0aW9uPgogICAgPHJkZjpBbHQ+CiAgICAgPHJkZjpsaSB4bWw6bGFuZz0ieC1kZWZhdWx0Ij5XaXJlZnJhbWUgbW9ubyB0b29sYmFyIGljb25zPC9yZGY6bGk+CiAgICA8L3JkZjpBbHQ+CiAgIDwvZGM6ZGVzY3JpcHRpb24+CiAgIDxkYzpzdWJqZWN0PgogICAgPHJkZjpCYWc+CiAgICAgPHJkZjpsaT5jdXN0b20gaWNvbiBkZXNpZ248L3JkZjpsaT4KICAgICA8cmRmOmxpPnRvb2xiYXIgaWNvbnM8L3JkZjpsaT4KICAgICA8cmRmOmxpPmN1c3RvbSBpY29uczwvcmRmOmxpPgogICAgIDxyZGY6bGk+aW50ZXJmYWNlIGRlc2lnbjwvcmRmOmxpPgogICAgIDxyZGY6bGk+dWkgZGVzaWduPC9yZGY6bGk+CiAgICAgPHJkZjpsaT5ndWkgZGVzaWduPC9yZGY6bGk+CiAgICAgPHJkZjpsaT50YXNrYmFyIGljb25zPC9yZGY6bGk+CiAgICA8L3JkZjpCYWc+CiAgIDwvZGM6c3ViamVjdD4KICAgPGRjOnJpZ2h0cz4KICAgIDxyZGY6QWx0PgogICAgIDxyZGY6bGkgeG1sOmxhbmc9IngtZGVmYXVsdCI+Q3JlYXRpdmUgQ29tbW9ucyBBdHRyaWJ1dGlvbi1Ob25Db21tZXJjaWFsIGxpY2Vuc2U8L3JkZjpsaT4KICAgIDwvcmRmOkFsdD4KICAgPC9kYzpyaWdodHM+CiAgIDxJcHRjNHhtcENvcmU6Q3JlYXRvckNvbnRhY3RJbmZvCiAgICBJcHRjNHhtcENvcmU6Q2lVcmxXb3JrPSJodHRwOi8vd3d3LmdlbnRsZWZhY2UuY29tIi8+CiAgIDxwbHVzXzFfOkltYWdlQ3JlYXRvcj4KICAgIDxyZGY6U2VxPgogICAgIDxyZGY6bGkKICAgICAgcGx1c18xXzpJbWFnZUNyZWF0b3JOYW1lPSJnZW50bGVmYWNlLmNvbSIvPgogICAgPC9yZGY6U2VxPgogICA8L3BsdXNfMV86SW1hZ2VDcmVhdG9yPgogICA8cGx1c18xXzpDb3B5cmlnaHRPd25lcj4KICAgIDxyZGY6U2VxPgogICAgIDxyZGY6bGkKICAgICAgcGx1c18xXzpDb3B5cmlnaHRPd25lck5hbWU9ImdlbnRsZWZhY2UuY29tIi8+CiAgICA8L3JkZjpTZXE+CiAgIDwvcGx1c18xXzpDb3B5cmlnaHRPd25lcj4KICAgPHhtcE1NOkhpc3Rvcnk+CiAgICA8cmRmOlNlcT4KICAgICA8cmRmOmxpCiAgICAgIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiCiAgICAgIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6NkNCRjEwNjg4MjI4RTAxMTk4OUNDMEExQUQwMkI1QzIiCiAgICAgIHN0RXZ0OndoZW49IjIwMTEtMDEtMjVUMTM6NTU6MzcrMDE6MDAiCiAgICAgIHN0RXZ0OmNoYW5nZWQ9Ii9tZXRhZGF0YSIvPgogICAgPC9yZGY6U2VxPgogICA8L3htcE1NOkhpc3Rvcnk+CiAgPC9yZGY6RGVzY3JpcHRpb24+CiA8L3JkZjpSREY+CjwveDp4bXBtZXRhPgo8P3hwYWNrZXQgZW5kPSJyIj8+ZwwvIwAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAA8dEVYdEFMVFRhZwBUaGlzIGlzIHRoZSBpY29uIGZyb20gR2VudGxlZmFjZS5jb20gZnJlZSBpY29ucyBzZXQuINhr6MQAAABEdEVYdENvcHlyaWdodABDcmVhdGl2ZSBDb21tb25zIEF0dHJpYnV0aW9uIE5vbi1Db21tZXJjaWFsIE5vIERlcml2YXRpdmVze92woAAAAEVpVFh0RGVzY3JpcHRpb24AAAAAAFRoaXMgaXMgdGhlIGljb24gZnJvbSBHZW50bGVmYWNlLmNvbSBmcmVlIGljb25zIHNldC4gvBH4GgAAAEhpVFh0Q29weXJpZ2h0AAAAAABDcmVhdGl2ZSBDb21tb25zIEF0dHJpYnV0aW9uIE5vbi1Db21tZXJjaWFsIE5vIERlcml2YXRpdmVzWILLBQAAAUNJREFUeNqsU01qhUAMdrpyIagLQQSFIrj2CO/dpB6hJ2lv4Byh7wQe4blWhIIgLgQHQVD8a2Lro9rx/UAHYmYmX75kkigI/7WGYXDHcSxBzrBXbmD8laHrOhek7Pt+QoH9GUS5gZlJCH7ati1BbaOG0zQdRVFkTdO4hJCAg3l/+kntE0TYCKYb1HV9QI3P2mLg/jsDACkQDSO4D5SNSpLkkeVUVdUjJFSWZe9Sg2Uxxu4hoaqqesuBbK1FURxABTvODAI8a5rGuAR5nu9V+093dF1nK4Isy+5xXpEYhsFmgjRNrzmznfuZZJ4DLBz0VMG+boTim0GHHJsLNn8ZpA/OIFHLsjzTNBmAj3AOOYN0utQgSRIfGF/mwhBCbdv2fucL9lWLAeMBhq66EEXRG2rHcV55lYvjGEnwJzoBhuLdlwADABC8MMboJUn6AAAAAElFTkSuQmCC') transparent";
		span_close.style.position = "absolute";
		span_close.style.right = "11px";
		span_close.style.top = "8px";		
		span_close.onclick = function() {
			var e = document.getElementById("wishlist_frame_cntnr"); 
			if (e) {e.remove()}
		}
		
		div_header.appendChild(span_close);
		
		div_cntnr.appendChild(div_header);
		var frame = document.createElement("IFRAME");
		frame.src = src;
		div_cntnr.appendChild(frame);
		document.body.appendChild(div_cntnr);

		frame.id = "wishlist_frame";
		frame.style.margin="0";
		frame.style.border = "none";
		frame.style.width = "550px";
		frame.style.height = "550px";
	}
	/*
	 * $.ajax({ url:src, type: 'get', dataType: 'text', success: function(data) {
	 * 
	 * data = data.replace("entity_manager/entity_manager.nocache.js",
	 * chrome.extension.getURL("entity_manager/entity_manager.nocache.js"))
	 * 
	 * var div_cntnr = document.createElement("DIV"); div_cntnr.id =
	 * "wishlist_frame_cntnr"; div_cntnr.style.position = "fixed";
	 * div_cntnr.style.top = "10px"; div_cntnr.style.left = "100px";
	 * div_cntnr.style.zIndex = "99998"; div_cntnr.style.backgroundColor =
	 * "white"; div_cntnr.style.boxShadow = "0 0 8px black";
	 * 
	 * 
	 * var div_header = document.createElement("DIV"); div_header.onmousedown =
	 * function () {dragwindow.dragMe("wishlist_frame_cntnr")}
	 * div_header.onmouseup = function() {dragwindow.letMe()};
	 * div_header.style.height = "32px";
	 * div_header.style.backgroundColor="#444444";
	 * 
	 * 
	 * 
	 * div_cntnr.appendChild(div_header);
	 * 
	 * 
	 * var frame = document.createElement("IFRAME"); frame.src = src;
	 * div_cntnr.appendChild(frame); document.body.appendChild(div_cntnr);
	 * //document.body.appendChild(frame);
	 * 
	 * frame.id ="wishlist_frame"; frame.style.border="none";
	 * frame.style.width="550px"; frame.style.height="550px"; var code =
	 * document.getElementById("wishlist_frame");
	 * 
	 * code.contentDocument.documentElement.innerHTML = data
	 * code.contentDocument.open(); code.contentDocument.write(data);
	 * code.contentDocument.close();
	 * 
	 * 
	 * //newScrpt.id = id; //newScrpt.type="text/html";
	 * //newScrpt.type="text/x-jQuery-tmpl"; //var textNode =
	 * document.createTextNode(data); //newScrpt.appendChild(textNode);
	 * //newScrpt.src = src; //newScrpt.src = src;
	 * //document.getElementsByTagName("BODY")[0].appendChild(frame); }, error:
	 * function(data){ }});
	 */
}