var APP_STORAGE_PREFIX = "com.ds.wishlist";
var ITEM_PREFIX = "wishitem";
var newItemCount = 0;

chrome.extension.onMessage.addListener(
	function(request, sender, sendResponse) {
		if (request.messageType == 'openLink'){		
			chrome.tabs.create({'url': request.link});
			sendResponse(true);
		}
		if (request.messageType == 'resetNewItem'){		
			newItemCount = 0;
			setBadget();
			sendResponse(true);
		}
		return true;
	});

chrome.contextMenus.removeAll(function() {
	chrome.contextMenus.create({
		'title' : 'Добавить в список моих желаний',
		'id' : 'add_to_my_wishList',
		'contexts' : [ 'image' ],
		'onclick' : function(info, tab) {
			console.log(info, tab);

			var free_id = "";

			var date = new Date();
			var res = "" + date.getTime();
			while (localStorage[APP_STORAGE_PREFIX + "." + ITEM_PREFIX + "_"
					+ res] != null) {
				res = "" + date.getTime();
			}
			free_id = res;

			var id = APP_STORAGE_PREFIX + "." + ITEM_PREFIX + "_" + free_id
			
			var data = {
				"id":free_id,
				"description":"",
				"picture_url":info.srcUrl,
				"notes":"",
				"price":"0.0",
				"reason":"",
				"where":"",
				"where_url":info.pageUrl
			};
				
			/*
				"{\"id\":\"" + free_id + "\"," 
					+ "\"description\":\"\","
					+ "\"picture_url\":\"" + info.srcUrl + "\","
					+ "\"notes\":\"\"," 
					+ "\"price\":\"0.0\","
					+ "\"reason\":\"\"," 
					+ "\"where\":\"\","
					+ "\"where_url\":\"" + info.pageUrl + "\",";
			// + "\"}";
			*/

			// localStorage[APP_STORAGE_PREFIX + "." + ITEM_PREFIX + "_" +
			// free_id] = s;

			chrome.tabs.sendRequest(tab.id, "grabImage", function(data_url) {				
				//data = data + "\"picture_data\":\"" + data_url + "\"}"
				if (typeof(data_url) == "string"){
					data.picture_data = data_url;
					localStorage[id] = JSON.stringify(data);								
					newItemCount++;
					setBadget();
				}
				//chrome.tabs.create({url : "wishlist.html"}); 
			});
		}
	});
})


function setBadget(){
	chrome.browserAction.setBadgeText(
		{
			"text":(newItemCount!=0)?""+newItemCount:""
		}
	);
}
